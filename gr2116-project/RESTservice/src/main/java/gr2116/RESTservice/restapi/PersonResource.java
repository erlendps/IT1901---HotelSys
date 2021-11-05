package gr2116.RESTservice.restapi;

import gr2116.core.Person;
import gr2116.persistence.HotelPersistence;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PersonResource {
    private static final Logger LOG = LoggerFactory.getLogger(RoomResource.class);

    private final Person person;
    
    public PersonResource(String username) {
        this.person = new Person(username);
    }
}
