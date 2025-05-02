import type Keycloak from 'keycloak-js';
import { type Configuration, type DataAnalysis, type DataCatalogInformation } from '@/generated/openapi';
import axios, { type AxiosInstance } from 'axios';
import { updateTokenAndItsExpiryTimestampAndStoreBoth } from '@/utils/SessionTimeoutUtils';
import * as backendApis from '@/generated/openapi';
import { type InitializedTransaction, type TransactionResult } from '@/generated/openapi';

interface ApiBackendClients {
  transactionControllerApi: backendApis.TransactionControllerApiInterface;
  dataCatalogControllerApi: backendApis.DatacatalogControllerApiInterface;
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
      transactionControllerApi: backendClientFactory(backendApis.TransactionControllerApi),
      dataCatalogControllerApi: backendClientFactory(backendApis.DatacatalogControllerApi),
    };
  }

  private getClientFactory(basePath: string): ApiClientFactory {
    return (constructor) => {
      return new constructor(undefined, basePath, this.axiosInstance);
    };
  }
}

export async function getAllDataAnalysis(getKeycloakPromise: () => Promise<Keycloak>): Promise<DataAnalysis[]> {
  try {
    const transactionApi = new ApiClientProvider(getKeycloakPromise()).backendClients.transactionControllerApi;
    return (await transactionApi.getAllDataAnalysis()).data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

export async function postStartTransactionInfrastructure(
  getKeycloakPromise: () => Promise<Keycloak>,
  dataAnalysisId: string
): Promise<InitializedTransaction> {
  try {
    const transactionApi = new ApiClientProvider(getKeycloakPromise()).backendClients.transactionControllerApi;
    return (await transactionApi.setupTransactionInfrastructure(dataAnalysisId)).data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

export async function postTransactionProcessData(
  getKeycloakPromise: () => Promise<Keycloak>,
  transactionId: string
): Promise<TransactionResult> {
  try {
    const transactionApi = new ApiClientProvider(getKeycloakPromise()).backendClients.transactionControllerApi;
    return (await transactionApi.processTransactionData(transactionId)).data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

export async function getDataCatalog(
  getKeycloakPromise: () => Promise<Keycloak>
): Promise<Array<DataCatalogInformation>> {
  try {
    const dataCatalogControllerApi = new ApiClientProvider(getKeycloakPromise()).backendClients
      .dataCatalogControllerApi;
    return (await dataCatalogControllerApi.getNFDI4HealthDataCatalog()).data;
  } catch (error) {
    console.error(error);
    return [];
  }
}
