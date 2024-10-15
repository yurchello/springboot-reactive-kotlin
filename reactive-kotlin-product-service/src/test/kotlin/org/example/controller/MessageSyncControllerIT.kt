package org.example.controller

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.example.dto.event.ProductSendEvent
import org.example.service.MessagingClient
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.time.Duration
import java.util.*
import kotlin.collections.set
import kotlin.test.assertEquals

@SpringBootTest
@TestPropertySource(
        properties = [
            "spring.kafka.consumer.auto-offset-reset=earliest"]
)
@Testcontainers
class MessageSyncControllerIT {
    companion object {

        private const val SEND_PRODUCT_TOPIC_NAME = "sync-product-event"

        @Container
        private val kafka = KafkaContainer(
                DockerImageName.parse("confluentinc/cp-kafka:7.6.1")
        )

        private val postgres: PostgreSQLContainer<*> = PostgreSQLContainer(DockerImageName.parse("postgres:14.7-alpine"))
                .apply {
                    this.withDatabaseName("reactive_prod").withUsername("postgres").withPassword("postgres")
                }

        fun r2dbcUrl(): String {
            return "r2dbc:postgresql://${postgres.host}:${postgres.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT)}/${postgres.databaseName}"
        }

        fun jdbcUrl(): String {
            return "jdbc:postgresql://${postgres.host}:${postgres.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT)}/${postgres.databaseName}"
        }

        @JvmStatic
        @DynamicPropertySource
        fun overrideProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.kafka.bootstrap-servers") { kafka.bootstrapServers }

            registry.add("spring.r2dbc.url", Companion::r2dbcUrl)
            registry.add("spring.r2dbc.username", postgres::getUsername)
            registry.add("spring.r2dbc.password", postgres::getPassword)
            //stub liquibase
            registry.add("spring.liquibase.url", Companion::jdbcUrl)
            registry.add("spring.liquibase.user", postgres::getUsername)
            registry.add("spring.liquibase.password", postgres::getPassword)
        }

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            postgres.start()
        }
    }

    @Autowired
    lateinit var messagingClient: MessagingClient

    @Test
    fun shouldSyncMessage() {
        //given
        var productSendEvent = ProductSendEvent(1L, "name", 3.3, "1")
        //when
        messagingClient.sendProduct(productSendEvent)
        //then
        val consumer: KafkaConsumer<String, ProductSendEvent> = configureConsumer()
        consumer.subscribe(listOf(SEND_PRODUCT_TOPIC_NAME))
        val records: ConsumerRecords<String, ProductSendEvent> = consumer.poll(Duration.ofMillis(10000L))
        consumer.close()

        assertEquals(1, records.count())
        assertEquals(productSendEvent, records.iterator().next().value())
    }

    private fun <K, V> configureConsumer(): KafkaConsumer<K, V> {
        val properties = Properties()
        properties[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafka.bootstrapServers
        properties[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        properties[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        properties[JsonDeserializer.TRUSTED_PACKAGES] = "*"
        properties[ConsumerConfig.GROUP_ID_CONFIG] = "group-java-test"
        properties[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        properties[JsonDeserializer.VALUE_DEFAULT_TYPE] = ProductSendEvent::class.java
        return KafkaConsumer(properties)
    }
}