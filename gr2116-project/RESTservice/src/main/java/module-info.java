module gr2116.RESTservice {
<<<<<<< HEAD
    
=======
    requires jakarta.ws.rs;

    requires jersey.common;
    requires jersey.server;
    requires jersey.media.json.jackson;

    requires org.glassfish.hk2.api;
    requires org.slf4j;

    requires gr2116.core;
    requires gr2116.persistence;

    opens gr2116.restapi to jersey.server;

>>>>>>> b690c6f (#97 fixed module-info to work with dependencies, commented spotbugs since it failed when no files are present)
}
