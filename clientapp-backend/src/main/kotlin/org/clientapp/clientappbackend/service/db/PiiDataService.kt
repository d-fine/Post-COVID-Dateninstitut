package org.clientapp.clientappbackend.service.db

import db.migration.ApprovalStatus
import java.time.LocalDateTime
import java.util.UUID
import org.clientapp.clientappbackend.entity.HubPiiDataEntity
import org.clientapp.clientappbackend.entity.SatPiiDataAttributesEntity
import org.clientapp.clientappbackend.entity.SatPiiDataBloomfilterEntity
import org.clientapp.clientappbackend.entity.SatPiiDataMetadataEntity
import org.clientapp.clientappbackend.repository.HubPiiDataRepository
import org.clientapp.clientappbackend.repository.SatPiiDataAttributesRepository
import org.clientapp.clientappbackend.repository.SatPiiDataBloomfilterRepository
import org.clientapp.clientappbackend.repository.SatPiiDataMetadataRepository
import org.openapitools.model.EntityInformation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PiiDataService(
    @Autowired private val hubPiiDataRepository: HubPiiDataRepository,
    @Autowired private val satPiiDataMetadataRepository: SatPiiDataMetadataRepository,
    @Autowired private val satPiiDataBloomfilterRepository: SatPiiDataBloomfilterRepository,
    @Autowired private val satPiiDataAttributesRepository: SatPiiDataAttributesRepository,
) {

  fun uploadPIIData(
      keycloakId: String,
      dataConsumerId: UUID?,
      fileName: String,
      description: String,
      data: Map<String, Map<String, String>>
  ): EntityInformation {

    val hubResearchData =
        hubPiiDataRepository.save(
            HubPiiDataEntity(userId = dataConsumerId),
        )
    satPiiDataMetadataRepository.save(
        SatPiiDataMetadataEntity(
            piiDataEntity = hubResearchData,
            name = fileName,
            description = description,
            validFrom = LocalDateTime.now(),
            validTo = null,
            modifyingUserId = UUID.fromString(keycloakId),
            status = ApprovalStatus.APPROVED.toString()),
    )
    for ((studyId, attributes) in data) {

      for ((attributeName, bloomFilter) in attributes) {
        val existingName =
            satPiiDataAttributesRepository.findByPiiDataEntityAndAttributeName(
                hubResearchData, attributeName)

        val attributeId = existingName?.attributeId ?: UUID.randomUUID()
        if (!satPiiDataAttributesRepository.existsByPiiDataEntityAndAttributeName(
            hubResearchData, attributeName)) {
          satPiiDataAttributesRepository.save(
              SatPiiDataAttributesEntity(
                  piiDataEntity = hubResearchData,
                  validFrom = LocalDateTime.now(),
                  validTo = null,
                  attributeId = attributeId,
                  attributeName = attributeName,
                  attributeType = "String"))
        }
        satPiiDataBloomfilterRepository.save(
            SatPiiDataBloomfilterEntity(
                piiDataEntity = hubResearchData,
                validFrom = LocalDateTime.now(),
                validTo = null,
                studyId = studyId,
                attributeId = attributeId,
                bloomfilterCode = bloomFilter))
      }
    }

    return EntityInformation(id = hubResearchData.id, name = fileName)
  }
}
