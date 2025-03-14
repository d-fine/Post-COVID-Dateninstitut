package org.datenmodell.datenmodellbackend.service.eurodat

import org.datenmodell.datenmodellbackend.eurodat.AppApi
import org.datenmodell.datenmodellbackend.eurodat.ImageApi
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EurodatTransactionInputService(
    private val appApi: AppApi,
    private val imageApi: ImageApi,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun register(
        appName: String,
        imageLocation: String,
    ): String {
        logger.debug(
            "Register the app with the parameters: appName is $appName, image location " +
                "is $imageLocation",
        )
        appApi.registerApp(appName)
        return imageApi.registerImage(
            appName,
            imageLocation,
        )
    }
}
