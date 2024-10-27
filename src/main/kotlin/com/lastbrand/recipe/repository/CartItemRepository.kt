package com.lastbrand.recipe.repository

import com.lastbrand.recipe.model.CartItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : JpaRepository<CartItem, Int> {
    fun findByCartIdAndRecipeId(cartId: Int, recipeId: Int): CartItem?
}