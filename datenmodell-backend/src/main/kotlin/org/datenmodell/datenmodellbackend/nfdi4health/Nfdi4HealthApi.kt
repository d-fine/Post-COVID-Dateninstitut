package org.datenmodell.datenmodellbackend.nfdi4health

import java.math.BigDecimal
import org.nfdi4health.api.SearchingResourcesApi
import org.nfdi4health.model.ApiResourcesPost200Response
import org.nfdi4health.model.ApiResourcesPostRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class Nfdi4HealthApi {

  @PreAuthorize("hasRole('ADMIN')")
  @Suppress("MagicNumber")
  fun getApiResourcesPostRequest(): ApiResourcesPost200Response? {
    val apiResourcesPostRequest =
        ApiResourcesPostRequest()
            .fq("(resource.classification.type:\"Dataset\")")
            .q("(resource.collection:\"Robert Koch Institute\")")
            .perPage(BigDecimal(30))
            .showFacets(false)
            .sortField(ApiResourcesPostRequest.SortFieldEnum.RELEVANCE)
            .sortOrder(ApiResourcesPostRequest.SortOrderEnum.ASC)
            .start(BigDecimal(0))

    val searchingResourcesApi = SearchingResourcesApi()
    return searchingResourcesApi.apiResourcesPost(apiResourcesPostRequest)
  }
}
