package org.example.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
open class WebClientConfiguration {
    @Bean
    open fun webClient() = WebClient.builder().baseUrl("http://localhost:8081").build()
}