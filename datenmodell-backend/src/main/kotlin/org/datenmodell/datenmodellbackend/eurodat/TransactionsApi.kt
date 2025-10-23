package org.datenmodell.datenmodellbackend.eurodat

import org.datenmodell.datenmodellbackend.configuration.AppProperties
import org.eurodat.eurodattransaction.openApiClient.api.TransactionResourceApi
import org.eurodat.eurodattransaction.openApiClient.model.AppRequest
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
            .addProviderItem(appProperties.eurodatTransactionAdditionalProvider)
    return transactionResourceApi.startTransaction(appRequest).id
  }

  fun endTransaction(transactionId: String) {
    transactionResourceApi.endTransaction(transactionId)
  }
}
