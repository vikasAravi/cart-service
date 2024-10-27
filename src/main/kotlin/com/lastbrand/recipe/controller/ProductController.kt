package com.lastbrand.recipe.controller

import com.lastbrand.recipe.domain.ProductDTO
import com.lastbrand.recipe.service.ProductService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService
) {
    @PostMapping
    fun createProduct(@RequestBody productDTO: ProductDTO) =
        productService.createProduct(productDTO)
}