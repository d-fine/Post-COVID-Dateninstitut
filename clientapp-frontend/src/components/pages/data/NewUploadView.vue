<script lang="ts">

import {
  Button, Column, DataTable, FileUpload,
  type FileUploadSelectEvent, type FileUploadRemoveEvent,
} from 'primevue';

import { postUploadReseachData } from '@/services/ApiClients';
import type Keycloak from "keycloak-js";
import { inject } from 'vue'
import { assertDefined } from "@/utils/TypeScriptUtils";

import { parseCSV, transformToPrimevueDataTable } from '@/utils/Utils';

interface CsvFile {
  header: string[];
  data: string[];
};

export default {
  setup() {
    var getKeycloakPromise = inject<() => Promise<Keycloak>>('getKeycloakPromise');
    return { getKeycloakPromise }
  },
  data() {
    return {
      fileNameDataMap: {} as Record<string, CsvFile>
    };
  },
  methods: {
    async uploadFiles(event) {
      event.files.forEach(file => {
        const reader = new FileReader();
        reader.onload = (e) => {
          const formData = new FormData();
          formData.append('file', new Blob([e.target?.result], { type: file.type }), file.name);
          const payload = new Blob([e.target?.result], { type: file.type });
          const response = postUploadReseachData(assertDefined(this.getKeycloakPromise),
            file.name,
            "postcovidclient",
            "test data",
            payload
          ).then(result => {
            if (result == null) {
              this.$toast.add({ severity: 'error', summary: `failed to upload file ${file.name}` , life: 3000  })
            }
            else {
              this.$toast.add({ severity: 'success', summary: `Uploaded ${file.name}` , life: 3000  })
            }
          });
        }
        reader.readAsArrayBuffer(file);
      })
    },

    onClear() {
      this.fileNameDataMap = {}
    },

    onFileRemove(event: FileUploadRemoveEvent) {
      delete this.fileNameDataMap[event.file.name]
    },

    onFileSelect(event: FileUploadSelectEvent) {
      event.files.forEach(file => {
        if (file.name in this.fileNameDataMap) {
          console.log(`${file.name} already uploaded`);
        }
        else {
          const reader = new FileReader();
          reader.onload = (e) => {
            this.csvToDataMap(file.name, e.target?.result);
          }
          reader.readAsText(file)
        }
      });
    },

    csvToDataMap(fileName: string, fileContent: string) {
      const parsed = parseCSV(fileContent, ",");
      this.fileNameDataMap[fileName] = {
        header: parsed?.header,
        data: transformToPrimevueDataTable(parsed?.header, parsed?.data)
      }
    }
  },
  components: {
    FileUpload,
    Button,
    Column,
    DataTable
  }
};
</script>

<template>
  <div class="card flex mt-4 mb-4">
    <FileUpload name="demo[]" accept=".csv" :maxFileSize="1000000" customUpload @select="onFileSelect"
      @remove="onFileRemove" @clear="onClear" @uploader="uploadFiles">
      <template #empty>
        <p>Drag and drop files to here to upload.</p>
      </template>
    </FileUpload>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
