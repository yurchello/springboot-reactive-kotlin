package org.example

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ProductApp

fun main(args: Array<String>) {
    SpringApplication.run(ProductApp::class.java, *args)
}


