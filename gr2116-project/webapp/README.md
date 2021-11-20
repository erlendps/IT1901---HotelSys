# webapp

Denne mappen inneholder filer for kjøring av webapp, som brukes til å servere REST-APIet. Strukturen er veldig enkel, i all hovedsak inneholder denne modulen kun [`web.xml`](src/main/webapp/WEB-INF/web.xml), som gjør det mulig å starte en webserver og servletcontainer, som i sin tur gjør det mulig å sende HTTP-forespørsler til endepunktet hvor [`RESTservice`](../RESTservice)-modulen tar imot og håndterer forespørslene.

Serveren bruker [`HotelConfig.java`](../RESTservice/src/main/java/gr2116/RESTservice/restserver/HotelConfig.java) for å ta imot body-en til forespørslene og gjøre om til objekter vi kan bruke. Serveren vil prøve å mappe alt som blir sendt til **baseURL/rest**.

For å starte serveren lokalt, kan man fra [`gr2116-project`](/gr2116-project) skrive inn kommandoen:

```shell
mvn -pl webapp jetty:run
```

Slik vi har konfigurert det, vil serveren nå lytte på port 8080 på din lokale maskin, det vil si at URLen som serveren vil behandle forespørsler for blir:

> <http://localhost:8080/rest/>*

Dersom man skulle ønske å hoste dette på en ekstern server, ville man ha måttet endret litt i [ui](../ui)-modulen, altså endre forekomster av *<http://localhost:8080>* til IP-addressen/domene-navnet til serveren.
