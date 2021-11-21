# Release 3

Issues for innlevering 3 kan finnes under milestone [Release 3](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2116/gr2116/-/milestones/3).

## Arkitektur 

Klassediagram for alle modulene er generert med PlantUML, og kan ses i [modules](../modules/README.md). I tillegg ligger alle diagrammene i [images](../images).

## Arbeidsvaner

Vi jobber mellom åtte og tolv timer i uken sammen på skolen, og fordeler resterende oppgaver mellom oss. Dersom det er noen problemer med å gjennomføre oppgaver individuelt tar vi kontakt med hverandre digitalt. Når vi sitter fysisk på skolen bruker vi parprogrammering ved behov. Koden skrevet av enkeltpersoner blir gått gjennom av andre gruppemedlemmer, slik at alle vet hvordan funksjoner blir implementert. I tillegg sørger dette for at feil blir oppdaget. Vi opplever at vi har god effektivitet og at den tekniske kvaliteten på prosjektet er høy.

## Kodekvalitet

Det er tester for alle de tre lagene. Jacoco brukes til å sjekke testdekningsgrad, Maven-tillegget CheckStyle benyttes for å forsikre at koden er leselig og SpotBugs benyttes til å finne bugs i koden. Mockito brukes i testmodulene, slik at klassene kan testes uavhengig av hverandre. Det er satt opp en git-'hook', som sørger for at alle commits har med issue-nummer. Denne ble lagt til sent i prosjektet, og fungerer kun når man arbeider lokalt (ikke i nettleser).

## Skjermbilder

Startsiden:

<img src="../images/start_page.png"
     width="800"
     alt="Skjermbilde av hovedsiden."
     style="float: left; margin-right: 10px;" />

Siden for å logge inn:

<img src="../images/start_page_login.png"
     width="800"
     alt="Skjermbilde av hovedsiden."
     style="float: left; margin-right: 10px;" />

Siden for å registrere seg:

<img src="../images/start_page_register.png"
     width="800"
     alt="Skjermbilde av hovedsiden."
     style="float: left; margin-right: 10px;" />

Så, et bilde av hvordan hovedsiden ser ut.

<img src="../images/main_page_release_3.png"
     width="800"
     alt="Skjermbilde av hovedsiden."
     style="float: left; margin-right: 10px;" />

Skjermbilde av siden for å legge inn penger. Kortnummer må være gyldig for at penger skal kunne settes inn, se eget avsnitt.

<img src="../images/deposit_page_release_2.png"
     width="800"
     alt="Skjermbilde av pengesiden."
     style="float: left; margin-right: 10px;" />


## Realisert

Appen bygger på konseptene fra den første og den andre innleveringen. Se [release1](../release1/README.md) og [release2](../release2/README.md). Nytt i den tredje utgivelsen er at appen er rigget opp med et REST API. Når man reserverer rom eller lager brukerkonto blir dette nå lagret på en server. Dermed kan man i teorien benytte appen hvor som helst, enn om det er hjemme, fra jobb eller på hotellet. Istedenfor e-post registrerer man seg nå med et brukernavn, og man lager et passord som man senere må bruke for å kunne logge inn. Brukernavnet må oppfylle krav, som nevnt under. Passordet blir hashet; det blir aldri lagret på serveren i klartekst. Vi har også realisert funksjonalitet som tillater å sortere hotellrom etter pris, romnummer og antallet fasiliteter på et rom.

## Legge til rom?

Vi har dessverre ikke rukket å lage et administratorpanel for applikasjonen vår, og derfor er det ikke mulig å endre eller legge til rom i hotellet. Man kan redigere JSON-filene manuelt, da disse ligger i 'user'-mappen i hvert respektive operativsystem, mer generelt:

> ~/HotelSys/dataHotel.json

