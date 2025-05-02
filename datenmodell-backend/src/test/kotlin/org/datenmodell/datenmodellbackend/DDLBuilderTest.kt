package org.datenmodell.datenmodellbackend

import org.datenmodell.datenmodellbackend.eurodat.ColumnType
import org.datenmodell.datenmodellbackend.eurodat.DDLBuilder
import org.junit.jupiter.api.Test

fun identityDefinition(): String {
  return DDLBuilder()
      .addColumnToTable(
          tableName = "input.data",
          columnType = ColumnType.INT,
          nullable = false,
          columnName = "id")
      .addColumnToTable(
          tableName = "input.data",
          columnType = ColumnType.TEXT,
          nullable = true,
          columnName = "research_data")
      .addColumnToTable(
          tableName = "input.data",
          columnType = ColumnType.TEXT,
          nullable = false,
          columnName = "security_column")
      .addColumnToTable(
          tableName = "output.data",
          columnType = ColumnType.INT,
          nullable = false,
          columnName = "id")
      .addColumnToTable(
          tableName = "output.data",
          columnType = ColumnType.TEXT,
          nullable = true,
          columnName = "research_data")
      .addColumnToTable(
          tableName = "output.data",
          columnType = ColumnType.TEXT,
          nullable = false,
          columnName = "security_column")
      .build()
}

class DdlBuilderTest {

  @Test
  fun simpleBuild() {
    val expected =
        "create table input.data " +
            "( id int not null, research_data text null, security_column text not null ); " +
            "create table output.data " +
            "( id int not null, research_data text null, " +
            "security_column text not null ); " +
            "ALTER TABLE output.data ENABLE ROW LEVEL SECURITY;"
    assert(expected == identityDefinition())
  }
}
