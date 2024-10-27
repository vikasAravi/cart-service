package com.lastbrand.recipe.service

import com.lastbrand.recipe.domain.AddRecipeRequest
import com.lastbrand.recipe.domain.CartDTO
import com.lastbrand.recipe.model.CartItem
import com.lastbrand.recipe.repository.CartItemRepository
import com.lastbrand.recipe.repository.CartRepository
import com.lastbrand.recipe.repository.RecipeRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
@Transactional
class CartService(
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository,
    private val recipeRepository: RecipeRepository
) {
    fun addRecipeToCart(cartId: Int, request: AddRecipeRequest): CartDTO {
        val cart = cartRepository.findById(cartId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find cart: $cartId")
        }
        val recipe = recipeRepository.findById(request.recipeId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found for cart: $cartId for recipeId: ${request.recipeId}")
        }
        val cartItemWithRecipeId = cartItemRepository.findByCartIdAndRecipeId(cartId, request.recipeId)
        if (cartItemWithRecipeId != null) {
            cartItemWithRecipeId.quantity = request.quantity // assumes the request always accepts the fresh value
            cartItemRepository.save(cartItemWithRecipeId)
            cart.totalInCents += recipe.priceInCents?.times(request.quantity) ?: 0
        } else {
            val cartItem = CartItem(
                cartId = cart.id,
                recipeId = recipe.id,
                quantity = request.quantity,
                type = "RECIPE"
            )
            cartItemRepository.save(cartItem)
            cart.totalInCents += recipe.priceInCents?.times(request.quantity) ?: 0
        }
        cartRepository.save(cart)
        return getCartById(cartId)
    }

    fun getCartById(cartId: Int): CartDTO {
        val cart = cartRepository.findById(cartId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find cart: $cartId")
        }
        val cartItems = cartRepository.findCartItemsByCartId(cart.id!!)
        val items = cartItems.mapNotNull {
            if (it.cartItemType != null) it.toCartItem() else null
        }
        return CartDTO(
            id = cartId,
            items = items
        )
    }

    fun removeRecipeFromCart(cartId: Int, recipeId: Int): CartDTO {
        val cart = cartRepository.findById(cartId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find cart: $cartId")
        }
        val cartItem = cartItemRepository.findByCartIdAndRecipeId(cartId, recipeId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found for cart: $cartId for recipeId: $recipeId")
        cartItemRepository.delete(cartItem)
        val recipe = recipeRepository.findById(cartItem.recipeId!!).orElseThrow {
            // THIS CASE WON'T HAPPEN
            ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found for cart: $cartId for recipeId: $recipeId")
        }
        cart.totalInCents -= (recipe?.priceInCents ?: 0) * cartItem.quantity
        cartRepository.save(cart)
        return getCartById(cartId)
    }

}