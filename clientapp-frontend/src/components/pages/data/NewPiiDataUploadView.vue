<script lang="ts">
import {
  FileUpload,
  Card,
  type FileUploadSelectEvent,
  type FileUploadRemoveEvent,
  type FileUploadUploaderEvent,
} from 'primevue';
import PrimeButton from 'primevue/button';

import { postUploadPiiData } from '@/services/ApiClients';
import type Keycloak from 'keycloak-js';
import { inject } from 'vue';
import { assertDefined } from '@/utils/TypeScriptUtils';

export default {
  setup() {
    const getKeycloakPromise = inject<() => Promise<Keycloak>>('getKeycloakPromise');
    return { getKeycloakPromise };
  },
  data() {
    return {
      fileNames: [] as string[],
      notebook_logo: '/Nexus_Logo.png',
    };
  },
  methods: {
     
    async uploadFiles(event: FileUploadUploaderEvent) {
      let fileList = event.files;
      if (fileList instanceof File) fileList = [fileList];
      for (const file of fileList) {
        const reader = new FileReader();
        reader.onload = async () => {
          const response = await postUploadPiiData(
            assertDefined(this.getKeycloakPromise),
            file.name,
            'postcovidclient',
            'test data',
            file
          );

          if (response == null) {
            this.$toast.add({ severity: 'error', summary: `Failed to upload file ${file.name}`, life: 3000 });
          } else {
            this.$toast.add({ severity: 'success', summary: `Uploaded ${file.name}`, life: 3000 });
          }
        };
        reader.readAsArrayBuffer(file);
      }
    },

    onClear() {
      this.fileNames = [];
    },

    onFileRemove(event: FileUploadRemoveEvent) {
      this.fileNames = this.fileNames.filter((name) => name !== event.file.name);
    },

    onFileSelect(event: FileUploadSelectEvent) {
      event.files.forEach((file: { name: string }) => {
        if (!this.fileNames.includes(file.name)) {
          this.fileNames.push(file.name);
        }
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
            <span class="text-(--df-orange) block text-center"> Datensätze verknüpfen <br /> </span>
            <span> Erkentnisse vertiefen </span>
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
          Der Datentreuhänder ermöglicht die sichere Verknüpfung verteilter Daten. Sie als Datenhaltende behalten
          stets die Kontrolle.
        </template>
      </Card>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-5 pb-5">
      <div class="text-3xl text-left uppercase ml-25 text-(--df-orange)">
        <span> PII zugänglich machen </span>
      </div>
    </div>

    <div class="flex justify-center mb-25 mt-5">
      <FileUpload
        name="demo[]"
        accept=".csv"
        :maxFileSize="1000000"
        :multiple="false"
        @select="onFileSelect"
        @remove="onFileRemove"
        @clear="onClear"
        @uploader="uploadFiles"
      >
        <template #header="FileUploadSelectEvent">
          <div class="flex-row h-100">
            <div class="text-2xl uppercase font-bold mb-5 text-(--df-orange)">PII-Daten Bereitstellen</div>
            <div class="mb-5">Laden Sie Ihre Daten unkompliziert hoch und ermöglichen Sie deren Nutzung.</div>
            <div class="mb-5">
              <span class="font-bold"> Achtung: </span> Aktuell unterstützen wir nur .csv Dateien!
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
        In unserem Verfahren haben Sie die Möglichkeit, personenidentifizierende Daten sicher bereitszustellen. Die
        Daten werden lediglich verwendet, um Bloomfilter zu erstellen – eine datenschutzzentrierte Methode, bei der die
        Originaldaten durch kryptografische Hash-Funktionen in anonyme Datenstrukturen umgewandelt werden. Diese
        Bloomfilter ermöglichen eine effiziente Datenverknüpfung, ohne die Originaldaten offenzulegen. Nur die
        Bloomfilter werden gespeichert und für künftige Auswertungen verwendet, was höchste Sicherheitsstandards
        gewährleistet und den Schutz der Privatsphäre sicherstellt.
        <br /><br />
        Ihre Originaldaten bleiben sicher und geschützt, während gleichzeitig wertvolle analytische Erkenntnisse
        gewonnen werden können.
      </div>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
