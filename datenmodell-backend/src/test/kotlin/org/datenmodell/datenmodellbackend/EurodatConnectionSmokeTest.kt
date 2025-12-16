package org.datenmodell.datenmodellbackend

import org.eurodat.eurodattransaction.openApiClient.api.ClientResourceApi
import org.eurodat.eurodattransaction.openApiClient.model.ClientIdParticipantMapping
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class EurodatConnectionSmokeTest(
    @Autowired private val clientResourceApi: ClientResourceApi,
) {
  @Test
  @Tag("eurodat-smoketest")
  fun smoketestGetRandomClientId() {
    val clientParticipantList: List<ClientIdParticipantMapping> =
        clientResourceApi.clientParticipantMapping

    val clientId: String = clientParticipantList.first().clientId
    Assertions.assertNotNull(clientId)
    Assertions.assertFalse(clientId.isEmpty())
  }
}
