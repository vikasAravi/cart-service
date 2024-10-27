package com.lastbrand.recipe.service

import com.lastbrand.recipe.model.RecipeProduct
import com.lastbrand.recipe.repository.RecipeProductRepository
import org.springframework.stereotype.Service

@Service
class RecipeProductService(
    private val recipeProductRepository: RecipeProductRepository
) {
    fun addRecipeProducts(recipeId: Int, productIds: List<Int>) {
        val recipeProducts = productIds.mapIndexed { _, productId ->
            RecipeProduct(
                recipeId = recipeId,
                productId = productId
            )
        }
        recipeProductRepository.saveAll(recipeProducts)
    }
}
