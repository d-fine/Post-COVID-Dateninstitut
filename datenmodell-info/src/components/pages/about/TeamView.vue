<script lang="ts">
import { Card } from 'primevue';

export default {
  data() {
    return {
      notebook_logo: '/Logo_Rechner.png',
      dfine: '/dfine_clear.png',
      uni: '/uni.png',
      uni_giessen: '/uni_giessen.png',
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
        <Card class="white-p-card max-w-2xl text-4xl mt-15 uppercase h-28 p-5 font-bold flex justify-center">
          <template #content>
            <span> Das Konsortium </span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
      <div class="logo-container ml-85">
        <img :src="notebook_logo" alt="Logo" class="w-55 rounded-full bg-white -mb-60" />
      </div>
    </div>
    <div class="pt-2 pb-2 flex flex-row items-center text-1xl mt-15 ml-70">
      <Card class="white-p-card font-bold pt-5 pb-5 pl-6 pr-6 w-75 text-lg">
        <template #content> Unser Konsortium kombiniert medizinisches, rechtliches und technisches Wissen. </template>
      </Card>
    </div>

    <div class="white-mid-section mt-15 mb-15 pt-13 pb-13">
      <div class="text-3xl text-left uppercase pb-7 ml-25 mr-25">
        <span> Unsere Expertise </span>
      </div>
      <div class="text-left pb-12 ml-25 mr-25 w-180 font-normal">
        Die inhaltlichen Herausforderungen der Post-Covid Challenge sind vielfältig und erstrecken sich von
        medizinischen bis hin zu rechtlichen Fachfragen. Darüber hinaus ist die Anbindung der innovativen
        Treuhänder-Lösung technisch hoch anspruchsvoll.
        <br /><br />
        Unser Team kombiniert in einzigartiger Weise alle notwendigen fachlichen und technischen Kenntnisse und
        Fähigkeiten, um den hohen Ansprüchen der Post-Covid Challenge gerecht zu werden.
      </div>
      <div class="flex">
        <div class="blue_box ml-[100px]! flex flex-col">
          <div class="header_box">d-fine</div>
          <div>
            d-fine ist eine Unternehmensberatung mit technologisch, analytisch, quantitativem Fokus. Im Konsortium
            verantwortet das Team die Software-Entwicklung und die Anbindung des Datentreuhänders.
          </div>
          <div class="logo">
            <img :src="dfine" alt="Logo" class="h-15 mt-9" />
          </div>
        </div>
        <div class="blue_box flex flex-col">
          <div class="header_box">Medizin</div>
          <div>
            Das Institut für Digitale Medizin und Klinische Datenwissenschaften sowie die Infektiologie der Uniklinik
            Frankfurt bieten international anerkannte Expertise in der klinischen Post-Covid Forschung.
          </div>
          <div class="logo">
            <img :src="uni" alt="Logo" class="h-15 mt-9" />
          </div>
        </div>
        <div class="blue_box flex flex-col">
          <div class="header_box">Recht</div>
          <div>
            Der Lehrstuhl für Öffentliches Recht der Universtität Gießen bringt Expertise zu Datenschutz und Ethikfragen
            ein. Im Konsortium prüft das Team regulatorische Rahmenbedingungen und entwickelt die Datentaxonomie.
          </div>
          <div class="logo">
            <img :src="uni_giessen" alt="Logo" class="h-15 mt-9" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
