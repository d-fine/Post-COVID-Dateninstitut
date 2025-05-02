<script setup lang="ts">
import { ref } from 'vue';
import { Card, DataTable, Column } from 'primevue';

const dataCatalog = ref<Item[]>(initializeDataCatalog());
const expandedRows = ref({});

const microscop_logo = '/Nexus_Logo.png';

interface Item {
  title: string;
  dataProvider: string;
  description: string;
  link: string;
}

function initializeDataCatalog(): Item[] {
  return [
    {
      title: 'NAKO Gesundheitsstudie',
      dataProvider: 'NAKO',
      description:
        'Die NAKO Gesundheitsstudie (kurz: NAKO) ist eine Langzeit-Bevölkerungsstudie (Dauer 20-30 Jahre). Sie wird von einem Netzwerk deutscher Forschungseinrichtungen, bestehend aus der Helmholtz-Gemeinschaft, den Universitäten und der Leibniz-Gemeinschaft, organisiert und durchgeführt. Ziel ist es, den Ursachen für die Entstehung von Volkskrankheiten, wie beispielsweise Krebs, Diabetes, Infektionskrankheiten und Herzinfarkt auf den Grund zu gehen.',
      link: 'https://nako.de/',
    },
    {
      title: 'Sektorenübergreifende Plattform (SÜP)',
      dataProvider: 'NAPKON',
      description:
        'Bei der Sektorenübergreifenden Plattform handelt es sich um eine nationale, multizentrische, minimal interventionelle (Blutentnahmen, Abstrich, Urinsammlung, Befragungen), prospektive Kohortenstudie, die Patient*innen mit einer diagnostizierten SARS-CoV-2 Infektion sowie Kontrollen einschließt, die stationär oder ambulant in universitären und nicht-universitären Krankenhäusern oder Arztpraxen aufgenommen oder behandelt werden.',
      link: 'https://napkon.de/',
    },
    {
      title: 'Hochauflösende Plattform (HAP)',
      dataProvider: 'NAPKON',
      description:
        'Die Hochauflösende Plattform (HAP) erfasst Daten stationär aufgenommener COVID-19-Patient*innen mit dem Ziel einer tiefen Phänotypisierung. Mittels prädiktiver Methoden soll der Verlauf und das Ergebnis der akuten und postakuten COVID-19-Erkrankung zu einem frühen Zeitpunkt vorhergesagt werden können, um somit eine angepasste klinische und paraklinische Versorgung zu gewährleisten. Des Weiteren sollen damit zentrale pathophysiologische Mechanismen spezifischer COVID-19-bezogener Pathologien entschlüsselt werden, um die Entwicklung von Therapien zu unterstützen.  ',
      link: 'https://napkon.de/',
    },
    {
      title: 'Populationsbasierte Plattform (POP)',
      dataProvider: 'NAPKON',
      description:
        'In der POP werden in geographisch definierten Sammelgebieten alle Patient*innen mit überstandener SARS-CoV-2-Infektion schweregradunabhängig und populationsrepräsentativ identifiziert und in ein diagnostisches Langzeitprogramm eingeladen. In einem epidemiologisch ausgerichteten Fokuskrankenhaus der jeweiligen Sammelregionen erfolgt die detaillierte retrospektive Erfassung des akuten Erkrankungsverlaufes und prospektive Biomaterialsammlung sowie die Erfassung der Langzeitfolgen und Komorbiditäten.',
      link: 'https://napkon.de/',
    },
    {
      title: 'German Corona Consensus Dataset (GECCO)',
      dataProvider: 'NAPKON',
      description:
        'Der GECCO Datensatz wurde kreiert, um Wissenschaftlern eine gemeinsame Grundlage zu geben, um die Zusammenführung und Verarbeitung von studienbezogenen Daten für die weitere Nutzung in allen beteiligten Projekten zur Verfügung zu stellen. Schon heute ist der GECCO Datensatz ein wichtiges Tool für die Projekte NAPKON, CODEX, COMPASS und ORCHESTRA.',
      link: 'https://napkon.de/',
    },
    {
      title: 'COVID-19-Impfungen in Deutschland',
      dataProvider: 'Robert Koch-Institut',
      description:
        "Die COVID-19-Impfung kann einen Wendepunkt in der Kontrolle der COVID-19-Pandemie darstellen und erfährt daher hohes Maß an öffentlicher Aufmerksamkeit. Einführung und Umsetzung der COVID-19-Impfung gehen mit besonderen Herausforderungen einher, die bei der Impfdatenerfassung zu berücksichtigen sind. In diesem Kontext ist es Ziel des Projekts 'Digitales Impfquoten-Monitoring' (DIM), tagesaktuell, bundesweit die Impfquote zu erfassen und folgend aufbereitet darzustellen, um zeitnah den Verlauf der COVID-19-Impfkampanne zu analysieren, bei Bedarf nach zusteuern, und logistisch bzw. organisatorische Konsequenzen zu ziehen. Der durch das DIM-Projekt bereitgestellte Datensatz enthält Daten über den Verlauf der COVID-19 Impfungen in Deutschland. Die hier veröffentlichten Impfdaten aggregieren Daten aus drei Datenquellen: - Die DIM-Daten enthalten Angaben der Impfzentren, mobilen Impfteams, Krankenhäuser und der Betriebsärzte_innen, die über die DIM-Webanwendung übermittelt werden - Der täglich aggregierte Kerndatensatz der impfenden Ärzt_innen über die Kassenärztliche Bundesvereinigung (KBV) - Der täglich aggregierte Kerndatensatz der impfenden Ärzt_innen über die Privatärztliche Bundesvereinigung (PBV).",
      link: 'https://robert-koch-institut.github.io/COVID-19-Impfungen_in_Deutschland/',
    },
    {
      title: 'German Index of Socioeconomic Deprivation (GISD)',
      dataProvider: 'Robert Koch-Institut',
      description:
        'Der German Index of Socioeconomic Deprivation (GISD) ist ein am Robert Koch-Institut entwickelter Index zur Erfassung regionaler sozioökonomischer Benachteiligung. Er wird verwendet, um regionale sozioökonomische Ungleichheiten in der Gesundheit sichtbar zu machen und Ansatzpunkte zur Erklärung regionaler Unterschiede in der Gesundheit aufzeigen zu können. Mit dem GISD wird es möglich, sozioökonomische Unterschiede in den Gesundheitschancen, Krankheits- und Sterberisiken in Deutschland auch dann zu untersuchen, wenn die betreffenden Gesundheitsdaten auf individueller Ebene keine Information zum sozioökonomischen Status enthalten. Für die Generierung des GISD werden Information der Bildungs-, Beschäftigungs- und Einkommenssituation in Kreisen und Gemeinden aus der Datenbank INKAR verwendet. Er wird auf der Ebene der Gemeinden generiert und wird für die Raumbezüge Gemeinden, Gemeindeverbände, Stadt- und Landkreise, Raumordnungsregionen, NUTS-2 und Postleitzahlbereiche bevölkerungsgewichtet aggregiert bereitgestellt. Die Gewichtung der Indikatoren wird über Hauptkomponentenanalysen innerhalb der Teildimensionen vorgenommen. Die aktuell verfügbaren Daten beziehen sich auf den Gebietsstand 31.12.2019 und enthalten Werte von 1998 bis 2019.',
      link: 'https://robert-koch-institut.github.io/German_Index_of_Socioeconomic_Deprivation_GISD/',
    },
    {
      title: 'Hochfrequente Mental Health Surveillance',
      dataProvider: 'Robert Koch-Institut',
      description:
        'Im Rahmen der Mental Health Surveillance (MHS) am Robert Koch-Institut (RKI) werden für eine Auswahl an Indikatoren der psychischen Gesundheit von Erwachsenen basierend auf Surveydaten Zeitreihen bestehend aus gleitenden Drei-Monats-Schätzern und Glättungskurven berechnet. Dadurch sollen Entwicklungen in der psychischen Gesundheit der erwachsenen Bevölkerung in Deutschland mit möglichst geringem Zeitverzug beobachtet und insbesondere negative Entwicklungen frühzeitig erkannt werden. Diese hochfrequente Surveillance wurde ursprünglich vor dem Hintergrund neuer Informationsbedarfe zur Entwicklung der psychischen Gesundheit der Bevölkerung in der COVID-19-Pandemie entwickelt.',
      link: 'https://robert-koch-institut.github.io/Hochfrequente_Mental_Health_Surveillance',
    },
    {
      title: 'Inanspruchnahme von Routineimpfungen in Deutschland - Ergebnisse aus der KV-Impfsurveillance',
      dataProvider: 'Robert Koch-Institut',
      description:
        'Dem Robert Koch-Institut (RKI) obliegt die Aufgabe, Daten zur Inanspruchnahme von Schutzimpfungen in der Bevölkerung in Deutschland zu erheben, aufzubereiten und national wie international zu berichten. Die wichtigste Datenquelle zur Berechnung von Impfquoten stellen die vertragsärztlichen Abrechnungsdaten dar, die von den Kassenärztlichen Vereinigungen (KVen) im Rahmen der „KV-Impfsurveillance“ (KVIS) an das RKI übermittelt werden. Begonnen als Gemeinschaftsprojekt mit den KVen im Jahr 2004, ist die KVIS seit dem Jahr 2020 im Infektionsschutzgesetz (IfSG) gesetzlich verankert (§13 (5) IfSG (https://www.gesetze-im-internet.de/ifsg/__13.html)). Neben der jährlichen Berichterstattung zu aktuellen Impfquoten im Epidemiologischen Bulletin (https://www.rki.de/DE/description/Infekt/EpidBull/epid_bull_node.html), ergänzt VacMap (https://www.rki.de/vacmap) als interaktives Dashboard die Kommunikation der Impfquoten in Deutschland und ermöglicht die Nachnutzung der Daten durch Akteure der Impfprävention. Anhand der Darstellung der Impfquoten nach Altersgruppen, im Zeitverlauf und auf regionaler Ebene können Defizite in der Umsetzung der Impfempfehlungen identifiziert und in der Folge zielgruppenspezifisch adressiert werden.',
      link: 'https://robert-koch-institut.github.io/Inanspruchnahme_von_Routineimpfungen_in_Deutschland-Ergebnisse_aus_der_KV-Impfsurveillance',
    },
    {
      title: 'Gesundheitsberichterstattung - Daten zu nichtübertragbaren Erkrankungen',
      dataProvider: 'Robert Koch-Institut',
      description:
        'Die Gesundheitsberichterstattung (GBE) des RKI informiert regelmäßig über die gesundheitliche Situation der Bevölkerung in Deutschland. Das Themenspektrum ist vielfältig und reicht von Gesundheitszustand und Einflussfaktoren auf die Gesundheit über Gesundheitsförderung, Prävention und gesundheitliche Versorgung bis hin zu den damit verbundenen Kosten. Der Fokus des vorliegenden Datensatzes liegt auf Indikatoren zu nichtübertragbaren Erkrankungen (noncommunicable diseases, NCD (https://www.who.int/news-room/fact-sheets/detail/noncommunicable-diseases)), wie Diabetes mellitus, Herz-Kreislauf-Erkrankungen, Krebs und psychischen Störungen sowie deren Einflussfaktoren.',
      link: 'https://robert-koch-institut.github.io/Gesundheitsberichterstattung_-_Daten_zu_nichtuebertragbaren_Erkrankungen/',
    },
    {
      title: 'Abwassersurveillance AMELAG',
      dataProvider: 'Robert Koch-Institut',
      description:
        'Das Vorhaben „Abwassermonitoring für die epidemiologische Lagebewertung“ (AMELAG) läuft vom 22.11.2022 bis zum 31.12.2025. Behörden, Kläranlagen und Labore arbeiten zusammen, um Proben zu nehmen, zu analysieren und zu bewerten. Das Ziel dieses Vorhabens ist es, SARS-CoV-2-Nachweise aus dem Abwasser als zusätzlichen Indikator zur epidemiologischen Lagebewertung auf Länder- und Bundesebene zu etablieren. Ebenso ist es das Ziel, Strukturen und Prozesse für ein bundesweites Netzwerk für die Abwassersurveillance weiter auszubauen, Konzepte für eine Verstetigung zu erstellen und die Möglichkeiten für ein Monitoring von weiteren Krankheitserregern im Abwasser zu erforschen. Abwassersurveillance ist eine Technik, um Erreger im Abwasser nachzuweisen, um Gesundheitsschutzmaßnahmen besser steuern zu können. Abwasserdaten erlauben keine genaue Einschätzung von Krankheitsschwere oder der Belastung des Gesundheitssystems. Bei der epidemiologischen Bewertung sollten die Daten mit anderen Indikatoren, z.B. aus der syndromischen Surveillance, kombiniert werden.',
      link: 'https://robert-koch-institut.github.io/Abwassersurveillance_AMELAG',
    },
    {
      title: 'Laborbestätigte Influenzafälle in Deutschland',
      dataProvider: 'Robert Koch-Institut',
      description:
        'Im Datensatz "Laborbestätigte Influenzafälle in Deutschland" werden die wöchentlichen Fallzahlen und Inzidenzen (Fälle pro 100.000 Personen) der nach den Vorgaben des Infektionsschutzgesetzes (IfSG) (https://www.gesetze-im-internet.de/ifsg/index.html) von den Gesundheitsämtern in Deutschland an die zuständigen Landesbehördne und das Robert Koch-Institut (RKI) übermittelten laborbestätigten Influenzafälle bereitgestellt.',
      link: 'https://robert-koch-institut.github.io/Influenzafaelle_in_Deutschland/',
    },
    {
      title: 'Respiratorische Synzytialvirusfälle in Deutschland',
      dataProvider: 'Robert Koch-Institut',
      description:
        'Im Datensatz "Respiratorische Synzytialvirusfälle in Deutschland" werden die Fallzahlen und Inzidenzen (Fälle pro 100.000 Personen) der nach den Vorgaben des Infektionsschutzgesetzes (IfSG) (https://www.gesetze-im-internet.de/ifsg/index.html) von den Gesundheitsämtern in Deutschland an die zuständigen Landesbehörden und das Robert Koch-Institut (RKI) übermittelten Infektionen mit dem Respiratorischen Synzytial-Virus (RSV) nach Meldewoche bereitgestellt.',
      link: 'https://robert-koch-institut.github.io/Influenzafaelle_in_Deutschland/',
    },
    {
      title: 'GrippeWeb - Daten des Wochenberichts',
      dataProvider: 'Robert Koch-Institut',
      description:
        'GrippeWeb ist ein Online-Portal des Robert Koch-Instituts (RKI). Es wurde im Jahr 2011 gegründet und ist das erste Webportal, das die Aktivität akuter Atemwegserkrankungen (ARE) in Deutschland beobachtet, und dabei Informationen direkt aus der Bevölkerung verwendet. Es ist ein robustes und zeitnahes System der partizipativen syndromischen Surveillance, welches aus den ganzjährigen wöchentlichen Selbstauskünften von Bürgerinnen und Bürgern gespeist wird und somit unabhängig von einer ärztlichen Konsultation ist. GrippeWeb wurde vom RKI u. a. als Überwachungsinstrument in der Vorbereitung und Bewältigung zukünftiger Pandemien entwickelt und als dieses auch zur Lagebewertung und Risikoeinschätzung der Coronavirus Disease 2019 (COVID-19) Pandemie genutzt. Bei GrippeWeb registrierte Teilnehmer und Teilnehmerinnen, werden wöchentlich gefragt, ob sie eine neu aufgetretene Atemwegserkrankung hatten mit Symptomen wie z.B. Husten, Schnupfen, Halsschmerzen oder Fieber, oder ob dies nicht der Fall war. Darüber hinaus werden u.a. potentielle Erregernachweise abgefragt. Über diese Angaben lassen sich wöchentliche gewichtete Inzidenzen (pro 100.000 Einw.) von akuten Atemwegserkrankungen (ARE), und grippeähnlichen Erkrankungen (Influenza-like Illness = ILI) in der Bevölkerung schätzen. Die wöchentlich aktualisierten Ergebnisse werden jeden Freitag im GrippeWeb-Wochenbericht unter www.rki.de/grippeweb (http://www.rki.de/grippeweb) veröffentlicht sowie der Datensatz auf GitHub zur Verfügung gestellt.',
      link: 'https://robert-koch-institut.github.io/GrippeWeb_Daten_des_Wochenberichts/',
    },
    {
      title: 'SARS-CoV-2 Sequenzdaten aus Deutschland',
      dataProvider: 'Robert Koch-Institut',
      description:
        'Ein zentraler Bestandteil einer erfolgreichen Erregersurveillance ist das Verständnis der Verbreitung eines Erregers sowie seiner pathogenen Eigenschaften. Hierbei stellt das Wissen über das Erregergenom eine wichtige Informationsquelle dar. So erlaubt der Nachweis von Mutationen im Genom eines Erregers, Verwandtschaftsbeziehungen zu rekonstruieren, Übertragungswege aufzudecken und Resistenzen vorherzusagen. Die Integrierte Genomische Surveillance (IGS) von SARS-CoV-2 zielt darauf ab, die Verbreitung des Virus und insbesondere von besorgniserregenden Virusvarianten in der Bevölkerung zu überwachen sowie auftretende Veränderungen des Virus genau zu beobachten. Besondere Bedeutung kommt dabei der öffentlichen Bereitstellung der genomischen Daten zu, um Wissenschaftlern in Deutschland und weltweit die Möglichkeit zu eigenständigen Analysen zu eröffnen. Im Rahmen der Coronavirus-Surveillanceverordnung (https://www.gesetze-im-internet.de/corsurv/BJNR601910021.html) wurden bis zum 31.05.2023 SARS-CoV-2 Sequenzdaten aus ganz Deutschland über den Deutschen Elektronischen Sequenzdaten-Hub (DESH) an das RKI übermittelt (https://doi.org/10.5281/zenodo.8046538). Mit Ablauf der Verordnung werden künftig Proben durch das IMSSC2 Labornetzwerk bereitgestellt und am RKI sequenziert, analysiert und hier bereitgestellt. Trotz reduzierter Probenanzahl, wird durch die sorgfältige Auswahl der beteiligten Labore ein repräsentativer Einblick in die Viruspopluation gesichert (Djin Ye Oh et al. 2022) (https://doi.org/10.1093/cid/ciac399). Zusätzlich werden Sequenzen vom NRZ Coronaviren an der Charité beigetragen um das IMSSC2 Netzwerk zu ergänzen.',
      link: 'https://robert-koch-institut.github.io/SARS-CoV-2-Sequenzdaten_aus_Deutschland/',
    },
    {
      title: 'SARI-Hospitalisierungsinzidenz',
      dataProvider: 'Robert Koch-Institut',
      description:
        'Zur Einschätzung der Krankheitslast schwerer symptomatischer Atemwegsinfektionen im stationären Bereich wird mithilfe von Daten aus der syndromischen Krankenhaussurveillance ICOSARI die Inzidenz der Fälle, die mit einer schweren akuten respiratorischen Infektion (SARI) in ein Krankenhaus zur Behandlung aufgenommen wurden, pro 100.000 Einwohner berechnet. Zeitnahe und valide Daten über die Häufigkeit von SARI im zeitlichen Verlauf und im Kontext zirkulierender Atemwegserreger in der Bevölkerung sind essenziell für die Einschätzung der epidemiologischen Lage und die Entwicklung und Anpassung von Präventionsstrategien.',
      link: 'https://robert-koch-institut.github.io/SARI-Hospitalisierungsinzidenz/',
    },
    {
      title: 'ARE-Konsultationsinzidenz',
      dataProvider: 'Robert Koch-Institut',
      description:
        'Zur Einschätzung der Krankheitslast akuter Atemwegsinfektionen im ambulanten Bereich wird die Inzidenz der Arztbesuche wegen einer akuten respiratorischen Erkrankung pro 100.000 Einwohner mit Daten aus einem Netzwerk primärversorgender Praxen in Deutschland wöchentlich berechnet (ARE-Konsultationsinzidenz). Zeitnahe und valide Daten über die Häufigkeit von akuten Atemwegserkrankungen, die zu einem Arztbesuch führen, sind essenziell für die Einschätzung der epidemiologischen Lage und die Entwicklung und Anpassung von Präventionsstrategien.',
      link: 'https://robert-koch-institut.github.io/ARE-Konsultationsinzidenz/',
    },
    {
      title: 'COVID-ARE-Konsultationsinzidenz',
      dataProvider: 'Robert Koch-Institut',
      description:
        'Zur Einschätzung der Krankheitslast symptomatischer Atemwegsinfektionen im ambulanten Bereich wird die Inzidenz der Arztbesuche wegen einer akuten respiratorischen Erkrankung mit zusätzlicher COVID-19-Diagnose pro 100.000 Einwohner mithilfe von Daten aus dem SEED(ARE)-Modul der Arbeitsgemeinschaft Influenza wöchentlich berechnet (COVID-ARE-Konsultationsinzidenz). Zeitnahe und valide Daten über die Häufigkeit von akuten Atemwegserkrankungen mit COVID-19 sind essenziell für die Einschätzung der epidemiologischen Lage und die Anpassung der Maßnahmen während der COVID-19-Pandemie.',
      link: 'https://robert-koch-institut.github.io/COVID-ARE-Konsultationsinzidenz/',
    },
    {
      title: 'AKTIN - Daten zur Aufenthaltsdauer in Notaufnahmen',
      dataProvider: 'Robert Koch-Institut',
      description:
        'Die Belastung von Notaufnahmen hängt von verschiedenen Faktoren ab, wie z.B. der Anzahl der Patient:innen, der Schwere ihrer Fälle, der Personalsituation und der Kapazität der Intensivbetten. Die durchschnittliche Aufenthaltsdauer in der Notaufnahme kann als Indikator für die Belastungssituation herangezogen werden. Der Datensatz „AKTIN - Daten zur Aufenthaltsdauer in Notaufnahmen“ enthält aggregierte Daten der Routinedokumentation aus einer Auswahl deutscher Notaufnahmen aus dem AKTIN-Notaufnahmeregister. Zur Erfassung der speziellen Belastungssituation während der COVID-19-Pandemie werden im vorliegenden Datensatz Daten zur durchschnittliche Aufenthaltsdauer von Patienti:innen in den Notaufnahmen bereitgestellt und mit den Werten vor der Pandemie verglichen.',
      link: 'https://robert-koch-institut.github.io/AKTIN_Daten_zur_Aufenthaltsdauer_in_Notaufnahmen/',
    },
    {
      title: 'Intensivkapazitäten und COVID-19-Intensivbettenbelegung in Deutschland',
      dataProvider: 'Robert Koch-Institut',
      description:
        'Das Intensivregister (www.intensivregister.de) ist eine digitale Plattform zur Echtzeiterfassung von intensivmedizinischen Behandlungs- und Bettenkapazitäten sowie den Fallzahlen intensivmedizinisch behandelter COVID-19-Patient:innen der etwa 1.300 Akut-Krankenhäuser Deutschlands. Bis 12 Uhr ist die tägliche Meldung laut Verordnung für die Krankenhäuser verpflichtend. Darüber hinaus kann beliebig oft gemeldet werden, sodass Veränderungen in den Kapazitäten aktuell berichtet werden können. Damit ermöglicht das Intensivregister in der Pandemie sowie darüber hinaus, Engpässe in der intensivmedizinischen Versorgung im regionalen und zeitlichen Vergleich zu erkennen. Es schafft somit eine wertvolle Grundlage zur Reaktion und zur datengestützten Handlungssteuerung in Echtzeit. Zur Aufrechterhaltung der Krankenhausversorgung bietet das DIVI-Intensivregister demnach ein Portal zur Suche freier Intensivbetten und zur Kontaktaufnahme zwischen Fachkolleg*innen, um die gegenseitige Unterstützung bei Behandlungsfragen im intensivmedizinischen Kontext zu erleichtern. Ebenso liefert es eine maßgebliche Informationsgrundlage für staatliche Steuerungs- und Planungsmaßnahmen sowie für die lokale und überregionale Steuerung und Koordinierung der Bettenbelegung.',
      link: 'https://robert-koch-institut.github.io/Intensivkapazitaeten_und_COVID-19-Intensivbettenbelegung_in_Deutschland/',
    },
    {
      title: 'Daten der Notaufnahmesurveillance',
      dataProvider: 'Robert Koch-Institut',
      description:
        "Der Datensatz 'Notaufnahmesurveillance' enthält aggregierte Daten der Routinedokumentation aus einer Auswahl deutscher Notaufnahmen aus dem AKTIN-Notaufnahmeregister (https://www.aktin.org/de-de/) und bildet die Grundlage für die Notaufnahmesurveillance (https://www.rki.de/sumo) am RKI.",
      link: 'https://robert-koch-institut.github.io/Daten_der_Notaufnahmesurveillance/',
    },
    {
      title: 'SARS-CoV-2 Infektionen in Deutschland',
      dataProvider: 'Robert Koch-Institut',
      description:
        'Der vorliegende Datansatz enthält umfassende Informationen zu SARS-CoV-2-Infektionen in Deutschland, die gemäß dem Infektionsschutzgesetze (IfSG) von den Gesundheitsämtern an das Robert Koch-Institut (RKI) gemeldet wurden. Die Daten umfassen Informationen zur Anzahl der bestätigten Fälle, Todesfälle und Genesungen, aus denen sich weitere Kennzahlen im Zusammenhang mit der COVID-19-Pandemie ableiten lassen. Der Datensatz wird täglich aktualisiert und enthält detaillierte Informationen auf Landkreisebene, die nach verschiedenen Altersgruppen aufgeschlüsselt sind. Die Bereitstellung des Datensatzes soll dazu beitragen, das Verständnis der COVID-19-Pandemie in Deutschland zu verbessern und die Berichterstattung, Forschung und Analyse in diesem Bereich zu unterstützen.',
      link: 'https://robert-koch-institut.github.io/SARS-CoV-2-Infektionen_in_Deutschland',
    },
    {
      title: 'COVID-19-Hospitalisierungen in Deutschland',
      dataProvider: 'Robert Koch-Institut',
      description:
        "Im Datensatz 'COVID-19-Hospitalisierungen' werden die aktuellen Zahlen der nach den Vorgaben des Infektionsschutzgesetzes - IfSG - erfassten hospitalisierten COVID-19-Fälle bereitgestellt. Um den Trend der Anzahl von Hospitalisierungen und der 7-Tage-Hospitalisierungsinzidenz besser bewerten zu können, wird die berichtete Hospitalisierungsinzidenz um eine Schätzung der zu erwartenden Anzahl an verzögert berichteten Hospitalisierungen ergänzt. Neben den Daten der gemeldeten COVID-19-Hospitalisierungen auf Bundes- und Länderebene wird daher ein Nowcasting der Anzahl hospitalisierter Fälle und der 7-Tage-Hospitalisierungsinzidenz auf Bundesebene durchgeführt. Ziel ist die Schätzung der Anzahl von hospitalisierten COVID-19-Fällen mit Meldedatum innerhalb der sieben vorhergehenden Tage - inklusive der noch nicht an das RKI berichteten Hospitalisierungen. Aufbauend auf dem Nowcasting wird eine Schätzung der adjustierten 7-Tage-Hospitalisierungsinzidenz durchgeführt.",
      link: 'https://robert-koch-institut.github.io/COVID-19-Hospitalisierungen_in_Deutschland/',
    },
    {
      title: 'COVID-19-Todesfälle in Deutschland',
      dataProvider: 'Robert Koch-Institut',
      description:
        "Im Datensatz 'COVID-19-Todesfälle in Deutschland' werden die Todesfälle in Bezug auf COVID-19 in Deutschland bereitgestellt. Darüber hinaus wird neben der Anzahl der übermittelten Todesfälle der Fall-Verstorbenen-Anteil berechnet. Angaben zum Tod zählen zu den melde- und übermittlungspflichtigen Inhalten. Bei der Ermittlung von Todesfällen und der Bewertung der entsprechenden Informationen in den Gesundheitsämtern unterschiedlich vorgegangen. In der Folge könnte es einerseits zu einer Unterschätzung der Anzahl der Todesfälle, andererseits zu einer Überschätzung des Anteils der Verstorbenen einer Infektionskrankheit kommen. Ausführlich Hinweise zur Datenerhebung und Interpretation können der Datensatzdokumentation entnommen werden.",
      link: 'https://robert-koch-institut.github.io/COVID-19-Todesfaelle_in_Deutschland/',
    },
    {
      title: '7-Tage-Inzidenz der COVID-19-Fälle in Deutschland',
      dataProvider: 'Robert Koch-Institut',
      description:
        "Im Datensatz '7-Tage-Inzidenz von COVID-19 in Deutschland' werden die aktuellen 7-Tage-Inzidenzen der nach den Vorgaben des Infektionsschutzgesetzes - IfSG - (https://www.gesetze-im-internet.de/ifsg/index.html) an das RKI übermittelten COVID-19-Fälle veröffentlicht. Datengrundlage zur Berechnung der 7-Tage-Inzidenzen sind die an das RKI übermittelten COVID-19-Fälle. Eine detaillierte Dokumentation zur Erhebung der Daten zum Infektionsgeschehen ist im Datensatz 'SARS-CoV-2-Infektionen in Deutschland (https://github.com/robert-koch-institut/SARS-CoV-2-Infektionen_in_Deutschland/blob/main/Aktuell_Deutschland_SarsCov2_Infektionen.csv)' enthalten. Die für die Berechnung der 7-Tage-Inzidenz notwendigen Bevölkerungsdaten bezieht das RKI durch das Statistisches Bundesamt (Destatis), Referat F24 | Bevölkerungsfortschreibung, Ausländer- und Integrationsstatistiken.",
      link: 'https://robert-koch-institut.github.io/COVID-19_7-Tage-Inzidenz_in_Deutschland/',
    },
    {
      title: 'COVID-SARI-Hospitalisierungsinzidenz',
      dataProvider: 'Robert Koch-Institut',
      description:
        'Zur Einschätzung der Krankheitslast schwerer symptomatischer Atemwegsinfektionen im stationären Bereich wird mithilfe von Daten aus der syndromischen Krankenhaussurveillance ICOSARI die Inzidenz der Fälle, die mit einer schweren akuten respiratorischen Infektion (SARI) und einer COVID-19-Diagnose  in ein  Krankenhaus zur Behandlung aufgenommen wurden, pro 100.000 Einwohner berechnet. Zeitnahe und valide Daten über die Häufigkeit von SARI mit COVID-19 sind essenziell für die Einschätzung der epidemiologischen Lage und die Anpassung der Maßnahmen während der COVID-19-Pandemie.',
      link: 'https://robert-koch-institut.github.io/COVID-SARI-Hospitalisierungsinzidenz/',
    },
  ];
}
</script>

