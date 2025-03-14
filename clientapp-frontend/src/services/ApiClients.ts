import type Keycloak from 'keycloak-js'
import { type Configuration, type EntityInformation, type ResearchDataInformation } from '@/generated/openapi';
import * as backendApis from '@/generated/openapi';
import { updateTokenAndItsExpiryTimestampAndStoreBoth } from '@/utils/SessionTimeoutUtils';
import axios, { type AxiosInstance } from 'axios';


interface ApiBackendClients {
  researchDataApi: backendApis.ResearchDataControllerApiInterface,
  userManagementApi: backendApis.UserManagementControllerApiInterface,
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
    const backendClientFactory = this.getClientFactory('http://localhost:8080/api');
    return {
      researchDataApi: backendClientFactory(backendApis.ResearchDataControllerApi),
      userManagementApi: backendClientFactory(backendApis.UserManagementControllerApi)
    };
  }

  private getClientFactory(basePath: string): ApiClientFactory {
    return (constructor) => {
      return new constructor(undefined, basePath, this.axiosInstance);
    };
  }
}


export async function postUploadReseachData(
  getKeycloakPromise: () => Promise<Keycloak>,
  fileName: string,
  dataConsumerUser: string,
  description: string,
  file: any): Promise<EntityInformation> {
  try {
    const researchDataControllerApi = new ApiClientProvider(getKeycloakPromise()).backendClients.researchDataApi;
    return (
      await researchDataControllerApi.uploadResearchData(fileName, dataConsumerUser, description, file)
    ).data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

export async function getAllResearchData(
  getKeycloakPromise: () => Promise<Keycloak>): Promise<ResearchDataInformation[]> {
  try {
    const researchDataControllerApi = new ApiClientProvider(getKeycloakPromise()).backendClients.researchDataApi;
    return (
      await researchDataControllerApi.getAllResearchData()
    ).data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

export async function getResearchDataSet(
  getKeycloakPromise: () => Promise<Keycloak>,
  researchDataId: string,
): Promise<any> {
  try {
    const researchDataControllerApi = new ApiClientProvider(getKeycloakPromise()).backendClients.researchDataApi;
    const response = await researchDataControllerApi.getResearchDataSet(researchDataId);
    return { filename: response.headers['x-filename'], file: response.data }
  } catch (error) {
    console.error(error);
    return null;
  }
}

export async function postCreateProviderUser(
  getKeycloakPromise: () => Promise<Keycloak>,
  username: string,
  firstName: string,
  surname: string): Promise<EntityInformation> {
  try {
    const userManagementControllerApi = new ApiClientProvider(getKeycloakPromise()).backendClients.userManagementApi;
    return (
      await userManagementControllerApi.createUser(username, firstName, surname)
    ).data;
  } catch (error) {
    console.error(error);
    return null;
  }
}