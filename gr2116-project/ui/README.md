# UI-modul

Denne modulen inneholder kode for det grafiske brukergrensesnittet i applikasjonen.

## Oppbygging
UI består av 9 pakker.
* [`access`](src/main/java/gr2116/ui/access)
* [`app`](src/main/java/gr2116/ui/app)
* [`controller`](src/main/java/gr2116/ui/controller)
* [`front`](src/main/java/gr2116/ui/front)
* [`main`](src/main/java/gr2116/ui/main)
* [`message`](src/main/java/gr2116/ui/message)
* [`money`](src/main/java/gr2116/ui/money)
* [`remoteerror`](src/main/java/gr2116/ui/remoteerror)
* [`utils`](src/main/java/gr2116/ui/utils)

Årsaken til at denne modulen er mer oppdelt enn andre, er at vi åpner noen av pakkene til `javafx.graphics` og `javafx.fxml` i `module-info.java`. `app` og `controller` inneholder henholdsvis de overordnede `App`, `RemoteApp` og `AppController` klassene. `front` inneholder kontrollere for applikasjonens forside (for å registrere seg og logge inn). `main` inneholder kontrollere for hovedsiden (der man reserverer hotellrom), samt `HotelRoomListItem`, en komponent for å vise informasjonen til et hotellrom med knapp for å reservere det. `message` inneholder `MessageListener`, et grensesnitt for å høre på meldinger, og `Message`, en enum som representerer ulike meldinger. Disse brukes til å sende informasjon oppover i hierarkiet, oftest til `AppController`, slik at underordnede klasser ikke trenger å ha tilgang til `hotelAccess` og `person`, for å sikre god innkapsling. `money` inneholder kontrolleren for siden der man setter inn penger. `remoteerror` inneholder kontrolleren for siden som vises dersom man ikke får kontakt med serveren. `utils` inneholder `FXMLUtils`, en klasse for å laste inn fxml til komponenter. Tidligere brukte vi denne i større grad, men for å sikre god model-view-controller-struktur, brukes den nå kun til å laste inn fxml for `HotelRoomListItem`.

## Testdekning

UI-modulen mangler testdekning i jacoco for access-pakken, app-pakken og remoteerror-pakken. Dette skyldes at klassene i disse pakkene testes i integrationtests. Vi anser det ikke som nyttig å teste disse to ganger.

## Ekskludering av spotbugs-warnings
Vi fikset på så mange spotbugs-warnings som mulig, men det var noen warnings som vi mener vi ikke kunne gjort noe med. Hvis man ser i [`excludeFilter.xml`](../config/excludeFilter.xml) kan man se at vi har eksludert bugs av typen EI_EXPOSE_REP2, spesifikt i `ui.main`-pakken. Vi hadde 3 warnings her, knyttet til at `AppController` deler `hotelAccess` og `person` med `MainPageController`, og `MainPageController` deler `person` videre med `UserPanelController`. Vi kunne ha implementert dette ved hjelp av meldinger, slik `MoneyPageController` og `FrontPageController` virker, men vurderte den gjeldende løsningen som mer hensiktsmessig. `AppController`-klassen hadde blitt rotete dersom den skulle ha logikk for alle deler av applikasjonen.

## Klassediagram

Klassediagram for UI-modul er delt i to, slik at det blir mer oversiktelig.
![klassediagram ui](../../docs/images/ui1.PNG "Klassediagram 1 for ui")
![klassediagram ui](../../docs/images/ui2.PNG "Klassediagram 2 for ui2")
