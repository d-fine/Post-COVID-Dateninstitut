package org.clientapp.clientappbackend.repository

import java.util.UUID
import org.clientapp.clientappbackend.entity.ApprovalStatusEntity
import org.clientapp.clientappbackend.entity.HubUserEntity
import org.clientapp.clientappbackend.entity.SatUserMetaDataEntity
import org.clientapp.clientappbackend.entity.TransactionStatusEntity
import org.clientapp.clientappbackend.entity.UserMetaDataId
import org.springframework.data.repository.CrudRepository

interface ApprovalStatusRepository : CrudRepository<ApprovalStatusEntity, UUID>

interface TransactionStatusRepository : CrudRepository<TransactionStatusEntity, UUID>

interface HubUserRepository : CrudRepository<HubUserEntity, UUID> {
  fun findIdByUsername(username: String?): HubUserEntity?
}

interface SatUserMetaDataRepository : CrudRepository<SatUserMetaDataEntity, UserMetaDataId>
