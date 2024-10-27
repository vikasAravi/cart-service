package com.lastbrand.recipe.controller

import com.lastbrand.recipe.domain.RecipeDTO
import com.lastbrand.recipe.service.RecipeService
import com.lastbrand.recipe.service.RecipeWorkflowService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/recipes")
class RecipeController(
    private val recipeWorkflowService: RecipeWorkflowService,
    private val recipeService: RecipeService
) {
    @PostMapping
    fun createRecipe(@RequestBody recipeDTO: RecipeDTO) =
        recipeWorkflowService.createRecipeWithProducts(recipeDTO)

    @GetMapping
    fun getRecipes(
        @RequestParam(required = true) pageNumber: Int = 0,
        @RequestParam(required = true) size: Int = 10
    ) = recipeService.getAllRecipesWithProducts(pageNumber, size)
}
