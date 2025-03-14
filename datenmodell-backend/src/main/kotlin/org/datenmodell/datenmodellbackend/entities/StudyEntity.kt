package org.datenmodell.datenmodellbackend.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "studies")
data class StudyEntity(
    @Id
    @Column(name = "study_id")
    val id: UUID,
    @Column(name = "title")
    var title: String,
    @Column(name = "content")
    var content: String,
)
