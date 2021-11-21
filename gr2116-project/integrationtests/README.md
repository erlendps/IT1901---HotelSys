# Integrationstests-modul

Denne modulen inneholder i likhet med [`webapp`](../webapp) en [`web.xml`](src/main/webapp/WEB-INF/web.xml)-fil som gjør det mulig å starte en webserver. Denne bruker istedenfor [`HotelITConfig.java`](../RESTservice/src/main/java/gr2116/RESTservice/restserver/HotelITConfig.java) som applikasjon, og serverer dermed et REST-API som bruker:

> ~/HotelSys/testITHotel.json

I modulen ligger [`HotelIT.java`](src/test/java/gr2116/ui/HotelIT.java) som inneholder integrasjontestene vi har skrevet. Dette er på en måte litt feil å si, ettersom vi for eksempel i [`persistence`](../persistence) også har en form for integrasjonstesting. Uansett så fokuserer denne modulen mer på interaksjon med REST-APIet.

## Oppbygging

Testene ligger som sagt i [`HotelIT.java`](src/test/java/gr2116/ui/HotelIT.java), og er på en måte en utvidelse av UI-testene, bare at den nå kjører en webserver som serverer REST-APIet og bruker dette. Mens vi i UI-testene tester [`DirectHotelAccess.java`](../ui/src/main/java/gr2116/ui/access/DirectHotelAccess.java), tester vi i integrasjontestene [`RemoteHotelAccess.java`](../ui/src/main/java/gr2116/ui/access/RemoteHotelAccess.java). Dette er grunnen til at test-coverage for denne klassen er lav, ettersom det testes her.

For at vi ikke skal endre på `dataHotel.json` når integrasjontestene kjører bruker vi altså [`HotelITConfig.java`](../RESTservice/src/main/java/gr2116/RESTservice/restserver/HotelITConfig.java) som applikasjon for webserveren og som serverer `testITHotel.json`. For at vi skal ha et `Hotel`-objektet å teste med, har vi i [`pom.xml`](pom.xml) gjort slik at under _pre-integration_-fasen så vil main-metoden i [`SetUpFile.java`](src/main/java/gr2116/integrationtests/SetUpFile.java) kjøres. Denne lager et `Hotel`-objekt med `HotelRoom`- og `Person`-objekter, og lagrer til:
> ~/HotelSys/testITHotel.json

[`HotelITConfig.java`](../RESTservice/src/main/java/gr2116/RESTservice/restserver/HotelITConfig.java) serverer deretter filen som ble generert. Til slutt tar [`HotelIT.java`](src/test/java/gr2116/ui/HotelIT.java) utgangspunkt i dataen som blir generert her når det testes.

Etter alle testene er kjørt, slettes filen som ble laget av `SetUpFile.java`.
