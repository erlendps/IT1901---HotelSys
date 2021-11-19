package gr2116.persistence.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import gr2116.core.HotelRoom;
import gr2116.core.Reservation;
import java.io.IOException;
import java.util.Iterator;

/**
 * Serializer for HotelRoom class.
 */
public class RoomSerializer extends JsonSerializer<HotelRoom> {

  /*
   * format: { "number": ... , "type": "...", "amenities": [ ... ], "price": ... ,
   * "reservations": [ ... ] }
   */

  @Override
  public void serialize(HotelRoom room, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    if (room == null) {
      throw new IllegalArgumentException("Room is null.");
    }
    gen.writeStartObject();
    gen.writeNumberField("number", room.getNumber());
    gen.writeStringField("type", room.getRoomType().name());
    gen.writeArrayFieldStart("amenities");
    for (String amenity : room.getAmenities()) {
      gen.writeString(amenity);
    }
    gen.writeEndArray();
    gen.writeNumberField("price", room.getPrice());
    gen.writeArrayFieldStart("reservations");
    Iterator<Reservation> it = room.getReservations();
    while (it.hasNext()) {
      gen.writeObject(it.next());
    }
    gen.writeEndArray();
    gen.writeEndObject();
  }
}
