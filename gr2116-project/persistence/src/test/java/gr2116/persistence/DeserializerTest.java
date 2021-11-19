package gr2116.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr2116.core.HotelRoom;
import gr2116.core.Person;
import gr2116.core.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests HotelDeserializer, PersonDeserializer, ReservationDeserializer and RoomDeserializer.
 */
public class DeserializerTest {
  private static ObjectMapper mapper;

  @BeforeEach
  public void setup() {
    mapper = HotelPersistence.createObjectMapper();

  }

  private final String reservationString = """
      {"room":101,
      "startDate":"2021-11-03",
      "endDate":"2021-11-05",
      "id":"1012021110320211105"}
      }
      """;

  private final String roomString = """
      {"number":101,
      "type":"Double",
      "amenities":["Internet"],
      "price":10.0,
      "reservations":[
        {"room":101,
        "startDate":"2021-11-03",
        "endDate":"2021-11-05",
        "id":"1012021110320211105"}
        ]
      }
      """;

  private final String personString = """
      {"username":"henry",
      "firstName":"Henry",
      "balance":80.0,
      "reservations":[
        {"room":101,
        "startDate":"2021-11-03",
        "endDate":"2021-11-05",
        "id":"1012021110320211105"}
        ]
      }
      """;

  @Test
  public void testReservationDeserializer() {
    try {
      assertNotNull(mapper.readValue(reservationString, Reservation.class));
      // "room" is not number field
      String badString = reservationString.replaceFirst("101", "\"101\"");
      assertNull(mapper.readValue(badString, Reservation.class));
      // "startDate" is not textfield
      badString = reservationString.replaceFirst("\"2021-11-03\"", "2021");
      assertNull(mapper.readValue(badString, Reservation.class));
      // "endDate" is not textfield
      badString = reservationString.replaceFirst("\"2021-11-05\"", "2021");
      assertNull(mapper.readValue(badString, Reservation.class));
      // invalid format on date
      badString = reservationString.replaceFirst("\"2021-11-05\"", "\"2021-15-24\"");
      assertNull(mapper.readValue(badString, Reservation.class));
      // not the same Id.
      badString = reservationString.replaceFirst("1012021110320211105", "1054373234");
      assertNull(mapper.readValue(badString, Reservation.class));
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testRoomDeserializer() {
    try {
      assertNotNull(mapper.readValue(roomString, HotelRoom.class));
      // "numer" is not a numeric field
      String badString = roomString.replaceFirst("101", "\"101\"");
      assertNull(mapper.readValue(badString, HotelRoom.class));
      // "type" is not text field
      badString = roomString.replaceFirst("\"Double\"", "1");
      assertNull(mapper.readValue(badString, HotelRoom.class));
      // "amenities" is not array field
      badString = roomString.replaceFirst("\\[\"Internet\"\\]", "\"Internet\"");
      assertNull(mapper.readValue(badString, HotelRoom.class));
      // "price" is not number field
      badString = roomString.replaceFirst("10.0", "\"10\"");
      assertNull(mapper.readValue(badString, HotelRoom.class));
      // "reservations" is not array
      badString = """
          {"number":101,
          "type":"Double",
          "amenities":["Internet"],
          "price":10.0,
          "reservations": 34229
          }
          """;
      assertNull(mapper.readValue(badString, HotelRoom.class));
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testPersonDeserializer() {
    try {
      assertNotNull(mapper.readValue(personString, Person.class));
      // "name" is not a text field
      String badString = personString.replaceFirst("\"henry\"", "4332");
      assertNull(mapper.readValue(badString, Person.class));
      // "balance" is not a numeric field
      badString = personString.replaceFirst("80.0", "\"4332\"");
      assertNull(mapper.readValue(badString, Person.class));
      // "reservations" is not an array
      badString = """
          {"username":"henry",
          "firstName":"Henry",
          "balance":80.0,
          "reservations": 102421
          }
          """;
      assertNull(mapper.readValue(badString, Person.class));
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }
}
