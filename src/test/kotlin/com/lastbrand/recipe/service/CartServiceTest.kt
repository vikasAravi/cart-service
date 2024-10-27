package com.lastbrand.recipe.service

import com.lastbrand.BaseUnitTest
import com.lastbrand.recipe.domain.AddRecipeRequest
import com.lastbrand.recipe.domain.CartItemsDTO
import com.lastbrand.recipe.model.Cart
import com.lastbrand.recipe.model.CartItem
import com.lastbrand.recipe.model.Recipe
import com.lastbrand.recipe.repository.CartItemRepository
import com.lastbrand.recipe.repository.CartRepository
import com.lastbrand.recipe.repository.RecipeRepository
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import java.util.*

@ContextConfiguration(classes = [CartService::class])
class CartServiceTest: BaseUnitTest() {
    @MockBean
    private lateinit var cartRepository: CartRepository
    @MockBean
    private lateinit var cartItemRepository: CartItemRepository
    @MockBean
    private lateinit var recipeRepository: RecipeRepository
    @Autowired
    private lateinit var cartService: CartService

    @Test
    fun addRecipe_CartNotFoundCase(){
        val cartId = 1
        Mockito.`when`(cartRepository.findById(cartId)).thenReturn(Optional.empty())
        val thrownException = assertThrows<ResponseStatusException> {
            cartService.addRecipeToCart(
                cartId,
                AddRecipeRequest(recipeId = 1, quantity = 1)
            )
        }
        assertEquals("Could not find cart: $cartId", thrownException.reason)
    }

    @Test
    fun addRecipe_RecipeNotFoundCase() {
        val cartId = 1
        val recipeId = 1
        val cart = Cart(id = cartId, totalInCents = 0)
        Mockito.`when`(cartRepository.findById(cartId)).thenReturn(Optional.of(cart))
        Mockito.`when`(recipeRepository.findById(recipeId)).thenReturn(Optional.empty())
        val thrownException = assertThrows<ResponseStatusException> {
            cartService.addRecipeToCart(
                cartId,
                AddRecipeRequest(recipeId = recipeId, quantity = 1)
            )
        }
        assertEquals(HttpStatus.NOT_FOUND, thrownException.statusCode)
        assertEquals("Recipe not found for cart: $cartId for recipeId: $recipeId", thrownException.reason)
    }

    @Test
    fun addRecipe_RecipeAlreadyExistsInCart() {
        val cartId = 1
        val recipeId = 1
        val cartItemId = 1
        val cart = Cart(id = cartId, totalInCents = 0)
        val recipe = Recipe(id = recipeId, priceInCents = 100)
        val cartItem = CartItem(id = cartItemId, quantity = 1, cartId = cartId, recipeId = recipeId, type = "RECIPE")
        val cartItemsList = listOf(
            CartItemsDTO(
                cartId = 1,
                cartTotalInCents = 500,
                cartItemQuantity = 2,
                cartItemType = "RECIPE",
                recipeId = 101,
                recipeName = "Spaghetti Carbonara",
                recipeDescription = "Classic Italian pasta dish",
                recipePriceInCents = 250,
                recipeCreatedBy = "Chef Mario",
                recipeInstructions = "Cook pasta, mix with sauce, serve hot.",
                recipePrepTimeMinutes = 10,
                recipeCookTimeMinutes = 20,
                recipeTotalTimeMinutes = 30,
                recipeDifficultyLevel = "Easy",
                recipeCuisineType = "Italian",
                recipeCaloriesPerServing = 400,
                recipeTags = "pasta, Italian, quick",
                recipeImageUrl = "http://example.com/spaghetti.jpg",
                recipeVideoUrl = "http://example.com/spaghetti-video.mp4",
                recipeRating = BigDecimal("4.5"),
                recipeReviewCount = 100
            ),
            CartItemsDTO(
                cartId = 1,
                cartTotalInCents = 500,
                cartItemQuantity = 1,
                cartItemType = "RECIPE",
                recipeId = 102,
                recipeName = "Chicken Curry",
                recipeDescription = "Spicy and flavorful chicken dish",
                recipePriceInCents = 300,
                recipeCreatedBy = "Chef Anjali",
                recipeInstructions = "Marinate chicken, cook with spices, serve with rice.",
                recipePrepTimeMinutes = 15,
                recipeCookTimeMinutes = 30,
                recipeTotalTimeMinutes = 45,
                recipeDifficultyLevel = "Medium",
                recipeCuisineType = "Indian",
                recipeCaloriesPerServing = 600,
                recipeTags = "chicken, curry, spicy",
                recipeImageUrl = "http://example.com/chicken-curry.jpg",
                recipeVideoUrl = "http://example.com/chicken-curry-video.mp4",
                recipeRating = BigDecimal("4.7"),
                recipeReviewCount = 150
            )
        )

        Mockito.`when`(cartRepository.findById(cartId)).thenReturn(Optional.of(cart))
        Mockito.`when`(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe))
        Mockito.`when`(cartItemRepository.findByCartIdAndRecipeId(cartId, recipeId)).thenReturn(cartItem)
        Mockito.`when`(cartItemRepository.save(any())).thenAnswer{ it.arguments[0] }
        Mockito.`when`(cartRepository.save(any())).thenAnswer { it.arguments[0] }
        Mockito.`when`(cartRepository.findCartItemsByCartId(cartId)).thenReturn(cartItemsList)

