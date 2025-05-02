package org.clientapp.clientappbackend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID
import org.clientapp.clientappbackend.service.db.TimeBoundMetaDataEntity

@Entity
@Table(name = "hub_research_data")
class HubResearchDataEntity(
    @Column(name = "user_id") val userId: UUID? = null,
    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
)

@Entity
@Table(name = "sat_research_data_meta_data")
@IdClass(ResearchDataMetaDataId::class)
@Suppress("LongParameterList")
class SatResearchDataMetaDataEntity(
    @Id
    @ManyToOne
    @JoinColumn(name = "research_data_id", referencedColumnName = "id")
    val researchDataEntity: HubResearchDataEntity,
    @Id @Column(name = "valid_from") override var validFrom: LocalDateTime,
    @Column(name = "valid_to") override var validTo: LocalDateTime?,
    @Column(name = "name") val name: String,
    @Column(name = "description") val description: String,
    @Column(name = "file") val file: ByteArray,
    @Column(name = "status") var status: String,
    @Column(name = "modifying_user_id") val modifyingUserId: UUID
) : TimeBoundMetaDataEntity {
  override fun copy(): TimeBoundMetaDataEntity {
    return SatResearchDataMetaDataEntity(
        researchDataEntity = researchDataEntity,
        validFrom = validFrom,
        validTo = validTo,
        name = name,
        description = description,
        file = file,
        status = status,
        modifyingUserId = modifyingUserId,
    )
  }
}

class ResearchDataMetaDataId(
    var researchDataEntity: HubResearchDataEntity,
    var validFrom: LocalDateTime,
) {
  constructor() : this(HubResearchDataEntity(), LocalDateTime.MIN)
}
