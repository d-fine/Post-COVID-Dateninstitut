<script setup lang="ts">
import { Card } from 'primevue';
import Button from 'primevue/button';

const notebook_logo = '/Logo_Rechner.png';
</script>

<template>
  <div class="max-w-6xl w-full">
    <div class="pt-4 pb-4 items-center ml-26 flex">
      <div class="block">
        <Card class="white-p-card max-w-2xl text-4xl mt-15 uppercase p-5 font-bold">
          <template #content>
            <span> Daten </span>
            <span class="text-(--df-orange)"> Verknüpfen</span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
      <div class="logo-container ml-70">
        <img :src="notebook_logo" alt="Logo" class="w-55 rounded-full bg-white -mb-55" />
      </div>
    </div>
    <div class="pt-2 pb-2 flex flex-row items-center text-1xl mt-15 ml-65 mb-25">
      <Card class="white-p-card font-bold pt-5 pb-5 pl-6 pr-6 w-91 text-lg">
        <template #content>
          Durch den Treuhänder können sensible Informationen sicher zusammen gebracht und Innovation auf vertrauensvolle
          Weise gefördert werden.
        </template>
      </Card>
    </div>

    <div class="white-mid-section mt-15 mb-25 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Über die Transaktionen </span>
      </div>
      <div class="text-left ml-25 mr-25 w-220 font-normal mb-7">
        In einem zunehmend datengetriebenen Umfeld ermöglicht der Datentreuhänder eine sichere und effiziente gemeinsame
        Analyse verteilter Daten. Als Datennehmer haben Sie die Möglichkeit, über den Datentreuhänder auf wertvolle
        Daten zuzugreifen, um fundierte Analysen durchzuführen.
        <br /><br />
        Der Prozess beginnt damit, dass Sie zunächst einen Antrag bei den jeweiligen Datenhaltendenstellen stellen.
        Dieser Antrag ist notwendig, um die Einhaltung aller rechtlichen Vorgaben und Datenschutzrichtlinien
        sicherzustellen. Sobald Ihr Antrag genehmigt wurde, werden die benötigten Daten von den Datenhaltenden
        freigegeben und bereitgestellt.
        <br /><br />
        Im letzten Schritt können Sie über die sichere Plattform des Datentreuhänders auf die freigegebenen Daten
        zugreifen. Hier haben Sie die Möglichkeit, die Daten zusammenzuführen, zu verknüpfen und detailliert
        auszuwerten. Dieser Prozess ermöglicht es Ihnen, tiefergehende Einblicke zu gewinnen und innovative Lösungen zu
        entwickeln, während die Sicherheit und Vertraulichkeit der Daten jederzeit gewährleistet bleiben. Der
        Datentreuhänder unterstützt Sie somit effektiv dabei, das volle Potenzial Ihrer Datenanalysen auszuschöpfen.
      </div>
      <div class="ml-25 mr-25 flex justify-center">
        <Button
          class="navigation_button w-[25%]! h-9"
          as="router-link"
          to="/data_processing/new_transaction"
          label="TRANSAKTION STARTEN >"
        />
      </div>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
