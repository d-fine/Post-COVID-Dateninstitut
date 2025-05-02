import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import org.clientapp.clientappbackend.entity.HubPiiDataEntity
import org.clientapp.clientappbackend.entity.SatPiiDataAttributesEntity
import org.clientapp.clientappbackend.entity.SatPiiDataBloomfilterEntity
import org.clientapp.clientappbackend.entity.SatPiiDataMetadataEntity
import org.clientapp.clientappbackend.repository.HubPiiDataRepository
import org.clientapp.clientappbackend.repository.SatPiiDataAttributesRepository
import org.clientapp.clientappbackend.repository.SatPiiDataBloomfilterRepository
import org.clientapp.clientappbackend.repository.SatPiiDataMetadataRepository
import org.clientapp.clientappbackend.service.db.PiiDataService
import org.junit.jupiter.api.BeforeEach
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

class PiiDataServiceTest {

  private lateinit var hubPiiDataRepository: HubPiiDataRepository
  private lateinit var satPiiDataMetadataRepository: SatPiiDataMetadataRepository
  private lateinit var satPiiDataBloomfilterRepository: SatPiiDataBloomfilterRepository
  private lateinit var satPiiDataAttributesRepository: SatPiiDataAttributesRepository

  private lateinit var piiDataService: PiiDataService

  @BeforeEach
  fun setUp() {
    hubPiiDataRepository = Mockito.mock(HubPiiDataRepository::class.java)
    satPiiDataMetadataRepository = Mockito.mock(SatPiiDataMetadataRepository::class.java)
    satPiiDataBloomfilterRepository = Mockito.mock(SatPiiDataBloomfilterRepository::class.java)
    satPiiDataAttributesRepository = Mockito.mock(SatPiiDataAttributesRepository::class.java)

    piiDataService =
        PiiDataService(
            hubPiiDataRepository,
            satPiiDataMetadataRepository,
            satPiiDataBloomfilterRepository,
            satPiiDataAttributesRepository)
  }

  @Test
  fun uploadPiiDataTest() {
    val keycloakId = UUID.randomUUID().toString()
    val dataConsumerId = UUID.randomUUID()
    val fileName = "testFile"
    val description = "testDescription"
    val data: Map<String, Map<String, String>> =
        mapOf(
            "study1" to
                mapOf(
                    "attribute1" to "bloomFilterCode1",
                    "attribute2" to "bloomFilterCode2",
                    "attribute3" to "bloomFilterCode3"),
            "study2" to
                mapOf(
                    "attribute1" to "bloomFilterCode4",
                    "attribute2" to "bloomFilterCode5",
                    "attribute3" to "bloomFilterCode6"))

    Mockito.`when`(hubPiiDataRepository.save(Mockito.any(HubPiiDataEntity::class.java)))
        .thenReturn(HubPiiDataEntity(userId = dataConsumerId))

    val result =
        piiDataService.uploadPIIData(keycloakId, dataConsumerId, fileName, description, data)

    val metadataCaptor = ArgumentCaptor.forClass(SatPiiDataMetadataEntity::class.java)
    Mockito.verify(satPiiDataMetadataRepository, Mockito.times(1)).save(metadataCaptor.capture())
    assertEquals(fileName, metadataCaptor.value.name)

    val attributesCaptor = ArgumentCaptor.forClass(SatPiiDataAttributesEntity::class.java)
    Mockito.verify(satPiiDataAttributesRepository, Mockito.times(6))
        .save(attributesCaptor.capture())

    val bloomfilterCaptor = ArgumentCaptor.forClass(SatPiiDataBloomfilterEntity::class.java)
    Mockito.verify(satPiiDataBloomfilterRepository, Mockito.times(6))
        .save(bloomfilterCaptor.capture())

    assertEquals(fileName, result.name)
    assertEquals("attribute1", attributesCaptor.allValues[0].attributeName)
    assertEquals("bloomFilterCode1", bloomfilterCaptor.allValues[0].bloomfilterCode)
  }
}
