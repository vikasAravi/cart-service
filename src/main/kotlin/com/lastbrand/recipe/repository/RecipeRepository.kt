package com.lastbrand.recipe.repository

import com.lastbrand.recipe.domain.RecipeProductsDTO
import com.lastbrand.recipe.model.Recipe
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RecipeRepository : JpaRepository<Recipe, Int> {
    @Query("""
        SELECT new com.lastbrand.recipe.domain.RecipeProductsDTO(
            r.id, r.name, r.description, r.createdBy, r.instructions, r.prepTimeMinutes, 
            r.cookTimeMinutes, r.totalTimeMinutes, r.difficultyLevel, r.cuisineType, 
            r.caloriesPerServing, r.tags, r.imageUrl, r.videoUrl, r.rating, r.reviewCount,
            p.id, p.name, p.description, p.priceInCents, p.stockQuantity, 
            p.category, p.subcategory, p.brand, 
            p.weightInGrams, p.dimensions, p.imageUrl
        )
        FROM Recipe r
        LEFT JOIN RecipeProduct rp ON r.id = rp.recipeId
        LEFT JOIN Product p ON rp.productId = p.id
    """)
    fun findAllWithProducts(pageable: Pageable): List<RecipeProductsDTO>
}