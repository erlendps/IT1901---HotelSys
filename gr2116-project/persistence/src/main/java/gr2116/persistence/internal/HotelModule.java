package gr2116.persistence.internal;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.Person;
import gr2116.core.Reservation;

/**
 * Custom Jackson module that extends SimpleModule.
 */
public class HotelModule extends SimpleModule {
  
  private static final String NAME = "HotelModule";

  /**
   * Constructor of HotelModule that extends the Jackson SimpleModule.
   */
  public HotelModule() {
    super(NAME, Version.unknownVersion());

    // Hotel
    addSerializer(Hotel.class, new HotelSerializer());
    addDeserializer(Hotel.class, new HotelDeserializer());
    
    // Person
    addSerializer(Person.class, new PersonSerializer());
    addDeserializer(Person.class, new PersonDeserializer());

    // Reservation
    addSerializer(Reservation.class, new ReservationSerializer());
    addDeserializer(Reservation.class, new ReservationDeserializer());

    // HotelRoom
    addSerializer(HotelRoom.class, new RoomSerializer());
    addDeserializer(HotelRoom.class, new RoomDeserializer());
  }
}
