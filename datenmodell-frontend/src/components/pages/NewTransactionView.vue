<script setup lang="ts">
import { ref, inject, onMounted } from 'vue';
import Keycloak from 'keycloak-js';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Button from 'primevue/button';
import { Card } from 'primevue';
import { useConfirm } from 'primevue/useconfirm';
import { useToast } from 'primevue/usetoast';
import { ConfirmDialog } from 'primevue';
import ProgressSpinner from 'primevue/progressspinner';

import { getAllDataAnalysis } from '@/services/ApiClients';
import { assertDefined } from '@/utils/TypeScriptUtils';
import { type TransactionResult, type DataAnalysis, type InitializedTransaction } from '@/generated/openapi';
import { postTransactionProcessData, postStartTransactionInfrastructure } from '@/services/ApiClients';

const confirm = useConfirm();
const toast = useToast();
const notebook_logo = '/Logo_Rechner.png';

const showTemplate = (dataAnalysis: DataAnalysis) => {
  confirm.require({
    group: 'templating',
    header: 'Datentransaktion starten',
    icon: 'pi pi-exclamation-circle',
    rejectProps: {
      label: 'ABBRECHEN',
      icon: 'pi pi-times',
      outlined: true,
    },
    acceptProps: {
      label: 'STARTE TRANSAKTION >',
    },
    accept: () => {
      postTransactionStart(dataAnalysis.id);
      toast.add({ severity: 'info', summary: 'Started', detail: 'Transaktion wurde gestartet!', life: 3000 });
    },
    reject: () => {
      toast.add({ severity: 'error', summary: 'Canceled', detail: 'Transaktionsstart wurde abgebrochen!', life: 3000 });
    },
  });
};

const getKeycloakPromise = inject<() => Promise<Keycloak>>('getKeycloakPromise');
const awaitingTransactionResult = ref<boolean>(false);
const allDataAnalysis = ref<DataAnalysis[]>([]);
const updateAllAnalysis = async () => {
  allDataAnalysis.value = await getAllDataAnalysis(assertDefined(getKeycloakPromise));
};
const selectedDataAnalysis = ref<DataAnalysis | null>(null);
const transactionInProgress = ref<InitializedTransaction | null>(null);
const result = ref<TransactionResult | null>(null);

const postTransactionStart = async (dataAnalysisId: string) => {
  awaitingTransactionResult.value = true;
  transactionInProgress.value = await postStartTransactionInfrastructure(
    assertDefined(getKeycloakPromise),
    dataAnalysisId
  );
  awaitingTransactionResult.value = false;
  result.value = null;
};

const postTransactionContinue = async () => {
  awaitingTransactionResult.value = true;
  const response = await postTransactionProcessData(
    assertDefined(getKeycloakPromise),
    assertDefined(transactionInProgress.value).transactionId
  );
  awaitingTransactionResult.value = false;
  result.value = response;
};

const InitializedTransactionAsDataTable = (initializedTransaction: InitializedTransaction) => {
  return [
    { property: 'transactionId', value: initializedTransaction.transactionId },
    { property: 'eurodatTransactionId', value: initializedTransaction.eurodatTransactionId },
    { property: 'eurodatClientId', value: initializedTransaction.eurodatClientId },
  ];
};

