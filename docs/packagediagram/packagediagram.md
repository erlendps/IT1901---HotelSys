```plantuml
@startuml component

    component core {
        package gr2116.core #FFFFCC
    }
    component persistence {
        package gr2116.persistence #FFFFCC
    }
    component RESTservice {
        package gr2116.restapi #FFFFCC
        package gr2116.restserver #FFFFCC
    }
    component ui{
        package gr2116.ui #FFFFCC
    }
    component webapp{
        package gr2116.webapp #FFFFCC
    }
    component integrationtests{
        package gr2116.integrationtests #FFFFCC
    }
    component jersey{
    }
    component jackson{
    }
    component javafx{
    }
    component fxml{
    }
    component jakarta{
    }

    gr2116.webapp .-> gr2116.restserver
    gr2116.webapp .-> gr2116.restapi
    gr2116.webapp .-> jersey

    gr2116.integrationtests .>gr2116.core
    gr2116.integrationtests .>gr2116.ui
    gr2116.integrationtests .>gr2116.restserver
    gr2116.integrationtests .->gr2116.restapi


    gr2116.restapi .-> gr2116.core
    gr2116.restapi .-> gr2116.persistence
    gr2116.restapi .-> jakarta

    gr2116.restserver .-> gr2116.core
    gr2116.restserver .-> gr2116.persistence
    gr2116.restserver .-> jakarta
    

    gr2116.ui .-> gr2116.core
    gr2116.ui .-> gr2116.persistence
    gr2116.ui .-> javafx
    gr2116.ui .-> fxml

    gr2116.persistence .-> gr2116.core
    gr2116.persistence .> jackson

@enduml

