package io.ryandeford.samples.webservicesample.controllers.rest

import io.ryandeford.samples.webservicesample.data.ServiceInfoData
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/info")
class ServiceInfoRestController {

    @GetMapping
    fun getInfo(): ServiceInfoData {
        return ServiceInfoData()
    }
}