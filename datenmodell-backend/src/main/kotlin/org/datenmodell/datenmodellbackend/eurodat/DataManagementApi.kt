package org.datenmodell.datenmodellbackend.eurodat

import org.eurodat.eurodatdatamanagement.openApiClient.api.DataManagementResourceApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DataManagementApi(
    @Autowired private val dataManagementResourceApi: DataManagementResourceApi,
) {

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
