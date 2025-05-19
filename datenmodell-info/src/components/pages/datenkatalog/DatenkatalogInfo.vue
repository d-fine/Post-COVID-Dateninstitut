<script setup lang="ts">
import { Card } from 'primevue';
import Popup from '@/components/CustomPopup.vue';
import { ref } from 'vue';
import Button from 'primevue/button';

const microscop_logo = '/Nexus_Logo.png';
const email = 'info.post-covid.dateninstitut@d-fine.com';
const virus = '/virus.svg';
const group_people = '/group_people.svg';
const open = '/open.svg';

const popupTriggers = ref({
  buttonTrigger: false,
});
const TogglePopup = (trigger) => {
  popupTriggers.value[trigger] = !popupTriggers.value[trigger];
};
</script>

<template>
  <div class="max-w-6xl w-full">
    <div class="pt-4 pb-4 items-center ml-26 flex">
      <div class="block">
        <Card class="white-p-card max-w-2xl text-4xl mt-15 uppercase p-5 font-bold">
          <template #content>
            <span> Der Datenkatalog - </span> <span class="text-(--df-orange)"> Daten </span> <br />
            <span class="text-(--df-orange)"> einfach zugänglich machen </span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
      <div class="logo-container ml-25">
        <img :src="microscop_logo" alt="Logo" class="w-55 rounded-full bg-white -mb-60" />
      </div>
    </div>
    <div class="pt-2 pb-2 flex flex-row items-center text-1xl mt-15 ml-60">
      <Card class="white-p-card font-bold pt-5 pb-5 pl-6 pr-6 w-97 text-lg">
        <template #content>
          Passende (Meta-)daten aus allen Bereichen für Ihre Forschungsfragen finden und damit Forschung möglich machen.
        </template>
      </Card>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Über den Datenkatalog </span>
      </div>
      <div class="text-left pb-12 ml-25 mr-25 font-normal">
        Unser Metadatenkatalog befindet sich aktuell im Aufbau. Ziel ist es, Ihnen baldmöglichst eine umfassende
        Übersicht über alle Forschungsdaten zu bieten. Damit soll der Zugang zu geeigneten Daten für
        Post-Covid-Forschungsfragen erleichtert werden.
        <br />
        Perspektivisch können die Daten auch für Verknüpfungen genutzt werden. Die Daten werden hierbei nicht dauerhaft
        in EuroDaT gespeichert sondern nur temporär für die Verarbeitung vorgehalten. Die datengebenden Stellen behalten
        zu jeder Zeit die Souvernänität über ihre Daten.
        <br />
        <br />
        Im Katalog finden Sie bereits jetzt Metadaten von renommierten Quellen wie NAKO und NAPKON. Zudem umfasst unser
        Katalog die Open Source Datensätze des Robert Koch-Instituts (RKI), die für die Post-Covid-Forschung von großem
        Interesse sind und zeigen, wie Open Source Daten die wissenschaftliche Arbeit bereichern können.
        <br />
        <br />
        Bitte beachten Sie, dass der Post-Covid Datennexus aktuell ein MVP (Minimum Viable Product) ist, und die
        Metadaten beispielhaft ausgewählt wurden. Die Anbindung an die datengebenden Stellen ist zum jetzigen Zeitpunkt
        noch nicht abgeschlossen.
      </div>

      <div class="flex">
        <div class="blue_box ml-[100px]! flex flex-col">
          <div class="header_box">
            <a href="https://nako.de/">NAKO</a>
          </div>
          <div>
            Die NAKO Gesundheitsstudie ist die umfassendste Langzeitstudie in Deutschland, die seit 2014 in 18
            Studienzentren durchgeführt wird. Dabei werden über 205.000 zufällig ausgewählte Bürgerinnen und Bürger
            eingehend medizinisch untersucht und zu ihren Lebensgewohnheiten befragt.
          </div>
          <div class="logo">
            <img :src="virus" alt="Virus" class="h-15 mt-7" />
          </div>
        </div>
        <div class="blue_box flex flex-col">
          <div class="header_box">
            <a href="https://napkon.de/">NAPKON</a>
          </div>
          <div>
            Das Nationale Pandemie Kohorten Netz (NAPKON) wurde im Juli 2020 als Teil des Netzwerks Universitätsmedizin
            ins Leben gerufen, um zuvor verstreute nationale Forschungsprojekte zu COVID-19 in einem einheitlichen
            Rahmen von Kohorten und Infrastruktur zu konsolidieren.
          </div>
          <div class="logo">
            <img :src="group_people" alt="Icon" class="h-15 mt-7" />
          </div>
        </div>
        <div class="blue_box flex flex-col">
          <div class="header_box">Open Data</div>
          <div>
            Open Data bezeichnet frei zugängliche Daten, die von jedermann genutzt, weiterverarbeitet und geteilt werden
            können. Um auf das Potential hinzuweisen, haben wir Open Data Datensätze des
            <a href="https://www.rki.de/">Robert Koch-Instituts</a>
            in unseren Datenkatalog aufgenommen.
          </div>
          <div class="logo">
            <img :src="open" alt="Icon" class="h-15 mt-7" />
          </div>
        </div>
      </div>
    </div>

    <div class="mb-15">
      <div class="text-3xl text-left uppercase ml-25 mb-7 text-(--df-orange)">
        <span> Ihre Metadaten registrieren </span>
      </div>
      <div class="text-left mb-7 ml-25 mr-25 font-normal">
        Unser Datenkatalog befindet sich aktuell noch im Aufbau. Wir arbeiten an einer stetigen Erweiterung.
        <br /><br />
        Sie haben Datensätze, die Sie gerne mit der Forschungs-Community teilen wollen? Registrieren Sie Ihr Angebot.
        <br />
        - Gemeinsam mehr schaffen!
      </div>
      <div class="ml-25 mr-25 flex justify-center">
        <Button
          class="navigation_button w-[25%]! h-9"
          @click="() => TogglePopup('buttonTrigger')"
          label="DATEN REGISTRIEREN >"
        />
      </div>
      <Popup v-if="popupTriggers.buttonTrigger" :TogglePopup="() => TogglePopup('buttonTrigger')">
        Aktuell können leider noch keine Metadaten aufgenommen werden. Bitte schauen Sie später noch einmal vorbei oder
        wenden Sie sich an <br />
        <a :href="'mailto:' + email" class="text-(--df-light-blue)">{{ email }}</a
        >.
      </Popup>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
