package com.lastbrand.recipe.repository

import com.lastbrand.recipe.model.RecipeProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecipeProductRepository : JpaRepository<RecipeProduct, Int>