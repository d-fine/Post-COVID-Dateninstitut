package org.clientapp.clientappbackend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

enum class ApprovalStatus {
    NONE,
    APPROVED,
    DENIED,
}

@Entity
@Table(name = "approval_status")
class ApprovalStatusEntity(
    @Id
    @Column(name = "status")
    var status: String,
)

enum class TransactionStatus {
    NONE,
    STARTED,
    FAILED,
    SUCCEEDED,
}

@Entity
@Table(name = "transaction_status")
class TransactionStatusEntity(
    @Id
    @Column(name = "status")
    val status: String,
)
