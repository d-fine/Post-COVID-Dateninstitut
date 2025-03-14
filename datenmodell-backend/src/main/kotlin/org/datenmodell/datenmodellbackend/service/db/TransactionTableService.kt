package org.datenmodell.datenmodellbackend.service.db

import db.migration.TransactionStatus
import org.datenmodell.datenmodellbackend.entities.DataAnalysisEntity
import org.datenmodell.datenmodellbackend.entities.TransactionEntity
import org.datenmodell.datenmodellbackend.entities.TransactionMetaDataEntity
import org.datenmodell.datenmodellbackend.repository.DataAnalysisRepository
import org.datenmodell.datenmodellbackend.repository.TransactionMetaDataRepository
import org.datenmodell.datenmodellbackend.repository.TransactionRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Service
class TransactionTableService(
    @Autowired private val transactionRepository: TransactionRepository,
    @Autowired private val transactionMetaDataRepository: TransactionMetaDataRepository,
    @Autowired private val dataAnalysisRepository: DataAnalysisRepository,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    private fun createTransaction(transactionEntity: TransactionEntity): TransactionEntity = transactionRepository.save(transactionEntity)

    fun createTransactionMetaData(
        dataAnalysisId: UUID,
        status: TransactionStatus,
    ): UUID? {
        val dataAnalysisEntity: DataAnalysisEntity? =
            dataAnalysisRepository
                .findById(dataAnalysisId)
                .getOrNull()

        dataAnalysisEntity?.let {
            val transactionEntity = TransactionEntity()
            createTransaction(transactionEntity)

            val transactionMetaDataEntity =
                TransactionMetaDataEntity(
                    transactionEntity,
                    OffsetDateTime.now(),
                    status,
                    dataAnalysisEntity,
                )
            return transactionMetaDataRepository
                .save(transactionMetaDataEntity)
                .transactionEntity.id
        }
            ?: run {
                throw IllegalStateException(
                    "The database table hub_data_analysis does not have the requested entries.",
                )
            }
    }

    fun updateTransactionMetaData(
        transactionId: UUID?,
        status: TransactionStatus,
    ) {
        val transactionEntity: TransactionEntity? =
            transactionId?.let {
                transactionRepository
                    .findById(transactionId)
                    .getOrNull()
            }

        transactionEntity?.let {
            var oldTransactionMetaDataEntity = getValidTransactionMetaData(transactionId)
            oldTransactionMetaDataEntity.validTo = OffsetDateTime.now()
            transactionMetaDataRepository.save(oldTransactionMetaDataEntity)

            val newTransactionMetaDataEntity =
                TransactionMetaDataEntity(
                    oldTransactionMetaDataEntity.transactionEntity,
                    OffsetDateTime.now(),
                    status,
                    oldTransactionMetaDataEntity.dataAnalysisEntity,
                )

            transactionMetaDataRepository.save(newTransactionMetaDataEntity)
        } ?: run {
            throw IllegalStateException(
                "The database table hub_data_analysis does not have the requested entries.",
            )
        }
    }

    fun getValidTransactionMetaData(id: UUID): TransactionMetaDataEntity {
        val transactionEntity: TransactionEntity? = transactionRepository.findById(id).getOrNull()
        return getSingleValidRecord(transactionEntity)
    }

    private fun getSingleValidRecord(transactionEntity: TransactionEntity?): TransactionMetaDataEntity {
        val transactionMetaDataEntityList =
            transactionMetaDataRepository
                .findByTransactionEntityAndValidToIsNull(transactionEntity)
        if (transactionMetaDataEntityList.size > 1) {
            throw IllegalStateException(
                "The database table has ill-defined entries: " +
                    "It contains more than one temporally valid record",
            )
        }
        if (transactionMetaDataEntityList.isEmpty()) {
            throw IllegalStateException(
                "The database table sat_data_analysis_meta_data does not have the requested " +
                    "entries.",
            )
        }
        return transactionMetaDataEntityList[0]
    }
}
