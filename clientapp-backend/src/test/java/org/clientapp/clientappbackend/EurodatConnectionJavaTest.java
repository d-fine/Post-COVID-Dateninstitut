package org.clientapp.clientappbackend;

import org.eurodat.api.ClientResourceApi;
import org.eurodat.model.ClientSelectorMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:postgresql://localhost:5433/postgres",
    "spring.datasource.username=postgres",
    "spring.datasource.password=postgres"})
class EurodatConnectionJavaTest {

  @Autowired
  ClientResourceApi clientResourceApi;

  private static final Logger logger = LoggerFactory.getLogger(EurodatConnectionTest.class);

  @Test
  @Tag("eurodat")
  void smoketestGetRandomClientId() {
    String clientId = clientResourceApi.apiV1ClientsClientmappingsGet().stream()
            .map(ClientSelectorMapping::getClientId).findFirst().orElse(null);
    Assertions.assertNotNull(clientId);
    Assertions.assertFalse(clientId.isEmpty());
    logger.info(() -> "Client ID: " + clientId);
  }

}