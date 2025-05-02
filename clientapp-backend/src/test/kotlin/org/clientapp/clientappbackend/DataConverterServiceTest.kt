import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import org.clientapp.clientappbackend.service.DataConverterService
import org.clientapp.clientappbackend.service.bloomfilter.BloomFilterService
import org.clientapp.clientappbackend.service.bloomfilter.DoubleHashingStrategy
import org.springframework.mock.web.MockMultipartFile

class DataConverterServiceTest {

  private var dataConverterService = DataConverterService()
  private var bloomFilterService = BloomFilterService()

  @Test
  fun parseToJsonTest() {
    val expected =
        """{"id":"8e40b5ad-063f-40f7-b64e-0a5a5d60e1aa","title":"answer-life-universe-everything","content":42}"""
    val fileBytes = Files.readAllBytes(Paths.get("src/test/resources/test.csv"))

    val intermediateParsedList =
        dataConverterService.inputStreamToList(
            inputStream = fileBytes.inputStream(), charset = charset("UTF-8"), delimiter = ",")

    val result = dataConverterService.transformListToJson(intermediateParsedList)
    assert(result[0] == expected)
  }

  @Test
  fun parseToDictTest() {
    val content =
        "StudyId;FirstName;Surname;PlaceOfBirth\n1;Lisa;Simpson;New York\n" +
            "2;Bart;Bartson;London\n3;Homer;Homerson;Frankfurt"
    val mockFile = MockMultipartFile("file", "test.csv", "text/csv", content.toByteArray())

    val result = dataConverterService.parseCsvFileToMap(mockFile, "StudyId")

    assertEquals(3, result.size)
    assertEquals("Lisa", result["1"]?.get("FirstName"))
    assertEquals("London", result["2"]?.get("PlaceOfBirth"))
  }

  @Test
  fun transformedResultTest() {
    val content =
        "StudyId;FirstName;Surname;PlaceOfBirth\n1;Lisa;Simpson;New York\n" +
            "2;Bart;Bartson;London\n3;Homer;Homerson;Frankfurt"
    val mockFile = MockMultipartFile("file", "test.csv", "text/csv", content.toByteArray())

    val convertedFile = dataConverterService.parseCsvFileToMap(mockFile, "StudyId")
    val strategy = DoubleHashingStrategy(100, 2, 2)
    val bloomfilterResult =
        bloomFilterService.transformDataMap(
            convertedFile, filterLength = 100, strategy = strategy, salt = "test")

    assertEquals(3, bloomfilterResult.size)
    assertNotEquals("Lisa", bloomfilterResult["1"]?.get("FirstName"))
    assertEquals(
        "0000010000000000010000000110010000100000010000000000000000000000000000000100000000001000000100000000",
        bloomfilterResult["1"]?.get("FirstName"))
  }
}
