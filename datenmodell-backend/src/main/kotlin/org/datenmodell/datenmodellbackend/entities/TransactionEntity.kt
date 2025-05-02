package org.datenmodell.datenmodellbackend.entities

import db.migration.TransactionStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.UUID
import org.datenmodell.datenmodellbackend.service.db.TimeBoundMetaDataEntity

@Entity
@Table(name = "hub_transaction")
class TransactionEntity(
    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
)

@Entity
@Table(name = "sat_transaction_meta_data")
@IdClass(TransactionMetaDataId::class)
@Suppress("LongParameterList")
class TransactionMetaDataEntity(
    @Id
    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    val transactionEntity: TransactionEntity,
    @Id
    @Column(name = "valid_from", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    override var validFrom: OffsetDateTime,
    @Column(name = "status") @Enumerated(EnumType.STRING) var transactionStatus: TransactionStatus,
    @ManyToOne
    @JoinColumn(name = "data_analysis_id", referencedColumnName = "id")
    val dataAnalysisEntity: DataAnalysisEntity,
    @Column(name = "valid_to", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    override var validTo: OffsetDateTime? = null,
    @Column(name = "eurodat_client_id") val eurodatClientId: String,
    @Column(name = "eurodat_workflow_name") val eurodatWorkflowName: String,
    @Column(name = "eurodat_image_id") val eurodatImageId: String,
    @Column(name = "eurodat_transaction_id") val eurodatTransactionId: String,
) : TimeBoundMetaDataEntity {
  override fun copy(): TransactionMetaDataEntity {
    return TransactionMetaDataEntity(
        transactionEntity = transactionEntity,
        validFrom = validFrom,
        transactionStatus = transactionStatus,
        dataAnalysisEntity = dataAnalysisEntity,
        validTo = validTo,
        eurodatClientId = eurodatClientId,
        eurodatWorkflowName = eurodatWorkflowName,
        eurodatImageId = eurodatImageId,
        eurodatTransactionId = eurodatTransactionId,
    )
  }
}

class TransactionMetaDataId(
    var transactionEntity: TransactionEntity,
    var validFrom: OffsetDateTime,
) {
  constructor() : this(TransactionEntity(), OffsetDateTime.MIN)
}
