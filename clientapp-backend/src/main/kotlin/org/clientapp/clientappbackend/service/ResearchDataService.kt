package org.clientapp.clientappbackend.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID;
import org.clientapp.clientappbackend.entity.HubResearchDataEntity
import org.clientapp.clientappbackend.entity.SatResearchDataAttributesEntity
import org.clientapp.clientappbackend.repository.HubResearchDataRepository
import org.clientapp.clientappbackend.repository.SatResearchDataAttributesRepository
import org.clientapp.clientappbackend.model.ResearchDataSet
import org.openapitools.model.EntityInformation
import org.openapitools.model.ResearchDataInformation
import org.clientapp.clientappbackend.entity.ApprovalStatus

@Service
class ResearchDataService(
    @Autowired private val hubResearchDataRepository: HubResearchDataRepository,
    @Autowired private val satResearchDataAttributesRepository: SatResearchDataAttributesRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun getResearchDataSet(researchDataId: UUID): ResearchDataSet {
        val datasets = satResearchDataAttributesRepository.findByResearchDataEntityAndValidToIsNull(
            HubResearchDataEntity(id = researchDataId)
        )
        if (datasets.size != 1) {
            throw Exception("Expecting exactly one valid match")
        }
        return ResearchDataSet(
            file = datasets.first().file,
            name = datasets.first().name,
        )
    }

    fun getAllResearchData(): List<ResearchDataInformation> {
        return satResearchDataAttributesRepository.findAllByValidToIsNull().map { it ->
            ResearchDataInformation(
                researchDataId = it.researchDataEntity.id,
                fileName = it.name,
                description = it.description
            )
        }
    }

    fun uploadResearchData(
        keycloakId: String,
        dataConsumerId: UUID?,
        fileName: String,
        description: String,
        file: ByteArray
    ): EntityInformation {
        val hubResearchData = hubResearchDataRepository.save(
            HubResearchDataEntity(
                userId = dataConsumerId
            )
        )
        satResearchDataAttributesRepository.save(
            SatResearchDataAttributesEntity(
                researchDataEntity = hubResearchData,
                name = fileName,
                description = description,
                file = file,
                validFrom = LocalDateTime.now(),
                validTo = null,
                modifyingUserId = UUID.fromString(keycloakId),
                status = ApprovalStatus.APPROVED.toString()
            )
        )
        return EntityInformation(id = hubResearchData.id, name = fileName)
    }
}
