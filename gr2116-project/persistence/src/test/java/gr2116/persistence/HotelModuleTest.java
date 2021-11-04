package gr2116.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gr2116.core.Amenity;
import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;
import gr2116.core.Person;
import gr2116.core.Reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.Arrays;


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
    person.setEmail("henry@mail.com");
    person.addBalance(100);
    Reservation res = new Reservation(
        room, LocalDate.of(2021, 11, 03), LocalDate.of(2021, 11, 05));
    person.addReservation(res);
    room.addReservation(res);

    hotel.addPerson(person);
    hotel.addRoom(room);
  }

  /*
  The string is generated from the hotel that is generated in the setup() methd
  */
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
      "email": "henry@mail.com",
      "balance":100.0,
      "reservations":[
        {"room":101,
        "startDate":"2021-11-03",
        "endDate":"2021-11-05",
        "id":1012021110320211105}
        ]}
      ]
    }
  """;

  @Test
  public void testSerializers() {
    try {
      assertEquals(hotelString.replaceAll("\\s+", ""), mapper.writeValueAsString(hotel));
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testDeserializers() {
    try {
      Hotel hotel2 = mapper.readValue(hotelString, Hotel.class);
      assertEquals(1, hotel2.getPersons().size());
      assertEquals(1, hotel2.getRooms().size());
      assertEquals(101, hotel2.getRooms().iterator().next().getNumber());
      assertEquals("Henry", hotel2.getPersons().iterator().next().getName());
      assertEquals(Arrays.asList("Internet"), hotel2.getRooms().iterator().next().getAmenities());
      // the string is generated from setup(), and there are custom implementations of 
      // .equals(o) for Person, rooms and reservation, therefore the next tests will work
      // and confirm we have the same object.
      assertEquals(person, hotel2.getPersons().iterator().next());
      assertEquals(room, hotel2.getRooms().iterator().next());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testSerializeThenDeserialize() {
    try {
      String json = mapper.writeValueAsString(hotel);
      Hotel hotel2 = mapper.readValue(json, Hotel.class);
      assertNotNull(hotel2);
      // if the two hotel objects has the same rooms and persons, its the same.
      assertEquals(hotel.getRooms(), hotel2.getRooms());
      assertEquals(hotel.getPersons(), hotel2.getPersons());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
}
