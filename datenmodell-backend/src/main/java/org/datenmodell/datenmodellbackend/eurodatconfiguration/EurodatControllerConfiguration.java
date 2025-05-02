package org.datenmodell.datenmodellbackend.eurodatconfiguration;

import org.eurodat.eurodatcontroller.openApiClient.ApiClient;
import org.eurodat.eurodatcontroller.openApiClient.api.ClientResourceApi;
import org.eurodat.eurodatcontroller.openApiClient.api.TransactionResourceApi;
import org.eurodat.eurodatcontroller.openApiClient.api.WorkflowResourceApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/** Configuration for the EuroDaT controller. */
@Configuration
@SuppressWarnings("java:S6830")
public class EurodatControllerConfiguration {

  private static final String QUALIFIER_NAME = "eurodat-controller-resource";

  @Value("${jks.base-path}")
  private String basePath;

  @Bean
  public ClientResourceApi clientResourceApi(@Qualifier(QUALIFIER_NAME) ApiClient apiClient) {
    return new ClientResourceApi(apiClient);
  }

  @Bean
  public TransactionResourceApi transactionResourceApi(
      @Qualifier(QUALIFIER_NAME) ApiClient apiClient) {
    return new TransactionResourceApi(apiClient);
  }

  @Bean
  public WorkflowResourceApi workflowResourceApi(@Qualifier(QUALIFIER_NAME) ApiClient apiClient) {
    return new WorkflowResourceApi(apiClient);
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
