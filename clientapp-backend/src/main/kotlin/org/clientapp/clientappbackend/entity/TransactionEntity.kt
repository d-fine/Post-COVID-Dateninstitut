package org.clientapp.clientappbackend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID
import org.clientapp.clientappbackend.service.db.TimeBoundMetaDataEntity

@Entity
@Table(name = "hub_transaction")
class HubTransactionEntity(
    @Column(name = "research_data_id") val researchDataId: UUID? = null,
    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
)

@Entity
@Table(name = "sat_transaction_meta_data")
@IdClass(TransactionMetaDataId::class)
class SatTransactionMetaDataEntity(
    @Id
    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    val hubTransactionEntity: HubTransactionEntity,
    @Id @Column(name = "valid_from") override var validFrom: LocalDateTime,
    @Column(name = "valid_to") override var validTo: LocalDateTime?,
    @Column(name = "user_id") val userId: UUID?,
    @Column(name = "status") var status: String,
) : TimeBoundMetaDataEntity {
  override fun copy(): TimeBoundMetaDataEntity {
    return SatTransactionMetaDataEntity(
        hubTransactionEntity = hubTransactionEntity,
        validFrom = validFrom,
        validTo = validTo,
        userId = userId,
        status = status)
  }
}

class TransactionMetaDataId(
    var hubTransactionEntity: HubTransactionEntity,
    var validFrom: LocalDateTime
) {
  constructor() : this(HubTransactionEntity(), LocalDateTime.MIN)
}