        val result = cartService.addRecipeToCart(cartId, AddRecipeRequest(recipeId = recipeId, quantity = 1))
        assertEquals(2, result.items.size)
    }

    @Test
    fun addRecipe_NewRecipe(){
        val cartId = 1
        val recipeId = 1
        val cart = Cart(id = cartId, totalInCents = 0)
        val recipe = Recipe(id = recipeId, priceInCents = 100)
        val cartItemsList = listOf(
            CartItemsDTO(
                cartId = 1,
                cartTotalInCents = 500,
                cartItemQuantity = 2,
                cartItemType = "RECIPE",
                recipeId = 101,
                recipeName = "Spaghetti Carbonara",
                recipeDescription = "Classic Italian pasta dish",
                recipePriceInCents = 250,
                recipeCreatedBy = "Chef Mario",
                recipeInstructions = "Cook pasta, mix with sauce, serve hot.",
                recipePrepTimeMinutes = 10,
                recipeCookTimeMinutes = 20,
                recipeTotalTimeMinutes = 30,
                recipeDifficultyLevel = "Easy",
                recipeCuisineType = "Italian",
                recipeCaloriesPerServing = 400,
                recipeTags = "pasta, Italian, quick",
                recipeImageUrl = "http://example.com/spaghetti.jpg",
                recipeVideoUrl = "http://example.com/spaghetti-video.mp4",
                recipeRating = BigDecimal("4.5"),
                recipeReviewCount = 100
            ),
            CartItemsDTO(
                cartId = 1,
                cartTotalInCents = 500,
                cartItemQuantity = 1,
                cartItemType = "RECIPE",
                recipeId = 102,
                recipeName = "Chicken Curry",
                recipeDescription = "Spicy and flavorful chicken dish",
                recipePriceInCents = 300,
                recipeCreatedBy = "Chef Anjali",
                recipeInstructions = "Marinate chicken, cook with spices, serve with rice.",
                recipePrepTimeMinutes = 15,
                recipeCookTimeMinutes = 30,
                recipeTotalTimeMinutes = 45,
                recipeDifficultyLevel = "Medium",
                recipeCuisineType = "Indian",
                recipeCaloriesPerServing = 600,
                recipeTags = "chicken, curry, spicy",
                recipeImageUrl = "http://example.com/chicken-curry.jpg",
                recipeVideoUrl = "http://example.com/chicken-curry-video.mp4",
                recipeRating = BigDecimal("4.7"),
                recipeReviewCount = 150
            )
        )

        Mockito.`when`(cartRepository.findById(cartId)).thenReturn(Optional.of(cart))
        Mockito.`when`(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe))
        Mockito.`when`(cartItemRepository.findByCartIdAndRecipeId(cartId, recipeId)).thenReturn(null)
        Mockito.`when`(cartItemRepository.save(any())).thenAnswer{ it.arguments[0] }
        Mockito.`when`(cartRepository.save(any())).thenAnswer { it.arguments[0] }
        Mockito.`when`(cartRepository.findCartItemsByCartId(cartId)).thenReturn(cartItemsList)

        val result = cartService.addRecipeToCart(cartId, AddRecipeRequest(recipeId = recipeId, quantity = 1))
        assertEquals(2, result.items.size)
    }

    @Test
    fun getCartById_CartNotFound(){
        val cartId = 1
        Mockito.`when`(cartRepository.findById(cartId)).thenReturn(Optional.empty())
        val thrownException = assertThrows<ResponseStatusException> {
            cartService.getCartById(cartId)
        }
        assertEquals("Could not find cart: $cartId", thrownException.reason)
    }

    @Test
    fun getCartById_CartFound(){
        val cartId = 1
        val cart = Cart(id = cartId, totalInCents = 10)
        Mockito.`when`(cartRepository.findById(cartId)).thenReturn(Optional.of(cart))
        val cartItemsList = listOf(
            CartItemsDTO(
                cartId = 1,
                cartTotalInCents = 500,
                cartItemQuantity = 2,
                cartItemType = "RECIPE",
                recipeId = 101,
                recipeName = "Spaghetti Carbonara",
                recipeDescription = "Classic Italian pasta dish",
                recipePriceInCents = 250,
                recipeCreatedBy = "Chef Mario",
                recipeInstructions = "Cook pasta, mix with sauce, serve hot.",
                recipePrepTimeMinutes = 10,
                recipeCookTimeMinutes = 20,
                recipeTotalTimeMinutes = 30,
                recipeDifficultyLevel = "Easy",
                recipeCuisineType = "Italian",
                recipeCaloriesPerServing = 400,
                recipeTags = "pasta, Italian, quick",
                recipeImageUrl = "http://example.com/spaghetti.jpg",
                recipeVideoUrl = "http://example.com/spaghetti-video.mp4",
                recipeRating = BigDecimal("4.5"),
                recipeReviewCount = 100
            ),
            CartItemsDTO(
                cartId = 1,
                cartTotalInCents = 500,
                cartItemQuantity = 1,
                cartItemType = "RECIPE",
                recipeId = 102,
                recipeName = "Chicken Curry",
                recipeDescription = "Spicy and flavorful chicken dish",
                recipePriceInCents = 300,
                recipeCreatedBy = "Chef Anjali",
                recipeInstructions = "Marinate chicken, cook with spices, serve with rice.",
                recipePrepTimeMinutes = 15,
                recipeCookTimeMinutes = 30,
                recipeTotalTimeMinutes = 45,
                recipeDifficultyLevel = "Medium",
                recipeCuisineType = "Indian",
                recipeCaloriesPerServing = 600,
                recipeTags = "chicken, curry, spicy",
                recipeImageUrl = "http://example.com/chicken-curry.jpg",
                recipeVideoUrl = "http://example.com/chicken-curry-video.mp4",
                recipeRating = BigDecimal("4.7"),
                recipeReviewCount = 150
            )
        )
        Mockito.`when`(cartRepository.findCartItemsByCartId(cartId)).thenReturn(cartItemsList)
        val result = cartService.getCartById(cartId)
        assertEquals(2, result.items.size)
        assertEquals("Chicken Curry", result.items[1].recipe?.name)
    }

    @Test
    fun removeRecipeFromCart_CartNotFound(){
        val cartId = 1
        val recipeId = 1
        Mockito.`when`(cartRepository.findById(cartId)).thenReturn(Optional.empty())
        val thrownException = assertThrows<ResponseStatusException> {
            cartService.removeRecipeFromCart(cartId, recipeId)
        }
        assertEquals(HttpStatus.NOT_FOUND, thrownException.statusCode)
        assertEquals("Could not find cart: $cartId", thrownException.reason)
    }

    @Test
    fun removeRecipeFromCart_CartWithRecipeNotFound(){
        val cartId = 1
        val cart = Cart(id = cartId, totalInCents = 10)
        val recipeId = 1
        Mockito.`when`(cartRepository.findById(cartId)).thenReturn(Optional.of(cart))
        Mockito.`when`(cartItemRepository.findByCartIdAndRecipeId(cartId, recipeId)).thenReturn(null)
        val thrownException = assertThrows<ResponseStatusException> {
            cartService.removeRecipeFromCart(cartId, recipeId)
        }
        assertEquals(HttpStatus.NOT_FOUND, thrownException.statusCode)
        assertEquals("Recipe not found for cart: $cartId for recipeId: $recipeId", thrownException.reason)
    }

    @Test
    fun removeRecipeFromCart_SuccessfulDelete(){
        val cartId = 1
        val cart = Cart(id = cartId, totalInCents = 10)
        val recipeId = 1
        val recipe = Recipe(id = recipeId, priceInCents = 100)
        val cartItemId = 1
        val cartItem = CartItem(id = cartItemId, quantity = 1, cartId = cartId, recipeId = recipeId, type = "RECIPE")
        Mockito.`when`(cartRepository.findById(cartId)).thenReturn(Optional.of(cart))
        Mockito.`when`(cartItemRepository.findByCartIdAndRecipeId(cartId, recipeId)).thenReturn(cartItem)
        doNothing().`when`(cartItemRepository).delete(cartItem)
        Mockito.`when`(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe))
        val cartItemsList = listOf(
            CartItemsDTO(
                cartId = 1,
                cartTotalInCents = 500,
                cartItemQuantity = 2,
                cartItemType = "RECIPE",
                recipeId = 101,
                recipeName = "Spaghetti Carbonara",
                recipeDescription = "Classic Italian pasta dish",
                recipePriceInCents = 250,
                recipeCreatedBy = "Chef Mario",
                recipeInstructions = "Cook pasta, mix with sauce, serve hot.",
                recipePrepTimeMinutes = 10,
                recipeCookTimeMinutes = 20,
                recipeTotalTimeMinutes = 30,
                recipeDifficultyLevel = "Easy",
                recipeCuisineType = "Italian",
                recipeCaloriesPerServing = 400,
                recipeTags = "pasta, Italian, quick",
                recipeImageUrl = "http://example.com/spaghetti.jpg",
                recipeVideoUrl = "http://example.com/spaghetti-video.mp4",
                recipeRating = BigDecimal("4.5"),
                recipeReviewCount = 100
            ),
            CartItemsDTO(
                cartId = 1,
                cartTotalInCents = 500,
                cartItemQuantity = 1,
                cartItemType = "RECIPE",
                recipeId = 102,
                recipeName = "Chicken Curry",
                recipeDescription = "Spicy and flavorful chicken dish",
                recipePriceInCents = 300,
                recipeCreatedBy = "Chef Anjali",
                recipeInstructions = "Marinate chicken, cook with spices, serve with rice.",
                recipePrepTimeMinutes = 15,
                recipeCookTimeMinutes = 30,
                recipeTotalTimeMinutes = 45,
                recipeDifficultyLevel = "Medium",
                recipeCuisineType = "Indian",
                recipeCaloriesPerServing = 600,
                recipeTags = "chicken, curry, spicy",
                recipeImageUrl = "http://example.com/chicken-curry.jpg",
                recipeVideoUrl = "http://example.com/chicken-curry-video.mp4",
                recipeRating = BigDecimal("4.7"),
                recipeReviewCount = 150
            )
        )
        Mockito.`when`(cartRepository.findCartItemsByCartId(cartId)).thenReturn(cartItemsList)
        val result = cartService.removeRecipeFromCart(cartId, recipeId)
        assertEquals(2, result.items.size)
        assertEquals("Chicken Curry", result.items[1].recipe?.name)
    }
}