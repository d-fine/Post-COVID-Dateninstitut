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

@Entity
@Table(name = "hub_pii_data")
class HubPiiDataEntity(
    @Column(name = "user_id") val userId: UUID? = null,
    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
)

@Entity
@Table(name = "sat_pii_data_meta_data")
@IdClass(PiiDataId::class)
@Suppress("LongParameterList")
class SatPiiDataMetadataEntity(
    @Id
    @ManyToOne
    @JoinColumn(name = "pii_data_id", referencedColumnName = "id")
    val piiDataEntity: HubPiiDataEntity,
    @Id @Column(name = "valid_from") val validFrom: LocalDateTime,
    @Column(name = "valid_to") val validTo: LocalDateTime?,
    @Column(name = "name") val name: String,
    @Column(name = "description") val description: String,
    @Column(name = "status") val status: String,
    @Column(name = "modifying_user_id") val modifyingUserId: UUID
)

@Entity
@Table(name = "sat_pii_data_attributes")
@IdClass(PiiDataAttributesId::class)
class SatPiiDataAttributesEntity(
    @Id
    @ManyToOne
    @JoinColumn(name = "pii_data_id", referencedColumnName = "id")
    val piiDataEntity: HubPiiDataEntity,
    @Id @Column(name = "attribute_id") var attributeId: UUID,
    @Id @Column(name = "valid_from") val validFrom: LocalDateTime,
    @Column(name = "valid_to") val validTo: LocalDateTime?,
    @Column(name = "attribute_name") val attributeName: String,
    @Column(name = "attribute_type") val attributeType: String
)

@Entity
@Table(name = "sat_pii_data_bloomfilter")
@IdClass(PiiDataBloomFilterId::class)
class SatPiiDataBloomfilterEntity(
    @Id
    @ManyToOne
    @JoinColumn(name = "pii_data_id", referencedColumnName = "id")
    val piiDataEntity: HubPiiDataEntity,
    @Id @Column(name = "valid_from") val validFrom: LocalDateTime,
    @Id @Column(name = "study_id") val studyId: String,
    @Id @Column(name = "attribute_id") val attributeId: UUID,
    @Column(name = "valid_to") val validTo: LocalDateTime?,
    @Column(name = "bloomfilter_code") val bloomfilterCode: String
)

class PiiDataId(
    var piiDataEntity: HubPiiDataEntity,
    var validFrom: LocalDateTime,
) {
  constructor() : this(HubPiiDataEntity(), LocalDateTime.now())
}

class PiiDataAttributesId(
    var piiDataEntity: HubPiiDataEntity,
    var validFrom: LocalDateTime,
    var attributeId: UUID
) {
  constructor() : this(HubPiiDataEntity(), LocalDateTime.now(), UUID(0, 0))
}

class PiiDataBloomFilterId(
    var piiDataEntity: HubPiiDataEntity,
    var validFrom: LocalDateTime,
    var studyId: String,
    var attributeId: UUID
) {
  constructor() : this(HubPiiDataEntity(), LocalDateTime.now(), String(), UUID(0, 0))
}
