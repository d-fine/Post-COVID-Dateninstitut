package org.datenmodell.datenmodellbackend.eurodat

import org.datenmodell.datenmodellbackend.configuration.AppProperties
import org.eurodat.eurodatcontroller.openApiClient.api.TransactionResourceApi
import org.eurodat.eurodatcontroller.openApiClient.model.AppRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TransactionsApi(
    @Autowired private val transactionResourceApi: TransactionResourceApi,
    @Autowired var appProperties: AppProperties,
) {

  fun startTransaction(appName: String): String {
    val appRequest =
        AppRequest()
            .appId(appName)
            .addConsumerItem(appProperties.eurodatClientId)
            .addProviderItem(appProperties.eurodatClientId)
    return transactionResourceApi.apiV1TransactionsPost(appRequest).id
  }

  fun deleteTransaction(transactionId: String) {
    transactionResourceApi.apiV1TransactionsTransactionIdDelete(transactionId)
  }
}
