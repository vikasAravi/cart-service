package com.lastbrand.recipe.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "recipes")
data class Recipe(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val name: String = "",
    val description: String? = null,

    @Column(name = "price_in_cents")
    val priceInCents: Int? = null,

    @Column(columnDefinition = "TEXT")
    val instructions: String = "",

    @Column(name = "prep_time_minutes")
    val prepTimeMinutes: Int? = null,

    @Column(name = "cook_time_minutes")
    val cookTimeMinutes: Int? = null,

    @Column(name = "total_time_minutes")
    val totalTimeMinutes: Int? = null,

    @Column(name = "difficulty_level")
    val difficultyLevel: String? = null, // Should be validated as 'easy', 'medium', 'hard'

    @Column(name = "created_on")
    val createdOn: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_by")
    val createdBy: String? = null,

    @Column(name = "is_active")
    val isActive: Boolean = true,

    @Column(name = "last_updated")
    val lastUpdated: LocalDateTime = LocalDateTime.now(),

    @Column(name = "cuisine_type")
    val cuisineType: String? = null,

    @Column(name = "calories_per_serving")
    val caloriesPerServing: Int? = null,

    @Column(columnDefinition = "JSON")
    val tags: String? = null, // Store as JSON string

    @Column(name = "image_url")
    val imageUrl: String? = null,

    @Column(name = "video_url")
    val videoUrl: String? = null,

    val rating: BigDecimal? = null,

    @Column(name = "review_count")
    val reviewCount: Int = 0
)
