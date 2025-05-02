package org.clientapp.clientappbackend.service.eurodat

import org.clientapp.clientappbackend.eurodat.DataManagementApi
import org.clientapp.clientappbackend.service.DataConverterService
import org.eurodat.eurodatdatamanagement.openApiClient.model.DataRequest
import org.eurodat.eurodatdatamanagement.openApiClient.model.InsertResult
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EurodatDataManagementService(
    private val dataManagementApi: DataManagementApi,
    private val dataConverterService: DataConverterService
) {
  private val logger = LoggerFactory.getLogger(javaClass)

  fun transformCsvFileToJson(file: ByteArray): List<String>? {
    val charset = charset("UTF-8")
    val delimiter = dataConverterService.inferDelimiter(file.inputStream())
    val rawDataList = dataConverterService.inputStreamToList(file.inputStream(), charset, delimiter)
    return dataConverterService.transformListToJson(rawDataList)
  }

  fun postData(
      clientId: String,
      transactionId: String,
      tableName: String,
      file: ByteArray
  ): InsertResult {
    logger.info("building request")
    var dataRequest = DataRequest()
    dataRequest.transactionId = transactionId
    dataRequest.table = tableName
    dataRequest.data = transformCsvFileToJson(file)
    return dataManagementApi.postData(clientId, dataRequest)
  }
}
