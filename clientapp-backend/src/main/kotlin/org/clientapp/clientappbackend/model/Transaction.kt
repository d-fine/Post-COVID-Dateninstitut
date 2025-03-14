package org.clientapp.clientappbackend.model

import java.time.LocalDateTime
import java.util.UUID

import org.openapitools.model.EntityInformation

data class HubTransaction(
    val id: UUID,
    val research_data_id: UUID,
)

data class SatTransactionMetaData(
    val transaction_id: UUID,
    val valid_from: LocalDateTime = LocalDateTime.now(),
    val valid_to: LocalDateTime? = null,
    val user_id: UUID,
    val status: String,
)

data class ApprovalStatus(
    val status: String,
)

data class TransactionStatus(
    val status: String,
)
