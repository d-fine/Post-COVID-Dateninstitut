package org.clientapp.clientappbackend.repository

import java.util.UUID
import org.clientapp.clientappbackend.entity.HubTransactionEntity
import org.clientapp.clientappbackend.entity.SatTransactionMetaDataEntity
import org.clientapp.clientappbackend.entity.TransactionMetaDataId
import org.clientapp.clientappbackend.service.db.HubSatTableRepositoryWrapper
import org.springframework.data.repository.CrudRepository

interface HubTransactionRepository : CrudRepository<HubTransactionEntity, UUID>

interface SatTransactionMetaDataRepository :
    CrudRepository<SatTransactionMetaDataEntity, TransactionMetaDataId> {
  fun findByHubTransactionEntityAndValidToIsNull(
      transactionEntity: HubTransactionEntity?
  ): List<SatTransactionMetaDataEntity>
}

class TransactionHubSatRepositoryWrapper(
    override val hubRepository: HubTransactionRepository,
    override val satRepository: SatTransactionMetaDataRepository
) :
    HubSatTableRepositoryWrapper<
        HubTransactionEntity, SatTransactionMetaDataEntity, TransactionMetaDataId> {
  override val satRepositoryTableName: String
    get() = "transaction"

  override fun getValidRecord(
      hubEntity: HubTransactionEntity?
  ): List<SatTransactionMetaDataEntity> =
      satRepository.findByHubTransactionEntityAndValidToIsNull(hubEntity)
}
