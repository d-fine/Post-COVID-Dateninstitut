package org.datenmodell.datenmodellbackend;

import org.eurodat.eurodatcontroller.openApiClient.api.ClientResourceApi;
import org.eurodat.eurodatcontroller.openApiClient.model.ClientSelectorMapping;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:postgresql://localhost:5432/postgres",
    "spring.datasource.username=postgres",
    "spring.datasource.password=postgres"})
class EurodatConnectionTestJava {

  @Autowired
  ClientResourceApi clientResourceApi;

  private static final Logger logger = LoggerFactory.getLogger(EurodatConnectionTest.class);

  @Test
  void smoketestGetRandomClientId() {
    String clientId = clientResourceApi.apiV1ClientsClientmappingsGet().stream()
        .map(ClientSelectorMapping::getClientId).findFirst().orElse(null);
    logger.info(() -> "Client ID: " + clientId);
  }

}