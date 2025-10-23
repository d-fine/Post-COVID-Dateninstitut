package org.datenmodell.datenmodellbackend.eurodat

import org.datenmodell.datenmodellbackend.configuration.AppProperties
import org.eurodat.eurodatapp.openApiClient.api.ImageResourceApi
import org.eurodat.eurodatapp.openApiClient.model.ImageRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ImageApi(
    @Autowired private val imageResourceApi: ImageResourceApi,
    @Autowired var appProperties: AppProperties,
) {

  fun registerImage(
      appName: String,
      imageLocation: String,
  ): String {
    val imageRequest =
        ImageRequest()
            .location(imageLocation)
            .registryUserName(appProperties.imageRepoUserId)
            .registryPassword(appProperties.imageRepoUserPassword)

    return imageResourceApi
        .createImage(
            appName,
            imageRequest,
        )
        .id
  }

  fun deleteImage(
      appName: String,
      imageId: String,
  ) {
    imageResourceApi.deleteImage(appName, imageId)
  }
}
