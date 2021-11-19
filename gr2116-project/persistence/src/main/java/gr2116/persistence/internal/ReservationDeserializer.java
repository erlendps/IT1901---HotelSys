package gr2116.persistence.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import gr2116.core.HotelRoom;
import gr2116.core.Reservation;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Deserializer for Reservation class.
 */
public class ReservationDeserializer extends JsonDeserializer<Reservation> {

  /*
   * format: { "room": ... , "startDate": "...", "endDate": "...", "id": ... }
   */

  @Override
  public Reservation deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = p.getCodec().readTree(p);
    return deserialize((JsonNode) treeNode);
  }

  /**
   * Custom implentation of jackson deserialize that take a JsonNode obejct as
   * argument.
   *
   * @param jsonNode the JsonNode to be processed.
   *
   * @return Reservation if everything checks out, null otherwise.
   */
  protected Reservation deserialize(JsonNode jsonNode) {
    if (jsonNode instanceof ObjectNode) {
      JsonNode roomNode = jsonNode.get("room");
      if (!(roomNode instanceof NumericNode)) {
        return null;
      }
      JsonNode startNode = jsonNode.get("startDate");
      if (!(startNode instanceof TextNode)) {
        return null;
      }
      JsonNode endNode = jsonNode.get("endDate");
      if (!(endNode instanceof TextNode)) {
        return null;
      }
      try {
        LocalDate.parse(startNode.asText());
        LocalDate.parse(endNode.asText());
      } catch (Exception e) {
        return null;
      }
      Reservation reservation = new Reservation(
          new HotelRoom(roomNode.asInt()), LocalDate.parse(startNode.asText()),
          LocalDate.parse(endNode.asText())
      );
      JsonNode idNode = jsonNode.get("id");
      if (reservation.getId().equals(idNode.asText())) {
        return reservation;
      }
    }
    return null;
  }

}
