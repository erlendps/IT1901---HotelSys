[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-908a85?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2116/gr2116/-/tree/main/)


# IT1901 Group project - HotelSys

Dette er et utviklingsprosjekt for gruppe 16 i faget IT1901.
Vi utvikler [HotelSys](gr2116-project) - en applikasjon for å booke hotellrom.

Kodingsprosjektet ligger i mappen [gr2116-project](gr2116-project). Her ligger de tre modulene som utgjør prosjektet. [_Core_](gr2116-project/core/src/main/java/gr2116/core) refererer til domenelogikk, [_persistence_](gr2116-project/persistence/src/main/java/gr2116/persistence) refererer til fillagringssystemet og [_ui_](gr2116-project/ui/src/main/java/gr2116/ui) refererer til det grafiske brukergrensesnittet i applikasjonen. Dokumentasjon for modulene ligger i [modules](docs/modules).


## Bygging og kjøring av prosjektet

Prosjektet kjøres og bygges av maven. Følgende kommandoer gjelder fra **gr2116-project**-mappa. Prosjektet må bygges før det kan kjøres eller testes.

For å bygge:
> mvn install

For å kjøre:
> mvn -pl ui javafx:run

For å teste:
> mvn test

Test coverage ligger i module-name/target/site/index.html. I GitPod kan denne vises ved hjelp av Live Server extension.


## Utgivelser

 - [Release 1](docs/release1)
 - [Release 2](docs/release2)


## Brukerhistorier

Funksjonaliteten og brukergrensesnittet til applikasjonen er bygget utifra et sett med [brukerhistorier](docs/brukerhistorier/brukerhistorier.md).
