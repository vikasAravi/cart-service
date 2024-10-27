package com.lastbrand.recipe.domain

import java.math.BigDecimal

data class ProductDTO(
    val name: String,
    val description: String,
    val priceInCents: Int,
    val stockQuantity: Int,
    val category: String,
    val subcategory: String,
    val brand: String?,
    val weightInGrams: BigDecimal,
    val dimensions: String,
    val imageUrl: String
)
