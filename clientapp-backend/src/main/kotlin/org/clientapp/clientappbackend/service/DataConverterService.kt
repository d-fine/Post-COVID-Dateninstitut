package org.clientapp.clientappbackend.service

import com.ibm.icu.text.CharsetDetector
import java.io.InputStream
import java.nio.charset.Charset
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class DataConverterService {

  fun inferDelimiter(inputStream: InputStream): String {
    val delimiterCounts = mutableMapOf<Char, Int>()
    inputStream.bufferedReader().use { reader ->
      val line = reader.readLine()
      delimiterCounts[';'] = line.count { it == ';' }
      delimiterCounts[','] = line.count { it == ',' }
    }
    if (delimiterCounts[';'] ?: 0 > delimiterCounts[','] ?: 0) {
      return ";"
    }
    return ","
  }

  fun detectEncodingWithICU(inputStream: InputStream): Charset {
    val bytes = inputStream.readBytes()
    val detector = CharsetDetector()
    detector.setText(bytes)
    val match = detector.detect()
    return Charset.forName(match.name)
  }

  fun parseCsvFileToMap(
      file: MultipartFile?,
      idColumn: String,
      delimiter: String? = null
  ): Map<String, Map<String, String>> {
    requireNotNull(file) { "File must not be null" }
    val usedDelimiter = delimiter ?: inferDelimiter(file.inputStream)
    val charset = detectEncodingWithICU(file.inputStream)
    return inputStreamToMap(file.inputStream, idColumn, charset, usedDelimiter)
  }

  fun transformListToJson(data: List<Map<String, String>>): List<String> {
    return data.map { row ->
      "{" +
          row.entries
              .map { entry ->
                var value = "\"${entry.value}\""
                if (entry.value.toIntOrNull() != null) {
                  value = entry.value
                }
                "\"${entry.key}\":${value}"
              }
              .joinToString(",") +
          "}"
    }
  }

  fun inputStreamToList(
      inputStream: InputStream,
      charset: Charset,
      delimiter: String = ";"
  ): List<Map<String, String>> {
    val dataList: List<Map<String, String>>
    inputStream.bufferedReader(charset).use { reader ->
      val csvParser =
          CSVParser(
              reader,
              CSVFormat.Builder.create()
                  .setDelimiter(delimiter)
                  .setHeader()
                  .setSkipHeaderRecord(true)
                  .build())
      dataList =
          csvParser.toList().map { csvRecord ->
            csvParser.headerNames.associateWith { csvRecord[it] }
          }
    }
    return dataList
  }

  fun inputStreamToMap(
      inputStream: InputStream,
      idColumn: String,
      charset: Charset,
      delimiter: String = ";"
  ): Map<String, Map<String, String>> {
    val dataMap = mutableMapOf<String, Map<String, String>>()

    inputStream.bufferedReader(charset).use { reader ->
      val csvParser =
          CSVParser(
              reader,
              CSVFormat.Builder.create()
                  .setDelimiter(delimiter)
                  .setHeader()
                  .setSkipHeaderRecord(true)
                  .build())

      val columnNames = csvParser.headerNames.filter { it != idColumn }
      for (csvRecord in csvParser) {
        val id = csvRecord[idColumn]
        dataMap[id] = columnNames.associateWith { csvRecord[it] }
      }
    }
    return dataMap
  }
}
