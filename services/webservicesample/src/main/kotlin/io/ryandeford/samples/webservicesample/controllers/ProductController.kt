package io.ryandeford.samples.webservicesample.controllers

import io.ryandeford.samples.webservicesample.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/products")
class ProductController @Autowired constructor(
    private val productService: ProductService
) {

    @GetMapping
    fun home(model: Model): String {
        val products = productService.getProducts()
        model["products"] = products
        return "products"
    }
}