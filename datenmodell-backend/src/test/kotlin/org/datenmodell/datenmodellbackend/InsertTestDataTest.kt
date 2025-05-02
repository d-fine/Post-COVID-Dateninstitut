package org.datenmodell.datenmodellbackend

import jakarta.transaction.Transactional
import org.datenmodell.datenmodellbackend.eurodat.ColumnType
import org.datenmodell.datenmodellbackend.eurodat.DDLBuilder
import org.datenmodell.datenmodellbackend.service.db.DataAnalysisTableService
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Commit
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class InsertTestDataTest(
    @Autowired private val dataAnalysisTableService: DataAnalysisTableService,
) {

  @Transactional
  @Commit
  @Test
  @Tag("database-setup")
  fun insertData() {
    dataAnalysisTableService.createMetaDataAnalysis(
        appName = "test1",
        description = "postcovid-app-1 image: read hardcoded input from output layer",
        imageLocation = "postcovid.azurecr.io/samples/postcovid-test-1",
        ddlStatement = identityDefinition())
    dataAnalysisTableService.createMetaDataAnalysis(
        appName = "test2",
        description =
            "postcovid-app-2 image: upload table to input layer and read from output layer",
        imageLocation = "postcovid.azurecr.io/samples/postcovid-test-2",
        ddlStatement = identityDefinition())

    val nakoDataDefinitionBuilder =
        DDLBuilder()
            .addColumnToTable("input.data", "id", ColumnType.INT, false)
            .addColumnToTable("input.data", "basis_sex", ColumnType.INT, false)
            .addColumnToTable("input.data", "d_co1_hha1", ColumnType.INT, true)
            .addColumnToTable("input.data", "a_co1_ewstat", ColumnType.INT, true)
            .addColumnToTable("input.data", "a_co1_erwnd", ColumnType.TEXT, true)
            .addColumnToTable("input.data", "d_co1_qol2", ColumnType.INT, true)
            .addColumnToTable("input.data", "d_co1_qol2a", ColumnType.INT, true)
            .addColumnToTable("input.data", "d_co1_cov7", ColumnType.TEXT, true)
            .addColumnToTable("input.data", "d_co1_ber1_1", ColumnType.INT, true)
            .addColumnToTable("input.data", "d_co1_ber1_2", ColumnType.INT, true)
            .addColumnToTable("input.data", "d_co1_tab1", ColumnType.INT, true)
            .addColumnToTable("input.data", "security_column", ColumnType.TEXT, false)
            .addColumnToTable("output.data", "id", ColumnType.INT, false)
            .addColumnToTable("output.data", "basis_sex", ColumnType.INT, false)
            .addColumnToTable("output.data", "d_co1_hha1", ColumnType.INT, true)
            .addColumnToTable("output.data", "a_co1_ewstat", ColumnType.INT, true)
            .addColumnToTable("output.data", "a_co1_erwnd", ColumnType.TEXT, true)
            .addColumnToTable("output.data", "d_co1_qol2", ColumnType.INT, true)
            .addColumnToTable("output.data", "d_co1_qol2a", ColumnType.INT, true)
            .addColumnToTable("output.data", "d_co1_cov7", ColumnType.TEXT, true)
            .addColumnToTable("output.data", "d_co1_ber1_1", ColumnType.INT, true)
            .addColumnToTable("output.data", "d_co1_ber1_2", ColumnType.INT, true)
            .addColumnToTable("output.data", "d_co1_tab1", ColumnType.INT, true)
            .addColumnToTable("output.data", "security_column", ColumnType.TEXT, false)

    dataAnalysisTableService.createMetaDataAnalysis(
        appName = "PPRL Matching",
        description = "Matcht zwei Datens�tze �ber bloomfilter-codierte PII",
        imageLocation = "postcovid.azurecr.io/samples/postcovid-test-2",
        ddlStatement = nakoDataDefinitionBuilder.build())
  }
}
