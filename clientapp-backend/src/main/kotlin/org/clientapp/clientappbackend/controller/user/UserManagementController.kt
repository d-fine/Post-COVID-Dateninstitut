package org.clientapp.clientappbackend.controller.user

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.http.ResponseEntity
import org.springframework.beans.factory.annotation.Autowired

import org.clientapp.clientappbackend.service.UserManagementService

import org.openapitools.model.EntityInformation
import org.openapitools.api.UserApi


@RestController
class UserManagementController(
    @Autowired private val userManagementService: UserManagementService,
) : UserApi {

    @PreAuthorize("hasRole('ADMIN')")
    override fun createUser(
        @RequestParam username: String,
        @RequestParam firstName: String?,
        @RequestParam surname: String?
    ): ResponseEntity<EntityInformation> {
        return ResponseEntity.ok(
            userManagementService.createUser(
                userName = username,
                firstName = firstName ?: "",
                surname = surname ?: "",
            ).toResponse()
        )
    }
}