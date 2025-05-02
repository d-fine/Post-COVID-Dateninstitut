package org.clientapp.clientappbackend.controller.user

import org.clientapp.clientappbackend.service.db.UserManagementService
import org.openapitools.api.UserApi
import org.openapitools.model.EntityInformation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
        userManagementService
            .createUser(
                userName = username,
                firstName = firstName ?: "",
                surname = surname ?: "",
            )
            .toResponse())
  }
}
