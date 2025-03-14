package org.datenmodell.datenmodellbackend.service.db

import org.datenmodell.datenmodellbackend.entities.DataAnalysisEntity
import org.datenmodell.datenmodellbackend.entities.DataAnalysisMetaDataEntity
import org.datenmodell.datenmodellbackend.repository.DataAnalysisMetaDataRepository
import org.datenmodell.datenmodellbackend.repository.DataAnalysisRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Service
class DataAnalysisTableService(
    @Autowired private val dataAnalysisRepository: DataAnalysisRepository,
    @Autowired private val dataAnalysisMetaDataRepository: DataAnalysisMetaDataRepository,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    private fun createDataAnalysis(dataAnalysisEntity: DataAnalysisEntity): DataAnalysisEntity =
        dataAnalysisRepository.save(dataAnalysisEntity)

    fun createMetaDataAnalysis(
        appName: String,
        description: String,
        imageLocation: String,
    ): DataAnalysisMetaDataEntity {
        val dataAnalysisEntity = DataAnalysisEntity(appName)
        createDataAnalysis(dataAnalysisEntity)

        val dataAnalysisMetaDataEntity =
            DataAnalysisMetaDataEntity(
                dataAnalysisEntity,
                OffsetDateTime.now(),
                description,
                imageLocation,
            )

        return dataAnalysisMetaDataRepository.save(dataAnalysisMetaDataEntity)
    }

    fun getValidDataAnalysisMetaData(id: UUID): DataAnalysisMetaDataEntity {
        val dataAnalysisEntity: DataAnalysisEntity? =
            dataAnalysisRepository
                .findById(id)
                .getOrNull()
        return getSingleValidRecord(dataAnalysisEntity)
    }

    private fun getSingleValidRecord(dataAnalysisEntity: DataAnalysisEntity?): DataAnalysisMetaDataEntity {
        val dataAnalysisMetaDataEntityList =
            dataAnalysisMetaDataRepository
                .findByDataAnalysisEntityAndValidToIsNull(dataAnalysisEntity)
        if (dataAnalysisMetaDataEntityList.size > 1) {
            throw IllegalStateException(
                "The database table has ill-defined entries: " +
                    "It contains more than one temporally valid record",
            )
        }
        if (dataAnalysisMetaDataEntityList.isEmpty()) {
            throw IllegalStateException(
                "The database table sat_data_analysis_meta_data does not have the requested " +
                    "entries.",
            )
        }
        return dataAnalysisMetaDataEntityList[0]
    }
}