Slik kan potensielle kunder (hotell) legge til hotellrommene sine manuelt. Dersom vi hadde hatt mer tid til å utvikle applikasjonen, ville vi lagt til et administratorpanel. For at det skal bli enklere å teste applikasjonen, lager den nå 30 tilfeldige rom dersom ingen rom er funnet i 'user'-mappen.
Syntaksen for å legge til rom i JSON-filen manuelt er som følger:
```
{
    "number" : 101,
    "type" : "Single",
    "amenities" : [ "KitchenFacilities", "Dryer", "Fridge" ],
    "price" : 250.0,
    "reservations" : [ ]
}
```

Merk at reservations skal være en tom JSON-array.

En annen mulighet for å legge til rom/personer eller slette rom/personer er å bruke [cURL](https://curl.se/). Dette forutsetter at man starter opp webserveren (se dokumentasjon på det [her](/README.md)) og har en terminal eller kommandolinje. Under følger noen eksempler på hvordan man kan legge til og fjerne personer:

For å legge til et rom med romnummer 821, type trippel rom, fasiliteter TV og dusj og pris 600:

```shell
curl -H "Content-Type: application/json" -d '{"number":821, "type":"Triple", "amenities":["Television", "Shower"], "price":600.0,"reservations":[]}' -X PUT http://localhost:8080/rest/hotel/rooms/821
```

For å legge til en person med navn brukernavn johnd, fornavn John, etternavn Doe, et hashet passord (se [PasswordUtil](/gr2116-project/core/src/main/java/gr2116/core/PasswordUtil.java), det hashede passordet tilsvarer: bigboy), balanse 400.0 og ingen reservasjoner:

```shell
curl -H "Content-Type: application/json" -d '{"username":"johnd", "firstName":"John", "lastName":"Doe", "password":"cd/wyrJsGBeH9Ll94NOeRQ==", "balance":400.0, "reservations": []}' -X PUT http://localhost:8080/rest/hotel/person/johnd
```

For å slette personen med brukernavn johnd:

```shell
curl -X DELETE http://localhost:8080/rest/hotel/person/johnd
```

For å slette rommet med romnummber 821:

```shell
curl -X DELETE http://localhost:8080/rest/hotel/rooms/821
```

For å se en komplett liste av mulige romtyper og fasiliteter, se i core sin [README](/gr2116-project/core/README.md).

## Brukernavn

Brukernavn må utelukkende bestå av bokstaver, store eller små.

## Passord

Passord må ha 6 eller flere tegn.

## Fornavn og etternavn

Alle navn er gyldige. Grunnen til at vi har gjort det slik er at det finnes [svært mange ulike navnekonvensjoner](https://www.kalzumeus.com/2010/06/17/falsehoods-programmers-believe-about-names/).

## Kortnummer

På siden for å legge inn penger på kontoen sin, må man skrive inn et gyldig kortnummer. Et gyldig kortnummer oppfyller følgende krav:
* Kortnummeret er 16 siffer langt.
* De første sifferne betegner utsteder, og må være enten 4 (Visa) eller 51-55 / 2221-2720 (Mastercard).
* Det siste sifferet må tilfredstille [Luhns' algoritme](https://en.wikipedia.org/wiki/Luhn_algorithm).


Under følger noen fungerende (men falske) kortnummer for testing:
* 4106 5778 3149 6288
* 5172 3119 4781 6718

## Diagrammer

Pakkediagram, klassediagrammer og sekvensdiagrammer ligger alle i mappen [images](../images). I tillegg kan man finne mer informasjon om hver enkelt diagram i egne mapper. 

### Pakkediagram

En markdown-filer med kode for å
generere pakkesiagram med PlantUML kan finnes i mappen [packagediagram](../packagediagram). 
For mer informasjon se [README filen](../packagediagram/README.md).


### Klassediagam

Markdown-filer med kode for å
generere klassediagrammer for alle modulene kan finnes i mappen [modules](../modules). 
For mer informasjon se [README filen](../modules/README.md).

### Sekvensdiagram

To Markdown-filer som generer to sekvensdiagram for når en bruker trykker på knappen "make reservation", kan finnes i mappen [modules](../sequenceDiagrams).
For mer informasjon se [README filen](../sequenceDiagrams/README.md).


