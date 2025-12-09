package org.datenmodell.datenmodellbackend.eurodatconfiguration;

import org.eurodat.eurodattransaction.openApiClient.ApiClient;
import org.eurodat.eurodattransaction.openApiClient.api.ClientResourceApi;
import org.eurodat.eurodattransaction.openApiClient.api.TransactionResourceApi;
import org.eurodat.eurodattransaction.openApiClient.api.WorkflowResourceApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/** Configuration for the EuroDaT transaction. */
@Configuration
@SuppressWarnings("java:S6830")
public class EurodatTransactionConfiguration {

  private static final String QUALIFIER_NAME = "eurodat-transaction-resource";

  @Value("${jks.base-path}")
  private String basePath;

  @Bean
  public ClientResourceApi clientResourceApi(@Qualifier(QUALIFIER_NAME) ApiClient apiClient) {
    return new ClientResourceApi(apiClient);
  }

  @Bean
  public WorkflowResourceApi workflowResourceApi(
      @Qualifier(QUALIFIER_NAME) ApiClient apiClient) {
    return new  WorkflowResourceApi(apiClient);
  }

  @Bean
  public TransactionResourceApi transactionResourceApi(
      @Qualifier(QUALIFIER_NAME) ApiClient apiClient) {
    return new TransactionResourceApi(apiClient);
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
