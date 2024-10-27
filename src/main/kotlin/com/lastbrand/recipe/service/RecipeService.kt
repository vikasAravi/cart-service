package com.lastbrand.recipe.service

import com.lastbrand.recipe.domain.ProductDTO
import com.lastbrand.recipe.domain.RecipeDTO
import com.lastbrand.recipe.domain.RecipeResponse
import com.lastbrand.recipe.model.Recipe
import com.lastbrand.recipe.repository.RecipeRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RecipeService(
    private val recipeRepository: RecipeRepository
) {

    fun createRecipe(recipeDTO: RecipeDTO): Recipe {
        val recipe = Recipe(
            name = recipeDTO.name,
            description = recipeDTO.description,
            priceInCents = recipeDTO.priceInCents,
            instructions = recipeDTO.instructions,
            prepTimeMinutes = recipeDTO.prepTimeMinutes,
            cookTimeMinutes = recipeDTO.cookTimeMinutes,
            totalTimeMinutes = recipeDTO.totalTimeMinutes,
            difficultyLevel = recipeDTO.difficultyLevel,
            createdOn = recipeDTO.createdOn ?: LocalDateTime.now(),
            createdBy = recipeDTO.createdBy,
            isActive = recipeDTO.isActive,
            lastUpdated = recipeDTO.lastUpdated ?: LocalDateTime.now(),
            cuisineType = recipeDTO.cuisineType,
            caloriesPerServing = recipeDTO.caloriesPerServing,
            tags = recipeDTO.tags,
            imageUrl = recipeDTO.imageUrl,
            videoUrl = recipeDTO.videoUrl,
            rating = recipeDTO.rating,
            reviewCount = recipeDTO.reviewCount
        )
        return recipeRepository.save(recipe)
    }

    fun getAllRecipesWithProducts(pageNumber: Int, pageSize: Int): RecipeResponse {
        val pageable: Pageable = PageRequest.of(pageNumber, pageSize)
        val recipeWithProducts = recipeRepository.findAllWithProducts(pageable)
        val recipeMap = mutableMapOf<Int?, RecipeDTO>()
        for (item in recipeWithProducts) {
            if (!recipeMap.containsKey(item.recipeId)) {
                recipeMap[item.recipeId] = RecipeDTO(
                    name = item.recipeName,
                    description = item.recipeDescription,
                    priceInCents = item.priceInCents,
                    createdBy = item.recipeCreatedBy,
                    instructions = item.recipeInstructions,
                    prepTimeMinutes = item.recipePrepTimeMinutes,
                    cookTimeMinutes = item.recipeCookTimeMinutes,
                    totalTimeMinutes = item.recipeTotalTimeMinutes,
                    difficultyLevel = item.recipeDifficultyLevel,
                    cuisineType = item.recipeCuisineType,
                    caloriesPerServing = item.recipeCaloriesPerServing,
                    tags = item.recipeTags,
                    imageUrl = item.recipeImageUrl,
                    videoUrl = item.recipeVideoUrl,
                    rating = item.recipeRating,
                    reviewCount = item.recipeReviewCount,
                    products = mutableListOf()
                )
            }
            item.productId.let {
                (recipeMap[item.recipeId]?.products as MutableList).add(
                    ProductDTO(
                        name = item.productName,
                        description = item.productDescription,
                        priceInCents = item.priceInCents,
                        stockQuantity = item.stockQuantity,
                        category = item.category,
                        subcategory = item.subcategory,
                        brand = item.brand,
                        weightInGrams = item.weightInGrams,
                        dimensions = item.dimensions,
                        imageUrl = item.imageUrl
                    )
                )
            }
        }
        return RecipeResponse(
            recipes = recipeMap.values.toList(),
            totalPages = (recipeWithProducts.size / pageSize) + if (recipeWithProducts.size % pageSize > 0) 1 else 0,
            currentPage = pageNumber,
            totalRecipes = recipeWithProducts.size
        )
    }
}
