package gr2116.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import gr2116.core.Amenity;
import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;
import gr2116.core.Person;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests HotelPersistence, both serializing and deserializing.
 */
public class HotelPersistenceTest {
  private HotelPersistence hotelPersistence;
  private HotelPersistence hotelPersistenceNull;

  @BeforeEach
  public void setup() {
    hotelPersistence = new HotelPersistence("test");
    hotelPersistenceNull = new HotelPersistence();
  }

  @Test
  public void testSerializingDeserializing() {
    // set up data
    Person rick = new Person("richard");
    Person kyle = new Person("kyle");
    Person tom = new Person("tom");
    rick.setFirstName("Richard");
    kyle.setFirstName("Kyle");
    tom.setFirstName("Tom");
    tom.setLastName("Hanks");
    tom.setPassword("password123");

    rick.addBalance(1000);
    kyle.addBalance(144);
    tom.addBalance(1000000000);

    Collection<Person> persons = new ArrayList<Person>();
    persons.add(rick);
    persons.add(kyle);
    persons.add(tom);
    
    HotelRoom room1 = new HotelRoom(HotelRoomType.Single, 101);
    room1.setPrice(0);
    HotelRoom room2 = new HotelRoom(HotelRoomType.Single, 102);
    room2.setPrice(20);
    HotelRoom room3 = new HotelRoom(HotelRoomType.Quad, 714);
    room3.setPrice(300);
    
    Collection<HotelRoom> rooms = new ArrayList<HotelRoom>();
    rooms.add(room1);
    rooms.add(room2);
    rooms.add(room3);
    
    room1.addAmenity(Amenity.Bathtub);
    room1.addAmenity(Amenity.Television);
    room2.addAmenity(Amenity.Fridge);
    room2.addAmenity(Amenity.Internet);
    room2.addAmenity(Amenity.Shower);
    room3.addAmenity(Amenity.KitchenFacilities);
    room3.addAmenity(Amenity.Television);
    room3.addAmenity(Amenity.WashingMachine);
    room3.addAmenity(Amenity.Shower);

    Hotel hotel = new Hotel(rooms, persons);
    
    hotel.makeReservation(rick, 101, LocalDate.of(2121, 6, 4), LocalDate.of(2121, 6, 7));
    hotel.makeReservation(kyle, 101, LocalDate.of(2121, 6, 11), LocalDate.of(2121, 6, 13));
    hotel.makeReservation(rick, 102, LocalDate.of(2121, 7, 13), LocalDate.of(2121, 7, 22));
    hotel.makeReservation(tom, 714, LocalDate.of(2121, 10, 12), LocalDate.of(2121, 10, 13));
    hotel.makeReservation(tom, 714, LocalDate.of(2121, 11, 12), LocalDate.of(2121, 11, 13));
    hotel.makeReservation(tom, 714, LocalDate.of(2122, 1, 12), LocalDate.of(2122, 1, 13));
    hotel.makeReservation(tom, 714, LocalDate.of(2122, 2, 12), LocalDate.of(2122, 2, 13));


    try {
      hotelPersistence.saveHotel(hotel);
      Hotel hotel2 = hotelPersistence.loadHotel();
      assertEquals(hotel.getRooms().size(), hotel2.getRooms().size());
      assertEquals(hotel.getPersons().size(), hotel2.getPersons().size());

      assertTrue(hotel2.getRooms().contains(room1));
      assertTrue(hotel2.getRooms().contains(room2));
      assertTrue(hotel2.getRooms().contains(room3));

      assertTrue(hotel2.getPersons().contains(rick));
      assertTrue(hotel2.getPersons().contains(kyle));
      assertTrue(hotel2.getPersons().contains(tom));

      assertTrue(hotel2.getRooms().equals(hotel.getRooms()));
      assertTrue(hotel2.getPersons().equals(hotel.getPersons()));

    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testPrefix() {
    HotelPersistence hotelPersistence = new HotelPersistence("test");
    assertEquals("test", hotelPersistence.getPrefix());

    assertThrows(IllegalArgumentException.class, () -> {new HotelPersistence("$/");});

    Hotel hotel = new Hotel();
    assertThrows(IllegalArgumentException.class, () -> hotelPersistenceNull.saveHotel(hotel));
    assertThrows(IllegalArgumentException.class, () -> hotelPersistenceNull.loadHotel());
    assertThrows(IllegalArgumentException.class, () -> hotelPersistence.saveHotel(null));
  }
  
  @Test
  public void testLoadNotFound() {
    HotelPersistence hotelPersistence = new HotelPersistence("notPrefix");
    try {
      assertEquals(30, hotelPersistence.loadHotel().getRooms().size());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }
}
