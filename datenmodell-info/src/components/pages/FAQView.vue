<script>
import { Accordion, AccordionPanel, AccordionHeader, AccordionContent } from 'primevue'

export default {
  data() {
    return {
      contents: [
        {
          value: 0,
          question: 'Wie registriere ich mich für die Plattform?',
          answer: 'Momentan ist noch keine Registrierung möglich.',
        },
        {
          value: 2,
          question: 'Wie installiere ich die Client-App?',
          answer: "Weitergehende Informationen finden Sie auf unserer <a href=\"https:\\about.gitlab.com\">GitLab-Seite</a>. ",
        },
        {
          value:3,
          question: 'Wie erfüllt ein transaktionsbasierter Datentreuhänder die Anforderungen der DSGVO?',
          answer: 'Der Datentreuhänder kapselt die Analyse aller Daten in einer von außen unzugänglichen Infrastruktur ab, sodass lediglich Dunkelverarbeitungen möglich sind. Die ausgegebenen Datenanalysen werden mit geeigneten Mitteln aggregiert und anonymisiert. Somit können angebundene Daten niemals eine natürliche Person identifizierbar machen, wie von der DSGVO gefordert. Weitere Details entnehmen Sie bitte der Info-Seite über EuroDaT und unserem IT-Sicherheitskonzept.'
        }
      ]

    };
  },
  components: {
    Accordion,
    AccordionPanel,
    AccordionHeader,
    AccordionContent
  }
};
</script>


<template>
  <div class="max-w-6xl pb-12 w-full">
    <div class="bg-white">
      <Accordion :value="[]" multiple>
        <AccordionPanel v-for="entry in contents" :value="entry.value">
          <AccordionHeader> {{ entry.question }} </AccordionHeader>
          <AccordionContent>
            <div v-html="entry.answer" class="m-0">
            </div>
          </AccordionContent>
        </AccordionPanel>
      </Accordion>
    </div>
  </div>
</template>


