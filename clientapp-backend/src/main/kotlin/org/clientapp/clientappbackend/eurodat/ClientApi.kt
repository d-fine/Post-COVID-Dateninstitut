package org.clientapp.clientappbackend.eurodat

import org.eurodat.api.ClientResourceApi
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ClientApi(
    @Autowired private val clientResourceApi: ClientResourceApi,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun getClientId() {
        clientResourceApi
            .apiV1ClientsClientmappingsGet()
            .map { it.clientId }
            .forEach { clientId -> logger.info("This is the $clientId") }
    }
}
