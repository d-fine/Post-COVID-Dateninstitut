package org.datenmodell.datenmodellbackend.model

data class ActiveTransaction(
    val eurodatClientId: String,
    val eurodatWorkflowName: String,
    val eurodatImageId: String,
    val eurodatTransactionId: String,
)
