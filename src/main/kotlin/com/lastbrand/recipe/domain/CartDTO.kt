package com.lastbrand.recipe.domain

import java.math.BigDecimal


data class AddRecipeRequest(
    val recipeId: Int,
    val quantity: Int
)

data class CartItem(
    val recipe: RecipeDTO? = null,
    val product: ProductDTO? = null
)

data class CartDTO(
    val id: Int,
    val items: List<CartItem> = emptyList()
)

data class CartItemsDTO(
    val cartId: Int ?= 0,
    val cartTotalInCents: Int ?= 0,
    val cartItemQuantity: Int ?= 0,
    val cartItemType: String ?= null,
    val recipeId: Int? = null,
    val recipeName: String? = null,
    val recipeDescription: String? = null,
    val recipePriceInCents: Int? = null,
    val recipeCreatedBy: String? = null,
    val recipeInstructions: String? = null,
    val recipePrepTimeMinutes: Int? = null,
    val recipeCookTimeMinutes: Int? = null,
    val recipeTotalTimeMinutes: Int? = null,
    val recipeDifficultyLevel: String? = null,
    val recipeCuisineType: String? = null,
    val recipeCaloriesPerServing: Int? = null,
    val recipeTags: String? = null,
    val recipeImageUrl: String? = null,
    val recipeVideoUrl: String? = null,
    val recipeRating: BigDecimal? = BigDecimal.ZERO,
    val recipeReviewCount: Int? = null,
    val productId: Int? = null,
    val productName: String? = null,
    val productDescription: String? = null,
    val priceInCents: Int? = null,
    val stockQuantity: Int? = null,
    val category: String? = null,
    val subcategory: String? = null,
    val brand: String? = null,
    val weightInGrams: BigDecimal? = null,
    val dimensions: String ? = null,
    val imageUrl: String? = null
) {
    fun toCartItem(): CartItem {
        if(cartItemType == "RECIPE") {
            val recipe = RecipeDTO(
                name = recipeName!!,
                description = recipeDescription!!,
                priceInCents = recipePriceInCents ?: 10,
                createdBy = recipeCreatedBy!!,
                instructions = recipeInstructions!!,
                prepTimeMinutes = recipePrepTimeMinutes!!,
                cookTimeMinutes = recipeCookTimeMinutes!!,
                totalTimeMinutes = recipeTotalTimeMinutes!!,
                difficultyLevel = recipeDifficultyLevel!!,
                cuisineType = recipeCuisineType!!,
                caloriesPerServing = recipeCaloriesPerServing!!,
                tags = recipeTags,
                imageUrl = recipeImageUrl,
                videoUrl = recipeVideoUrl,
                rating = recipeRating,
                reviewCount = recipeReviewCount!!,
            )
            return CartItem(recipe = recipe)
        }
        val product =  ProductDTO(
            name = productName!!,
            description = productDescription!!,
            priceInCents = priceInCents!!,
            stockQuantity = stockQuantity!!,
            category = category!!,
            subcategory = subcategory!!,
            brand = brand,
            weightInGrams = weightInGrams!!,
            dimensions = dimensions!!,
            imageUrl = imageUrl!!
        )
        return CartItem(product = product)
    }
}
