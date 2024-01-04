package io.ryandeford.samples.webservicesample.repositories

import io.ryandeford.samples.webservicesample.models.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class ProductRepositoryTests @Autowired constructor(
    private val entityManager: TestEntityManager,
    private val productRepository: ProductRepository
) {

    @Test
    fun `When findByIdOrNull then return Product`() {
        val product = Product(
            name = "Test Product 1",
            description = "Test Description 1",
            price = 1.00,
        )

        entityManager.persist(product)
        entityManager.flush()

        val found = productRepository.findByIdOrNull(product.id!!)
        assertThat(found).isEqualTo(product)
    }
}