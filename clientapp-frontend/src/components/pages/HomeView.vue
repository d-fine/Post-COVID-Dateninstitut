<script setup lang="ts">
import { Card } from 'primevue';
import PrimeButton from 'primevue/button';

const microscop_logo = '/Nexus_Logo.png';
</script>

<template>
  <div class="max-w-6xl w-full">
    <div class="pt-4 pb-4 items-center ml-26 flex">
      <div class="block">
        <Card class="white-p-card max-w-2xl text-4xl mt-15 uppercase p-5 font-bold">
          <template #content>
            <span class="text-(--df-orange) block text-center"> Record Linkage <br /> </span>
            <span> Beginnt mit Vertrauen </span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
      <div class="logo-container ml-50">
        <img :src="microscop_logo" alt="Logo" class="w-55 rounded-full bg-white -mb-23" />
      </div>
    </div>
    <div class="pt-2 pb-2 flex flex-row items-center text-1xl mt-15 ml-65">
      <Card class="white-p-card font-bold pt-5 pb-5 pl-6 pr-6 w-105 text-lg">
        <template #content>
          Willkommen in der Client-App des Datennexus! Hier können Sie ihre Daten einfach und sicher bereitstellen und
          verwalten.
        </template>
      </Card>
    </div>

    <div class="white-mid-section mt-15 mb-25 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Willkommen in der Client-App! </span>
      </div>
      <div class="text-left ml-25 mr-25 w-190 font-normal mb-10">
        Die Client-App ist Ihr Zugang zum Datennexus als Datenhaltende. Mit Hilfe der App können Sie Daten für die
        Forschung zugänglich machen und individuell für verschiedene Use-Cases und Nutzungsanträge passgenau Daten zur
        Verfügung stellen. Sie als Datenhaltende behalten stets die Kontrolle und Souveränität über ihre Daten.
        <br /><br />
        Mit Hilfe der Client-App können Sie sowohl Studiendaten als auch hochsensible personenidentifizierende
        Informationen (PII), verschlüsselt, bereitstellen. Alles unter höchsten Sicherheitsstandards.
        <br /><br />
        Laden Sie Ihre Daten unkompliziert hoch und ermöglichen Sie deren Nutzung für vielseitige Auswertungen.
      </div>
      <div class="ml-25 mr-25 flex justify-center">
        <PrimeButton
          class="navigation_button w-[25%]! h-9"
          as="router-link"
          to="/data/new-upload"
          label="Daten Bereistellen >"
        />
      </div>
    </div>
  </div>
</template>
