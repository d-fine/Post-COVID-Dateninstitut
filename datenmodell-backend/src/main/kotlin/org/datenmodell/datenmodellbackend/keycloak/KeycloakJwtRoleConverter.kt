package org.datenmodell.datenmodellbackend.keycloak

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service

/**
 * Uses the Keycloak Token to extract the client roles. These act as input for the Spring Security
 * Framework.
 */
@Service
class KeycloakJwtRoleConverter : Converter<Jwt, Collection<GrantedAuthority>> {

  @Value("\${keycloak.client}") lateinit var client: String

  @Suppress("kotlin:S6530")
  override fun convert(jwt: Jwt): Collection<GrantedAuthority>? {
    val resourceAccess = jwt.getClaim<Map<String, Any>>("resource_access")
    val roles: List<String> =
        (resourceAccess?.get(client) as? Map<String, Any>)?.get("roles") as? List<String>
            ?: emptyList()
    return roles.map { role -> SimpleGrantedAuthority("ROLE_$role") }
  }
}
