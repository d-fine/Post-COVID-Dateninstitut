package org.datenmodell.datenmodellbackend.repository

import org.datenmodell.datenmodellbackend.entities.DataAnalysisEntity
import org.datenmodell.datenmodellbackend.entities.DataAnalysisMetaDataEntity
import org.datenmodell.datenmodellbackend.entities.DataAnalysisMetaDataId
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface DataAnalysisRepository : CrudRepository<DataAnalysisEntity, UUID>

interface DataAnalysisMetaDataRepository : CrudRepository<DataAnalysisMetaDataEntity, DataAnalysisMetaDataId> {
    fun findByDataAnalysisEntity(dataAnalysisEntity: DataAnalysisEntity?): DataAnalysisMetaDataEntity

    fun findByDataAnalysisEntityAndValidToIsNull(dataAnalysisEntity: DataAnalysisEntity?): List<DataAnalysisMetaDataEntity>
}
