package org.clientapp.clientappbackend.controller.researchdata

import java.util.UUID
import org.clientapp.clientappbackend.service.db.ResearchDataService
import org.clientapp.clientappbackend.service.db.UserManagementService
import org.openapitools.api.ResearchDataApi
import org.openapitools.model.EntityInformation
import org.openapitools.model.ResearchDataInformation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class ResearchDataController(
    @Autowired private val researchDataService: ResearchDataService,
    @Autowired private val userManagementService: UserManagementService,
) : ResearchDataApi {

  @PreAuthorize("hasRole('ADMIN')")
  override fun getResearchDataSet(@RequestParam researchDataId: UUID): ResponseEntity<Resource> {
    return ResponseEntity.ok(
        ByteArrayResource(researchDataService.getResearchDataSet(researchDataId).file))
  }

  @PreAuthorize("hasRole('ADMIN')")
  override fun getAllResearchData(): ResponseEntity<List<ResearchDataInformation>> {
    return ResponseEntity.ok(researchDataService.getAllResearchData())
  }

  @PreAuthorize("hasRole('ADMIN')")
  override fun uploadResearchData(
      @RequestParam fileName: String,
      @RequestParam dataConsumerUsername: String?,
      @RequestParam description: String?,
      @RequestPart file: MultipartFile?
  ): ResponseEntity<EntityInformation> {
    val keycloakId = userManagementService.getKeycloakId()
    val dataConsumerId = userManagementService.getIdForUsername(dataConsumerUsername)
    return ResponseEntity.ok(
        researchDataService.uploadResearchData(
            keycloakId,
            dataConsumerId,
            fileName,
            description ?: "",
            file!!.bytes,
        ))
  }
}
