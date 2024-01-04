package io.ryandeford.samples.webservicesample.controllers.rest

import io.ryandeford.samples.webservicesample.models.Product
import io.ryandeford.samples.webservicesample.repositories.ProductRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductRestControllerClassTests @Autowired constructor(
    private val restTemplate: TestRestTemplate,
    private val productRepository: ProductRepository,
) {

    @BeforeEach
    fun clearDatabase() {
        productRepository.deleteAll()
    }

    @Test
    fun `Assert create product status code, content, and headers`() {
        val product = Product(
            name = "Test Create Product Name",
            description = "Test Create Product Description",
            price = 1.00,
        )

        assertThat(productRepository.findAll()).isEmpty()

        val entity = restTemplate.exchange<Product>(
            url = "/api/products",
            method = HttpMethod.POST,
            requestEntity = HttpEntity(product)
        )

        assertThat(productRepository.findAll()).hasSize(1)

        assertThat(entity.statusCode).isEqualTo(HttpStatus.CREATED)

        assertThat(entity.body).isInstanceOf(Product::class.java)
        val productData = entity.body!!
        assertThat(productData.id).isInstanceOf(UUID::class.java)
        assertThat(productData.name).isEqualTo(product.name)
        assertThat(productData.description).isEqualTo(product.description)
        assertThat(productData.price).isEqualTo(product.price)

        assertThat(entity.headers.location.toString()).isEqualTo("/api/products/${productData.id}")

        val fetchedProduct = productRepository.findByIdOrNull(productData.id!!)
        assertThat(productData.id).isEqualTo(fetchedProduct?.id)
        assertThat(productData.name).isEqualTo(fetchedProduct?.name)
        assertThat(productData.description).isEqualTo(fetchedProduct?.description)
        assertThat(productData.price).isEqualTo(fetchedProduct?.price)
    }

    @Test
    fun `Assert get all products status code and content`() {
        val product1 = Product(
            name = "Test Product Name 1",
            description = "Test Product Description 1",
            price = 1.00,
        )
        val product2 = Product(
            name = "Test Product Name 2",
            description = "Test Product Description 2",
            price = 2.00,
        )
        val product3 = Product(
            name = "Test Product Name 3",
            description = "Test Product Description 3",
            price = 3.00,
        )

        val products = listOf(product1, product2, product3)

        assertThat(productRepository.findAll()).isEmpty()

        products.forEach { product ->
            productRepository.save(product)
        }

        assertThat(productRepository.findAll()).hasSize(3)

        val entity = restTemplate.exchange<Iterable<Product>>(
            url = "/api/products",
            method = HttpMethod.GET,
        )

        assertThat(productRepository.findAll()).hasSize(3)

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)

        assertThat(entity.body).isInstanceOf(Iterable::class.java)
        val productListData = entity.body!!

        assertThat(productListData).hasSize(3)

        var productData1 = productListData.elementAt(0)
        var productData2 = productListData.elementAt(1)
        var productData3 = productListData.elementAt(2)

        assertThat(productData1.id).isInstanceOf(UUID::class.java)
        assertThat(productData1.id).isEqualTo(product1.id)
        assertThat(productData1.name).isEqualTo(product1.name)
        assertThat(productData1.description).isEqualTo(product1.description)
        assertThat(productData1.price).isEqualTo(product1.price)

        val fetchedProduct1 = productRepository.findByIdOrNull(productData1.id!!)
        assertThat(productData1.id).isEqualTo(fetchedProduct1?.id)
        assertThat(productData1.name).isEqualTo(fetchedProduct1?.name)
        assertThat(productData1.description).isEqualTo(fetchedProduct1?.description)
        assertThat(productData1.price).isEqualTo(fetchedProduct1?.price)

        assertThat(productData2.id).isInstanceOf(UUID::class.java)
        assertThat(productData2.id).isEqualTo(product2.id)
        assertThat(productData2.name).isEqualTo(product2.name)
        assertThat(productData2.description).isEqualTo(product2.description)
        assertThat(productData2.price).isEqualTo(product2.price)

        val fetchedProduct2 = productRepository.findByIdOrNull(productData2.id!!)
        assertThat(productData2.id).isEqualTo(fetchedProduct2?.id)
        assertThat(productData2.name).isEqualTo(fetchedProduct2?.name)
        assertThat(productData2.description).isEqualTo(fetchedProduct2?.description)
        assertThat(productData2.price).isEqualTo(fetchedProduct2?.price)

        assertThat(productData3.id).isInstanceOf(UUID::class.java)
        assertThat(productData3.id).isEqualTo(product3.id)
        assertThat(productData3.name).isEqualTo(product3.name)
        assertThat(productData3.description).isEqualTo(product3.description)
        assertThat(productData3.price).isEqualTo(product3.price)

        val fetchedProduct3 = productRepository.findByIdOrNull(productData3.id!!)
        assertThat(productData3.id).isEqualTo(fetchedProduct3?.id)
        assertThat(productData3.name).isEqualTo(fetchedProduct3?.name)
        assertThat(productData3.description).isEqualTo(fetchedProduct3?.description)
        assertThat(productData3.price).isEqualTo(fetchedProduct3?.price)
    }

    @Test
    fun `Assert get product status code and content`() {
        val product = Product(
            name = "Test Get Product Name",
            description = "Test Get Product Description",
            price = 1.00,
        )

        assertThat(productRepository.findAll()).isEmpty()
        productRepository.save(product)
        assertThat(productRepository.findAll()).hasSize(1)

        val entity = restTemplate.exchange<Product>(
            url = "/api/products/${product.id}",
            method = HttpMethod.GET,
        )

        assertThat(productRepository.findAll()).hasSize(1)

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)

        assertThat(entity.body).isInstanceOf(Product::class.java)
        val productData = entity.body!!
        assertThat(productData.id).isInstanceOf(UUID::class.java)
        assertThat(productData.id).isEqualTo(product.id)
        assertThat(productData.name).isEqualTo(product.name)
        assertThat(productData.description).isEqualTo(product.description)
        assertThat(productData.price).isEqualTo(product.price)

        val fetchedProduct = productRepository.findByIdOrNull(productData.id!!)
        assertThat(productData.id).isEqualTo(fetchedProduct?.id)
        assertThat(productData.name).isEqualTo(fetchedProduct?.name)
        assertThat(productData.description).isEqualTo(fetchedProduct?.description)
        assertThat(productData.price).isEqualTo(fetchedProduct?.price)
    }

    @Test
    fun `Assert update product status code and content`() {
        val product = Product(
            name = "Test Update Product Name",
            description = "Test Update Product Description",
            price = 1.00,
        )

        assertThat(productRepository.findAll()).isEmpty()
        productRepository.save(product)
        assertThat(productRepository.findAll()).hasSize(1)

        val productUpdated = Product(
            name = "Test Update Product Name Updated",
            description = "Test Update Product Description Updated",
            price = 1.11,
        )

        val entity = restTemplate.exchange<Product>(
            url = "/api/products/${product.id}",
            method = HttpMethod.PUT,
            requestEntity = HttpEntity(productUpdated)
        )

        assertThat(productRepository.findAll()).hasSize(1)

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)

        assertThat(entity.body).isInstanceOf(Product::class.java)
        val productData = entity.body!!
        assertThat(productData.id).isInstanceOf(UUID::class.java)
        assertThat(productData.id).isEqualTo(product.id)
        assertThat(productData.name).isEqualTo(productUpdated.name)
        assertThat(productData.description).isEqualTo(productUpdated.description)
        assertThat(productData.price).isEqualTo(productUpdated.price)

        val fetchedProduct = productRepository.findByIdOrNull(productData.id!!)
        assertThat(productData.id).isEqualTo(fetchedProduct?.id)
        assertThat(productData.name).isEqualTo(fetchedProduct?.name)
        assertThat(productData.description).isEqualTo(fetchedProduct?.description)
        assertThat(productData.price).isEqualTo(fetchedProduct?.price)
    }

    @Test
    fun `Assert delete product status code and content`() {
        val product = Product(
            name = "Test Delete Product Name",
            description = "Test Delete Product Description",
            price = 1.00,
        )

        assertThat(productRepository.findAll()).isEmpty()
        productRepository.save(product)
        assertThat(productRepository.findAll()).hasSize(1)

        val entity = restTemplate.exchange<Product>(
            url = "/api/products/${product.id}",
            method = HttpMethod.DELETE
        )

        assertThat(productRepository.findAll()).hasSize(0)

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)

        assertThat(entity.body).isInstanceOf(Product::class.java)
        val productData = entity.body!!
        assertThat(productData.id).isInstanceOf(UUID::class.java)
        assertThat(productData.id).isEqualTo(product.id)
        assertThat(productData.name).isEqualTo(product.name)
        assertThat(productData.description).isEqualTo(product.description)
        assertThat(productData.price).isEqualTo(product.price)

        val fetchedProduct = productRepository.findByIdOrNull(productData.id!!)
        assertThat(fetchedProduct).isNull()
    }
}