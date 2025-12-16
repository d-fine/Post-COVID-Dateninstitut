package org.clientapp.clientappbackend

import jakarta.transaction.Transactional
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.name
import org.clientapp.clientappbackend.service.db.ResearchDataService
import org.clientapp.clientappbackend.service.db.UserManagementService
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Commit
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class InsertTestDataTest(
    @Autowired private val researchDataService: ResearchDataService,
    @Autowired private val userManagementService: UserManagementService
) {

  @Transactional
  @Commit
  @Test
  @Tag("database-setup")
  fun insertConsumerUserAndTestFile() {
    val testUserKeyloakId = "b7554068-75bd-4ebe-a805-416273972286"
    val clientId = "postcovidclient2"
    userManagementService.createUser(clientId, "first", "sur")
    val listFilePath = listOf("src/test/resources/test-eurodat-minimal.csv")
    for (filePath in listFilePath) {
      val fileBytes = Files.readAllBytes(Paths.get(filePath))
      val fileName = Paths.get(filePath).name
      researchDataService.uploadResearchData(
          testUserKeyloakId,
          userManagementService.getIdForUsername(clientId),
          fileName,
          "minimal csv file with id column, research data column, row level security column",
          fileBytes)
    }
  }
}
