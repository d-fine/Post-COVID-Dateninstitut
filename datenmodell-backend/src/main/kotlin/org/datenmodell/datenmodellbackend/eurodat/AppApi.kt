package org.datenmodell.datenmodellbackend.eurodat

import org.eurodat.eurodatapp.openApiClient.api.AppResourceApi
import org.eurodat.eurodatapp.openApiClient.model.AppRegistration
import org.eurodat.eurodatapp.openApiClient.model.TableSecurityMapping
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AppApi(
    @Autowired private val appResourceApi: AppResourceApi,
) {

  fun registerApp(appName: String, ddlStatement: String) {
    val appRegistration = AppRegistration()
    appRegistration.id = appName
    appRegistration.transactionDDL = ddlStatement
    val tableSecurityMapping = TableSecurityMapping()
    tableSecurityMapping.tableName = "data"
    tableSecurityMapping.rowBaseOutputSecurityColumn = "security_column"
    appRegistration.safeDepositDDL = "string"
    appRegistration.tableSecurityMapping = listOf(tableSecurityMapping)

    appResourceApi.apiV1AppServiceAppsPost(appRegistration)
  }

  fun deleteApp(appName: String) {
    appResourceApi.apiV1AppServiceAppsAppIdDelete(appName)
  }
}
