import { ApiClientProvider } from '@/services/ApiClients';
import { type StudyInformation } from '@/generated/openapi';
import type Keycloak from 'keycloak-js';

export async function getStudies(getKeycloakPromise: () => Promise<Keycloak>): Promise<Array<StudyInformation>> {
  try {
    const studyControllerApi = new ApiClientProvider(getKeycloakPromise()).backendClients.studyControllerApi;
    return (await studyControllerApi.getAll()).data;
  } catch (error) {
    console.error(error);
    return [];
  }
}
