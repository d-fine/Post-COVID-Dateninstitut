package org.datenmodell.datenmodellbackend.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "hub_data_analysis")
class DataAnalysisEntity(
    @Column(name = "app_name", unique = true)
    var appName: String,
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
)

@Entity
@Table(name = "sat_data_analysis_meta_data")
@IdClass(DataAnalysisMetaDataId::class)
class DataAnalysisMetaDataEntity(
    @Id
    @ManyToOne
    @JoinColumn(name = "data_analysis_id", referencedColumnName = "id")
    val dataAnalysisEntity: DataAnalysisEntity,
    @Id
    @Column(name = "valid_from", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    var validFrom: OffsetDateTime,
    @Column(name = "description")
    var description: String,
    @Column(name = "image_location")
    var imageLocation: String,
    @Column(name = "valid_to", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    var validTo: OffsetDateTime? = null,
)

class DataAnalysisMetaDataId(
    var dataAnalysisEntity: DataAnalysisEntity,
    var validFrom: OffsetDateTime,
) {
    constructor() : this(DataAnalysisEntity(""), OffsetDateTime.MIN)
}
