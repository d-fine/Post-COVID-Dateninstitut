package org.datenmodell.datenmodellbackend.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AppProperties {
  @Value("\${image-repository.user-id}") lateinit var imageRepoUserId: String

  @Value("\${image-repository.password}") lateinit var imageRepoUserPassword: String

  @Value("\${spring.security.oauth2.client.registration.eurodat.client-id}")
  lateinit var eurodatClientId: String
}
