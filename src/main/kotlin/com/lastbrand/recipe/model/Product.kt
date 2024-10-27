package com.lastbrand.recipe.model

import java.math.BigDecimal
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "products")
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val name: String = "",
    val description: String? = null,
    @Column(name = "price_in_cents")
    val priceInCents: Int = 0,
    @Column(name = "stock_quantity")
    val stockQuantity: Int = 0,
    val category: String? = null,
    val subcategory: String? = null,
    @Column(name = "is_active")
    val isActive: Boolean = true,
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val brand: String? = null,
    @Column(name = "weight_in_grams")
    val weightInGrams: BigDecimal? = null,
    val dimensions: String? = null,
    @Column(name = "image_url")
    val imageUrl: String? = null
)
