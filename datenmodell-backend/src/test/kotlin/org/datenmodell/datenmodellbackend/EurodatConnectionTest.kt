package org.datenmodell.datenmodellbackend

import org.eurodat.eurodatcontroller.openApiClient.api.ClientResourceApi
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    properties = [
        "spring.datasource.url=jdbc:postgresql://localhost:5432/postgres",
        "spring.datasource.username=postgres",
        "spring.datasource.password=postgres",
    ],
)
class EurodatConnectionTest(
    @Autowired private val clientResourceApi: ClientResourceApi,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Test
    fun smoketestGetRandomClientId() {
        val clientId: String? =
            clientResourceApi
                .apiV1ClientsClientmappingsGet()
                .map { it.clientId }
                .first()

        logger.info("Client ID: $clientId")
    }
}
