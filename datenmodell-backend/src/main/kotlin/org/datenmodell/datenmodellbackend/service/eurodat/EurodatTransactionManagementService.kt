package org.datenmodell.datenmodellbackend.service.eurodat

import org.datenmodell.datenmodellbackend.eurodat.AppApi
import org.datenmodell.datenmodellbackend.eurodat.ImageApi
import org.datenmodell.datenmodellbackend.eurodat.TransactionsApi
import org.datenmodell.datenmodellbackend.eurodat.WorkflowApi
import org.datenmodell.datenmodellbackend.eurodat.WorkflowRegistrationApi
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

const val MAX_NUMBER_OF_REQUEST_ATTEMPTS: Int = 12
const val SLEEP_INTERVAL: Long = 5000

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
        for (count in 1..MAX_NUMBER_OF_REQUEST_ATTEMPTS) {
            logger.debug("Start the workflow registration, attempt: $count of $MAX_NUMBER_OF_REQUEST_ATTEMPTS")
            try {
                workflowRegistrationApi.registerWorkflow(appName, imageId, workflowName)
                break
            } catch (e: HttpClientErrorException) {
                logger.debug("EuroDat response to the request: $e")
                logger.debug("Pause for $SLEEP_INTERVAL ms")
                Thread.sleep(SLEEP_INTERVAL)
            }
        }

        val transactionId = transactionsApi.startTransaction(appName)
        workflowApi.startWorkflow(transactionId, workflowName)
        return transactionId
    }

    fun deleteAll(
        appName: String,
        imageId: String,
        workflowName: String,
        transactionId: String,
    ) {
        transactionsApi.deleteTransaction(transactionId)
        workflowRegistrationApi.deleteWorkflow(appName, workflowName)
        imageApi.deleteImage(appName, imageId)
        for (count in 1..MAX_NUMBER_OF_REQUEST_ATTEMPTS) {
            logger.debug("Start the deletion, attempt: $count of $MAX_NUMBER_OF_REQUEST_ATTEMPTS")
            try {
                appApi.deleteApp(appName)
                break
            } catch (e: HttpClientErrorException) {
                logger.debug("EuroDat response to the request: $e")
                logger.debug("Pause for $SLEEP_INTERVAL ms")
                Thread.sleep(SLEEP_INTERVAL)
            }
        }
    }
}
