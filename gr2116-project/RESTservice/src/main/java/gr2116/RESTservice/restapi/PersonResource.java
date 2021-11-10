package gr2116.RESTservice.restapi;

import gr2116.core.Person;
import gr2116.core.Hotel;
import gr2116.persistence.HotelPersistence;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;


public class PersonResource {
  private static final Logger LOG = LoggerFactory.getLogger(RoomResource.class);

  private final String usename;
  private final Person person;
  private final Hotel hotel;

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
        hotelPersistence.saveHotel(this.hotel);
      } catch (IllegalStateException | IOException e) {
        System.err.println("Couldn't auto-save Hotel: " + e);
      }
      }
    }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public void addPerson(String name) {
    LOG.debug("addPerson({})", name);
    Person person = new Person(name);
    person.setUsername(usename);
    hotel.addPerson(person);
    autoSaveHotel();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public void addBalance(Double balance) {
    LOG.debug("addBalance({})", balance);
    person.addBalance(balance);
    autoSaveHotel();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public void addReservation(Reservation reservation) {
    LOG.debug("addReservation({})", reservation);
    person.addReservation(reservation);
    autoSaveHotel();
  }
}