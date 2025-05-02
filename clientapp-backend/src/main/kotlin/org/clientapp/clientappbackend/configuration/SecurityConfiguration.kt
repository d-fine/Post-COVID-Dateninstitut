package org.clientapp.clientappbackend.configuration

import org.clientapp.clientappbackend.keycloak.KeycloakJwtRoleConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Component
@ConfigurationProperties(prefix = "spring.web.cors")
class CorsProperties {
  var allowedOrigins: List<String> = emptyList()
  var allowedMethods: List<String> = emptyList()
  var allowedHeaders: List<String> = emptyList()
  var allowCredentials: Boolean = false
}

@Bean
fun corsConfigurationSource(corsProperties: CorsProperties): CorsConfigurationSource {
  val configuration = CorsConfiguration()
  configuration.allowedOrigins = corsProperties.allowedOrigins
  configuration.allowedMethods = corsProperties.allowedMethods
  configuration.allowCredentials = corsProperties.allowCredentials
  configuration.allowedHeaders = corsProperties.allowedHeaders
  val source = UrlBasedCorsConfigurationSource()
  source.registerCorsConfiguration("/**", configuration)
  return source
}

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(
    private val keycloakJwtRoleConverter: KeycloakJwtRoleConverter,
    @Autowired var corsProperties: CorsProperties
) {
  @Bean
  @Throws(Exception::class)
  fun filterChain(http: HttpSecurity): SecurityFilterChain {

    val converter = JwtAuthenticationConverter()
    converter.setJwtGrantedAuthoritiesConverter(this.keycloakJwtRoleConverter)
    http {
      cors { configurationSource = corsConfigurationSource(corsProperties) }
      csrf { disable() }
      sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
      authorizeHttpRequests { authorize(anyRequest, authenticated) }
      oauth2ResourceServer { jwt { jwtAuthenticationConverter = converter } }
    }
    return http.build()
  }
}
