<script lang="ts">
import { Card, Button as PrimeButton } from 'primevue';

export default {
  data() {
    return {
      notebook_logo: '/Logo_Rechner.png',
      glass: '/glass.svg',
      lock: '/lock.svg',
      law: '/law.svg',
      link: '/link.svg',
      reports: [
        { name: 'd-fine_Abschlussbericht_Stufe_1.pdf', title: 'Abschlussbericht Stufe 1' },
        { name: 'd-fine_Abschlusspraesentation_Stufe_1.pdf', title: 'Abschlusspräsentation Stufe 1' },
        { name: 'd-fine_Abschlussbericht_Stufe_2.pdf', title: 'Abschlussbericht Stufe 2' },
        { name: 'd-fine_Abschlusspraesentation_Stufe_2.pdf', title: 'Abschlusspräsentation Stufe 2' },
        { name: 'd-fine_Abschlussbericht_Stufe_3.pdf', title: 'Abschlussbericht Stufe 3' },
        { name: 'd-fine_Abschlusspraesentation_Stufe_3.pdf', title: 'Abschlusspräsentation Stufe 3' },
      ],
    };
  },
  components: {
    Card,
    PrimeButton,
  },
  methods: {
    downloadReport(fileName) {
      const fileUrl = `/${fileName}`;
      const link = document.createElement('a');
      link.href = fileUrl;
      link.setAttribute('download', fileName);
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },
    getPdfUrl(fileName) {
      return `/${fileName}`;
    },
  },
};
</script>

<template>
  <div class="max-w-6xl w-full">
    <div class="pt-4 pb-4 items-center ml-26 flex">
      <div class="block">
        <Card class="white-p-card max-w-2xl text-4xl mt-15 uppercase p-5 font-bold">
          <template #content>
            <span> Pilot-Use-Case für das <br /> </span>
            <span class="text-(--df-orange)"> Dateninstitut </span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
      <div class="logo-container ml-45">
        <img :src="notebook_logo" alt="Logo" class="w-55 rounded-full bg-white -mb-60" />
      </div>
    </div>
    <div class="pt-2 pb-2 flex flex-row items-center text-1xl mt-15 ml-55">
      <Card class="white-p-card font-bold pt-5 pb-5 pl-6 pr-6 w-99 text-lg">
        <template #content>
          Die Bundesregierung will die datengetriebene Wertschöpfung in Deutschland stärken.
        </template>
      </Card>
    </div>

    <div class="orange_box ml-50 mr-50 mt-15 mb-15">
      <p class="text-xl ml-5 mr-5 mb-5 mt-5">
        Wir freuen uns, bekanntgeben zu können, dass wir den <b>ersten Platz</b> bei der Post-COVID Challenge des
        Bundesinnenministeriums erreicht haben. Unsere Lösung zur Überwindung von Datensilos dank Record Linkage wurde
        als das innovativste und skalierbarste MVP ausgezeichnet. Wir sehen diese Auszeichnung als Anfang einer neuen
        Phase und freuen uns darauf, unsere Arbeit als Enabler der deutschen Datenökonomie fortzusetzen!
      </p>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> für die Datenökonomie </span>
      </div>
      <div class="text-left pb-12 ml-25 mr-25 w-180 font-normal text-(--df-blue)">
        Sowohl die ehemalige als auch die aktuelle Bundesregierung haben die Notwendigkeit erkannt, die digitale
        Wirtschaft in Deutschland zu stärken. Im Fokus dieser Initiative der ehemaligen Bundesregierung stand
        insbesondere die Wertschöpfung durch datenbasierte Geschäftsmodelle. Hierfür sollte mit dem Dateninstitut eine
        zentrale Anlaufstelle geschaffen werden, die Daten zugänglich macht und Prozesse standardisiert. Als Pilot-Use
        Case haben wir im Rahmen der Post-Covid Challenge erprobt, wie das Dateninstitut arbeiten könnte und welchen
        Mehrwert es für die Forschung und die Wirtschaft bieten kann.
      </div>

      <div class="grid gap-12 grid-cols-2 ml-25 mr-25 gap-x-30">
        <Card>
          <template #header>
            <img :src="link" />
          </template>
          <template #title> DATENVERKNÜPFUNG </template>
          <template #subtitle>
            <span class="font-normal text-(--df-blue)">
              Wir verbinden existierende Datensätze und schaffen so eine maßgeschneiderte Datengrundlage für Ihre
              Forschungsvorhaben. Durch Record Linkage auf Einzelpatientenebene ermöglichen wir es Ihnen alle relevanten
              Datenquellen zu kombinieren.
            </span>
          </template>
        </Card>
        <Card>
          <template #header>
            <img :src="glass" />
          </template>
          <template #title> AUFFINDBARKEIT </template>
          <template #subtitle>
            <span class="font-normal text-(--df-blue)">
              Über unseren Metadaten-Katalog erhalten Sie einen Überblick über alle verfügbaren Datensätze und Ihre
              Inhalte. Ausgehend von mehreren der umfassendsten Datensätzen zur Post-Covid Forschung finden Sie die
              passenden Daten für Ihr Vorhaben.
            </span>
          </template>
        </Card>
        <Card>
          <template #header>
            <img :src="lock" />
          </template>
          <template #title> DATENSCHUTZ </template>
          <template #subtitle>
            <span class="font-normal text-(--df-blue)">
              Der Datennexus nutzt einen innovativen transaktionsbasierten Datentreuhänder, um die angefragten
              Datensätze zu verbinden. Mit dieser technisch besonders sicher abgeschirmten Datenverarbeitung stellen wir
              sicher, dass der Datenschutz gewahrt ist.
            </span>
          </template>
        </Card>
        <Card>
          <template #header>
            <img :src="law" />
          </template>
          <template #title> RECHTSSICHERHEIT </template>
          <template #subtitle>
            <span class="font-normal text-(--df-blue)">
              Wir prüfen die rechtlichen Rahmenbedingungen, unter denen Daten in der medizinischen Forschung genutzt und
              geteilt werden dürfen. Mit unserer Datentaxonomie können Sie so schnell prüfen, welche Anforderungen für
              Ihr Vorhaben gelten und welche rechtlichen Fragen einschlägig sind.
            </span>
          </template>
        </Card>
      </div>
    </div>

    <div class="text-3xl text-left uppercase ml-25 mb-7 text-(--df-orange)">
      <span> Abschlussberichte </span>
    </div>
    <div class="font-normal text-(--df-blue) ml-25 mr-25 mb-7">
      Die Post-Covid Challenge besteht aus drei Stufen. Am Ende jeder Stufe erstellen die teilnehmenden Teams einen
      Abschlussbericht mit den Ergebnisse der jeweiligen Stufe. Zusätzlich dazu halten die teilnehmenden Teams einen
      Vortrag, der die wesentlichen Punkte des Berichts zusammenfasst. Hier veröffentlichen wir unsere Abschlussberichte
      und die Folien unserer Abschlusspräsentationen für alle Stufen der Post-Covid Challenge.
    </div>

    <div class="flex flex-wrap ml-25 mr-25 mb-15">
      <div
        v-for="(report, index) in reports"
        :key="index"
        style="margin-bottom: 10px"
        class="flex items-center w-1/2 mb-2.5"
      >
        <a :href="getPdfUrl(report.name)" target="_blank" style="margin-right: 5px">
          {{ report.title }}
        </a>
        <PrimeButton class="navigation_button w-[8%]!" @click="downloadReport(report.name)" icon="pi pi-download">
        </PrimeButton>
      </div>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
