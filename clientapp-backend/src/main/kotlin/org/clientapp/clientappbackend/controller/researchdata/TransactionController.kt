package org.clientapp.clientappbackend.controller.researchdata

import db.migration.TransactionStatus
import java.util.UUID
import org.clientapp.clientappbackend.service.db.ResearchDataService
import org.clientapp.clientappbackend.service.db.TransactionTableService
import org.clientapp.clientappbackend.service.eurodat.EurodatDataManagementService
import org.openapitools.api.TransactionApi
import org.openapitools.model.EntityInformation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionController(
    @Autowired private val researchDataService: ResearchDataService,
    @Autowired private val eurodatDataManagementService: EurodatDataManagementService,
    @Autowired private val transactionTableService: TransactionTableService
) : TransactionApi {

  override fun uploadTransactionResearchData(
      clientId: String,
      transactionId: String,
      tableName: String,
      researchDataId: UUID
  ): ResponseEntity<EntityInformation> {
    val hubResearchDataEntity = researchDataService.getHubResearchDataEntity(researchDataId)
    val byteData = researchDataService.getResearchDataSet(researchDataId).file

    val insertResult =
        eurodatDataManagementService.postData(
            tableName = tableName,
            clientId = clientId,
            transactionId = transactionId,
            file = byteData,
        )
    transactionTableService.createTransactionMetaData(
        researchDataId = researchDataId,
        userId = hubResearchDataEntity?.userId,
        status = TransactionStatus.SUCCEEDED)

    return ResponseEntity.ok(EntityInformation(researchDataId, insertResult.result))
  }
}
