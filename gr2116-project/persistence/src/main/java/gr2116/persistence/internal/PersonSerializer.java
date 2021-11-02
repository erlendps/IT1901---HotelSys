package gr2116.persistence.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

import gr2116.core.Person;


public class PersonSerializer extends JsonSerializer<Person> {

 /*
  * format: { "examlpe@email.com": {}}
  */
  @Override
  public void serialize(Person value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    // TODO Auto-generated method stub
    
  }

}
