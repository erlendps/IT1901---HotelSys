Issuses på gitlab for innlevering 1 kan finnes under milestone MVP 1/3.

Designet til først innleverinig legges på bakgrunn av brukerhistorien:

## Brukerhistorie
### Booking av hotelrom 

> Som privatperson ønsker jeg å finne hotellrom av romtypen jeg vil ha, slik at jeg får et godt opphold. 
> _- Bruker_

Brukeren har behov for å kunne velge hvilken romtype de vil at hotellrommet skal ha, og deretter kunne se de ledige rommene med denne romtypen. Det bør kunne velges hvilken periode brukeren vil reservere rom for, og se tidligere reservasjoner brukeren har gjort. Dermed er det lett å huske hvilke rom brukeren har reservert. 

## ER-diagram

Et Entity Relationship Diagram vises under, det angit en konseptsuelle sammenhenger mellom klassene. 
Et hotell kan ha mange rom. Rommet kan ha mange fasiliteter, men kun en reservasjonskalender. Reservasjonskalenderen kan ha mange reservasjoner. En reservasjon er koblet til en person, mens en persjon kan ha mange reservasjoner. Personen søker i hotell for å finne ledige rom med riktige fasiliteter. 

<img src="ER-diagram___Konseptuell_modell_.png"
     alt="ER-diagram"
     style="float: left; margin-right: 10px; width: 100%" />

## Designskisse

Under vises en skisse av hvordan appen skal se ut for brukeren. Brukeren kan velge fasiliteter og romtype, og får dermed opp rom som passer denne beskrivelsen. For første innleveringen vil appen være enkel.

<img src="skisse_app.png"
     alt="Skisse av app"
     style="float: left; margin-right: 10px; width: 100%" />

## Realisert

Når appen åpnes kommer det opp et innlogingsvindu. Her skriver man inn e-postadresse. E-postadressen må være gyldid for at man skal komme seg videre. Dersom e-postadressen er brukt tidligere kommer man direkte videre, eller så må man skrive inn navn også. Når man trykker "Sign in"  ser man på venstre side navn, E-postadresse, balanse og reservasjoner. Midten er tom, fram til du velger dato for inn og utsjekking, samt hvilken romtype du vil ha. Da vil rom som er ledig i denne perioden og som har denne romtypen dukke opp på skjermen. Om bruker har gjort enn feil, vil det komme en forklarende feilmelding, slik at bruker kan rette opp feilen. I tillegg er det implenter fillagring, slik at reservasjoner brukeren gjør lagres i en fil. Denne filen kan lagres, og leses ved senere bruk av appen. Dersom man logger seg inn med samme mail, vil man kunne se reservasjoner som  er gjort tidligere.

