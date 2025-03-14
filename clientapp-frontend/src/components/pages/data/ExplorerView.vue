<script setup lang="ts">
import type { ResearchDataInformation } from '@/generated/openapi';
import type Keycloak from "keycloak-js";

import { Button, Column, DataTable } from 'primevue'
import { useRoute } from 'vue-router';
import { inject, ref, onMounted } from 'vue'
import { assertDefined } from "@/utils/TypeScriptUtils";
import { getResearchDataSet, getAllResearchData } from '@/services/ApiClients';

interface DatasetsSchema {
  fileName: string;
  datasetId: string;
};

const route = useRoute().path;
const allResearchData = ref<ResearchDataInformation[]>([]);
var getKeycloakPromise = inject<() => Promise<Keycloak>>('getKeycloakPromise');

const updateAllResearchData = async () => {
  allResearchData.value = await getAllResearchData(assertDefined(getKeycloakPromise))
}

const downloadDataSet = async (researchDataId: string, name: string) => {
  const response = await getResearchDataSet(assertDefined(getKeycloakPromise), researchDataId);
  const blob = new Blob([response.file]);
  const link = document.createElement('a');
  link.href = URL.createObjectURL(blob);
  link.download = name;
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
  URL.revokeObjectURL(link.href);
}

onMounted(updateAllResearchData);
</script>

<template>
  <div class="flex-col">
    <div>
      <Button @click="updateAllResearchData" label="refresh research data" class="m-4"></Button>
    </div>
  </div>

  <div>
    <DataTable :value="allResearchData" class="max-w-7xl">
      <Column field="fileName" header="file"></Column>
      <Column field="description" header="description"></Column>
      <Column header="download">
        <template #body="slotProps">
          <Button label="download file" @click="downloadDataSet(slotProps.data.researchDataId, slotProps.data.fileName)" />
        </template>
      </Column>
      <Column field="researchDataId" header="view in browser">
        <template #body="slotProps">
          <RouterLink :to="{ path: '/data/datasets/id', query: { researchDataId: slotProps.data.researchDataId } }"> Go
            to dataset
          </RouterLink>
        </template>
      </Column>
    </DataTable>
  </div>
</template>
<style></style>
