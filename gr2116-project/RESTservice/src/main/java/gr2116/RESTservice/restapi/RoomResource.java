package gr2116.RESTservice.restapi;

import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.persistence.HotelPersistence;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoomResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(RoomResource.class);

    private final HotelRoom room;
    private final Hotel hotel;

    @Context
    private HotelPersistence hotelPersistence;

    public void setHotelPersistence(HotelPersistence hotelPersistence) {
        this.hotelPersistence = hotelPersistence;
    }

    public RoomResource(HotelRoom room, Hotel hotel) {
        this.room = room;
        this.hotel = hotel;
    }

    private void autoSaveHotel() {
        if (hotelPersistence != null) {
            try {
                hotelPersistence.saveHotel(hotel);
                } catch (IllegalStateException | IOException e) {
                    System.err.println("Couldn't auto-save Hotel: " + e);
                }
            }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HotelRoom getHotelRoom() {
      LOG.debug("getRoom({})", room.getNumber());
      return this.room;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void addHotelRoom(HotelRoom room) {
        LOG.debug("addHotelRoom({})", room);
        hotel.addRoom(room);
        System.out.println("bruuuuuh");
        autoSaveHotel();
    }

}
