package gr2116.persistence.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.Person;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Deserializes Hotel from json.
 */
public class HotelDeserializer extends JsonDeserializer<Hotel> {
  private final PersonDeserializer personDeserializer = new PersonDeserializer();
  private final RoomDeserializer roomDeserializer = new RoomDeserializer();

  /*
   * Deseralizes a Hotel object with the following
   * format: { "rooms": [ ... ], "persons": [ ... ] }.
   */
  @Override
  public Hotel deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = p.getCodec().readTree(p);
    return deserialize((JsonNode) treeNode);
  }
  
  /**
   * Deserializes a Hotel object.
   *
   * @param jsonNode The JSON node which should be formatted as a serialized Hotel object.
   * @return The Hotel object
   */
  private Hotel deserialize(JsonNode jsonNode) {
    if (jsonNode instanceof ObjectNode) {
      Collection<HotelRoom> rooms = new ArrayList<>();
      Collection<Person> persons = new ArrayList<>();
      JsonNode roomNodes = jsonNode.get("rooms");
      if (roomNodes instanceof ArrayNode) {
        for (JsonNode roomNode : (ArrayNode) roomNodes) {
          HotelRoom room = roomDeserializer.deserialize(roomNode);
          rooms.add(room);
        }
      }
      JsonNode personNodes = jsonNode.get("persons");
      if (personNodes instanceof ArrayNode) {
        for (JsonNode personNode : (ArrayNode) personNodes) {
          Person person = personDeserializer.deserialize(personNode);
          persons.add(person);
        }
      }
      Hotel hotel = new Hotel(rooms, persons);
      return hotel;
    }
    return null;
  }
}
