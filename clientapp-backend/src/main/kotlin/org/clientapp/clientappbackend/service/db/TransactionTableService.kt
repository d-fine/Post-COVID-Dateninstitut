package org.clientapp.clientappbackend.service.db

import db.migration.TransactionStatus
import java.time.LocalDateTime
import java.util.UUID
import org.clientapp.clientappbackend.entity.HubTransactionEntity
import org.clientapp.clientappbackend.entity.SatTransactionMetaDataEntity
import org.clientapp.clientappbackend.repository.HubTransactionRepository
import org.clientapp.clientappbackend.repository.SatTransactionMetaDataRepository
import org.clientapp.clientappbackend.repository.TransactionHubSatRepositoryWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionTableService(
    @Autowired private val transactionRepository: HubTransactionRepository,
    @Autowired private val transactionMetaDataRepository: SatTransactionMetaDataRepository,
    private val transactionAccess: TransactionHubSatRepositoryWrapper =
        TransactionHubSatRepositoryWrapper(transactionRepository, transactionMetaDataRepository)
) {

  private fun createTransaction(transactionEntity: HubTransactionEntity): HubTransactionEntity =
      transactionRepository.save(transactionEntity)

  fun createTransactionMetaData(
      researchDataId: UUID,
      userId: UUID?,
      status: TransactionStatus,
  ): UUID {
    val transactionEntity = createTransaction(HubTransactionEntity(researchDataId = researchDataId))
    val transactionMetaDataEntity =
        SatTransactionMetaDataEntity(
            transactionEntity,
            LocalDateTime.now(),
            null,
            userId,
            status.toString(),
        )
    return transactionMetaDataRepository.save(transactionMetaDataEntity).hubTransactionEntity.id!!
  }

  fun updateTransactionMetaData(
      transactionId: UUID,
      status: TransactionStatus,
  ) {
    var transactionEntity = transactionAccess.invalidateCurrentSatEntity(transactionId)
    transactionEntity.status = status.toString()
    transactionAccess.satRepository.save(transactionEntity)
  }

  fun getValidTransactionMetaData(id: UUID): SatTransactionMetaDataEntity {
    return transactionAccess.getValidRecord(id)
  }
}
