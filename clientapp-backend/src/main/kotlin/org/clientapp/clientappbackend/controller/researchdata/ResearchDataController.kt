package org.clientapp.clientappbackend.controller.researchdata

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity

import org.clientapp.clientappbackend.service.ResearchDataService
import org.clientapp.clientappbackend.service.UserManagementService

import org.openapitools.model.EntityInformation
import org.openapitools.model.ResearchDataInformation
import org.openapitools.api.ResearchDataApi

import org.slf4j.LoggerFactory
import java.util.UUID

@RestController
class ResearchDataController(
    @Autowired private val researchDataService: ResearchDataService,
    @Autowired private val userManagementService: UserManagementService,
) : ResearchDataApi {

    @PreAuthorize("hasRole('ADMIN')")
    override fun getResearchDataSet(@RequestParam researchDataId: UUID): ResponseEntity<Resource> {
        return ResponseEntity.ok(
            ByteArrayResource(researchDataService.getResearchDataSet(researchDataId).file)
        )
    }

    @PreAuthorize("hasRole('ADMIN')")
    override fun getAllResearchData(): ResponseEntity<List<ResearchDataInformation>> {
        return ResponseEntity.ok(
            researchDataService.getAllResearchData()
        )
    }

    @PreAuthorize("hasRole('ADMIN')")
    override fun uploadResearchData(
        @RequestParam fileName: String,
        @RequestParam dataConsumerUsername: String?,
        @RequestParam description: String?,
        @RequestPart file: MultipartFile?
    ): ResponseEntity<EntityInformation> {
        val logger = LoggerFactory.getLogger(javaClass)
        logger.debug("Entering controller method")
        val keycloakId = userManagementService.getKeycloakId()
        val dataConsumerId = userManagementService.getIdForUsername(dataConsumerUsername)
        return ResponseEntity.ok(
            researchDataService.uploadResearchData(
                keycloakId,
                dataConsumerId,
                fileName,
                description ?: "",
                file!!.bytes,
            )
        )
    }
}
