[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-908a85?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2116/gr2116/-/tree/main/)


# IT1901 Group project - HotelSys

Dette er et utviklingsprosjekt for gruppe 16 i faget IT1901.
Vi utvikler [HotelSys](gr2116-project) - en applikasjon for å booke hotellrom.

Kodingsprosjektet ligger i mappen [gr2116-project](gr2116-project). Her ligger de fire hovedmodulene som utgjør prosjektet. [`core`](gr2116-project/core) refererer til domenelogikk, [`persistence`](gr2116-project/persistence) refererer til fillagringssystemet og [_ui_](gr2116-project/ui/src/main/java/gr2116/ui) refererer til det grafiske brukergrensesnittet i applikasjonen. [`RESTservice`](gr2116-project/RESTservice) utgjøre REST API-et som håndterer HTTP-request sendt fra applikasjonen. Dokumentasjon for hver enkelt modul ligger i [modules](docs/modules) og under "rot-mappen" til hver modul.

Diagrammer er skrevet i markdown og bruker PlantUML. Kildekoden for hvert diagram ligger i [diagrams](docs/diagrams).

## Bygging og kjøring av prosjektet

Selve prosjektet ligger under [**gr2116-project**](gr2116-project). Prosjektet kjøres og bygges av maven. Følgende kommandoer gjelder fra **gr2116-project**-mappa. Prosjektet må bygges før det kan kjøres eller testes.

For å bygge:
> mvn install

Det finnes to versjoner av appen. Den tar ikke i bruk REST-APIet og lagrer lokalt. Den andre versjonen tar i bruk REST-APIet, som den gjør ved å sende HTTP-requests til endepunktet. Dette er forklart mer i egen README fil i [`RESTservice`](gr2116-project/RESTservice).
For å kjøre den lokale versjonen av appen:

> mvn -pl ui javafx:run

For å kjøre "remote"-versjonen av appen må man også starte en webserver som skal håndtere HTTP-requester. Dette gjøres ved å starte en jetty webapp.
For å starte jetty:

> mvn -pl webapp jetty:run

Deretter kan man starte appen:

> mvn -P remoteapp -pl ui javafx:run

For å teste:

> mvn test

For å verifisere, samt å kjøre integrasjonstester:

> mvn verify

Test coverage fra jacoco ligger i `module-name/target/site/index.html`. I GitPod kan denne vises ved hjelp av Live Server extension. Det samme kan man lokalt i VSCode med samme Live Server extension. Vi har hatt noen problemer med å kjøre UI tester (både ui-tester og integrasjonstester) i gitpod, men testene skal kjøre lokalt og dette er noe vi anbefaler.


## Utgivelser

 - [Release 1](docs/release1)
 - [Release 2](docs/release2)
 - [Release 3](docs/release3)


## Brukerhistorier

Funksjonaliteten og brukergrensesnittet til applikasjonen er bygget utifra et sett med [brukerhistorier](docs/brukerhistorier/brukerhistorier.md).

## Kodeverktøy

I prosjektet bruker vi noen kodeanalyseverktøy for å sikre at kodekvaliteten er god. For å sjekke output fra disse verktøyene kan man kjøre:

> mvn verify

### Spotbugs

Et verktøy som analyserer koden og gir tilbakemelding på generell kodekvalitet. Den kan oppdage eventuelle bugs også. Vi har valgt å eksludere noen bugs. Begrunnelsen for det ligger i de modulene det er aktuelt for ([`ui`](gr2116-project/ui) og [`RESTservice`](gr2116-project/RESTservice)). Man kan se hva som blir eksludert i [`excludeFilter.xml`](gr2116-project/config/excludeFilter.xml)

### Checkstyle

Verktøyet analyserer koden og gir tilbakemelding på formatering av koden, samt javadoc tilbakemeldinger. Vi bruker google sine checks, men vi har gjort noen endringer som ignorerer noen varsler. Man kan se hva som blir sjekket i [`custom_google_checks.xml`](gr2116-project/config/custom_google_checks.xml).
