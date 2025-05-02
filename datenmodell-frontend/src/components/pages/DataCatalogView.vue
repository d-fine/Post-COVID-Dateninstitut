<script setup lang="ts">
import type { DataCatalogInformation } from '@/generated/openapi';
import type Keycloak from 'keycloak-js';

import { inject, ref, onMounted } from 'vue';
import { Button, Column, DataTable } from 'primevue';
import { getDataCatalog } from '@/services/ApiClients';
import { assertDefined } from '@/utils/TypeScriptUtils';

const dataCatalog = ref<DataCatalogInformation[]>([]);
const getKeycloakPromise = inject<() => Promise<Keycloak>>('getKeycloakPromise');

const updateDataCatalog = async () => {
  dataCatalog.value = await getDataCatalog(assertDefined(getKeycloakPromise));
};

onMounted(updateDataCatalog);
</script>

<template>
  <div class="flex-col">
    <div>
      <Button @click="updateDataCatalog" label="refresh data catalog" class="m-4"></Button>
    </div>
  </div>

  <div>
    <h1>Datenkatalog</h1>
    <DataTable :value="dataCatalog" class="max-w-7xl">
      <Column field="id" header="ID" />
      <Column field="title" header="Title" />
      <Column field="content" header="Content" />
      <Column field="dataProvider" header="Data Provider" />
      <Column field="link" header="Link" />
    </DataTable>
  </div>
</template>
