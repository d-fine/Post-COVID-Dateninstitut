package org.datenmodell.datenmodellbackend.eurodatconfiguration;

import org.eurodat.eurodatdatamanagement.openApiClient.ApiClient;
import org.eurodat.eurodatdatamanagement.openApiClient.api.DataManagementResourceApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class EurodatDataManagementConfiguration {

  private static final String QUALIFIER_NAME = "eurodat-datamanagement-resource";

  @Value("${jks.base-path}")
  private String basePath;

  @Bean
  public DataManagementResourceApi dataManagementResourceApi(@Qualifier(QUALIFIER_NAME) ApiClient apiClient) {
    return new DataManagementResourceApi(apiClient);
  }

  @Bean(QUALIFIER_NAME)
  public ApiClient apiClient(RestClient restClient) {
    ApiClient apiClient = new ApiClient(restClient);
    apiClient.setBasePath(basePath);
    return apiClient;
  }
}
