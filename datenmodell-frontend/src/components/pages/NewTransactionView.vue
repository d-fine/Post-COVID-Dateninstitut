<script lang="ts">

import { ref } from 'vue';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Button from 'primevue/button'
import { useConfirm } from 'primevue/useconfirm';
import { useToast } from 'primevue/usetoast';
import { ConfirmDialog } from 'primevue';


interface DatasetMetadata {
    name: string,
    provider: string
    short_info: string,
}

// Contains information to select record linkage logic
interface AppLogic {
    label: string,
    short_info: string,
}

export default {
    setup() {
        const confirm = useConfirm();
        const toast = useToast();

        const showTemplate = (selectedProcessingLogic: string | null) => {
            confirm.require({
                group: 'templating',
                header: 'Datentransaktion starten',
                icon: 'pi pi-exclamation-circle',
                rejectProps: {
                    label: 'Cancel',
                    icon: 'pi pi-times',
                    outlined: true,
                },
                acceptProps: {
                    label: 'Start transaction',
                    icon: 'pi pi-check',
                },
                accept: () => {
                    toast.add({ severity: 'info', summary: 'Started', detail: 'You have started the transaction', });
                },
                reject: () => {
                    toast.add({ severity: 'error', summary: 'Canceled', detail: 'Transaction has been cancelled', });
                }
            });
        };

        const postTransactionStart = (logic: AppLogic, datasets: DatasetMetadata[]) => {
            const sets = datasets.map(dataset => dataset.name).join(', ');
            alert(`Starting transaction with ${logic.label} and datasets ${sets} `);
        }

        const getAllDatasets = () => {
            // Replace with API call to fetch all data sets that the user is allowed to process
            return [
                {
                    name: 'Datensatz A',
                    provider: 'Datenhalter A',
                    short_info: 'Ich bin interessant',
                },
                {
                    name: 'Datensatz B',
                    provider: 'Datenhalter B',
                    short_info: 'Ich bin interessant',
                },
            ]
        }

        const getAllLogicApps = () => {
            return [
                {
                    label: 'Identity I',
                    short_info: 'Performs no operation on dataset'
                },
                {
                    label: 'Identity II',
                    short_info: 'Also performs no operation on dataset'
                },
            ]
        }

        const allDataSets = ref(getAllDatasets())
        const allLogicApps = ref(getAllLogicApps());
        const selectedDatasets = ref(null);
        const selectedProcessingLogic = ref(null);

        return {
            getAllDatasets, getAllLogicApps, postTransactionStart, showTemplate,
            allDataSets, allLogicApps, selectedDatasets, selectedProcessingLogic,
            confirm, toast,
        }
    },

    components: {
        DataTable,
        Column,
        Button,
        ConfirmDialog,
    },
};
</script>


<template>
    <div class="centered">
        <div> The following processing logic is available:</div>
        <div class="datasets">
            <DataTable :value="allLogicApps" showGridlines tableStyle="min-width: 50rem" selectionMode="single"
                v-model:selection="selectedProcessingLogic">
                <Column field="label" header="name"></Column>
                <Column field="short_info" header="info"></Column>
            </DataTable>
            {{ selectedProcessingLogic == null ? 'null' : selectedProcessingLogic }}
        </div>
        <div>The following datasets are available for you </div>
        <div class="datasets">
            <DataTable :value="allDataSets" showGridlines tableStyle="min-width: 50rem" selectionMode="multiple"
                v-model:selection="selectedDatasets">
                <Column selectionMode="multiple"></Column>
                <Column field="name" header="name"></Column>
                <Column field="provider" header="provider"></Column>
                <Column field="short_info" header="info"></Column>
            </DataTable>
        </div>
        {{ selectedDatasets }}

        <ConfirmDialog group="templating">
            <template #message>
                <div>
                    <div>
                        You are using {{ selectedProcessingLogic.label }} to process the following data sets:
                    </div>
                    <div>
                        <DataTable :value="selectedDatasets" showGridlines tableStyle="min-width: 50rem">
                            <Column field="name" header="Dataset"></Column>
                            <Column field="provider" header="Data provider"></Column>
                            <Column field="short_info" header="Info"></Column>
                        </DataTable>
                    </div>
                </div>
            </template>
        </ConfirmDialog>

        <div class="center">
            <Button label="Start transaction" :disabled="!selectedProcessingLogic || selectedDatasets.length === 0"
                @click="showTemplate(selectedProcessingLogic)" />
            <Toast ref="toast" />
        </div>
    </div>
</template>

<style scoped>
.datasets {
    width: 100%;
    padding-top: 1rem;
    padding-bottom: 1rem;
}

.center {
    margin: auto;
}

.centered {
    display: flex;
    flex-direction: column;
}
</style>
