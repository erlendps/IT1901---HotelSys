## Arkitektur 
sett inn PlantUML

## Arbeidsvaner
Vi jobber fire timer sammen på skolen, og fordeler oppgaver innad. Dersom det er noen problemer tar vi kontakt digitalt. Når vi sitter fysisk på skolen bruker vi parprogrammering ved behov. Koden skrevet av enkeltperson blir gått gjennom av andre gruppemedlemmer, for best mulig Kodekvalitet.

## Kodekvalitet
Det er flere tester for alle de tre lagene. Jacoco brukes til å se testdekningsgrad, Maven-tillegget CheckStyle benyttes for å forsikre seg om at koden opprettholder kodingsstandard og SpotBugs benytter til å finne bugs i koden. I tillegg benyttes Mockito i de ulike testene i core, slik at klassene kan testes uavhengig av hverandre
