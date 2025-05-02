package org.clientapp.clientappbackend.eurodat

import org.eurodat.eurodatdatamanagement.openApiClient.api.DataManagementResourceApi
import org.eurodat.eurodatdatamanagement.openApiClient.model.DataRequest
import org.eurodat.eurodatdatamanagement.openApiClient.model.InsertResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DataManagementApi(
    @Autowired private val dataManagementResourceApi: DataManagementResourceApi,
) {

  fun postData(clientId: String, dataRequest: DataRequest): InsertResult {
    return dataManagementResourceApi.apiV1ParticipantsParticipantIdDataPost(clientId, dataRequest)
  }

  fun getData(
      clientId: String,
      outputTable: String,
      transactionId: String,
  ): List<String> =
      dataManagementResourceApi.apiV1ParticipantsParticipantIdDataGet(
          clientId,
          outputTable,
          transactionId,
          null,
      )
}
