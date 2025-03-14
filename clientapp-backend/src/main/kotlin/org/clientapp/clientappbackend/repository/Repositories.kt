package org.clientapp.clientappbackend.repository

import org.clientapp.clientappbackend.entity.ApprovalStatusEntity
import org.clientapp.clientappbackend.entity.TransactionStatusEntity
import org.clientapp.clientappbackend.entity.HubUserEntity
import org.clientapp.clientappbackend.entity.HubResearchDataEntity
import org.clientapp.clientappbackend.entity.HubTransactionEntity
import org.clientapp.clientappbackend.entity.SatUserMetaDataEntity
import org.clientapp.clientappbackend.entity.SatTransactionMetaDataEntity
import org.clientapp.clientappbackend.entity.SatResearchDataAttributesEntity

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ApprovalStatusRepository : CrudRepository<ApprovalStatusEntity, UUID>
interface TransactionStatusRepository : CrudRepository<TransactionStatusEntity, UUID>

interface HubUserRepository : CrudRepository<HubUserEntity, UUID> {
    fun findIdByUsername(username: String?): HubUserEntity?
}
interface SatUserMetaDataRepository : CrudRepository<SatUserMetaDataEntity, UUID>

interface HubResearchDataRepository : CrudRepository<HubResearchDataEntity, UUID>
interface SatResearchDataAttributesRepository : CrudRepository<SatResearchDataAttributesEntity, UUID> {

    fun findAllByValidToIsNull(): List<SatResearchDataAttributesEntity>
    fun findByResearchDataEntityAndValidToIsNull(
        researchDataEntity: HubResearchDataEntity,
    ): List<SatResearchDataAttributesEntity>
}

interface HubTransactionRepository : CrudRepository<HubTransactionEntity, UUID>
interface SatTransactionMetaDataRepository : CrudRepository<SatTransactionMetaDataEntity, UUID>