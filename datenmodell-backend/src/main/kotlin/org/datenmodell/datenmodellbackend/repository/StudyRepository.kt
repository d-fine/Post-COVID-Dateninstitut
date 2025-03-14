package org.datenmodell.datenmodellbackend.repository

import org.datenmodell.datenmodellbackend.entities.StudyEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface StudyRepository : CrudRepository<StudyEntity, UUID>
