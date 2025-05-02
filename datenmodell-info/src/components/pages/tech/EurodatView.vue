Cardsty
<script lang="ts">
import { Card } from 'primevue';

export default {
  data() {
    return {
      eurodat: '/eurodat.png',
      court: '/court.svg',
      green: '/green.svg',
    };
  },
  components: {
    Card,
  },
};
</script>

<template>
  <div class="max-w-6xl w-full">
    <div class="pt-4 pb-4 items-center ml-26 flex">
      <div class="block">
        <Card class="white-p-card max-w-2xl text-4xl mt-15 p-5 font-bold">
          <template #content>
            <span class="text-(--df-orange)"> EuroDaT <br /> </span>
            <span class="text-xl"> Europas erster transaktionsbasierter Datentreuhänder </span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
    </div>
    <div class="pt-2 pb-2 flex flex-row text-1xl mt-15 ml-50">
      <div class="block">
        <Card class="white-p-card font-bold pt-5 pb-5 pl-6 pr-6 w-90 text-lg">
          <template #content>
            Der Datentreuhänder ermöglicht die gemeinsame Analyse verteilter Daten. Sensible Daten werden nicht geteilt.
            Die Datenhaltenden behalten die Kontrolle.
          </template>
        </Card>
      </div>
      <div class="logo-container ml-25">
        <a href="https://www.eurodat.org/">
          <img :src="eurodat" alt="Logo" class="w-90 -mt-13" />
        </a>
      </div>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Über EuroDaT </span>
      </div>
      <div class="text-left pb-12 ml-25 mr-25 font-normal">
        EuroDaT ist ein europäischer Datentreuhänder, der mit seinem einzigartigen Prinzip der Datentransaktionen den
        sicheren und rechtskonformen Austausch von Daten zwischen beliebigen Parteien sicherstellt und unabhängige
        Analysen auf gemeinsamen Daten ermöglicht.
        <br />
        <br />
        Entstanden ist EuroDaT im Rahmen eines BMWK-Fördervorhabens. Seit Januar 2022 erarbeitet ein internationales
        Konsortium die rechtlichen und technologischen Voraussetzungen. Basierend auf vier praxisnahen Anwendungsfällen
        wurde das Fundament für die EuroDaT GmbH gelegt, die seit Juli 2023 operativ Datentransaktionen durchführen
        kann.
      </div>
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Datenschutz bei EuroDaT </span>
      </div>
      <div class="text-left ml-25 mr-25 font-normal">
        Der Datenschutz sichert die Anonymität und Datenkontrolle der Patienten ab. Die DSGVO stellt entsprechend
        höchste Anforderungen an die Verarbeitung sensibler Patientendaten, damit eine Re-Identifikation ausgeschlossen
        ist.
        <br />
        <br />
        Der Datentreuhänder erfüllt diese Anforderungen vollständig automatisch, indem er die Daten aus verschiedenen
        Quelle getrennt anfragt und sie in einer hochsicheren, technisch abgekapselten Umgebung zusammenführt. In dieser
        Infrastruktur haben weder Datengebende, Datennehmende noch EuroDaT Zugriff oder Einblick auf die Daten.
        Datenanalysen erfolgen ausschließlich als Dunkelverarbeitung. Die dafür vorgesehenen Algorithmen werden vorab
        von allen Beteiligten freigegebenen. Und die Ausführung der Algorithmen wird ohne Eingriffsmöglichkeit von außen
        automatisch gestartet.
        <br />
        <br />
        Darüber hinaus legt EuroDaT feste Strutkuren und Formatierungen für den Input und Output der Daten fest. So ist
        sichergestellt, dass selbst bei einem böswilligen Angriff autorisierter Nutzender keine Daten unerlaubt
        abgerufen werden können.
        <br />
        <br />
        Das innovative Konzept der transaktionsbasierten Treuhänders ist mit verschiedenen Datenschutzstellen abgestimmt
        und bietet ein technisches Werkzeug, um die strengen Datenschutzbestimmungen jederzeit zu erfüllen.
      </div>
    </div>

    <div class="pt-4 pb-4 mb-15">
      <div class="text-3xl text-left pb-12 ml-25 font-bold">
        <span class="text-(--df-orange)"> Erfolgreiche Use Cases <br /> </span>
        <span class="text-(--df-blue)"> von EuroDaT </span>
      </div>
      <div class="text-left pb-12 ml-25 mr-25 font-normal text-(--df-blue)">
        EuroDaT stellt als transaktionsbasierter Datentreuhänder die Infrastruktur für verschiedenste Anwendungsfälle
        bereit. Standardisierte Transaktionen, die vollständig auf dem EuroDaT Rahmenwerk aufbauen, können in den App
        Stores „EuroDaT Universal“ und „EuroDaT Preset“ ausgewählt werden.
        <br />
        Die Funktionsweise des Treuhänders stellen wir im Folgenden anhand von zwei Anwendungsfällen vor:
      </div>

      <div class="grid gap-12 grid-cols-2 ml-25 mr-25 gap-x-30">
        <Card>
          <template #header>
            <img :src="court" class="h-[70px]" />
          </template>
          <template #title>
            <span class="font-bold"> BANKEN - BETRUGSERKENNUNG </span>
          </template>
          <template #subtitle>
            <span class="text-(--df-blue)">
              Über EuroDaT können Banken ihre Transaktionsdaten miteinander verknüpfen, ohne dass ihre sensiblen
              Geschäftsgeheimnisse für andere Banken einsehbar sind. Dadurch können kriminelle Transaktionsnetzwerke
              auch über mehrere Finanzinstitute hinweg entdeckt und Geldwäsche effektiv bekämpft werden.
            </span>
          </template>
        </Card>
        <Card>
          <template #header>
            <img :src="green" class="h-[70px]" />
          </template>
          <template #title>
            <span class="font-bold"> START UPS - UMWELTREPORTING </span>
          </template>
          <template #subtitle>
            <span class="text-(--df-blue)">
              Start ups sowie Kleine und Mittlere Unternehmen (KMUs) können über EuroDaT ihre sensiblen Geschäftsdaten
              strukturieren und für verpflichtendes Reporting aufbereiten. Die Daten können dabei weder durch andere
              Unternehmen noch EuroDaT selbst eingesehen werden. Die teilnehmenden Unternehmen reduzieren ihren
              regulatorische Aufwand hierdurch deutlich.
            </span>
          </template>
        </Card>
      </div>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
