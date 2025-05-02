<script setup lang="ts">
import { Card } from 'primevue';
import Button from 'primevue/button';

const microscop_logo = '/Nexus_Logo.png';
</script>

<template>
  <div class="max-w-6xl w-full">
    <div class="pt-4 pb-4 items-center ml-26 flex">
      <div class="block">
        <Card class="white-p-card max-w-2xl text-4xl mt-15 uppercase p-5 font-bold">
          <template #content>
            <span> Wissen Verbinden - </span> <br />
            <span class="text-(--df-orange)"> Erkentnisse teilen </span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
      <div class="logo-container ml-70">
        <img :src="microscop_logo" alt="Logo" class="w-55 rounded-full bg-white -mb-60" />
      </div>
    </div>
    <div class="pt-2 pb-2 flex flex-row items-center text-1xl mt-15 ml-45">
      <Card class="white-p-card font-bold pt-5 pb-5 pl-6 pr-6 w-103 text-lg">
        <template #content>
          Der ResearchHub fördert die Zusammenarbeit zwischen Innovatoren und Forschern, indem er Daten und
          Datenauswertung zusammenbringt.
        </template>
      </Card>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Über den ResearchHub </span>
      </div>
      <div class="text-left pb-12 ml-25 mr-25 font-normal">
        In der sich rasch entwickelnden Welt der Datenwissenschaften und Technologie ist kollaborative Innovation ein
        Schlüssel zum Fortschritt. Der ResearchHub wurde gegründet, um hierfür Synergien zu fördern und als Drehscheibe
        für Forschende, Data Science-Entwickelnde und Unternehmen weltweit zu dienen - insbesondere in der
        Post-Covid-Forschung. Unsere Plattform versteht sich als Vermittler und Katalysator: Wir verbinden Menschen,
        Ideen und Ressourcen, um das volle Potenzial von Daten zu nutzen.
        <br /><br />
        Mit der fortschreitenden Digitalisierung gewinnt der Zugang zu qualitativ hochwertigen Daten und innovativen
        Algorithmen immer mehr an Bedeutung. Der ResearchHub bietet sowohl etablierten Unternehmen als auch neuen
        Start-ups die Möglichkeit, ihre Data Science-Lösungen einem breiten Publikum vorzustellen. Gleichzeitig bietet
        er Forschenden und Instituten die Möglichkeit, ihre wertvollen Datensätzen für innovative Auswertungsmethoden
        bereitzustellen, die für evidenzbasierte Erkenntnisse und wegweisende Data Science-Anwendungen unerlässlich
        sind. Unser Ziel ist es, Barrieren abzubauen und einen nahtlosen Austausch von Wissen und Ressourcen zu
        ermöglichen.
        <br /><br />
        Im Kern steht der ResearchHub für Zusammenarbeit und gemeinschaftlichen Fortschritt. Wir sind fest davon
        überzeugt, dass gesellschaftliche Herausforderungendurch den offenen Zugang zu Wissen und Technologien
        überwunden werden können. Treten Sie unserer Gemeinschaft bei und seien Sie Teil dieser spannenden Reise, in der
        wir gemeinsam die Zukunft gestalten.
      </div>
      <div class="flex">
        <div class="blue_box ml-[100px]! flex flex-col">
          <div class="small_header">Das Netzwerk</div>
          Melden Sie sich an und werden Sie Teil eines wachsenden Netzwerks von Datenenthusiasten, Forschenden und
          Innovation, das sich dafür einsetzt, das Potenzial von Post-Covid-Daten zu erschließen.
          <br /><br />
          <div class="mb-2 flex justify-center mt-auto">
            <Button class="navigation_button h-9" as="router-link" to="/" label="WERDEN SIE TEIL >" />
          </div>
        </div>
        <div class="blue_box">
          <div class="small_header">Die Möglichkeiten</div>
          Erkunden Sie ein breites Spektrum an Data-Science-Lösungen und Datensätzen. Ganz gleich, ob Sie
          fortschrittliche ML-Algorithmen nutzen oder auf umfangreiche Datensätze zugreifen möchten, unsere Plattform
          bringt Sie mit den richtigen Ressourcen zusammen.
          <br /><br />
          <div class="mb-2 flex justify-center mt-auto">
            <Button
              class="navigation_button h-9"
              as="router-link"
              to="/researchhub/datascience"
              label="ANGEBOT DURCHSUCHEN >"
            />
          </div>
        </div>
        <div class="blue_box">
          <div class="small_header">Die Platform</div>
          Bessere Forschung bedeutet vor allem effektivere Forschung. Dafür müssen wir Forschende verschiedenster
          Fachrichtungen mit neuen Werkzeugen und einer umfassenden Datengrundlage ausrüsten. Hierfür stellen wir
          entscheidende Werkzeuge bereit.
          <br /><br />
          <div class="mb-2 flex justify-center mt-auto">
            <Button class="navigation_button h-9" as="router-link" to="/about/target" label="MEHR INFORMATIONEN >" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
