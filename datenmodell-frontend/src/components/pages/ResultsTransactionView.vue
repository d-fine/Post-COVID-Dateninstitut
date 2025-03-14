<script lang="ts">
import type { StudyInformation } from '@/generated/openapi';
import { getStudies } from '@/utils/SearchStudyData';
import DataTable from 'primevue/datatable'
import Column from 'primevue/column';
import Button from 'primevue/button';
import {inject, ref} from 'vue'
import type Keycloak from "keycloak-js";
import {assertDefined} from "@/utils/TypeScriptUtils";

interface ResultSchema {
  name: string;
  status: string; // Running, Computed, Completed
  download_id: string; // use for API request to fetch computed data
};

export default {
  setup() {
    const resultsTable = ref<ResultSchema[]>([]);
    const getResults = () => {
      // Replace with API call to get all results and their status
      resultsTable.value = [
        {
          name: "Study Data",
          status: "Computed",
          download_id: 'studies'
        },
        {
          name: "Other data",
          status: "Completed",
          download_id: "empty_dummy"
        }
      ]
    };
    var getKeycloakPromise = inject<() => Promise<Keycloak>>('getKeycloakPromise');
    // getResults(); // Uncomment for initial load of results, currently there to test refresh button
    return { resultsTable, getResults, getKeycloakPromise }
  },

  data() {
    return {
      studies: new Array<StudyInformation>()
    };
  },
  methods: {
    routeNewTransaction() {
      alert('route to newTransaction page')
    },

    async getAllStudies() {
      this.studies = await getStudies(assertDefined(this.getKeycloakPromise));
    },
    async downloadResultData(name: string) {
      // Hardcoded with studies tables, replace to use columns from resultTable to fetch correct table
      let data = await getStudies(assertDefined(this.getKeycloakPromise));
      let header = Object.keys(data[0]).join(',') + '\n'
      let rows = data.map(item => Object.values(item).join(',')).join('\n');

      const anchor = document.createElement('a');
      anchor.href = 'data:text/csv;charset=utf-8,' + encodeURIComponent(header + rows);
      anchor.target = '_blank';
      anchor.download = `${name}.csv`;
      anchor.click();
    }

  },
  components: {
    Button,
    DataTable,
    Column,
  }
};
</script>

<template>
  <div class="centered">
    <p>
      <Button label="Start new transaction" @click="routeNewTransaction" />
      <Button label="Refresh results" @click="getResults" />
    </p>
    <div>
      <DataTable :value="resultsTable" showGridlines tableStyle="min-width: 50rem">
        <Column field="name" header="name"></Column>
        <Column field="status" header="Status"></Column>
        <Column>
          <template #body="{ data }">
            <div v-if="data.status === 'Computed'">
              {{ data }}
              <Button label="Download" @click="downloadResultData(data.name)" />
            </div>
            <div v-else-if="data.status === 'Completed'">
              Completed transaction can no longer be download, recompute the result
            </div>
            <div v-else>
              Running
            </div>
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>

<style>
.centered {
  display: flex;
  justify-content: center;
  flex-direction: column;
}
</style>
