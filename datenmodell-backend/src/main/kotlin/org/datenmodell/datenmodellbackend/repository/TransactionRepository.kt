package org.datenmodell.datenmodellbackend.repository

import org.datenmodell.datenmodellbackend.entities.TransactionEntity
import org.datenmodell.datenmodellbackend.entities.TransactionMetaDataEntity
import org.datenmodell.datenmodellbackend.entities.TransactionMetaDataId
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface TransactionRepository : CrudRepository<TransactionEntity, UUID>

interface TransactionMetaDataRepository :
    CrudRepository<
        TransactionMetaDataEntity,
        TransactionMetaDataId,
    > {
    fun findByTransactionEntity(transactionEntity: TransactionEntity?): TransactionMetaDataEntity

    fun findByTransactionEntityAndValidToIsNull(transactionEntity: TransactionEntity?): List<TransactionMetaDataEntity>
}
