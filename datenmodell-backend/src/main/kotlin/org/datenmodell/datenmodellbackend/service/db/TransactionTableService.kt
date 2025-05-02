package org.datenmodell.datenmodellbackend.service.db

import db.migration.TransactionStatus
import java.time.OffsetDateTime
import java.util.UUID
import kotlin.jvm.optionals.getOrNull
import org.datenmodell.datenmodellbackend.entities.TransactionEntity
import org.datenmodell.datenmodellbackend.entities.TransactionMetaDataEntity
import org.datenmodell.datenmodellbackend.model.ActiveTransaction
import org.datenmodell.datenmodellbackend.repository.DataAnalysisRepository
import org.datenmodell.datenmodellbackend.repository.TransactionHubSatAccess
import org.datenmodell.datenmodellbackend.repository.TransactionMetaDataRepository
import org.datenmodell.datenmodellbackend.repository.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionTableService(
    @Autowired private val transactionRepository: TransactionRepository,
    @Autowired private val transactionMetaDataRepository: TransactionMetaDataRepository,
    @Autowired private val dataAnalysisRepository: DataAnalysisRepository,
    private val transactionAccess: TransactionHubSatAccess =
        TransactionHubSatAccess(transactionRepository, transactionMetaDataRepository)
) {

  private fun createTransaction(transactionEntity: TransactionEntity): TransactionEntity =
      transactionRepository.save(transactionEntity)

  fun createTransactionMetaData(
      dataAnalysisId: UUID,
      status: TransactionStatus,
      activeTransaction: ActiveTransaction
  ): UUID {
    val dataAnalysisEntity = dataAnalysisRepository.findById(dataAnalysisId).getOrNull()
    dataAnalysisEntity?.let {
      val transactionEntity = createTransaction(TransactionEntity())
      val transactionMetaDataEntity =
          TransactionMetaDataEntity(
              transactionEntity,
              OffsetDateTime.now(),
              status,
              dataAnalysisEntity,
              null,
              activeTransaction.eurodatClientId,
              activeTransaction.eurodatWorkflowName,
              activeTransaction.eurodatImageId,
              activeTransaction.eurodatTransactionId,
          )
      return transactionMetaDataRepository.save(transactionMetaDataEntity).transactionEntity.id!!
    }
        ?: run {
          throw IllegalStateException(
              "The database table hub_data_analysis does not have the requested entries.",
          )
        }
  }

  fun updateTransactionMetaData(
      transactionId: UUID,
      status: TransactionStatus,
  ) {
    val transactionEntity = transactionAccess.invalidateCurrentSatEntity(transactionId)
    transactionEntity.transactionStatus = status
    transactionAccess.satRepository.save(transactionEntity)
  }

  fun getValidTransactionMetaData(id: UUID): TransactionMetaDataEntity {
    return transactionAccess.getValidRecord(id)
  }
}
