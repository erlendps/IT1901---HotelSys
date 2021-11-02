package gr2116.persistence.internal;

import java.io.IOException;

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

import gr2116.core.Amenity;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;
import gr2116.core.Reservation;

/**
 * Deserializer for HotelRoom class.
 */
public class RoomDeserializer extends JsonDeserializer<HotelRoom> {

  private final ReservationDeserializer reservationDeserializer = new ReservationDeserializer();

  /*
  format:
  {
    "number": ... ,
    "type": "...",
    "amenities": [ ... ],
    "price": ... ,
    "reservations": [ ... ]
  }
  */

  @Override
  public HotelRoom deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    TreeNode treeNode = p.getCodec().readTree(p);
    return deserialize((JsonNode) treeNode);
  }

  /**
   * Custom implementation of jackson deserialize that takes a JsonNode object as argument.
   *
   * @param jsonNode the JsonNode to be processed
   *
   * @return HotelRoom if everything checks out, null otherwise
   */
  private HotelRoom deserialize(JsonNode jsonNode) {
    if (jsonNode instanceof ObjectNode objectNode) {
      HotelRoom room;
      JsonNode numberNode = objectNode.get("number");
      if (!(numberNode instanceof NumericNode) || numberNode.asInt() <= 0) {
        return null;
      }
      JsonNode typeNode = objectNode.get("type");
      if (!(typeNode instanceof TextNode)) {
        return null;
      }
      room = new HotelRoom(HotelRoomType.valueOf(typeNode.asText()), numberNode.asInt());
      JsonNode amenityNode = objectNode.get("amenities");
      if (amenityNode instanceof ArrayNode) {
        for (JsonNode amenity : (ArrayNode) amenityNode) {
          room.addAmenity(Amenity.valueOf(amenity.asText()));
        }
      }
      JsonNode priceNode = objectNode.get("price");
      if (priceNode instanceof NumericNode && priceNode.asDouble() >= 0) {
        room.setPrice(priceNode.asDouble());
      }
      JsonNode reservationNode = objectNode.get("reservations");
      if (reservationNode instanceof ArrayNode) {
        for (JsonNode res : (ArrayNode) reservationNode) {
          Reservation reservation = reservationDeserializer.deserialize(res);
          room.addReservation(reservation);
        }
      }
      return room;
    }
    return null;
  }
}
