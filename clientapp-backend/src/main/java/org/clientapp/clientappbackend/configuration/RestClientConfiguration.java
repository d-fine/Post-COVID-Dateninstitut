package org.clientapp.clientappbackend.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.function.Function;
import org.eurodat.ApiClient;
import org.eurodat.api.ClientResourceApi;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.NimbusJwtClientAuthenticationParametersConverter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest;
import org.springframework.security.oauth2.client.endpoint.RestClientClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/** The REST client configuration. */
@Configuration
public class RestClientConfiguration {

  private static final String EURODAT_NAME = "eurodat";

  @Value("${jks.base-path}")
  private String basePath;

  @Value("${jks.file-path}")
  private String jksFilePath;

  @Value("${jks.certificate-alias}")
  private String certificateAlias;

  @Value("${jks.password}")
  private String jksPassword;

  /**
   * Builds the client resource api.
   *
   * @param apiClient an api client
   * @return the client resource api
   */
  @Bean
  public ClientResourceApi clientResourceApi(@Qualifier(EURODAT_NAME) ApiClient apiClient) {
    return new ClientResourceApi(apiClient);
  }

  /**
   * Builds an api client.
   *
   * @param restClient a REST client
   * @return an api client
   */
  @Bean(EURODAT_NAME)
  public ApiClient apiClient(RestClient restClient) {
    ApiClient apiClient = new ApiClient(restClient);
    apiClient.setBasePath(basePath);
    return apiClient;
  }

  @Bean
  public RestClient restClient(Interceptor interceptor) {
    return RestClient.builder().requestInterceptor(interceptor).build();
  }

  /** Custom request interceptor to inject a constant client registration id into the request. */
  @Component
  public class Interceptor implements ClientHttpRequestInterceptor {

    private static final Authentication ANONYMOUS_AUTHENTICATION =
        new AnonymousAuthenticationToken(
            "anonymous", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public Interceptor(OAuth2AuthorizedClientManager authorizedClientManager) {
      this.authorizedClientManager = authorizedClientManager;
    }

    @NotNull
    @Override
    public ClientHttpResponse intercept(
        @NotNull HttpRequest request,
        @NotNull byte[] body,
        @NotNull ClientHttpRequestExecution execution)
        throws IOException {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication == null) {
        authentication = ANONYMOUS_AUTHENTICATION;
      }
      OAuth2AuthorizeRequest req =
          OAuth2AuthorizeRequest.withClientRegistrationId(EURODAT_NAME)
              .principal(authentication.getName())
              .build();
      OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(req);
      if (authorizedClient == null) {
        return execution.execute(request, body);
      }
      OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
      request.getHeaders().setBearerAuth(accessToken.getTokenValue());
      return execution.execute(request, body);
    }
  }

  /**
   * Builds a token client for OAuth2 access token client credentials grant request workflows.
   *
   * @return an OAuth2AccessTokenResponseClient
   * @throws Exception in case of failure
   */
  @Bean
  public OAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest> webClientTokenClient()
      throws Exception {

    RSAPrivateKey privateKey =
        JksReader.readRsaPrivateKeyFromJks(jksFilePath, certificateAlias, jksPassword);
    RSAPublicKey publicKey =
        JksReader.readRsaPublicKeyFromJks(jksFilePath, certificateAlias, jksPassword);
    RestClientClientCredentialsTokenResponseClient tokenResponseClient =
        new RestClientClientCredentialsTokenResponseClient();
    Function<ClientRegistration, JWK> jwkResolver =
        clientRegistration -> {
          if (clientRegistration
              .getClientAuthenticationMethod()
              .equals(ClientAuthenticationMethod.PRIVATE_KEY_JWT)) {
            return new RSAKey.Builder(publicKey).privateKey(privateKey).build();
          }
          return null;
        };

    tokenResponseClient.addParametersConverter(
        new NimbusJwtClientAuthenticationParametersConverter<>(jwkResolver));
    return tokenResponseClient;
  }

  /**
   * Builds an authorized client manager for OAuth2.
   *
   * @param clientRegistrationRepository a ClientRegistrationRepository
   * @param authorizedClientService an OAuth2AuthorizedClientService
   * @param tokenResponseClient an OAuth2AccessTokenResponseClient
   * @return an OAuth2AuthorizedClientManager
   */
  @Bean
  public OAuth2AuthorizedClientManager authorizedClientManager(
      ClientRegistrationRepository clientRegistrationRepository,
      OAuth2AuthorizedClientService authorizedClientService,
      OAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest> tokenResponseClient) {

    OAuth2AuthorizedClientProvider authorizedClientProvider =
        OAuth2AuthorizedClientProviderBuilder.builder()
            .clientCredentials(b -> b.accessTokenResponseClient(tokenResponseClient))
            .build();

    AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
        new AuthorizedClientServiceOAuth2AuthorizedClientManager(
            clientRegistrationRepository, authorizedClientService);
    authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

    return authorizedClientManager;
  }
}
