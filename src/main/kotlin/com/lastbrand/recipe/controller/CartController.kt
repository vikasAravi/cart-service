package com.lastbrand.recipe.controller

import com.lastbrand.recipe.domain.AddRecipeRequest
import com.lastbrand.recipe.service.CartService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/carts")
class CartController(
    private val cartService: CartService
) {

    @PostMapping("/{id}/add_recipe")
    fun addRecipeToCart(
        @PathVariable id: Int,
        @RequestBody request: AddRecipeRequest
    ) = cartService.addRecipeToCart(id, request)

    @GetMapping("/{id}")
    fun getCartById(
        @PathVariable id: Int,
    ) = cartService.getCartById(id)

    @DeleteMapping("/{cartId}/recipes/{recipeId}")
    fun removeRecipeFromCart(
        @PathVariable cartId: Int,
        @PathVariable recipeId: Int
    ) = cartService.removeRecipeFromCart(cartId, recipeId)
}