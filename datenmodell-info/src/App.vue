<script setup lang="ts">
import 'primeicons/primeicons.css';

import { RouterView } from 'vue-router';
import NavigationBar from './components/NavigationBar.vue';
import ContactBar from './components/ContactBar.vue';
</script>

<template>
  <div class="bg-(--df-faded-light-blue) flex flex-col min-h-screen items-center">
    <NavigationBar class="pb-4 bt-4 flex-0 w-7xl" />
    <div class="flex flex-1 flex-col w-full items-center">
      <RouterView />
    </div>
    <ContactBar class="pb-4 bt-4 flex-0 w-7xl" />
  </div>
</template>

<style scoped>
* {
  font-family: 'Roboto', sans-serif;
}
</style>
