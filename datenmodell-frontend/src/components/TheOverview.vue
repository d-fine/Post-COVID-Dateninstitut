<script lang="ts">
  import type { StudyInformation } from '@/generated/openapi';
  import {getStudies} from '@/utils/SearchStudyData';
  import {inject} from "vue";
  import type Keycloak from "keycloak-js";
  import {assertDefined} from "@/utils/TypeScriptUtils";

  export default {
    setup() {
      return {
        getKeycloakPromise: inject<() => Promise<Keycloak>>('getKeycloakPromise'),
      };
    },
    data() {
        return {
            studies: new Array<StudyInformation>()
        }
    },
    methods: {
      async getAllStudies() {
        this.studies =  await getStudies(assertDefined(this.getKeycloakPromise));
      }
    }
  }
</script>

<template>
    <!-- TODO refactor with primevue table -->
  <div class="about">
    <button @click="getAllStudies">
      Get study data
    </button>
    <table>
      <thead>
        <tr>
          <th>
            <span>ID</span>
          </th>
          <th>
            <span>Title</span>
          </th>
          <th>
            <span>Content</span>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in studies" :key="item.id">
          <td>
            {{item.id}}
          </td>
          <td>
            {{item.title}}
          </td>
          <td>
            {{item.content}}
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>



