package com.lastbrand.recipe.model

import jakarta.persistence.*

@Entity
@Table(name = "cart_items")
data class CartItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "cart_id", nullable = false)
    var cartId: Int? = null,

    @Column(name = "product_id", nullable = true)
    var productId: Int? = null,

    @Column(name = "recipe_id", nullable = true)
    var recipeId: Int? = null,

    @Column(nullable = false)
    var quantity: Int = 1,

    @Column(nullable = false)
    var type: String = "RECIPE"
)
