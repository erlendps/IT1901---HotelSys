package gr2116.RESTservice.restapi;

import gr2116.core.Hotel;
import gr2116.core.Person;
import gr2116.persistence.HotelPersistence;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
   * Sets the HotelPersistence for this resource.
   *
   * @param hotelPersistence
   */
  public void setHotelPersistence(HotelPersistence hotelPersistence) {
    this.hotelPersistence = hotelPersistence;
  }

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
   * Helper method that saves the hotel.
   */
  private void autoSaveHotel() {
    if (hotelPersistence != null) {
      try {
        hotelPersistence.saveHotel(this.hotel);
      } catch (IllegalStateException | IOException e) {
        System.err.println("Couldn't auto-save Hotel: " + e);
      }
    }
  }

  /**
  * Gets the corresponding person.
  *
  * @return the corresponding person
  */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Person getPerson(){
    LOG.debug("getPerson({})", username);
    return this.person;
  }
  
  /**
   * Adds (either replaces or adds a new instance) of the given person.
   *
   * @param person the person to be added/shall replace
   *
   * @return false if the oldPerson was replaced, true if its a new Person
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public boolean addPerson(Person person) {
    LOG.debug("addPerson({})", person);
    Person oldPerson = hotel.addPerson(person);
    autoSaveHotel();
    return oldPerson == null;
  }

  /**
   * Deletes the requested person.
   *
   * @return true if the person was deleted
   */
  @DELETE
  public boolean removePerson() {
    LOG.debug("removePerson({})", person);
    if (person == null) {
      throw new IllegalArgumentException("Person is null, cannot delete.");
    }
    hotel.removePerson(person);
    autoSaveHotel();
    return true;
  }
}