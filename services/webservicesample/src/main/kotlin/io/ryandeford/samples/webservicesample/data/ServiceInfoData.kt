package io.ryandeford.samples.webservicesample.data

import java.time.Instant

data class ServiceInfoData (
    val name: String = "Sample Web Service Application",
    val description: String = "This is a sample web service application implemented by Ryan DeFord",
    val date: Instant = Instant.now(),
)
