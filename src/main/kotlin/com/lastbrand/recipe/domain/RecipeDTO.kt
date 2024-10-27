package com.lastbrand.recipe.domain

import java.time.LocalDateTime
import java.math.BigDecimal

data class RecipeDTO(
    val name: String,
    val description: String,
    val productIds: List<Int> = emptyList(),
    val priceInCents: Int, // can be derived from products price, for simplicity adding it here.
    val instructions: String,
    val prepTimeMinutes: Int,
    val cookTimeMinutes: Int,
    val totalTimeMinutes: Int,
    val difficultyLevel: String,
    val createdOn: LocalDateTime? = LocalDateTime.now(),
    val createdBy: String,
    val isActive: Boolean = true,
    val lastUpdated: LocalDateTime? = LocalDateTime.now(),
    val cuisineType: String,
    val caloriesPerServing: Int,
    val tags: String?,
    val imageUrl: String?,
    val videoUrl: String?,
    val rating: BigDecimal? = BigDecimal.ZERO,
    val reviewCount: Int = 0,
    val products: List<ProductDTO>? = null
)

data class RecipeProductsDTO(
    val recipeId: Int,
    val recipeName: String,
    val recipeDescription: String,
    val recipeCreatedBy: String,
    val recipeInstructions: String,
    val recipePrepTimeMinutes: Int,
    val recipeCookTimeMinutes: Int,
    val recipeTotalTimeMinutes: Int,
    val recipeDifficultyLevel: String,
    val recipeCuisineType: String,
    val recipeCaloriesPerServing: Int,
    val recipeTags: String?,
    val recipeImageUrl: String?,
    val recipeVideoUrl: String?,
    val recipeRating: BigDecimal? = BigDecimal.ZERO,
    val recipeReviewCount: Int = 0,
    val productId: Int,
    val productName: String,
    val productDescription: String,
    val priceInCents: Int = 0,
    val stockQuantity: Int = 0,
    val category: String,
    val subcategory: String,
    val brand: String,
    val weightInGrams: BigDecimal,
    val dimensions: String,
    val imageUrl: String
)

data class RecipeResponse (
    val recipes: List<RecipeDTO> = emptyList(),
    val totalPages: Int = 0,
    val currentPage: Int = 0,
    val totalRecipes: Int = 0
)

