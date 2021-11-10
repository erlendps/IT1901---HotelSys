package gr2116.RESTservice.restapi;

import gr2116.core.Person;
import gr2116.core.Hotel;
import gr2116.persistence.HotelPersistence;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

import java.util.Collection;


public class PersonResource {
    private static final Logger LOG = LoggerFactory.getLogger(RoomResource.class);

    String usename;
    Person person;
    Hotel hotel;

     @Context
     private HotelPersistence hotelPersistence;

    
    public PersonResource(String username, Person person, Hotel hotel) {
      this.person = person;
      this.usename = username;
      this.hotel = hotel;
    }

    private void autoSaveHotel() {
    if (hotelPersistence != null) {
      try {
        hotelPersistence.saveHotel(hotel, "data");
      } catch (IllegalStateException | IOException e) {
        System.err.println("Couldn't auto-save Hotel: " + e);
      }
    }
    }

    @PUT
    //@Consumes(MediaType.APPLICATION_JSON)
    public void addPerson() {
        hotel.addPerson(person);
        autoSaveHotel();
    }
}