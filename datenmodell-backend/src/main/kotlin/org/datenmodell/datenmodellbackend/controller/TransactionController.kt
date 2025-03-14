package org.datenmodell.datenmodellbackend.controller

import db.migration.TransactionStatus
import org.datenmodell.datenmodellbackend.configuration.AppProperties
import org.datenmodell.datenmodellbackend.service.db.DataAnalysisTableService
import org.datenmodell.datenmodellbackend.service.db.TransactionTableService
import org.datenmodell.datenmodellbackend.service.eurodat.EurodatTransactionInputService
import org.datenmodell.datenmodellbackend.service.eurodat.EurodatTransactionManagementService
import org.datenmodell.datenmodellbackend.service.eurodat.EurodatTransactionOutputService
import org.openapitools.api.TransactionApi
import org.openapitools.model.TransactionResult
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class TransactionController(
    @Autowired var appProperties: AppProperties,
    private val eurodatTransactionInputService: EurodatTransactionInputService,
    private val eurodatTransactionManagementService: EurodatTransactionManagementService,
    private val eurodatTransactionOutputService: EurodatTransactionOutputService,
    private val dataAnalysisTableService: DataAnalysisTableService,
    private val transactionTableService: TransactionTableService,
) : TransactionApi {
    private val logger = LoggerFactory.getLogger(javaClass)

    @PreAuthorize("hasRole('ADMIN')")
    override fun postTransaction(
        datasetId: UUID,
        dataAnalysisId: UUID,
    ): ResponseEntity<TransactionResult> {
        val eurodatClientId = appProperties.eurodatClientId
        val eurodatWorkflowName = generateTechnicalId()
        val eurodatOutputTable = "data"

        val dataAnalysisMetaDataEntity = dataAnalysisTableService.getValidDataAnalysisMetaData(dataAnalysisId)
        val eurodatAppName = dataAnalysisMetaDataEntity.dataAnalysisEntity.appName
        val imageLocation = dataAnalysisMetaDataEntity.imageLocation

        val eurodatImageId = eurodatTransactionInputService.register(eurodatAppName, imageLocation)
        val eurodatTransactionId =
            eurodatTransactionManagementService.startTransaction(
                eurodatAppName,
                eurodatImageId,
                eurodatWorkflowName,
            )

        val transactionId =
            transactionTableService.createTransactionMetaData(
                dataAnalysisId,
                TransactionStatus.STARTED,
            )

        val outputTableRecordList = eurodatTransactionOutputService.getData(eurodatClientId, eurodatOutputTable, eurodatTransactionId)

        eurodatTransactionManagementService.deleteAll(eurodatAppName, eurodatImageId, eurodatWorkflowName, eurodatTransactionId)
        transactionTableService.updateTransactionMetaData(
            transactionId,
            TransactionStatus.SUCCEEDED,
        )

        return ResponseEntity.ok(
            TransactionResult(
                datasetId = datasetId,
                csvStr = outputTableRecordList[0],
            ),
        )
    }

    private fun generateTechnicalId(): String {
        val charPool: List<Char> = ('a'..'z') + ('0'..'9')
        return "postcovidwf" + List(10) { charPool.random() }.joinToString("")
    }
}
