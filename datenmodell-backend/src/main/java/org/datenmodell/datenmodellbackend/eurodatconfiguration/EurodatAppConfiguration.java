package org.datenmodell.datenmodellbackend.eurodatconfiguration;

import org.eurodat.eurodatapp.openApiClient.ApiClient;
import org.eurodat.eurodatapp.openApiClient.api.AppResourceApi;
import org.eurodat.eurodatapp.openApiClient.api.ImageResourceApi;
import org.eurodat.eurodatapp.openApiClient.api.WorkflowRegistrationResourceApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/** Configuration for the EuroDaT app. */
@Configuration
@SuppressWarnings("java:S6830")
public class EurodatAppConfiguration {

  private static final String QUALIFIER_NAME = "eurodat-app-resource";

  @Value("${jks.base-path}")
  private String basePath;

  @Bean
  public AppResourceApi appResourceApi(@Qualifier(QUALIFIER_NAME) ApiClient apiClient) {
    return new AppResourceApi(apiClient);
  }

  @Bean
  public ImageResourceApi imageResourceApi(@Qualifier(QUALIFIER_NAME) ApiClient apiClient) {
    return new ImageResourceApi(apiClient);
  }

  @Bean
  public WorkflowRegistrationResourceApi workflowRegistrationResourceApi(
      @Qualifier(QUALIFIER_NAME) ApiClient apiClient) {
    return new WorkflowRegistrationResourceApi(apiClient);
  }

  /**
   * Builds an api client.
   *
   * @param restClient a REST client
   * @return an api client
   */
  @Bean(QUALIFIER_NAME)
  public ApiClient apiClient(RestClient restClient) {
    ApiClient apiClient = new ApiClient(restClient);
    apiClient.setBasePath(basePath);
    return apiClient;
  }
}
