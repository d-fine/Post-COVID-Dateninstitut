package org.datenmodell.datenmodellbackend;

import org.eurodat.eurodatcontroller.openApiClient.api.ClientResourceApi;
import org.eurodat.eurodatcontroller.openApiClient.model.ClientSelectorMapping;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class EurodatConnectionJavaTest {

  @Autowired ClientResourceApi clientResourceApi;

  private static final Logger logger = LoggerFactory.getLogger(EurodatConnectionTest.class);

  @Test
  @Tag("eurodat")
  void smoketestGetRandomClientId() {
    String clientId =
        clientResourceApi.apiV1ClientsClientmappingsGet().stream()
            .map(ClientSelectorMapping::getClientId)
            .findFirst()
            .orElse(null);
    logger.info(() -> "Client ID: " + clientId);
  }
}
