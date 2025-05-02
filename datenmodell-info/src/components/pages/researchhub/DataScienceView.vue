<script setup lang="ts">
import { Card } from 'primevue';
import Button from 'primevue/button';
import Popup from '@/components/CustomPopup.vue';
import { ref } from 'vue';

const popupTriggers = ref({
  buttonTrigger: false,
});
const TogglePopup = (trigger) => {
  popupTriggers.value[trigger] = !popupTriggers.value[trigger];
};
const microscop_logo = '/Nexus_Logo.png';
const clipboard = '/clipboard.svg';
const calculator = '/calculator.svg';
const linegraph = '/linegraph.svg';
const pointgraph = '/pointgraph.svg';
const email = 'info.post-covid.dateninstitut@d-fine.com';
</script>

<template>
  <div class="max-w-6xl w-full">
    <div class="pt-4 pb-4 items-center ml-26 flex">
      <div class="block">
        <Card class="white-p-card max-w-2xl text-4xl mt-15 uppercase p-5 font-bold">
          <template #content>
            <span> Entdecken Sie </span> <br />
            <span class="text-(--df-orange)"> Data Science-Lösungen </span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
      <div class="logo-container ml-50">
        <img :src="microscop_logo" alt="Logo" class="w-55 rounded-full bg-white -mb-60" />
      </div>
    </div>
    <div class="pt-2 pb-2 flex flex-row items-center text-1xl mt-15 ml-45">
      <Card class="white-p-card font-bold pt-5 pb-5 pl-6 pr-6 w-99 text-lg">
        <template #content>
          Entdecken Sie innovative Datenlösungen und bringen Sie Ihre Forschung mit den neuesten Data Science-Angeboten
          voran.
        </template>
      </Card>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Der Data Science Marktplatz </span>
      </div>
      <div class="text-left ml-25 mr-25 font-normal">
        Willkommen auf dem Data Science Marktplatz, Ihrem Tor zu den neuesten und innovativsten
        Datenwissenschaftslösungen, die speziell für die Herausforderungen und Chancen der Post-Covid-Ära entwickelt
        wurden. Hier finden Sie eine vielfältige Auswahl an Angeboten, die von führenden datengetriebenen Unternehmen
        sowie aufstrebenden Start-ups bereitgestellt werden.
        <br /><br />
        Ob Sie an der Automatisierung von Prozessen interessiert sind, tiefere Einblicke in Ihre Daten gewinnen möchten
        oder robuste Algorithmen zur Lösung komplexer Probleme suchen – der Data Science Marktplatz ist Ihr idealer
        Startpunkt. Durchsuchen Sie unsere Sammlung registrierter Data Science Angebote, treten Sie direkt mit Anbietern
        in Kontakt und lassen Sie sich von den Möglichkeiten inspirieren, die eine Zusammenarbeit mit qualifizierten
        Datenexperten und Technologieanbietern bietet. Seien Sie Teil einer sich ständig weiterentwickelnden
        Gemeinschaft, die Innovationen vorantreibt und gemeinschaftliche Erfolge ermöglicht.
      </div>
    </div>

    <div class="pt-4 pb-4 mb-15">
      <div class="text-3xl text-left pb-12 ml-25 font-bold">
        <span class="text-(--df-orange) uppercase"> Finden Sie Data Science Algorithmen </span>
      </div>

      <div class="grid gap-12 grid-cols-2 ml-25 mr-25 gap-x-30">
        <Card>
          <template #header>
            <img :src="clipboard" class="h-[70px]" />
          </template>
          <template #title>
            <span class="font-bold"> Deskriptive Statistik </span>
          </template>
          <template #subtitle>
            <span class="text-(--df-blue)">
              Entdecken Sie die verborgenen Geschichten in Ihren Daten mit unserer deskriptiven Statistik, die Ihnen
              hilft, zentrale Tendenzen, Streuungen und Verteilungen auf einen Blick zu verstehen.
            </span>
          </template>
        </Card>
        <Card>
          <template #header>
            <img :src="calculator" class="h-[70px]" />
          </template>
          <template #title>
            <span class="font-bold"> Regressionsanalyse </span>
          </template>
          <template #subtitle>
            <span class="text-(--df-blue)">
              Nutzen Sie unsere Regressionsanalyse, um die Beziehungen und Einflüsse mehrerer Variablen auf Ihre
              Zielgröße zu entschlüsseln, damit Sie präzise Vorhersagen und fundierte Entscheidungen treffen können.
            </span>
          </template>
        </Card>
        <Card>
          <template #header>
            <img :src="linegraph" class="h-[70px]" />
          </template>
          <template #title>
            <span class="font-bold"> Überlebensanalyse </span>
          </template>
          <template #subtitle>
            <span class="text-(--df-blue)">
              Profitieren Sie von unserer Expertise in der Überlebensanalyse, um die Zeit bis zu kritischen Ereignissen
              präzise zu modellieren. Ideal für Anwendungen in Bereichen wie Medizin und Produktlebensdauer.
            </span>
          </template>
        </Card>
        <Card>
          <template #header>
            <img :src="pointgraph" class="h-[70px]" />
          </template>
          <template #title>
            <span class="font-bold"> Graphische Darstellung </span>
          </template>
          <template #subtitle>
            <span class="text-(--df-blue)">
              Mit unserer grafischen Darstellung verwandeln wir komplexe Datensätze in klar verständliche und
              ansprechende Visualisierungen, die Ihnen helfen, entscheidende Erkenntnisse wirkungsvoll zu kommunizieren.
            </span>
          </template>
        </Card>
      </div>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Ihre Data Science Lösung Anbieten </span>
      </div>
      <div class="text-left mb-7 ml-25 mr-25 font-normal">
        Sie haben eine Data Science Lösung entwickelt, die Sie kommerzialisieren und Forschenden zur Nutzung anbieten
        möchten? Registrieren Sie Ihr Angebot auf dem Data Science Marktplatz und treten Sie mit der
        Forschungs-Community in Kontakt. Egal ob Analyse-Algorithmus, Statistik-Suite oder Visualisierungs-Tool: Hier
        finden Sie Abnehmer für Ihr Produkt.
      </div>
      <div class="ml-25 mr-25 flex justify-center">
        <Button
          class="navigation_button w-[25%]! h-9"
          @click="() => TogglePopup('buttonTrigger')"
          label="LÖSUNG ANBIETEN >"
        />
      </div>
      <Popup v-if="popupTriggers.buttonTrigger" :TogglePopup="() => TogglePopup('buttonTrigger')">
        Aktuell können leider noch keine Data Science Lösungen aufgenommen werden. Bitte schauen Sie später noch einmal
        vorbei oder wenden Sie sich an
        <a :href="'mailto:' + email" class="text-(--df-light-blue)">{{ email }}</a
        >.
      </Popup>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
