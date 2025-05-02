<script lang="ts">
import { Divider, Toolbar } from 'primevue';
import DatennexusLogo from './DatennexusLogo.vue';

export default {
  data() {
    return {
      routes: [
        {
          path: '/impressum',
          name: 'Impressum',
        },
        {
          path: '/rechtliches-und-datenschutz/',
          name: 'Datenschutz',
        },
      ],
      email: 'info.post-covid.dateninstitut@d-fine.com',
    };
  },
  components: {
    Divider,
    Toolbar,
    DatennexusLogo,
  },
};
</script>

<template>
  <div class="bg-(--df-dark-blue) flex max-w-6xl justify-center pb-4 pt-4 text-white">
    <div class="w-full max-w-6xl">
      <div class="flex pl-5 pr-5">
        <div class="flex-none" style="font-weight: bold">Kontakt</div>
        <div class="flex flex-grow justify-center">
          <p>
            Sind Sie an weiteren Informationen über den Post-Covid Datennexus interessiert? <br />
            Kontaktieren Sie uns gerne per Mail. <br />
            <a :href="'mailto:' + email" class="text-(--df-light-blue)">{{ email }}</a>
          </p>
        </div>
      </div>
      <Divider />
      <Toolbar class="p-toolbar">
        <template #start>
          <DatennexusLogo />
        </template>
        <template #center>
          <div class="flex text-left" style="width: 615px">
            <li v-for="route in routes" :key="route.name" class="ml-1 mr-1 flex justify-center p-2">
              <a
                :href="'https://www.d-fine.com/' + route.path"
                target="_blank"
                class="bg-(--df-dark-blue) flex justify-center text-white"
              >
                {{ route.name }}
              </a>
            </li>
          </div>
        </template>
        <template #end>
          <a
            :href="'https://www.d-fine.com/'"
            target="_blank"
            class="bg-(--df-dark-blue) flex justify-center text-white"
          >
            &copy; 2025 d-fine GmbH
          </a>
        </template>
      </Toolbar>
    </div>
  </div>
</template>

<style scoped>
.p-toolbar.border {
  color: #003c50 !important;
}

.p-toolbar {
  background: #003c50;
  color: white;
  border: #003c50;
}
</style>
