package org.clientapp.clientappbackend;

import java.util.List;
import org.eurodat.eurodattransaction.openApiClient.api.ClientResourceApi;
import org.eurodat.eurodattransaction.openApiClient.model.ClientIdParticipantMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class EurodatConnectionJavaSmokeTest {

  @Autowired
  ClientResourceApi clientResourceApi;

  private static final Logger logger = LoggerFactory.getLogger(EurodatConnectionSmokeTest.class);

  @Test
  @Tag("eurodat-smoketest")
  void smoketestJavaGetRandomClientId() {

    List<ClientIdParticipantMapping> clientIdParticipantMappings =
        clientResourceApi.getClientParticipantMapping();
    String clientId =
        clientIdParticipantMappings.stream()
            .findFirst()
            .map(ClientIdParticipantMapping::getClientId)
            .get();
    Assertions.assertNotNull(clientId);
    Assertions.assertFalse(clientId.isEmpty());
  }
}
