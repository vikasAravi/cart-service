package com.lastbrand.recipe.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "recipe_products")
data class RecipeProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "recipe_id", nullable = true)
    val recipeId: Int? = null,

    @Column(name = "product_id", nullable = true)
    val productId: Int? = null,

    @Column(name = "is_optional")
    val isOptional: Boolean = false,

    @Column(name = "is_active")
    val isActive: Boolean = true,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