const downloadResultStringAsFile = () => {
  if (result.value) {
    const blob = new Blob([result.value.csvStr], { type: 'text/plain' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'result.json';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
    transactionInProgress.value = null;
  }
};

onMounted(updateAllAnalysis);
</script>

<template>
  <div class="max-w-6xl w-full">
    <div class="pt-4 pb-4 items-center ml-26 flex">
      <div class="block">
        <Card class="white-p-card max-w-2xl text-4xl mt-15 uppercase p-5 font-bold">
          <template #content>
            <span class="text-(--df-orange)"> Transaktion </span>
            <span> Starten</span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
      <div class="logo-container ml-60">
        <img :src="notebook_logo" alt="Logo" class="w-55 rounded-full bg-white -mb-55" />
      </div>
    </div>
    <div class="pt-2 pb-2 flex flex-row items-center text-1xl mt-15 ml-65 mb-25">
      <Card class="white-p-card font-bold pt-5 pb-5 pl-6 pr-6 w-89 text-lg">
        <template #content>
          Hier können Sie Daten verknüpfen und Ihre Analysen im geschützten Umfeld unseres Datentreuhänders durchführen.
        </template>
      </Card>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Transaktion durchführen </span>
      </div>
      <div class="text-left ml-25 mr-25 w-220 font-normal">
        Bitte beachten Sie, dass Ihnen ausschließlich durch die Datenhaltenden freigegebene Daten zur Verfügung stehen.
        Um mit Ihrer Analyse zu beginnen, wählen Sie bitte die spezifischen Datensätze aus, die Sie nutzen möchten. So
        stellen wir sicher, dass alle Nutzungen den geltenden Sicherheits- und Datenschutzrichtlinien entsprechen.
      </div>
    </div>

    <div>
      <div class="text-3xl text-left uppercase ml-25 mb-7 text-(--df-orange)">
        <span> Transaktion Starten </span>
      </div>
      <div class="text-left mb-7 ml-25 mr-25 font-normal">
        Um eine Transaktion durchführen zu können, wähle Sie bitte eine EuroDaT App Logik aus.
      </div>
    </div>

    <div class="ml-25 mr-25">
      <div class="pt-4 pb-4 w-full">
        <DataTable
          :value="allDataAnalysis"
          showGridlines
          tableStyle="min-width: 50rem"
          selectionMode="single"
          v-model:selection="selectedDataAnalysis"
        >
          <Column field="name" header="App Name"></Column>
          <Column field="description" header="Beschreibung"></Column>
        </DataTable>
      </div>

      <div class="ml-25 mr-25 flex justify-center mb-25 mt-5">
        <Button
          class="navigation_button w-[240px]! h-9"
          label="TRANSAKTION STARTEN >"
          :disabled="awaitingTransactionResult || !selectedDataAnalysis || !(transactionInProgress == null)"
          @click="showTemplate(assertDefined(selectedDataAnalysis))"
        />
      </div>

      <div v-if="transactionInProgress == null && awaitingTransactionResult" class="mb-25 flex flex-center">
        <ProgressSpinner />
      </div>

      <div v-if="!(transactionInProgress == null)" class="pb-4 -mt-15">
        <div>
          <div class="pb-4 pt-4">Die folgende Transaktion wurde gestartet:</div>
          <DataTable :value="InitializedTransactionAsDataTable(transactionInProgress)" tableStyle="min-width: 50rem">
            <Column field="property" header="Transaktionseigenschaft"></Column>
            <Column field="value" header="Wert"></Column>
          </DataTable>
        </div>
        <div v-if="!(result == null)" class="ml-25 mr-25 flex justify-center mb-25 mt-5">
          <div>
            <Button
              class="navigation_button w-[240px]! h-9"
              label="ERGEBNIS RUNTERLADEN >"
              @click="downloadResultStringAsFile"
            />
          </div>
        </div>
        <div v-else class="ml-25 mr-25 flex justify-center mb-25 mt-5">
          <Button
            class="navigation_button w-[240px]! h-9"
            label="TRANSAKTION FORTSETZEN >"
            :disabled="awaitingTransactionResult"
            @click="postTransactionContinue"
          />
        </div>
      </div>

      <div v-if="awaitingTransactionResult && result == null" class="mb-25 flex flex-center">
        <ProgressSpinner />
      </div>

      <ConfirmDialog group="templating">
        <template #message>
          <div>
            <div mb-5>Sie nutzen {{ selectedDataAnalysis?.name }} um eine EuroDaT Transaktion zu starten.</div>
          </div>
        </template>
      </ConfirmDialog>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