<template>
  <div class="max-w-6xl w-full">
    <div class="pt-4 pb-4 items-center ml-26 flex mb-45">
      <div class="block">
        <Card class="white-p-card max-w-2xl text-4xl mt-15 uppercase p-5 font-bold">
          <template #content>
            <span class="text-(--df-orange)"> Der Datenkatalog </span> <br />
            <span> Daten finden und nutzen </span>
          </template>
        </Card>
        <div style="background-color: var(--df-orange); width: 70%; height: 5px; margin-left: 16px"></div>
      </div>
      <div class="logo-container ml-30">
        <img :src="microscop_logo" alt="Logo" class="w-55 rounded-full bg-white -mb-60" />
      </div>
    </div>

    <div>
      <div class="text-3xl text-left uppercase ml-25 text-(--df-orange)">
        <span> Datenkatalog durchsuchen </span>
      </div>
    </div>

    <div class="mt-5 mb-15 ml-25 mr-25">
      <DataTable
        v-model:expandedRows="expandedRows"
        :value="dataCatalog"
        dataKey="title"
        paginator
        :rows="10"
        padding="30px"
      >
        <Column expander style="width: 3rem" />
        <Column field="title" header="Titel" style="width: 50%" />
        <Column field="dataProvider" header="Datenhalter" style="width: 25%" />
        <Column header="Website" style="width: 25%">
          <template #body="slotProps">
            <a :href="slotProps.data.link" target="_blank" class="underline"> Mehr Informationen </a>
          </template>
        </Column>
        <template #expansion="slotProps">
          <div class="p-3" style="width: 90%; margin-left: 49px">
            <h5>{{ slotProps.data.description }}</h5>
          </div>
        </template>
      </DataTable>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>
