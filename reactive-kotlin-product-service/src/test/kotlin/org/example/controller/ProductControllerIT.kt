package org.example.controller



import org.example.BaseIT
import org.example.dto.ProductDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductControllerIT : BaseIT() {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun shouldReturnProductById() {
        //when
        val result = restTemplate.getForEntity("/product/1", ProductDto::class.java);
        //then
        val expectedProd = ProductDto(1, "test", 1.2)
        assertEquals(expectedProd, result.body)
    }

    @Test
    fun shouldReturnProductList() {
        //when
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity(null, headers)
        val result = restTemplate.exchange("/product/list", HttpMethod.GET, request,
                object : ParameterizedTypeReference<List<ProductDto>>() {})
        val list = result.body
        //then
        assertTrue(list!!.size > 1)
    }
}