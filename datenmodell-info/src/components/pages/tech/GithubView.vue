<script lang="ts">
import { Card, Button as PrimeButton } from 'primevue';

export default {
  data() {
    return {
      github: '/github.svg',
    };
  },
  methods: {
    go_to_git() {
      window.location.href = 'https://github.com/d-fine/Post-COVID-Dateninstitut';
    },
  },
  components: {
    Card,
    PrimeButton,
  },
};
</script>

<template>
  <div class="max-w-6xl w-full">
    <div class="pt-4 pb-4 items-center ml-26 flex">
      <div class="block">
        <Card class="white-p-card max-w-2xl text-4xl mt-15 min-w-75 p-5 font-bold">
          <template #content>
            <span class="text-(--df-orange)"> GitHub <br /> </span>
            <span class="text-xl"> Zugang zum Quellcode </span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
    </div>
    <div class="pt-2 pb-12 flex flex-row text-1xl mt-15 ml-50">
      <div class="block">
        <Card class="white-p-card font-bold pt-5 pb-5 pl-6 pr-6 w-93 text-lg">
          <template #content>
            Die Veröffentlichung des Codes fördert Transparenz und Nachnutzung. Sie ermöglicht der Community, aktiv zur
            Weiterentwicklung beizutragen.
          </template>
        </Card>
      </div>
      <div class="logo-container ml-25">
        <a href="https://github.com/">
          <img :src="github" alt="Logo" class="w-50 -mt-40" />
        </a>
      </div>
    </div>

    <div class="white-mid-section mb-15 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Info & Link </span>
      </div>
      <div class="text-left ml-25 mr-25 mb-7 font-normal">
        Auf unserer GitHub-Seite veröffentlichen wir den Quellcode für unsere technische Lösung. So wollen wir die
        Transparenz unseres Ansatzes unterstreichen. Darüber hinaus gibt es Dokumentationen zur Installation und
        Nutzung. Der Zugang ist unkompliziert über den unten stehenden Link möglich.
        <br /><br />
        Wir laden Entwickler ein, aktiv an der Weiterentwicklung unserer Anwendung mitzuwirken: GitHub bietet dafür
        zahlreiche Community-Funktionen, mit denen man Probleme melden, Vorschlägen einreichen und sich mit den
        Entwicklern austauschen kann. Ihre Ideen sind entscheidend, um gemeinsam die Herausforderungen im
        Datenmanagement anzugehen!
      </div>
      <div class="ml-25 mr-25 flex justify-center">
        <PrimeButton class="navigation_button w-[25%]! h-9" @click="go_to_git" label="ZUM GITHUB REPO >" />
      </div>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
