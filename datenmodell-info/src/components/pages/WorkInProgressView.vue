<script>
import { Card } from 'primevue';

export default {
    data() {
    return {
      notebook_logo: '/Logo_Rechner.png',
    };
  },
    components: {
        Card
    }
};
</script>


<template>
    <div class="max-w-6xl w-full">

        <div class="pt-4 pb-4 items-center ml-26 flex">
            <div class="block">
                <Card class="white-p-card max-w-3xl text-4xl mt-15 p-5 font-bold"> 
                    <template #content>
                        <span class="text-(--df-orange)"> Diese Seite befindet sich im Aufbau. <br> </span>
                        <span class="text-lg"> Schauen Sie gerne zu einem späteren Zeitpunkt nochmal vorbei. </span>
                    </template>
                </Card>
                <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px;">         
                </div>
            </div>
        </div>
        <div class="pt-2 pb-2 flex flex-row text-1xl mt-15 ml-150">
            <div class="logo-container ml-25"> 
                <a href="https://www.eurodat.org/"> 
                    <img :src="notebook_logo" alt="Logo" class="w-70 rounded-full bg-white -mt-7 mb-25"/>  
                </a>  
            </div>
        </div>

    </div>    
</template>
  

<style scoped src="src/assets/main.css"> 

</style>