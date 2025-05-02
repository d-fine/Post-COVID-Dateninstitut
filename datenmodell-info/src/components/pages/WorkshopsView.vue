<script lang="ts">
import { Card } from 'primevue';

export default {
  data() {
    return {
      notebook_logo: '/Logo_Rechner.png',
      email: 'info.post-covid.dateninstitut@d-fine.com',
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
        <Card class="white-p-card max-w-2xl text-4xl mt-15 uppercase p-5 font-bold">
          <template #content>
            <span> Wissen teilen in </span> <br />
            <span class="text-(--df-orange)"> Workshops </span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
      <div class="logo-container ml-50">
        <img :src="notebook_logo" alt="Logo" class="w-55 rounded-full bg-white -mb-60" />
      </div>
    </div>
    <div class="pt-2 pb-2 flex flex-row items-center text-1xl mt-15 ml-45">
      <Card class="white-p-card font-bold pt-5 pb-5 pl-6 pr-6 w-100 text-lg">
        <template #content>
          Durch Workshops wollen wir aktiv den Wissensaustausch fördern, Forschende und Datenhaltende verbinden und zur
          gemeinsamen Diskussion anregen.
        </template>
      </Card>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Geplante Workshops </span>
      </div>
      <div class="text-left pb-10 ml-25 mr-25 w-180 font-normal">
        Gerne möchten wir Sie über die von uns geplanten Workshops informieren. Hier können Sie alle anstehenden Termine
        und Informationen zu unseren Workshops finden.
      </div>
      <div class="ml-25 mr-25">
        <div class="font-bold text-2xl pb-3">
          <a href="https://datnet.eu/event/fokus-gesundheitsdaten-anwendungsfelder-und-potenziale-von-dtm/">
            Fokus Gesundheitsdaten - Anwendungsfelder und Potenziale von DTM
          </a>
        </div>
        <div class="text-(--df-orange) pb-3 ml-5">Dienstag, 13. Mai 2025, 13:00 bis 15:00 Uhr, Online</div>
        <div class="font-normal w-210 pb-3 ml-5">
          Die Verknüpfung und Aufbereitung von Gesundheitsdaten birgt Potenziale für die medizinische Forschung, die
          bisher noch nicht ausreichend ausgeschöpft werden. Datentreuhandmodelle können hier innovative Lösungen
          technischer, rechtlicher und organisatorisch Art bieten. Der Workshop soll eine Brücke schlagen zwischen
          allgemeinen Rahmenbedingungen der Datenregulierung und den spezifischen Anforderungen aus der Praxis. Dabei
          stehen zwei Projekte im Fokus. Das Projekt „Datennexus“, vertreten durch Dr. Felix Mackenroth und Dr. Irina
          Tihaa von d-fine, entwickelt ein Ökosystem im Bereich Post-COVID, das eine datensouveräne, DSGVO-konforme
          Verknüpfung von Daten auf Personenebene - auch über sektorale Grenzen hinweg - ermöglicht. Es ist im Rahmen
          der von der Bundesregierung geförderten Post-COVID Challenge entstanden und basiert auf dem
          transaktionsbasierten Datentreuhänder EuroDaT, der sichere und kontrollierte Datenflüsse ohne zentrale
          Speicherung ermöglicht. Das durch das BMBF geförderte Projekt AVATAR, vertreten durch Prof. Dr. Cord
          Spreckelsen, entwickelt Anonymisierungsverfahren für patientenbezogene Datensätze, die in sogenannten Avataren
          zusammengeführt werden. Dadurch sollen Datenanfrage und Datenspende unter rechtskonformen Bedingungen
          vereinfacht und so die innovative Nutzung von Gesundheitsdaten gefördert werden.
          <br /><br />
          Felix Mackenroth ist promovierter Physiker und bei d-fine Manager für Themen des öffentlichen Sektors mit
          Fokus auf konzeptionell und technologisch innovativen Lösungen für Datenteilen und rechtlichen Themen.
          <br /><br />
          Irina Tihaa ist promovierte Neurophysikerin und Managerin im Bereich Pharma & Healthcare. Ihr Schwerpunkt
          liegt auf Projekten zur Förderung der Datennutzung und datenbasierten Entscheidungen sowie der
          menschzentrierten Gestaltung digitaler und technologischer Innovationen.
          <br /><br />
          Cord Spreckelsen ist Professor für Medizinische Informatik am Institut für Medizinische Statistik, Informatik
          und Datenwissenschaften des Universitätsklinikums Jena. Zu seinen Forschungsschwerpunkten gehören u. a.
          Wissensbasen und Anwendungen der künstlichen Intelligenz in der Medizin sowie Semantic Web Techniken im
          medizinischen Wissensmanagement.
          <br /><br />
          Moderiert wird der Workshop durch Marlin Mayer und Till Seidemann des Datentreuhand-Kompetenznetzwerks DaTNet.
        </div>
        <div class="text-(--df-orange) ml-5">
          <a href="https://datnet.eu/event/fokus-gesundheitsdaten-anwendungsfelder-und-potenziale-von-dtm/">
            Anmeldung und mehr Informationen finden Sie hier.
          </a>
        </div>
      </div>
    </div>

    <div class="mb-15">
      <div class="text-3xl text-left uppercase ml-25 mb-7 text-(--df-orange)">
        <span> Sie haben Interesse? </span>
      </div>
      <div class="text-left mb-25 ml-25 mr-25 font-normal">
        Sie wollen mehr Informationen zu unserem Datenraum oder haben Intersse an einem Workshop?
        <br /><br />
        Gerne planen wir gemeinsam mit Ihnen eine Infoveranstaltung, einen Workshop oder einen kurzen ersten Austausch.
        <br />
        Kontaktieren Sie uns einfach per Mail an:
        <a :href="'mailto:' + email" class="text-(--df-light-blue)">{{ email }}</a
        >.
      </div>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>

<style scoped src="src/assets/main.css"></style>
