package org.clientapp.clientappbackend.configuration

import org.clientapp.clientappbackend.keycloak.KeycloakJwtRoleConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain

import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

import org.slf4j.LoggerFactory

 @Bean
 fun corsConfigurationSource(): CorsConfigurationSource {
     val configuration = CorsConfiguration()
     configuration.allowedOrigins = listOf("http://localhost:5173") // TODO: adjust for prod, workaround for CORS
     configuration.allowedMethods = listOf("GET", "POST")
     configuration.allowCredentials = true
     configuration.allowedHeaders = listOf("*")
     val source = UrlBasedCorsConfigurationSource()
     source.registerCorsConfiguration("/**", configuration)
     return source
 }

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
       cors { configurationSource = corsConfigurationSource() }
      csrf { disable() }
      sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
      authorizeHttpRequests { authorize(anyRequest, authenticated) }
      oauth2ResourceServer { jwt { jwtAuthenticationConverter = converter } }
    }
    return http.build()
  }
}