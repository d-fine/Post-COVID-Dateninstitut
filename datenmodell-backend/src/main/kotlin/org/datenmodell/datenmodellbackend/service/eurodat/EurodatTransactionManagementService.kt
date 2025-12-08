package org.datenmodell.datenmodellbackend.service.eurodat

import org.datenmodell.datenmodellbackend.eurodat.AppApi
import org.datenmodell.datenmodellbackend.eurodat.ImageApi
import org.datenmodell.datenmodellbackend.eurodat.TransactionsApi
import org.datenmodell.datenmodellbackend.eurodat.WorkflowApi
import org.datenmodell.datenmodellbackend.eurodat.WorkflowRegistrationApi
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

const val MAX_NUMBER_OF_REQUEST_ATTEMPTS: Int = 60
const val SLEEP_INTERVAL: Long = 1000

@Service
class EurodatTransactionManagementService(
    private val transactionsApi: TransactionsApi,
    private val workflowRegistrationApi: WorkflowRegistrationApi,
    private val workflowApi: WorkflowApi,
    private val imageApi: ImageApi,
    private val appApi: AppApi,
) {
  private val logger = LoggerFactory.getLogger(javaClass)

  fun startTransaction(
      appName: String,
      imageId: String,
      workflowName: String,
  ): String {
    logger.debug(
        "Start the transaction with parameters: appName is $appName, imageId is " +
            "$imageId, workflowName is $workflowName",
    )
    val registerWorkflow: () -> Unit = {
      workflowRegistrationApi.registerWorkflow(appName, imageId, workflowName)
    }
    runUntilSuccessOrAbort("registerWorkflow", registerWorkflow)

    val transactionId = transactionsApi.startTransaction(appName)
    return transactionId
  }

  fun startWorkflow(transactionId: String, workflowName: String) =
      workflowApi.startWorkflow(transactionId, workflowName)

  fun deleteAll(
      appName: String,
      imageId: String,
      workflowName: String,
      transactionId: String,
  ) {
    val deleteWorkflow: () -> Unit = {
      workflowRegistrationApi.deleteWorkflow(appName, workflowName)
    }
    runUntilSuccessOrAbort("deleteWorkflow", deleteWorkflow)

    val endTransaction: () -> Unit = { transactionsApi.endTransaction(transactionId) }
    runUntilSuccessOrAbort("endTransaction", endTransaction)

    val deleteImage: () -> Unit = { imageApi.deleteImage(appName, imageId) }
    runUntilSuccessOrAbort("deleteImage", deleteImage)

    val deleteApp: () -> Unit = { appApi.deleteApp(appName) }
    runUntilSuccessOrAbort("deleteApp", deleteApp)
  }

  fun runUntilSuccessOrAbort(message: String, targetFunction: () -> Unit) {
    for (count in 1..MAX_NUMBER_OF_REQUEST_ATTEMPTS) {
      logger.debug("Run attempt, $message , $count of $MAX_NUMBER_OF_REQUEST_ATTEMPTS")
      try {
        targetFunction()
        break
      } catch (e: HttpClientErrorException) {
        logger.debug("EuroDat response to the request: $e")
        logger.debug("Pause for $SLEEP_INTERVAL ms")
        Thread.sleep(SLEEP_INTERVAL)
      }
    }
  }
}
