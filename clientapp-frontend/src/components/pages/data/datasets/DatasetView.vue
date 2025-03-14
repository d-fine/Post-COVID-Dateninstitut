<script setup lang="ts">
import { useRoute } from 'vue-router';
import { ref, inject } from 'vue';
import { Column, DataTable } from 'primevue'
import type Keycloak from "keycloak-js";
import { getResearchDataSet } from '@/services/ApiClients';
import { assertDefined } from '@/utils/TypeScriptUtils';
import { parseCSV, transformToPrimevueDataTable } from '@/utils/Utils';

var getKeycloakPromise = inject<() => Promise<Keycloak>>('getKeycloakPromise');
const route = useRoute();
const fileData = ref({})
const fetchData = async () => {
  const response = await getResearchDataSet(assertDefined(getKeycloakPromise), route.query.researchDataId);
  const parsed = parseCSV(response.file)
  fileData.value = {
    header: parsed.header,
    data: transformToPrimevueDataTable(parsed.header, parsed.data)
  }
}
fetchData();
</script>


<template>
  <div class="m-4">
    <DataTable :value="fileData.data" class="mt-4 mb-4 max-w-7xl">
      <Column v-for="col in fileData.header" :field="col" :header="col"></Column>
    </DataTable>
  </div>
</template>
