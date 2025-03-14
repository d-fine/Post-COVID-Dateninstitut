package org.clientapp.clientappbackend.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt

import org.clientapp.clientappbackend.repository.HubUserRepository
import org.clientapp.clientappbackend.repository.SatUserMetaDataRepository
import org.clientapp.clientappbackend.model.HubUser
import org.clientapp.clientappbackend.entity.HubUserEntity
import org.clientapp.clientappbackend.entity.SatUserMetaDataEntity

import java.util.UUID;
import java.time.LocalDateTime

@Service
class UserManagementService(
    @Autowired private val dataUserRepository: HubUserRepository,
    @Autowired private val dataUserMetadataRepository: SatUserMetaDataRepository,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun getIdForUsername(username: String?): UUID? = dataUserRepository.findIdByUsername(username)?.id

    fun getKeycloakId(): String {
        val auth = SecurityContextHolder.getContext().authentication
        val principal = auth.principal as Jwt
        return principal.getClaim<String>("sub") ?: throw IllegalArgumentException("User ID not found")
    }

    fun createUser(
        userName: String,
        firstName: String,
        surname: String
    ): HubUser {
        if (dataUserRepository.findIdByUsername(userName) != null) {
            throw Exception("username is already taken")
        }
        val user = dataUserRepository.save(
            HubUserEntity(
                username = userName,
            )
        )
        dataUserMetadataRepository.save(
            SatUserMetaDataEntity(
                hubUserEntity = user,
                firstName = firstName,
                surname = surname,
                validFrom = LocalDateTime.now(),
                validTo = null
            )
        )
        return user.toModel()
    }
}
