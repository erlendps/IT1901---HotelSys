package gr2116.RESTservice.restapi;

import gr2116.core.HotelRoom;
import gr2116.core.Hotel;
import gr2116.core.Reservation;
import gr2116.persistence.HotelPersistence;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class RoomResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(RoomResource.class);

    private final HotelRoom room;
    private final Hotel hotel;

    @Context
    private HotelPersistence hotelPersistence;


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
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void addReservationRoom(Reservation reservation) {
        LOG.debug("addReservationRoom({})", reservation);
        room.addReservation(reservation);
        autoSaveHotel();
    }

}
