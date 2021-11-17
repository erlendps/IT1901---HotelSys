package gr2116.RESTservice.restapi;

import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.Person;
import gr2116.persistence.HotelPersistence;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The top-level rest service for Hotel.
 */
@Path(HotelService.HOTEL_MODEL_SERVICE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class HotelService {
  public static final String HOTEL_MODEL_SERVICE_PATH = "hotel";
  private static final Logger LOG = LoggerFactory.getLogger(HotelService.class);

  @Context
  private Hotel hotel;

  @Context
  private HotelPersistence hotelPersistence;

  /**
   * The root resource, i.e. /hotel
   *
   * @return the Hotel
   */
  @GET
  public Hotel getHotel() {
    LOG.debug("Hotel: " + hotel);
    return hotel;
  }
  
  /**
   * Returns a PersonResource for the given person. If the given person does not exist in the
   * hotel, it will create a PersonResource where the person is null. It checks if the person
   * already exists, and if it does, the PersonResource will be iniated with that person object.
   * The PersonResource is responsible for handling request from baseURI/person/{username}
   *
   * @param username the Persons username
   *
   * @return PersonResource iniated with null if the person does not exist, or initiated with 
   *         the person that matches the username if it exists.
   */
  @Path("/person/{username}")
  public PersonResource getPersonResource(@PathParam("username") String username) {
    Collection<Person> matches = hotel.getPersons(p -> p.getUsername().equals(username));
    Person person;
    if (matches.size() > 1) {
      throw new IllegalStateException("Multiple matches for person " + username);
    } else if (matches.size() == 0) {
      person = null;
    } else {
      person = matches.iterator().next();
    }

    LOG.debug("Sub-resource person for person with username: " + username + ", " + person);
    PersonResource personResource = new PersonResource(username, person, hotel);
    personResource.setHotelPersistence(hotelPersistence);
    return personResource;
  }

  /**
   * Returns a RoomResource for the given room. If the given room does not exist in the
   * hotel, it will create a RoomResource where the room is null. It checks if the room
   * already exists, and if it does, the RoomResource will be iniated with that HotelRoom object.
   * The RoomResource is responsible for handling request from baseURI/rooms/{roomNumber}.
   *
   * @param roomNumber the roomNumber of the room
   *
   * @return RoomResource iniated with null if the room does not exist, or initiated with 
   *         the room that matches the roomNumber if it exists.
   */
  @Path("/rooms/{roomNumber}")
  public RoomResource getRoomResource(@PathParam("roomNumber") String roomNumber) {
    HotelRoom room;
    Integer number = Integer.parseInt(roomNumber);
    Collection<HotelRoom> matches = hotel.getRooms(p -> p.getNumber() == number);
    
    if (matches.size() > 1) {
      throw new IllegalStateException("Multiple matches for room number" + roomNumber);
    } else if (matches.size() == 0) {
      room = null;
    } else {
      room = matches.iterator().next();
    }

    LOG.debug("Sub-resource room for room number " + number + ": " + room);

    RoomResource roomResource = new RoomResource(room, hotel);
    roomResource.setHotelPersistence(hotelPersistence);
    return roomResource;
  }
}

