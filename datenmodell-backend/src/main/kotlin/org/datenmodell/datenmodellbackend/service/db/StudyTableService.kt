package org.datenmodell.datenmodellbackend.service.db

import org.datenmodell.datenmodellbackend.entities.StudyEntity
import org.datenmodell.datenmodellbackend.model.Study
import org.datenmodell.datenmodellbackend.repository.StudyRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StudyTableService(
    @Autowired private val studyRepository: StudyRepository,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun getAll(): List<Study> = studyRepository.findAll().map { it.toModel() }

    private fun StudyEntity.toModel(): Study =
        Study(
            id = this.id,
            title = this.title,
            content = this.content,
        )
}
