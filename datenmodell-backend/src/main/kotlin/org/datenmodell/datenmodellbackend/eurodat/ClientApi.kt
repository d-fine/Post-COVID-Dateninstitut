package org.datenmodell.datenmodellbackend.eurodat

import org.eurodat.eurodattransaction.openApiClient.api.ClientResourceApi
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ClientApi(
    @Autowired private val clientResourceApi: ClientResourceApi,
) {
  private val logger = LoggerFactory.getLogger(javaClass)

  fun getClientId() {
    clientResourceApi.clientParticipantMapping
        .map { it.clientId }
        .forEach { clientId ->
          logger.info("Client entry in clientParticipantMappingList: $clientId")
        }
  }
}
