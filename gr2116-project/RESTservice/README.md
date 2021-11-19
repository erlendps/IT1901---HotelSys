# RESTservice-modul

Denne modulen inneholder kode for REST-api og kode for REST-server.

## API

Vi har bygget et REST-API som støtter følgende operasjoner:
* `GET /hotel`: Hente ut hele hotellobjektet fra serveren
* `GET hotel/person/{brukernavn}`: Henter ut person {brukernavn} fra serveren
* `PUT hotel/person/{brukernavn}` med JSON for personobjektet: Legger til personen til hotellobjektet på serveren
* `DELETE hotel/person/{brukernavn}`: Sletter personen fra serveren
* `GET hotel/rooms/{romnummer}`: Henter ut rommet {romnummer} fra serveren
* `PUT /hotel/rooms/{romnummer}` med JSON for romobjektet: Legger til rommet til hotellobjektet på serveren
* `DELETE /hotel/rooms/{romnummer}`: Sletter rommet fra serveren

## Testdekning

Testdekningen for RESTservice-modulen er noe lavere enn for de andre modulene. Dette skyldes at funksjonaliteten testes i integrationtests. Vi anser det ikke som nyttig å teste samme funksjonalitet to ganger.
