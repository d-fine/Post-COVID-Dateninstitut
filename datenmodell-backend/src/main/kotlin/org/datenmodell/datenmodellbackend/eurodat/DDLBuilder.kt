package org.datenmodell.datenmodellbackend.eurodat

enum class ColumnType {
  TEXT,
  INT;

  override fun toString(): String {
    return name.lowercase()
  }
}

class TableColumn(val columnName: String, val columnType: ColumnType, val nullable: Boolean) {
  fun toSql(): String =
      "$columnName ${
        columnType.toString()
    } ${if (nullable) "null" else "not null"}"
}

class DDLBuilder {
  var ddlMap: MutableMap<String, MutableList<TableColumn>> = mutableMapOf()

  fun addTable(tableName: String): DDLBuilder {
    ddlMap[tableName] = mutableListOf()
    return this
  }

  fun addColumnToTable(
      tableName: String,
      columnName: String,
      columnType: ColumnType,
      nullable: Boolean
  ): DDLBuilder {
    if (!ddlMap.containsKey(tableName)) {
      addTable(tableName)
    }
    ddlMap[tableName]?.add(TableColumn(columnName, columnType, nullable))
    return this
  }

  fun createTableStatement(table: String, columns: List<TableColumn>): String {
    if (columns.isEmpty()) {
      return ""
    }
    val (first, rest) = Pair(columns.first(), columns.drop(1))
    val inner = rest.fold(first.toSql()) { acc, element -> acc + ", " + element.toSql() }
    val security =
        if (table.startsWith("output.")) {
          " ALTER TABLE $table ENABLE ROW LEVEL SECURITY;"
        } else {
          ""
        }
    return "create table $table ( $inner );$security"
  }

  fun build(): String {
    return ddlMap.entries
        .map { createTableStatement(it.key, it.value) }
        .reduce { acc, element -> acc + " " + element }
  }
}
