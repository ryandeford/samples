package io.ryandeford.samples.webservicesample.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class HomeController {

    @GetMapping
    fun home(model: Model): String {
        model["message"] = "Welcome to Ryan's Sample Web Service Application"
        return "home"
    }
}