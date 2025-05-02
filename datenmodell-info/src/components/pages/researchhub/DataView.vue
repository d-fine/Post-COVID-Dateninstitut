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
const face = '/face.svg';
const gene = '/gene.svg';
const group = '/group.svg';
const data = '/data.svg';
const email = 'info.post-covid.dateninstitut@d-fine.com';
</script>

<template>
  <div class="max-w-6xl w-full">
    <div class="pt-4 pb-4 items-center ml-26 flex">
      <div class="block">
        <Card class="white-p-card max-w-2xl text-4xl mt-15 uppercase p-5 font-bold">
          <template #content>
            <span> Entdecken Sie spannende</span> <br />
            <span class="text-(--df-orange)"> Datensätze </span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
      <div class="logo-container ml-40">
        <img :src="microscop_logo" alt="Logo" class="w-55 rounded-full bg-white -mb-60" />
      </div>
    </div>
    <div class="pt-2 pb-2 flex flex-row items-center text-1xl mt-15 ml-45">
      <Card class="white-p-card font-bold pt-5 pb-5 pl-6 pr-6 w-85 text-lg">
        <template #content>
          Entdecken Sie aussagekräftige Daten und bringen Sie Ihre Forschung durch proaktive Kooperationen voran.
        </template>
      </Card>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Der Daten Marktplatz </span>
      </div>
      <div class="text-left ml-25 mr-25 font-normal">
        Willkommen auf dem Daten Marktplatz, dem Zugang zu hochwertigen und einzigartigen Datensätzen, die speziell auf
        die Bedürfnisse der Post-Covid-Ära ausgerichtet sind. Der Marktplatz bietet Forschenden, Entwickelnden und
        Innovationstreibenden eine breite Palette an Datenquellen, bereitgestellt von renommierten Institutionen und
        vertrauenswürdigen Partnern. Hier können Sie Kooperations-Partner für Projekte und Studien gewinnen, ihre
        Projekte mit neusten Daten bereichern und so tiefere Einblicke gewinnen.
        <br /><br />
        Stellen Sie sich vor, in der Lage zu sein, neue Trends zu erkennen, Hypothesen zu testen und Lösungen zu
        entwickeln, die in einer dynamischen Welt in der wir leben von entscheidender Bedeutung sind. Der Daten
        Marktplatz ist mehr als nur eine Vision; er ist der Schlüssel zu künftigen Erkenntnissen und bahnbrechenden
        Entdeckungen. Wir laden Sie ein, Teil dieser Reise zu sein und gemeinsam die Voraussetzungen zu schaffen, um
        Innovationen und Fortschritte voran zu treiben.
      </div>
    </div>

    <div class="pt-4 pb-4 mb-15">
      <div class="text-3xl text-left pb-12 ml-25 font-bold">
        <span class="text-(--df-orange) uppercase"> Finden Sie Datensätze für Ihre Modelle </span>
      </div>

      <div class="grid gap-12 grid-cols-2 ml-25 mr-25 gap-x-30">
        <Card>
          <template #header>
            <img :src="face" class="h-[70px]" />
          </template>
          <template #title>
            <span class="font-bold"> Bild-Daten </span>
          </template>
          <template #subtitle>
            <span class="text-(--df-blue)">
              Entdecken Sie Bilddaten. Ideal für Projekte in der KI-Entwicklung, medizinischen Bildgebung oder visuellen
              Analysen, bieten diese Daten einen Einblick in visuelle Muster und Strukturen.
            </span>
          </template>
        </Card>
        <Card>
          <template #header>
            <img :src="gene" class="h-[70px]" />
          </template>
          <template #title>
            <span class="font-bold"> Genom-Daten </span>
          </template>
          <template #subtitle>
            <span class="text-(--df-blue)">
              Nutzen Sie umfassende Genomdaten für Fortschritte in der genetischen Forschung und personalisierten
              Medizin. Diese Daten helfen dabei, genetische Variationen zu verstehen und neue therapeutische Ansätze zu
              entwickeln.
            </span>
          </template>
        </Card>
        <Card>
          <template #header>
            <img :src="group" class="h-[70px]" />
          </template>
          <template #title>
            <span class="font-bold"> Kohorten-Daten </span>
          </template>
          <template #subtitle>
            <span class="text-(--df-blue)">
              Greifen Sie auf wertvolle Kohortendaten zu, die Ihnen helfen, Bevölkerungsstudien und epidemiologische
              Analysen durchzuführen. Diese Daten bieten Einblicke in Gesundheitsmuster und soziale Trends.
            </span>
          </template>
        </Card>
        <Card>
          <template #header>
            <img :src="data" class="h-[70px]" />
          </template>
          <template #title>
            <span class="font-bold"> Statistische Daten </span>
          </template>
          <template #subtitle>
            <span class="text-(--df-blue)">
              Bereichern Sie Ihre Analysen mit umfassenden statistischen Daten, die Trends und Muster in verschiedenen
              Branchen aufdecken. Diese Daten sind entscheidend für fundierte Entscheidungsprozesse und strategische
              Planungen.
            </span>
          </template>
        </Card>
      </div>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Ihre Datensätze Registrieren </span>
      </div>
      <div class="text-left mb-7 ml-25 mr-25 font-normal">
        Sie haben Datensätze, die Sie mit innovativen Methoden auswerten möchten? Oder Sie suchen nach passenden
        Kooperationspartnern für eine umfassende Analyse Ihrer Daten? Registrieren Sie Ihr Angebot auf dem Daten
        Marktplatz und treten Sie mit der Data Science-Community in Kontakt. Egal ob Bilddaten, Genomdaten,
        Kohortendaten oder statistische Daten: Hier finden Sie Interessenten, die Ihre Daten analysieren können.
      </div>
      <div class="ml-25 mr-25 flex justify-center">
        <Button
          class="navigation_button w-[25%]! h-9"
          @click="() => TogglePopup('buttonTrigger')"
          label="DATEN REGISTRIEREN >"
        />
      </div>
      <Popup v-if="popupTriggers.buttonTrigger" :TogglePopup="() => TogglePopup('buttonTrigger')">
        Aktuell können leider noch keine Datensätze aufgenommen werden. Bitte schauen Sie später noch einmal vorbei oder
        wenden Sie sich an
        <a :href="'mailto:' + email" class="text-(--df-light-blue)">{{ email }}</a
        >.
      </Popup>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
