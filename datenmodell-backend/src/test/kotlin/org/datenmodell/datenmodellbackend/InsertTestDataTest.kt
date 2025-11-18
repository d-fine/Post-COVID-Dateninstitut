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

    val demoVideoBuilder =
        DDLBuilder()
            .addColumnToTable("input.study_data", "id", ColumnType.TEXT, false)
            .addColumnToTable(
                "input.study_data", "pommes_konsum", ColumnType.INT, false)
            .addColumnToTable("input.pii_data", "id", ColumnType.TEXT, false)
            .addColumnToTable("input.pii_data", "vorname", ColumnType.TEXT, false)
            .addColumnToTable("input.pii_data", "nachname", ColumnType.TEXT, false)
            .addColumnToTable("input.pii_data", "geburtstag", ColumnType.TEXT, false)
            .addColumnToTable("input.pii_data", "geschlecht", ColumnType.TEXT, false)
            .addColumnToTable("input.pii_data", "wohnort", ColumnType.TEXT, false)
            .addColumnToTable("output.data", "pommes_konsum", ColumnType.INT, false)
            .addColumnToTable("output.data", "post_covid_score", ColumnType.INT, false)
            .addColumnToTable("output.data", "security_column", ColumnType.TEXT, false)



    dataAnalysisTableService.createMetaDataAnalysis(
        appName = "record_linkage",
        description = "Verknüpfung der Daten zweiter Datenhalter auf Personenebene",
        imageLocation = "postcovid.azurecr.io/samples/postcovid-demovideo-1",
        ddlStatement = demoVideoBuilder.build())


    val dataDefinitionBuilder =
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
        ddlStatement = dataDefinitionBuilder.build())

    val piiList =
        listOf("id", "Vorname", "Nachname", "Geburtsdatum", "Geburtsort", "Geschlecht", "Wohnort")
    val piiDefinition =
        dataDefinitionBuilder
            .addColumnToTable("output.data", "my_id", ColumnType.INT, true)
            .addColumnToTable("output.data", "other_id", ColumnType.INT, true)
    for (attribute in piiList) {
      piiDefinition
          .addColumnToTable("input.my_pii", attribute, ColumnType.TEXT, true)
          .addColumnToTable("input.other_pii", attribute, ColumnType.TEXT, true)
    }

    dataAnalysisTableService.createMetaDataAnalysis(
        appName = "Dummy PPRL",
        description = "Test Matching mit Dummy Daten",
        imageLocation = "postcovid.azurecr.io/samples/postcovid-pprl-1",
        ddlStatement = piiDefinition.build())

    dataAnalysisTableService.createMetaDataAnalysis(
        appName = "test",
        description =
            "postcovid-app-2 image: upload table to input layer and read from output layer",
        imageLocation = "postcovid.azurecr.io/samples/postcovid-test-2",
        ddlStatement = identityDefinition())

  }
}
