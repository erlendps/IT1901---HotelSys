package gr2116.persistence.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

import gr2116.core.Person;
import gr2116.core.Reservation;

/**
 * Serializer for Person class.
 */
public class PersonSerializer extends JsonSerializer<Person> {

 /*
  format:
  { 
    "name": "...",
    "email": "...",
    "balance": ... ,
    "reservations": [ ... ]
  }
  */
  @Override
  public void serialize(Person person, JsonGenerator gen,
      SerializerProvider serializer) throws IOException {
    gen.writeStartObject();
    if (person.getName() != null) {
      gen.writeStringField("name", person.getName());
    }
    if (person.getEmail() != null) {
      gen.writeStringField("email", person.getEmail());
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
