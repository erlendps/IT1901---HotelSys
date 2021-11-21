package gr2116.persistence.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import gr2116.core.Person;
import gr2116.core.Reservation;
import java.io.IOException;

/**
 * Serializer for Person class.
 */
public class PersonSerializer extends JsonSerializer<Person> {

  /*
   * Serializes a Person object with the following
   * format: { "name": "...", "username": "...", "balance": ... , "reservations": [
   * ... ] }
   */
  
  @Override
  public void serialize(Person person, JsonGenerator gen,
        SerializerProvider serializer) throws IOException {
    if (person == null) {
      throw new IllegalArgumentException("Person is null.");
    }
    gen.writeStartObject();
    gen.writeStringField("username", person.getUsername());
    if (person.getFirstName() != null) {
      gen.writeStringField("firstName", person.getFirstName());
    }
    if (person.getLastName() != null) {
      gen.writeStringField("lastName", person.getLastName());
    }
    if (person.getHashedPassword() != null) {
      gen.writeStringField("password", person.getHashedPassword());
    }
    gen.writeNumberField("balance", person.getBalance());
    gen.writeArrayFieldStart("reservations");
    for (Reservation res : person.getReservations()) {
      gen.writeObject(res);
    }
    gen.writeEndArray();
    gen.writeEndObject();
  }
}
