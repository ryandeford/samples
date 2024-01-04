package io.ryandeford.samples.webservicesample.controllers.rest

import io.ryandeford.samples.webservicesample.data.ServiceInfoData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.time.Instant

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServiceInfoRestControllerTests @Autowired constructor(
    private val restTemplate: TestRestTemplate
) {
    @Test
    fun `Assert GET service info status code and content`() {
        val start = Instant.now()
        val entity = restTemplate.exchange<ServiceInfoData>(
            url = "/api/info",
            method = HttpMethod.GET,
        )

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)

        assertThat(entity.body).isNotNull
        val serviceInfo = entity.body!!
        assertThat(serviceInfo.name).isEqualTo("Sample Web Service Application")
        assertThat(serviceInfo.description).isEqualTo("This is a sample web service application implemented by Ryan DeFord")
        assertThat(serviceInfo.date).isAfter(start)
    }
}