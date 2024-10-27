package com.lastbrand.recipe.service

import com.lastbrand.recipe.domain.RecipeDTO
import com.lastbrand.recipe.model.Recipe
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecipeWorkflowService(
    private val recipeService: RecipeService,
    private val recipeProductService: RecipeProductService
) {

    @Transactional
    fun createRecipeWithProducts(recipeDTO: RecipeDTO): Recipe {
        val createdRecipe = recipeService.createRecipe(recipeDTO)
        recipeProductService.addRecipeProducts(createdRecipe.id!!, recipeDTO.productIds)
        return createdRecipe
    }
}
