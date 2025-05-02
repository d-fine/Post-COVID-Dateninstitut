package org.datenmodell.datenmodellbackend.repository

import java.util.UUID
import org.datenmodell.datenmodellbackend.entities.TransactionEntity
import org.datenmodell.datenmodellbackend.entities.TransactionMetaDataEntity
import org.datenmodell.datenmodellbackend.entities.TransactionMetaDataId
import org.datenmodell.datenmodellbackend.service.db.HubSatTableRepositoryWrapper
import org.springframework.data.repository.CrudRepository

interface TransactionRepository : CrudRepository<TransactionEntity, UUID>

interface TransactionMetaDataRepository :
    CrudRepository<TransactionMetaDataEntity, TransactionMetaDataId> {
  fun findByTransactionEntityAndValidToIsNull(
      transactionEntity: TransactionEntity?
  ): List<TransactionMetaDataEntity>
}

class TransactionHubSatAccess(
    override val hubRepository: CrudRepository<TransactionEntity, UUID>,
    override val satRepository: TransactionMetaDataRepository
) :
    HubSatTableRepositoryWrapper<
        TransactionEntity, TransactionMetaDataEntity, TransactionMetaDataId> {
  override val satRepositoryTableName: String
    get() = "transaction"

  override fun getValidRecord(hubEntity: TransactionEntity?): List<TransactionMetaDataEntity> =
      satRepository.findByTransactionEntityAndValidToIsNull(hubEntity)
}
