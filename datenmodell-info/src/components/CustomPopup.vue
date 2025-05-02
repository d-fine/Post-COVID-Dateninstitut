<script setup lang="ts">
import Button from 'primevue/button';

defineProps(['TogglePopup']);
</script>

<template>
  <div class="popup">
    <div class="popup-inner pb-5 pt-5 w-95">
      <div class="pb-5">
        <slot />
      </div>
      <Button @click="TogglePopup()" class="navigation_button w-[50%]! h-9" label="Schließen" />
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
