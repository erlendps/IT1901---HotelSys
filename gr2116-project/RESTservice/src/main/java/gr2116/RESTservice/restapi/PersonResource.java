package gr2116.RESTservice.restapi;

import gr2116.core.Person;
import gr2116.core.Hotel;
import gr2116.core.Reservation;
import gr2116.persistence.HotelPersistence;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;


/**
 * Used for all requests referring to a Person by name.
 */

public class PersonResource {
  private static final Logger LOG = LoggerFactory.getLogger(RoomResource.class);

  private final String username;
  private final Person person;
  private final Hotel hotel;

  @Context
  private HotelPersistence hotelPersistence;

  /**
  * Initializes PersonResource. Each method wil check and use what is needed.
  *
  * @param username the name of the person, needed to add a new person
  * @param person the person, needed for most request
  * @param hotel the hotel, needed for put
  */
  public PersonResource(String username, Person person, Hotel hotel) {
    this.person = person;
    this.username = username;
    this.hotel = hotel;
  }


  /**
  * Gets the corresponding person
  *
  * @return the corresponding person
  */
  @GET
  public Person getPerson(){
    LOG.debug("getPerson({})",username);
    return this.person;
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
  public boolean addPerson(Person person) {
    LOG.debug("addPerson({})", person);
    Person oldPerson = hotel.addPerson(person);
    autoSaveHotel();
    return oldPerson == null;
  }
  /*
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
  } */
}