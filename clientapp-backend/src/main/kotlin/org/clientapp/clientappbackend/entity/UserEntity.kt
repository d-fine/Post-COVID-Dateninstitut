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
import org.clientapp.clientappbackend.model.HubUser

@Entity
@Table(name = "hub_user")
class HubUserEntity(
    @Column(name = "username") val username: String,
    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
) {
  fun toModel(): HubUser =
      HubUser(
          id = id!!,
          username = username,
      )
}

@Entity
@Table(name = "sat_user_meta_data")
@IdClass(UserMetaDataId::class)
class SatUserMetaDataEntity(
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val hubUserEntity: HubUserEntity,
    @Id @Column(name = "valid_from") val validFrom: LocalDateTime,
    @Column(name = "valid_to") val validTo: LocalDateTime?,
    @Column(name = "first_name") val firstName: String,
    @Column(name = "surname") val surname: String,
)

class UserMetaDataId(
    var hubUserEntity: HubUserEntity,
    var validFrom: LocalDateTime,
) {
  constructor() : this(HubUserEntity(""), LocalDateTime.MIN)
}
