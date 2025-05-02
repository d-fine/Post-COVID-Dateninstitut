<script lang="ts">
import { inject } from 'vue';

import type Keycloak from 'keycloak-js';
import { Card, Button as PrimeButton } from 'primevue';

export default {
  setup() {
    return {
      getKeycloakPromise: inject<() => Promise<Keycloak>>('getKeycloakPromise'),
    };
  },
  data() {
    return {
      glass: '/glass.svg',
      lock: '/lock.svg',
      law: '/law.svg',
      link: '/link.svg',
      laptop: '/laptop.svg',
      dfine: '/dfine.png',
      microscop_logo: '/Nexus_Logo.png',
    };
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
        <Card class="white-p-card max-w-2xl text-4xl mt-15 uppercase p-5 font-bold">
          <template #content>
            <span class="text-(--df-orange) block text-center"> Record Linkage <br /> </span>
            <span> beginnt mit Vertrauen </span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
      <div class="logo-container ml-70">
        <img :src="microscop_logo" alt="Logo" class="w-55 rounded-full bg-white -mb-23" />
      </div>
    </div>
    <div class="pt-2 pb-2 flex flex-row items-center justify-center text-1xl mt-15">
      <Card class="white-p-card p-5 flex font-bold flex-col text-lg w-105">
        <template #content>
          <div>
            <span class="uppercase"> Unsere Mission: </span> <br />
            Wir unterstützen Ihre Forschung mit passenden Daten aus allen relevanten Bereichen.
          </div>
          <div class="mt-5 flex justify-center">
            <PrimeButton class="navigation_button h-9" label="ÜBER UNS >" />
          </div>
        </template>
      </Card>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-13 pb-13">
      <div class="orange_box ml-50 mr-50 mb-15">
        <p class="text-center italic text-xl ml-5 mr-5 mb-5 mt-5">
          „Das d-fine Konsortium zeigt einen vielversprechenden Ansatz zur Record-Linkage auf Einzelpatientenebene: Wir
          erwarten substanziellen Mehrwert und einen klaren Business-Case.“
        </p>
        <div class="flex flex-col mr-5 mb-5 text-right">
          <a href="https://zeg-berlin.de/staff/marc-pignot/" target="_blank">
            Dr. Marc Pignot, Chief Strategy Officer
          </a>
          <a href="https://zeg-berlin.de/" target="_blank">
            Zentrum für Epidemiologie und Gesundheitsforschung Berlin
          </a>
        </div>
      </div>
      <div class="flex">
        <div class="ml-25 uppercase">
          Built by
          <div style="background-color: var(--df-orange); width: 70%; height: 5px"></div>
        </div>
        <a :href="'https://www.d-fine.com/'" target="_blank">
          <img :src="dfine" class="ml-20 w-23" />
        </a>
      </div>
    </div>

    <div class="pt-4 pb-8">
      <Card>
        <template #title>
          <div class="text-3xl text-left uppercase pb-12 ml-25">
            <span class="text-(--df-orange)"> Zentrale Herausforderungen <br /> </span>
            <span> Und unsere Lösungen </span>
          </div>
        </template>
        <template #content>
          <div class="grid gap-12 grid-cols-2 ml-25 mr-25 gap-x-30">
            <Card>
              <template #header>
                <img :src="link" />
              </template>
              <template #title> DATENVERKNÜPFUNG </template>
              <template #subtitle>
                <span class="text-(--df-blue)">
                  Wir verbinden existierende Datensätze und schaffen so eine maßgeschneiderte Datengrundlage für Ihre
                  Forschungsvorhaben. Durch Record Linkage auf Einzelpatientenebene ermöglichen wir es Ihnen alle
                  relevanten Datenquellen zu kombinieren.
                </span>
              </template>
              <template #content> </template>
            </Card>
            <Card>
              <template #header>
                <img :src="glass" />
              </template>
              <template #title> AUFFINDBARKEIT </template>
              <template #subtitle>
                <span class="text-(--df-blue)">
                  Über unseren Metadaten-Katalog erhalten Sie einen Überblick über alle verfügbaren Datensätze und Ihre
                  Inhalte. Ausgehend von mehreren der umfassendsten Datensätzen zur Post-Covid Forschung finden Sie die
                  passenden Daten für Ihr Vorhaben.
                </span>
              </template>
              <template #content> </template>
            </Card>
            <Card>
              <template #header>
                <img :src="lock" />
              </template>
              <template #title> Datenschutz </template>
              <template #subtitle>
                <span class="text-(--df-blue)">
                  Der Datennexus nutzt einen innovativen transaktionsbasierten Datentreuhänder, um die angefragten
                  Datensätze zu verbinden. Mit dieser technisch besonders sicher abgeschirmten Datenverarbeitung stellen
                  wir sicher, dass der Datenschutz gewahrt ist.
                </span>
              </template>
              <template #content> </template>
            </Card>
            <Card>
              <template #header>
                <img :src="law" />
              </template>
              <template #title> RECHTSSICHERHEIT </template>
              <template #subtitle>
                <span class="text-(--df-blue)">
                  Wir prüfen die rechtlichen Rahmenbedingungen, unter denen Daten in der medizinischen Forschung genutzt
                  und geteilt werden dürfen. Mit unserer Datentaxonomie können Sie so schnell prüfen, welche
                  Anforderungen für Ihr Vorhaben gelten und welche rechtlichen Fragen einschlägig sind.
                </span>
              </template>
              <template #content> </template>
            </Card>
          </div>
        </template>
      </Card>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-12 ml-25 mr-25">
        <span>
          Wir Schaffen Mehrwert <br />
          für alle Seiten
        </span>
      </div>
      <div class="flex">
        <div class="blue_box ml-[100px]!">
          <div class="small_header">Datennehmende</div>
          Sie haben ein Forschungsprojekt und benötigen Daten?
          <br />
          <br />
          Bei uns finden Sie schnell einen Überblick über verfügbare Daten und die einmalige Möglichkeit verschiedene
          Datenquellen DSGVO-konform miteinander zu verbinden.
          <div class="mt-5 mb-2 flex justify-center">
            <PrimeButton class="navigation_button h-9" label="DATEN DURCHSUCHEN >" />
          </div>
        </div>
        <div class="blue_box">
          <div class="small_header">DatenGebende</div>
          Sie haben Daten und wollen sie zugänglich machen?
          <br />
          <br />
          Bei uns finden Sie eine Lösung für die niederschwellige Bereitstellung Ihrer Daten und einfache
          Kooperations-möglichkeiten mit anderen Forschenden.
          <div class="mt-5 mb-2 flex justify-center">
            <PrimeButton class="navigation_button h-9" label="DATEN REGISTRIEREN >" />
          </div>
        </div>
        <div class="blue_box">
          <div class="small_header">Interessierte</div>
          Sie wollen sich über existierende Vorhaben und Daten informieren?
          <br />
          <br />
          Bei uns finden Sie einfach zugängliche und umfassende Informationen zu unserem Service, den angebundenen
          Datenquellen und der eingesetzter Technik.
          <div class="mt-5 mb-2 flex justify-center">
            <PrimeButton class="navigation_button h-9" label="MEHR INFORMATIONEN >" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
