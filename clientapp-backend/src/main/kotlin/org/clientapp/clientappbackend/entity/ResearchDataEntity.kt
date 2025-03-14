package org.clientapp.clientappbackend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table

import java.util.UUID
import java.time.LocalDateTime

@Entity
@Table(name = "hub_research_data")
class HubResearchDataEntity(
    @Column(name = "user_id")
    val userId: UUID? = null,
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
)

@Entity
@Table(name = "sat_research_data_attributes")
@IdClass(ResearchDataAttributesId::class)
class SatResearchDataAttributesEntity(
    @Id
    @ManyToOne
    @JoinColumn(name = "research_data_id", referencedColumnName = "id")
    val researchDataEntity: HubResearchDataEntity,
    @Id
    @Column(name = "valid_from")
    val validFrom: LocalDateTime,
    @Column(name = "valid_to")
    val validTo: LocalDateTime?,
    @Column(name = "name")
    val name: String,
    @Column(name = "description")
    val description: String,
    @Column(name = "file")
    val file: ByteArray,
    @Column(name = "status")
    val status: String,
    @Column(name = "modifying_user_id")
    val modifyingUserId: UUID
)

class ResearchDataAttributesId(
    var researchDataEntity: HubResearchDataEntity,
    var validFrom: LocalDateTime,
) {
    constructor() : this(HubResearchDataEntity(), LocalDateTime.MIN)
}
