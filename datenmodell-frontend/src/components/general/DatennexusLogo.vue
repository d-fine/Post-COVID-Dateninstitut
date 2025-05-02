<script lang="ts">
export default {
  data() {
    return {
      logo: '/datennexus_logo.svg',
    };
  },
};
</script>

<template>
  <div class="flex items-center pr-5 text-left">
    <img :src="logo" class="mr-5 h-auto w-16" />
    <div class="text-(--df-light-blue) text-lg">
      <span> POST-COVID</span> <br />
      <span> DATENNEXUS </span>
    </div>
  </div>
</template>
