package io.ryandeford.samples.webservicesample.controllers.rest

import io.ryandeford.samples.webservicesample.models.Product
import io.ryandeford.samples.webservicesample.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/api/products")
class ProductRestController @Autowired constructor(
    private val productService: ProductService
) {

    @PostMapping(
        consumes = [
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE
        ]
    )
    fun createProduct(@RequestBody product: Product): ResponseEntity<Product> {
        val created = productService.createProduct(product)
        return ResponseEntity.created(URI.create("/api/products/${created.id}")).body(created)
    }

    @GetMapping
    fun getAllProducts(): Iterable<Product> {
        val all = productService.getProducts()
        return all
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: UUID): Product {
        val product = productService.getProductById(id)
        return product
    }

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: UUID, @RequestBody product: Product): Product {
        product.id = id
        productService.updateProduct(product)
        return product
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: UUID): Product {
        val product = productService.deleteProductById(id)
        return product
    }
}