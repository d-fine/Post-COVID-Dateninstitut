import type Keycloak from 'keycloak-js';
import { type Configuration, type EntityInformation, type ResearchDataInformation } from '@/generated/openapi';
import * as backendApis from '@/generated/openapi';
import { updateTokenAndItsExpiryTimestampAndStoreBoth } from '@/utils/SessionTimeoutUtils';
import axios, { type AxiosInstance } from 'axios';

interface ApiBackendClients {
  researchDataApi: backendApis.ResearchDataControllerApiInterface;
  userManagementApi: backendApis.UserManagementControllerApiInterface;
  transactionApi: backendApis.TransactionControllerApiInterface;
  piiDataApi: backendApis.PiiDataControllerApiInterface;
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
    const backendClientFactory = this.getClientFactory('http://localhost:8081/api');
    return {
      researchDataApi: backendClientFactory(backendApis.ResearchDataControllerApi),
      userManagementApi: backendClientFactory(backendApis.UserManagementControllerApi),
      transactionApi: backendClientFactory(backendApis.TransactionControllerApi),
      piiDataApi: backendClientFactory(backendApis.PiiDataControllerApi),
    };
  }

  private getClientFactory(basePath: string): ApiClientFactory {
    return (constructor) => {
      return new constructor(undefined, basePath, this.axiosInstance);
    };
  }
}

export async function postTransactionUploadData(
  getKeycloakPromise: () => Promise<Keycloak>,
  clientId: string,
  transactionId: string,
  tableName: string,
  researchDataId: string
): Promise<EntityInformation | null> {
  try {
    const transactionApi = new ApiClientProvider(getKeycloakPromise()).backendClients.transactionApi;
    return (await transactionApi.uploadTransactionResearchData(clientId, transactionId, tableName, researchDataId))
      .data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

type PossibleFileTypes = File | Blob;

interface ResearchDataSet {
  filename: string;
  file: PossibleFileTypes; // Replace 'any' with a more specific type if known
}

export async function postUploadReseachData(
  getKeycloakPromise: () => Promise<Keycloak>,
  fileName: string,
  dataConsumerUser: string,
  description: string,
  file: Blob
): Promise<EntityInformation | null> {
  try {
    const researchDataControllerApi = new ApiClientProvider(getKeycloakPromise()).backendClients.researchDataApi;
    const payload = new File([file], fileName, { type: file.type });
    return (await researchDataControllerApi.uploadResearchData(fileName, dataConsumerUser, description, payload)).data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

export async function postUploadPiiData(
  getKeycloakPromise: () => Promise<Keycloak>,
  fileName: string,
  dataConsumerUser: string,
  description: string,
  file: File
): Promise<EntityInformation | null> {
  try {
    const piiDataControllerApi = new ApiClientProvider(getKeycloakPromise()).backendClients.piiDataApi;
    return (await piiDataControllerApi.uploadPiiData(fileName, dataConsumerUser, description, file)).data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

export async function getAllResearchData(
  getKeycloakPromise: () => Promise<Keycloak>
): Promise<ResearchDataInformation[]> {
  try {
    const researchDataControllerApi = new ApiClientProvider(getKeycloakPromise()).backendClients.researchDataApi;
    return (await researchDataControllerApi.getAllResearchData()).data;
  } catch (error) {
    console.error(error);
    return [];
  }
}

export async function getResearchDataSet(
  getKeycloakPromise: () => Promise<Keycloak>,
  researchDataId: string
): Promise<ResearchDataSet | null> {
  try {
    const researchDataControllerApi = new ApiClientProvider(getKeycloakPromise()).backendClients.researchDataApi;
    const response = await researchDataControllerApi.getResearchDataSet(researchDataId);
    return { filename: response.headers['x-filename'], file: response.data };
  } catch (error) {
    console.error(error);
    return null;
  }
}

export async function postCreateProviderUser(
  getKeycloakPromise: () => Promise<Keycloak>,
  username: string,
  firstName: string,
  surname: string
): Promise<EntityInformation | null> {
  try {
    const userManagementControllerApi = new ApiClientProvider(getKeycloakPromise()).backendClients.userManagementApi;
    return (await userManagementControllerApi.createUser(username, firstName, surname)).data;
  } catch (error) {
    console.error(error);
    return null;
  }
}
