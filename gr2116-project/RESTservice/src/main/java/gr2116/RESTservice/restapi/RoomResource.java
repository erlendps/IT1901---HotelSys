package gr2116.RESTservice.restapi;

import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.persistence.HotelPersistence;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used for all request to the API with a specified roomNumber.
 */
public class RoomResource {
    
  private static final Logger LOG = LoggerFactory.getLogger(RoomResource.class);

  private final HotelRoom room;
  private final Hotel hotel;

  @Context
  private HotelPersistence hotelPersistence;

  /**
   * Sets the HotelPersistence for this resource.
   *
   * @param hotelPersistence the HotelPersistance to be set
   */
  public void setHotelPersistence(HotelPersistence hotelPersistence) {
    this.hotelPersistence = hotelPersistence;
  }

  /**
   * Initializes this RoomResource with the given arguments.
   *
   * @param room the room, could be either null or an actual HotelRoom object
   * @param hotel the hotel
   */
  public RoomResource(HotelRoom room, Hotel hotel) {
    this.room = room;
    this.hotel = hotel;
  }

  /**
   * Helper method that saves the hotel.
   */
  private void autoSaveHotel() {
    if (hotelPersistence != null) {
      try {
        hotelPersistence.saveHotel(this.hotel);
        System.out.println("Saved!");
      } catch (IllegalStateException | IOException e) {
        System.err.println("Couldn't auto-save Hotel: " + e);
      }
    }
  }
  
  /**
   * Handles the GET-requset for the room with this.room's roomNumber. Returns
   * the room.
   *
   * @return the room (this)
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public HotelRoom getHotelRoom() {
    LOG.debug("getRoom({})", room.getNumber());
    return this.room;
  }

  /**
   * Adds (either replaces or adds a new instance) of the given room.
   *
   * @param room the room to be added or replaced
   *
   * @return false if oldPerson was overwritten, true if its a new instance
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public boolean addHotelRoom(HotelRoom room) {
    LOG.debug("addHotelRoom({})", room);
    HotelRoom oldRoom = this.hotel.addRoom(room);
    autoSaveHotel();
    return oldRoom == null;
  }

  /**
   * Deletes the requested room.
   *
   * @return true if the room was deleted
   */
  @DELETE
  public boolean removeRoom() {
    if (room == null) {
      throw new IllegalStateException("This room does not exist.");
    }
    hotel.removeRoom(room);
    autoSaveHotel();
    return true;
  }
}
