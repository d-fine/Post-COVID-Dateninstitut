package org.datenmodell.datenmodellbackend.eurodat

import org.eurodat.eurodatapp.openApiClient.api.WorkflowRegistrationResourceApi
import org.eurodat.eurodatapp.openApiClient.model.WorkflowRegistrationRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class WorkflowRegistrationApi(
    @Autowired private val workflowRegistrationResourceApi: WorkflowRegistrationResourceApi,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun registerWorkflow(
        appName: String,
        imageId: String,
        workflowName: String,
    ) {
        val workflowRegistrationRequest =
            WorkflowRegistrationRequest()
                .imageId(imageId)
                .workflowId(workflowName)
                .addStartCommandItem("python")
                .addStartCommandItem("main.py")

        workflowRegistrationResourceApi.apiV1AppServiceAppsAppIdWorkflowsPost(appName, workflowRegistrationRequest)
    }

    fun deleteWorkflow(
        appName: String,
        workflowName: String,
    ) {
        workflowRegistrationResourceApi
            .apiV1AppServiceAppsAppIdWorkflowsWorkflowIdDelete(appName, workflowName)
    }
}
