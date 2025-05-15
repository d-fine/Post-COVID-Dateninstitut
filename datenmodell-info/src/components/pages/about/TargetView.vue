<script lang="ts">
import { Card } from 'primevue';

export default {
  data() {
    return {
      notebook_logo: '/Logo_Rechner.png',
      datennexus_video: '/Datennexus_Erklaervideo.mp4',
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
            <span> Unser </span>
            <span class="text-(--df-orange)"> Zielbild </span>
            <span>
              für <br />
              eine bessere Forschung
            </span>
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
          Der Datennexus vernetzt Daten, Modelle und Forschende und verbessert so die Datengrundlage für etablierte und
          innovative Forschungsvorhaben.
        </template>
      </Card>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Unser Ganzheitlicher Ansatz </span>
      </div>
      <div class="text-left pb-12 ml-25 mr-25 w-180 font-normal">
        Besser Forschung bedeutet vor allem effektivere Forschung. Dafür müssen wir Forschende verschiedenster
        Fachrichtungen mit neuen Werkzeugen und einer umfassenden Datengrundlage ausrüsten. Hierfür stellen wir
        entscheidende Werkzeuge bereit:
        <br />
        Wir vernetzen Forschende durch wirkungsvolle Kommunikationswerkzeuge.
        <br />
        Wir vernetzen Prozesse und Methoden durch Standardisierung von Formaten und Algorithmen.
        <br />
        Wir vernetzen Daten durch ein innovatives Record Linkage mit einem transaktionsbasierten Datentreuhänder.
      </div>
      <div class="flex">
        <div class="blue_box ml-[100px]!">
          <div class="header_box">Record Linkage</div>
          Die Verknüpfung verschiedener Datenquellen auf Einzelpatientenebene schafft neue Datensätze mit bisher
          unerreichbarem Informationsgehalt. So ermöglichen wir die Bearbeitung völlig neuer Forschungsfragen.
          <br /><br />
          <div class="box_number">01</div>
        </div>
        <div class="blue_box">
          <div class="header_box">Standardisierung</div>
          Einheitliche Standards und Schnittstellen schaffen eine gemeinsame Grundlage, auf der Forschende sich
          verständigen. So können verschiedene Forschungs-ansätze und Datenquellen miteinander verschnitten und
          verglichen werden.
          <div class="box_number">02</div>
        </div>
        <div class="blue_box">
          <div class="header_box">Kooperation</div>
          Innovative Forschung braucht heutzutage interdisziplinäre Zusammenarbeit. Wir schaffen eine Plattform für
          einen Austausch zwischen Datengebenden, Datennehmenden und Code-Entwicklern. So entsteht kooperative
          Forschungsarbeit.
          <div class="box_number">03</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
