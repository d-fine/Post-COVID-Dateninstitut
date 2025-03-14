package org.datenmodell.datenmodellbackend.eurodat

import org.eurodat.eurodatapp.openApiClient.api.AppResourceApi
import org.eurodat.eurodatapp.openApiClient.model.AppRegistration
import org.eurodat.eurodatapp.openApiClient.model.TableSecurityMapping
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AppApi(
    @Autowired private val appResourceApi: AppResourceApi,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun registerApp(appName: String) {
        val appRegistration = AppRegistration()
        appRegistration.id = appName
        appRegistration.transactionDDL = "create table input.data " +
            "(id int not null, research_data text null ); " +
            "create table output.data " +
            "(id int not null primary key, research_data text null, " +
            "security_column text not null ); " +
            "ALTER TABLE output.data ENABLE ROW LEVEL SECURITY;"
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
