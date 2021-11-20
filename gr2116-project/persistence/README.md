# Persistence-modul

Denne modulen inneholder kode for fil-lagring og lasting, og brukes til serialisering, romgenerering og til å lagre/laste data lokalt og på serveren.
Modulen inneholder klassene [`HotelPersistence`](src/main/java/gr2116/persistence/HotelPersistence.java), for fil-lagring og lasting, og [`RoomGenerator`](src/main/java/gr216/persistence/RoomGenerator.java), for genering av rom.
I tillegg ligger der kode for serialisering og deserialisering i mappen [`internal`](src/main/java/gr2116/persistence/internal).

## Klassediagram

Under følger klassediagrammet for `persistence`-modulen.

![klassediagram persistence](../../docs/images/persistence.PNG "Klassediagram persistence")
