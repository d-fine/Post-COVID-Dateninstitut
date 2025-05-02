package org.datenmodell.datenmodellbackend.eurodat

import org.eurodat.eurodatcontroller.openApiClient.api.WorkflowResourceApi
import org.eurodat.eurodatcontroller.openApiClient.model.WorkflowRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class WorkflowApi(
    @Autowired private val workflowResourceApi: WorkflowResourceApi,
) {

  fun startWorkflow(
      transactionId: String,
      workflowName: String,
  ): String {
    val workflowRequest = WorkflowRequest().workflowDefinitionId(workflowName)
    return workflowResourceApi
        .apiV1TransactionsTransactionIdWorkflowsPost(
            transactionId,
            workflowRequest,
        )
        .workflowRunId
  }
}
