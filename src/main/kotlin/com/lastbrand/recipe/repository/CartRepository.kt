package com.lastbrand.recipe.repository

import com.lastbrand.recipe.domain.CartItemsDTO
import com.lastbrand.recipe.model.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CartRepository: JpaRepository<Cart, Int> {
    @Query("""
        SELECT 
            new com.lastbrand.recipe.domain.CartItemsDTO(
                c.id, c.totalInCents, ci.quantity, ci.type,
                r.id, r.name, r.description, r.priceInCents, r.createdBy, r.instructions,
                r.prepTimeMinutes, r.cookTimeMinutes, r.totalTimeMinutes, r.difficultyLevel, r.cuisineType,
                r.caloriesPerServing, r.tags, r.imageUrl, r.videoUrl, r.rating, r.reviewCount,
                p.id, p.name, p.description, p.priceInCents, p.stockQuantity,
                p.category, p.subcategory, p.brand,
                p.weightInGrams, p.dimensions, p.imageUrl
        )
        FROM Cart c
        LEFT JOIN CartItem ci
        LEFT JOIN Recipe r ON r.id = ci.recipeId
        LEFT JOIN Product p ON p.id = ci.productId
        WHERE c.id = :cartId
    """)
    fun findCartItemsByCartId(@Param("cartId") cartId: Int): List<CartItemsDTO>
}