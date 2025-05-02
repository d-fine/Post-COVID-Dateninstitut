package org.datenmodell.datenmodellbackend.controller

import db.migration.TransactionStatus
import java.util.UUID
import org.datenmodell.datenmodellbackend.configuration.AppProperties
import org.datenmodell.datenmodellbackend.model.ActiveTransaction
import org.datenmodell.datenmodellbackend.service.db.DataAnalysisTableService
import org.datenmodell.datenmodellbackend.service.db.TransactionTableService
import org.datenmodell.datenmodellbackend.service.eurodat.EurodatTransactionInputService
import org.datenmodell.datenmodellbackend.service.eurodat.EurodatTransactionManagementService
import org.datenmodell.datenmodellbackend.service.eurodat.EurodatTransactionOutputService
import org.openapitools.api.TransactionApi
import org.openapitools.model.DataAnalysis
import org.openapitools.model.InitializedTransaction
import org.openapitools.model.TransactionResult
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController

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
      dataAnalysisId: UUID,
  ): ResponseEntity<TransactionResult> {
    val eurodatClientId = appProperties.eurodatClientId
    val eurodatWorkflowName = generateTechnicalId()
    val eurodatOutputTable = "data"

    val dataAnalysisMetaDataEntity =
        dataAnalysisTableService.getValidDataAnalysisMetaData(dataAnalysisId)
    val eurodatAppName = dataAnalysisMetaDataEntity.dataAnalysisEntity.appName.replace(" ", "")
    val imageLocation = dataAnalysisMetaDataEntity.imageLocation

    val eurodatImageId =
        eurodatTransactionInputService.register(
            eurodatAppName, imageLocation, dataAnalysisMetaDataEntity.ddlStatement)
    val eurodatTransactionId =
        eurodatTransactionManagementService.startTransaction(
            eurodatAppName,
            eurodatImageId,
            eurodatWorkflowName,
        )
    eurodatTransactionManagementService.startWorkflow(eurodatTransactionId, eurodatWorkflowName)

    val activeTransaction =
        ActiveTransaction(
            eurodatClientId = eurodatClientId,
            eurodatWorkflowName = eurodatWorkflowName,
            eurodatImageId = eurodatImageId,
            eurodatTransactionId = eurodatTransactionId)

    val transactionId =
        transactionTableService.createTransactionMetaData(
            dataAnalysisId, TransactionStatus.STARTED, activeTransaction)

    val outputTableRecordList =
        eurodatTransactionOutputService.getData(
            eurodatClientId, eurodatOutputTable, eurodatTransactionId)

    eurodatTransactionManagementService.deleteAll(
        eurodatAppName, eurodatImageId, eurodatWorkflowName, eurodatTransactionId)
    transactionTableService.updateTransactionMetaData(
        transactionId,
        TransactionStatus.SUCCEEDED,
    )

    return ResponseEntity.ok(
        TransactionResult(
            transactionId = transactionId,
            csvStr = outputTableRecordList[0],
        ),
    )
  }

  override fun setupTransactionInfrastructure(
      dataAnalysisId: UUID
  ): ResponseEntity<InitializedTransaction> {
    val eurodatClientId = appProperties.eurodatClientId
    val eurodatWorkflowName = generateTechnicalId()

    val dataAnalysisMetaDataEntity =
        dataAnalysisTableService.getValidDataAnalysisMetaData(dataAnalysisId)
    val eurodatAppName = dataAnalysisMetaDataEntity.dataAnalysisEntity.appName.replace(" ", "")
    val imageLocation = dataAnalysisMetaDataEntity.imageLocation

    logger.info("register")
    val eurodatImageId =
        eurodatTransactionInputService.register(
            eurodatAppName, imageLocation, dataAnalysisMetaDataEntity.ddlStatement)
    logger.info("starting")
    val eurodatTransactionId =
        eurodatTransactionManagementService.startTransaction(
            eurodatAppName,
            eurodatImageId,
            eurodatWorkflowName,
        )

    val activeTransaction =
        ActiveTransaction(
            eurodatClientId = eurodatClientId,
            eurodatWorkflowName = eurodatWorkflowName,
            eurodatImageId = eurodatImageId,
            eurodatTransactionId = eurodatTransactionId)

    val transactionId =
        transactionTableService.createTransactionMetaData(
            dataAnalysisId, TransactionStatus.STARTED, activeTransaction)
    return ResponseEntity.ok(
        InitializedTransaction(
            eurodatClientId = eurodatClientId,
            eurodatTransactionId = eurodatTransactionId,
            transactionId = transactionId))
  }

  @Suppress("TooGenericExceptionCaught")
  override fun processTransactionData(transactionId: UUID): ResponseEntity<TransactionResult> {
    val transaction = transactionTableService.getValidTransactionMetaData(transactionId)
    val eurodatOutputTable = "data"
    var outputTableRecordList: List<String>

    try {
      eurodatTransactionManagementService.startWorkflow(
          transaction.eurodatTransactionId, transaction.eurodatWorkflowName)

      outputTableRecordList =
          eurodatTransactionOutputService.getData(
              transaction.eurodatClientId, eurodatOutputTable, transaction.eurodatTransactionId)
    } catch (e: Exception) {
      transactionTableService.updateTransactionMetaData(
          transactionId,
          TransactionStatus.FAILED,
      )
      throw e
    }

    val dataAnalysisMetaDataEntity =
        dataAnalysisTableService.getValidDataAnalysisMetaData(transaction.dataAnalysisEntity.id!!)
    val eurodatAppName = dataAnalysisMetaDataEntity.dataAnalysisEntity.appName.replace(" ", "")

    eurodatTransactionManagementService.deleteAll(
        eurodatAppName,
        transaction.eurodatImageId,
        transaction.eurodatWorkflowName,
        transaction.eurodatTransactionId)
    transactionTableService.updateTransactionMetaData(
        transactionId,
        TransactionStatus.SUCCEEDED,
    )

    return ResponseEntity.ok(
        TransactionResult(
            transactionId = transactionId,
            csvStr = outputTableRecordList[0],
        ),
    )
  }

  override fun getAllDataAnalysis(): ResponseEntity<List<DataAnalysis>> {
    return ResponseEntity.ok(dataAnalysisTableService.getAllValidDataAnalysisApps())
  }

  @Suppress("MagicNumber")
  private fun generateTechnicalId(): String {
    val charPool: List<Char> = ('a'..'z') + ('0'..'9')
    return "postcovidwf" + List(10) { charPool.random() }.joinToString("")
  }
}
