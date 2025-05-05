package org.clientapp.clientappbackend.controller.piidata

import org.clientapp.clientappbackend.service.DataConverterService
import org.clientapp.clientappbackend.service.bloomfilter.BloomFilterService
import org.clientapp.clientappbackend.service.bloomfilter.DoubleHashingStrategy
import org.clientapp.clientappbackend.service.db.PiiDataService
import org.clientapp.clientappbackend.service.db.UserManagementService
import org.openapitools.api.PiiDataApi
import org.openapitools.model.EntityInformation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class PiiDataController(
    @Autowired private val dataConverterService: DataConverterService,
    @Autowired private val piiDataService: PiiDataService,
    @Autowired private val userManagementService: UserManagementService,
    @Autowired private val bloomFilterService: BloomFilterService
) : PiiDataApi {

  @PreAuthorize("hasRole('ADMIN')")
  @Suppress("MagicNumber")
  override fun uploadPiiData(
      @RequestParam fileName: String,
      @RequestParam dataConsumerUsername: String?,
      @RequestParam description: String?,
      @RequestPart file: MultipartFile?
  ): ResponseEntity<EntityInformation> {
    val logger = LoggerFactory.getLogger(javaClass)
    logger.debug("Entering controller method")
    val keycloakId = userManagementService.getKeycloakId()
    val dataConsumerId = userManagementService.getIdForUsername(dataConsumerUsername)
    val convertedCsvFile = dataConverterService.parseCsvFileToMap(file, "id")
    val filterLength = 100
    val strategy = DoubleHashingStrategy(length = filterLength, iterations = 2, nGramLength = 2)
    val bloomFilterResult =
        bloomFilterService.transformDataMap(
            convertedCsvFile,
            filterLength = filterLength,
            strategy = strategy,
            salt = "veryLongGeneratedSaltByDataConsumer")
    return ResponseEntity.ok(
        piiDataService.uploadPIIData(
            keycloakId, dataConsumerId, fileName, description ?: "", bloomFilterResult))
  }
}
