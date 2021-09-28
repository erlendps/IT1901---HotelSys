# IT1901 Group project

This is the group project in IT1901 for gr2116
Dette prosjektet er et utviklingsprosjekt for grupper 16 i faget IT1901.

Kodingsprosjektet ligger i mapppen [gr2116-project](gr2116-project). Her ligger de tre modulene som utgjør prosjektet. [_Core_](gr2116-project/core/src/main/java/gr2116/core) refererer til domenelogikk, [_persistence_](gr2116-project/persistence/src/main/java/gr2116/persistence) refererer til fillagringssystemet og [_ui_](gr2116-project/ui/src/main/java/gr2116/ui) refererer til det grafiske brukergrensesnittet i applikasjonen.

Prosjektet kjøres og bygges av maven.

For å kjøre (fra rotprosjekt **gr2116-project**-mappa):
> mvn install \
> mvn -pl ui javafx:run

For å teste:
> mvn test

Test coverage ligger i module-name/target/site/index.html. I GitPod kan denne vises ved hjelp av Live Server extension.
