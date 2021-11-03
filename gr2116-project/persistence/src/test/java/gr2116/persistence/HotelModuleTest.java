package gr2116.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gr2116.core.Amenity;
import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;
import gr2116.core.Person;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;


public class HotelModuleTest {
  private ObjectMapper mapper;
  private Hotel hotel;
  private HotelRoom room;
  private Person person;


  @BeforeEach
  public void setup() {
    mapper = HotelPersistence.createObjectMapper();
    hotel = new Hotel();
    room = new HotelRoom(HotelRoomType.Double, 101);
    room.setPrice(10);
    room.addAmenity(Amenity.Internet);
    person = new Person("Henry");
    person.addBalance(100);
    person.makeReservation(room, LocalDate.now(), LocalDate.now().plusDays(2));

    hotel.addPerson(person);
    hotel.addRoom(room);
  }

  private static final String hotelString = """
    {"rooms":[
      {"number":101,
      "type":"Double",
      "amenities":["Internet"],
      "price":10.0,
      "reservations":[
        {"room":101,
        "startDate":"2021-11-03",
        "endDate":"2021-11-05",
        "id":1012021110320211105}
        ]}
      ],
    "persons":[
      {"name":"Henry",
      "balance":80.0,
      "reservations":[
        {"room":101,
        "startDate":"2021-11-03",
        "endDate":"2021-11-05",
        "id":1012021110320211105}
        ]}
      ]
    }
  """;

  private static final String roomString = """
    {"number":101,
    "type":"Double",
    "amenities":["Internet"],
    "price":10.0,
    "reservations":[
      {"room":101,
      "startDate":"2021-11-03",
      "endDate":"2021-11-05",
      "id":1012021110320211105}
      ]
    }
  """;

  private static final String personString = """
    {"name":"Henry",
    "balance":80.0,
    "reservations":[
      {"room":101,
      "startDate":"2021-11-03",
      "endDate":"2021-11-05",
      "id":1012021110320211105}
      ]
    }
  """;

  private static final String reservationString = """
  {
    "room":101,
    "startDate":"2021-11-03",
    "endDate":"2021-11-05",
    "id":1012021110320211105
  }
  """;

  @Test
  public void testSerializers() {
    try {
      assertEquals(hotelString.replaceAll("\\s+", ""), mapper.writeValueAsString(hotel));
      assertEquals(personString.replaceAll("\\s+", ""), mapper.writeValueAsString(person));
      assertEquals(roomString.replaceAll("\\s+", ""), mapper.writeValueAsString(room));
      assertEquals(reservationString.replaceAll("\\s+", ""), mapper.writeValueAsString(
          person.getReservations().iterator().next()));
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }
}
