package io.ryandeford.samples.webservicesample.services

import io.ryandeford.samples.webservicesample.models.Product
import io.ryandeford.samples.webservicesample.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService @Autowired constructor(
    private val productRepository: ProductRepository
) {

    fun createProduct(product: Product): Product {
        productRepository.save(product)
        return product
    }

    fun getProducts(): Iterable<Product> {
        val products = productRepository.findAll()
        return products
    }

    fun getProductById(id: UUID): Product {
        val product = productRepository.findByIdOrNull(id) ?: throw Exception("Unable to find product with provided ID: '$id'")
        return product
    }

    fun updateProduct(product: Product): Product {
        productRepository.save(product)
        return product
    }

    fun deleteProductById(id: UUID): Product {
        val product = getProductById(id)
        productRepository.deleteById(id)
        return product
    }
}
