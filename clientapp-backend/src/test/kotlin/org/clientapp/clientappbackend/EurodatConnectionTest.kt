package org.clientapp.clientappbackend

import org.eurodat.api.ClientResourceApi
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class EurodatConnectionTest(
    @Autowired private val clientResourceApi: ClientResourceApi,
) {
  private val logger = LoggerFactory.getLogger(javaClass)

  @Test
  @Tag("eurodat")
  fun smoketestGetRandomClientId() {
    val clientId: String? =
        clientResourceApi.apiV1ClientsClientmappingsGet().map { it.clientId }.first()

    logger.info("Client ID: $clientId")
  }
}
