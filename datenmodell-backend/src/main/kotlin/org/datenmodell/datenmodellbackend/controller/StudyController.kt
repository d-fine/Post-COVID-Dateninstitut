package org.datenmodell.datenmodellbackend.controller

import org.datenmodell.datenmodellbackend.eurodat.ClientApi
import org.datenmodell.datenmodellbackend.model.Study
import org.datenmodell.datenmodellbackend.service.db.StudyTableService
import org.openapitools.api.StudyApi
import org.openapitools.model.StudyInformation
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController

@RestController
class StudyController(
    private val studyTableService: StudyTableService,
    private val clientApi: ClientApi,
) : StudyApi {
    private val logger = LoggerFactory.getLogger(javaClass)

    @PreAuthorize("hasRole('ADMIN')")
    override fun getAll(): ResponseEntity<List<StudyInformation>> {
        // Just a smoketest: authenticate & get sth back from EuroDat
        clientApi.getClientId()

        return ResponseEntity.ok(
            studyTableService
                .getAll()
                .map { it.toResponse() },
        )
    }

    private fun Study.toResponse(): StudyInformation =
        StudyInformation(
            id = this.id.toString(),
            title = this.title,
            content = this.content,
        )
}
