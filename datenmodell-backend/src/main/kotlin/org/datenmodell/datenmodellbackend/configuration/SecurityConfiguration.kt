package org.datenmodell.datenmodellbackend.configuration

import org.datenmodell.datenmodellbackend.keycloak.KeycloakJwtRoleConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(private val keycloakJwtRoleConverter: KeycloakJwtRoleConverter) {
  @Bean
  @Throws(Exception::class)
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    val converter = JwtAuthenticationConverter()
    converter.setJwtGrantedAuthoritiesConverter(this.keycloakJwtRoleConverter)
    http {
      cors { disable() }
      csrf { disable() }
      sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
      authorizeHttpRequests { authorize(anyRequest, authenticated) }
      oauth2ResourceServer { jwt { jwtAuthenticationConverter = converter } }
    }
    return http.build()
  }
}
