<script lang="ts">
import { Card, FileUpload, type FileUploadUploaderEvent } from 'primevue';
import PrimeButton from 'primevue/button';
import { postUploadReseachData } from '@/services/ApiClients';
import type Keycloak from 'keycloak-js';
import { inject } from 'vue';
import { assertDefined } from '@/utils/TypeScriptUtils';

interface CsvFile {
  header: string[];
  data: string[];
}

export default {
  setup() {
    const getKeycloakPromise = inject<() => Promise<Keycloak>>('getKeycloakPromise');
    return { getKeycloakPromise };
  },
  data() {
    return {
      fileNameDataMap: {} as Record<string, CsvFile>,
      notebook_logo: '/Nexus_Logo.png',
    };
  },
  methods: {
    async uploadFiles(event: FileUploadUploaderEvent) {
      let fileList = event.files;
      if (fileList instanceof File) fileList = [fileList];
      fileList.forEach((file) => {
        const reader = new FileReader();
        reader.onload = (e: ProgressEvent<FileReader>) => {
          const formData = new FormData();
          const arrayBuffer = e.target?.result;
          if (arrayBuffer) {
            formData.append('file', new Blob([arrayBuffer], { type: file.type }), file.name);
            const payload = new Blob([arrayBuffer], { type: file.type });
            postUploadReseachData(
              assertDefined(this.getKeycloakPromise),
              file.name,
              'postcovidclient',
              'test data',
              new File([payload], file.name, { type: payload.type })
            ).then((result) => {
              if (result == null) {
                this.$toast.add({ severity: 'error', summary: `failed to upload file ${file.name}`, life: 3000 });
              } else {
                this.$toast.add({ severity: 'success', summary: `Uploaded ${file.name}`, life: 3000 });
              }
            });
          }
        };
        reader.readAsArrayBuffer(file);
      });
    },
  },
  components: {
    FileUpload,
    PrimeButton,
    Card,
  },
};
</script>

<template>
  <div class="max-w-6xl w-full">
    <div class="pt-4 pb-4 items-center ml-26 flex">
      <div class="block">
        <Card class="white-p-card max-w-2xl text-4xl mt-15 uppercase p-5 font-bold">
          <template #content>
            <span class="text-(--df-orange) block text-center"> Daten Bereitstellen <br /> </span>
            <span> Forschung Ermöglichen </span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
      <div class="logo-container ml-50">
        <img :src="notebook_logo" alt="Logo" class="w-55 rounded-full bg-white -mb-23" />
      </div>
    </div>
    <div class="pt-2 pb-2 flex flex-row items-center text-1xl mt-15 ml-65 mb-25">
      <Card class="white-p-card font-bold pt-5 pb-5 pl-6 pr-6 w-105 text-lg">
        <template #content>
          Der Datentreuhänder ermöglicht die gemeinsame Analyse verteilter Daten. Sie als Datenhaltende behalten stets
          die Kontrolle.
        </template>
      </Card>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-5 pb-5">
      <div class="text-3xl text-left uppercase ml-25 text-(--df-orange)">
        <span> Daten zugänglich machen </span>
      </div>
    </div>

    <div class="flex justify-center mb-25 mt-5">
      <FileUpload name="demo[]" accept=".csv" :maxFileSize="1000000" :multiple="false" @uploader="uploadFiles">
        <template #header="FileUploadSelectEvent">
          <div class="flex-row h-100">
            <div class="text-2xl uppercase font-bold mb-5 text-(--df-orange)">Daten Bereitstellen</div>
            <div class="mb-5">Laden Sie Ihre Daten unkompliziert hoch und ermöglichen Sie deren Nutzung.</div>
            <div class="mb-5">
              <span class="font-bold"> Achtung: </span> Aktuell unterstüzten wir nur .csv Dateien!
              <br />
              Die Bereitstellung muss durch den Button "Upload" bestätigt werden.
            </div>
            <div class="text-1xl font-bold">Datei auswählen:</div>
            <div class="upload_button flex justify-center">
              <PrimeButton
                label="Upload"
                icon="pi pi-cloud-upload"
                @click="uploadFiles(FileUploadSelectEvent)"
                severity="secondary"
                class="h-11"
              />
            </div>
          </div>
        </template>
        <template #empty>
          <div class="h-25 flex justify-center mb-15 z-">
            <span class="upload-field border w-130 flex justify-center border-dashed flex-wrap">
              Datei ins Feld ziehen und ablegen.
            </span>
          </div>
        </template>
      </FileUpload>
    </div>

    <div class="white-mid-section mt-15 mb-25 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Weitere Informationen </span>
      </div>
      <div class="text-left ml-25 mr-25 w-220 font-normal">
        Laden Sie Ihre Daten unkompliziert hoch und ermöglichen Sie deren Nutzung für vielseitige Auswertungen.
        <br /><br />
        Unser System gewährleistet höchste Sicherheitsstandards und schützt Ihre Daten durch strenge
        Datenschutzmaßnahmen. Durch unseren geschützten Datenraum behalten Sie die volle Kontrolle über Ihre Datensätze,
        während Sie sie für Analysten, Forscher und andere interessierte Parteien zugänglich machen. Profitieren Sie von
        einer sicheren Umgebung, in der wertvolle Erkenntnisse gewonnen werden können, ohne Kompromisse bei der
        Sicherheit und Privatsphäre einzugehen.
      </div>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
