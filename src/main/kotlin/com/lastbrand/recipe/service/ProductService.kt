package com.lastbrand.recipe.service

import com.lastbrand.recipe.model.Product
import com.lastbrand.recipe.domain.ProductDTO
import com.lastbrand.recipe.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    @Transactional
    fun createProduct(productDTO: ProductDTO): Product {
        val product = Product(
            name = productDTO.name,
            description = productDTO.description,
            priceInCents = productDTO.priceInCents,
            stockQuantity = productDTO.stockQuantity,
            category = productDTO.category,
            subcategory = productDTO.subcategory,
            brand = productDTO.brand,
            weightInGrams = productDTO.weightInGrams,
            dimensions = productDTO.dimensions,
            imageUrl = productDTO.imageUrl
        )
        return productRepository.save(product)
    }

}