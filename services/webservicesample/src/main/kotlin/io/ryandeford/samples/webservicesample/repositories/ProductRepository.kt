package io.ryandeford.samples.webservicesample.repositories

import io.ryandeford.samples.webservicesample.models.Product
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ProductRepository : CrudRepository<Product, UUID>
