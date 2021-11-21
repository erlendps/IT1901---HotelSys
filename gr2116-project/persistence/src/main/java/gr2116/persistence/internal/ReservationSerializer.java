package gr2116.persistence.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import gr2116.core.Reservation;
import java.io.IOException;

/**
 * Serializer for Reservation class.
 */
public class ReservationSerializer extends JsonSerializer<Reservation> {

  /*
   * Serializer for Reservation object with the following
   * format: { "room": ... , "startDate": "...", "endDate": "...", "id": ... }
   */
  @Override
  public void serialize(Reservation reservation, JsonGenerator gen,
      SerializerProvider serializers) throws IOException {
    if (reservation == null) {
      throw new IllegalArgumentException("Reservation is null.");
    }
    gen.writeStartObject();
    gen.writeNumberField("room", reservation.getRoomNumber());
    gen.writeStringField("startDate", reservation.getStartDate().toString());
    gen.writeStringField("endDate", reservation.getEndDate().toString());
    gen.writeStringField("id", reservation.getId());
    gen.writeEndObject();
  }
}
