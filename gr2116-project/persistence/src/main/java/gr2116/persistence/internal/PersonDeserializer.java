package gr2116.persistence.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import gr2116.core.Person;
import gr2116.core.Reservation;
import java.io.IOException;

/**
 * Deserializer for Person class.
 */
public class PersonDeserializer extends JsonDeserializer<Person> {

  private ReservationDeserializer reservationDeserializer = new ReservationDeserializer();

  @Override
  public Person deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = p.getCodec().readTree(p);
    return deserialize((JsonNode) treeNode);
  }

  /**
   * Custom implementation that takes a jsonNode as arugment.
   *
   * @param jsonNode jsonNode to be processed
   *
   * @return Person if everything checks out, null otherwise.
   */
  protected Person deserialize(JsonNode jsonNode) {
    if (jsonNode instanceof ObjectNode objectNode) {
      Person person;
      JsonNode usernameNode = objectNode.get("username");
      if (!(usernameNode instanceof TextNode)) {
        return null;
      }
      person = new Person(usernameNode.asText());
      JsonNode firstNameNode = objectNode.get("firstName");
      if (firstNameNode instanceof TextNode) {
        person.setFirstName(firstNameNode.asText());
      }
      JsonNode lastNameNode = objectNode.get("lastName");
      if (lastNameNode instanceof TextNode) {
        person.setLastName(lastNameNode.asText());
      }
      JsonNode balanceNode = objectNode.get("balance");
      if (balanceNode instanceof NumericNode) {
        person.addBalance(balanceNode.asDouble());
      } else {
        return null;
      }
      JsonNode reservationNode = objectNode.get("reservations");
      if (reservationNode instanceof ArrayNode) {
        for (JsonNode reservation : (ArrayNode) reservationNode) {
          Reservation res = reservationDeserializer.deserialize(reservation);
          if (!(res == null)) {
            person.addReservation(res);
          }
        }
      } else {
        return null;
      }
      return person;
    }
    return null;
  }
}
