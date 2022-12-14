package gr2116.persistence.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.Person;
import java.io.IOException;

/**
 * Serializes Hotel to json.
 */
public class HotelSerializer extends JsonSerializer<Hotel> {

  /*
   * Serializes a Hotel object to the following
   * format: { "rooms": [ ... ], "persons": [ ... ] }
   */
  @Override
  public void serialize(Hotel hotel, JsonGenerator gen,
      SerializerProvider serializers) throws IOException {
    if (hotel == null) {
      throw new IllegalArgumentException("Hotel is null.");
    }
    gen.writeStartObject();
    gen.writeArrayFieldStart("rooms");
    for (HotelRoom room : hotel.getRooms()) {
      gen.writeObject(room);
    }
    gen.writeEndArray();
    gen.writeArrayFieldStart("persons");
    for (Person person : hotel.getPersons()) {
      gen.writeObject(person);
    }
    gen.writeEndArray();
    gen.writeEndObject();
  }

}
