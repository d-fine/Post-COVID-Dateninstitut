<script setup lang="ts">
import type { ResearchDataInformation } from '@/generated/openapi';
import type Keycloak from 'keycloak-js';

import { Button, Column, DataTable, Dialog, Card } from 'primevue';
import { inject, ref, onMounted } from 'vue';
import { assertDefined } from '@/utils/TypeScriptUtils';
import { getResearchDataSet, getAllResearchData } from '@/services/ApiClients';

import DatasetView from './datasets/DatasetView.vue';

const euroDatUploadVisible = ref(false);
const euroDatUploadDatasetName = ref('');
const euroDatUploadDatasetId = ref('');
const notebook_logo = '/Nexus_Logo.png';

const setEurodatUploadDialog = (researchDataId: string, name: string) => {
  euroDatUploadDatasetName.value = name;
  euroDatUploadDatasetId.value = researchDataId;
  euroDatUploadVisible.value = true;
};

const allResearchData = ref<ResearchDataInformation[]>([]);
const getKeycloakPromise = inject<() => Promise<Keycloak>>('getKeycloakPromise');

const updateAllResearchData = async () => {
  allResearchData.value = await getAllResearchData(assertDefined(getKeycloakPromise));
};

const downloadDataSet = async (researchDataId: string, name: string) => {
  const response = await getResearchDataSet(assertDefined(getKeycloakPromise), researchDataId);
  if (response) {
    const blob = new Blob([response.file]);
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = name;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    URL.revokeObjectURL(link.href);
  }
};

onMounted(updateAllResearchData);
</script>

<template>
  <div class="max-w-6xl w-full">
    <div class="pt-4 pb-4 items-center ml-26 flex">
      <div class="block">
        <Card class="white-p-card max-w-2xl text-4xl mt-15 uppercase p-5 font-bold">
          <template #content>
            <span class="text-(--df-orange) block text-center"> Bereitgestellte Daten </span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
      <div class="logo-container ml-50">
        <img :src="notebook_logo" alt="Logo" class="w-55 rounded-full bg-white -mb-23" />
      </div>
    </div>
    <div class="pt-2 pb-2 flex flex-row items-center text-1xl mt-15 ml-85 mb-25">
      <Card class="white-p-card font-bold pt-5 pb-5 pl-6 pr-6 w-73 text-lg">
        <template #content>
          Behalten Sie den Überblick über Ihre Daten und deren Nutzung. <br />
          Einfach, transparent, sicher.
        </template>
      </Card>
    </div>

    <div class="white-mid-section mt-15 mb-10 pt-5 pb-5">
      <div class="text-3xl text-left uppercase ml-25 text-(--df-orange)">
        <span> Übersicht über Ihre Daten </span>
      </div>
    </div>

    <div class="ml-25 mr-25 mb-25">
      <div class="mb-10 text-(--df-dark-blue)">
        Hier finden Sie einen Überblick über die von Ihnen bereitgestellten Daten.
        <br /><br />
        Unser System gewährleistet höchste Sicherheitsstandards und schützt Ihre Daten durch strenge
        Datenschutzmaßnahmen. Durch unseren geschützten Datenraum behalten Sie die volle Kontrolle über Ihre Datensätze,
        während Sie sie für Analysten, Forscher und andere interessierte Parteien zugänglich machen. Profitieren Sie von
        einer sicheren Umgebung, in der wertvolle Erkenntnisse gewonnen werden können, ohne Kompromisse bei der
        Sicherheit und Privatsphäre einzugehen.
      </div>
      <Dialog
        v-model:visible="euroDatUploadVisible"
        class="modal-backdrop"
        header="DATEN BEREITSTELLEN"
        :style="{ width: '27rem' }"
      >
        <div>Dateiname: {{ euroDatUploadDatasetName }}</div>
        <DatasetView :datasetId="euroDatUploadDatasetId" />
      </Dialog>
      <DataTable :value="allResearchData" class="max-w-7xl">
        <Column field="fileName" header="Dateiname"></Column>
        <Column field="description" header="Beschreibung"></Column>
        <Column header="Download">
          <template #body="slotProps">
            <Button
              label="Download"
              @click="downloadDataSet(slotProps.data.researchDataId, slotProps.data.fileName)"
              class="white-button"
            />
          </template>
        </Column>
        <Column header="Bereitstellen">
          <template #body="slotProps">
            <Button
              label="EuroDaT"
              @click="setEurodatUploadDialog(slotProps.data.researchDataId, slotProps.data.fileName)"
              class="white-button"
            />
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
