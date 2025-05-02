package org.datenmodell.datenmodellbackend.service.eurodat

import org.datenmodell.datenmodellbackend.eurodat.DataManagementApi
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EurodatTransactionOutputService(
    private val dataManagementApi: DataManagementApi,
) {
  private val logger = LoggerFactory.getLogger(javaClass)

  @Suppress("TooGenericExceptionCaught")
  fun getData(
      clientId: String,
      outputTable: String,
      transactionId: String,
  ): List<String> {
    for (count in 1..MAX_NUMBER_OF_REQUEST_ATTEMPTS) {
      logger.debug(
          "Start the data retrieval, attempt: " + "$count of $MAX_NUMBER_OF_REQUEST_ATTEMPTS",
      )
      try {
        dataManagementApi.getData(clientId, outputTable, transactionId)[0]
        break
      } catch (e: IndexOutOfBoundsException) {
        if (count == MAX_NUMBER_OF_REQUEST_ATTEMPTS) {
          logger.debug("Assuming that the result is actually empty")
          return listOf("")
        }
        logger.debug("EuroDaT returns empty list: $e")
        logger.debug("Pause for $SLEEP_INTERVAL ms")
        Thread.sleep(SLEEP_INTERVAL)
      }
    }
    return dataManagementApi.getData(clientId, outputTable, transactionId)
  }
}
