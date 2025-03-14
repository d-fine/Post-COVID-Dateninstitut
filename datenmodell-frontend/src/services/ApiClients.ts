import type Keycloak from 'keycloak-js';
import { type Configuration } from '@/generated/openapi';
import axios, { type AxiosInstance } from 'axios';
import { updateTokenAndItsExpiryTimestampAndStoreBoth } from '@/utils/SessionTimeoutUtils';
import * as backendApis from '@/generated/openapi';

interface ApiBackendClients {
  studyControllerApi: backendApis.StudyControllerApiInterface;
}

type ApiClientConstructor<T> = new (
  configuration: Configuration | undefined,
  basePath: string,
  axios: AxiosInstance
) => T;
type ApiClientFactory = <T>(constructor: ApiClientConstructor<T>) => T;

export class ApiClientProvider {
  private readonly keycloakPromise: Promise<Keycloak>;
  readonly axiosInstance: AxiosInstance;
  readonly backendClients: ApiBackendClients;

  constructor(keycloakPromise: Promise<Keycloak>) {
    this.keycloakPromise = keycloakPromise;
    this.axiosInstance = axios.create({});
    this.backendClients = this.constructBackendClients();
    this.registerAutoAuthenticatingAxiosInterceptor();
  }

  private registerAutoAuthenticatingAxiosInterceptor(): void {
    this.axiosInstance.interceptors.request.use(
      async (config) => {
        const bearerToken = await this.getBearerToken();
        if (bearerToken) {
          config.headers['Authorization'] = `Bearer ${bearerToken}`;
        }
        return config;
      },
      (error) => Promise.reject(error)
    );
  }

  private async getBearerToken(): Promise<string | undefined> {
    const keycloak = await this.keycloakPromise;
    if (keycloak.authenticated) {
      await updateTokenAndItsExpiryTimestampAndStoreBoth(keycloak);
      return keycloak.token;
    } else {
      return undefined;
    }
  }

  private constructBackendClients(): ApiBackendClients {
    const backendClientFactory = this.getClientFactory('/api');
    return {
      studyControllerApi: backendClientFactory(backendApis.StudyControllerApi)
    };
  }

  private getClientFactory(basePath: string): ApiClientFactory {
    return (constructor) => {
      return new constructor(undefined, basePath, this.axiosInstance);
    };
  }
}
