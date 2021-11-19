package gr2116.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Hotel class.
 */
public class HotelTest {
  Hotel hotel;
  Person tom;
  LocalDate today = LocalDate.now();
  LocalDate overmorrow = today.plusDays(2);
  HotelRoom room1 = mock(HotelRoom.class);
  HotelRoom room2 = mock(HotelRoom.class);
  Collection<HotelRoom> rooms = new ArrayList<>(Arrays.asList(room1, room2));

  @BeforeEach
  public void setup() {
    hotel = new Hotel();
    tom = mock(Person.class);
  }

  @Test
  public void testConstructor() {
    Hotel emptyHotel = new Hotel();
    assertEquals(Arrays.asList(), emptyHotel.getRooms((r) -> true));
    when(room1.getNumber()).thenReturn(101);
    when(room2.getNumber()).thenReturn(102);
    Hotel fullHotel = new Hotel(rooms);
    assertEquals(rooms, fullHotel.getRooms());
    Hotel hotelWithPerson = new Hotel(rooms, Arrays.asList(tom));
    assertEquals(rooms, hotelWithPerson.getRooms());
    assertEquals(Arrays.asList(tom), hotelWithPerson.getPersons());
  }

  @Test
  public void testRoomsIsPersistent() {
    Hotel fullHotel = new Hotel(rooms);
    HotelRoom room3 = mock(HotelRoom.class);
    rooms.add(room3);
    assertNotEquals(rooms, fullHotel.getRooms((r) -> true));
  }

  @Test
  public void testAddPerson() {
    assertThrows(IllegalArgumentException.class, () -> {
      hotel.addPerson(null);
    });
    hotel.addPerson(tom);
    assertEquals(1, hotel.getPersons().size());
    assertEquals(tom, hotel.getPersons().iterator().next());
    hotel.addPerson(tom);
    assertEquals(1, hotel.getPersons().size());
  }

  @Test
  public void testRemovePerson() {
    hotel.addPerson(tom);
    assertEquals(1, hotel.getPersons().size());
    hotel.removePerson(tom);
    assertEquals(0, hotel.getPersons().size());
  }

  @Test
  public void testGetPersonsPredicate() {
    // need to create instances of Person since predicate does not work with mocks.
    Person tomas = new Person("tomas");
    Person rick = new Person("rick");
    tomas.addBalance(100);
    rick.addBalance(25);
    tomas.setFirstName("Thomas");
    rick.setFirstName("Richard");
    tomas.setLastName("Sanders");
    rick.setLastName("Sanders");
    hotel.addPerson(tomas);
    hotel.addPerson(rick);
    assertEquals(Arrays.asList(tomas), hotel.getPersons((p) -> p.getBalance() > 50));
    assertEquals(0, hotel.getPersons((p) -> p.getFirstName().equals("Bernie")).size());
    assertTrue(hotel.getPersons((p) -> p.getLastName().equals("Sanders")).contains(tomas));
    assertTrue(hotel.getPersons((p) -> p.getLastName().equals("Sanders")).contains(rick));
  }

  @Test
  public void testAddRoom() {
    assertEquals(Arrays.asList(), hotel.getRooms((r) -> true));
    when(room1.getNumber()).thenReturn(101);
    when(room2.getNumber()).thenReturn(102);
    hotel.addRoom(room1);
    hotel.addRoom(room2);
    assertEquals(Arrays.asList(room1, room2), hotel.getRooms());
    assertThrows(IllegalArgumentException.class, () -> hotel.addRoom(null));
  }

  @Test
  public void testRemoveRoom() {
    hotel.addRoom(room1);
    hotel.removeRoom(room1);
    assertEquals(Arrays.asList(), hotel.getRooms((r) -> true));
  }

  @Test
  public void testGetRoomsPredicate() {
    HotelRoom room3 = mock(HotelRoom.class);
    when(room1.getRoomType()).thenReturn(HotelRoomType.Single);
    when(room2.getRoomType()).thenReturn(HotelRoomType.Double);
    when(room1.getNumber()).thenReturn(101);
    when(room2.getNumber()).thenReturn(102);
    when(room3.getNumber()).thenReturn(103);
    hotel.addRoom(room1);
    hotel.addRoom(room2);
    hotel.addRoom(room3);
    assertEquals(Arrays.asList(room1), hotel.getRooms(
        (room) -> room.getRoomType() == HotelRoomType.Single)
    );
    assertEquals(Arrays.asList(room2, room3), hotel.getRooms((room) -> room.getNumber() > 101));
    
    when(room1.hasAmenity(Amenity.Internet)).thenReturn(true);
    when(room2.hasAmenity(Amenity.Internet)).thenReturn(false);
    assertEquals(Arrays.asList(room1), hotel.getRooms((room) -> room.hasAmenity(Amenity.Internet)));
  }

  @Test
  public void testMakeReservation() {
    HotelRoom room = mock(HotelRoom.class);
    
    LocalDate start = LocalDate.now();
    LocalDate end = LocalDate.now().plusDays(1);
    when(room.isAvailable(start, end)).thenReturn(true);
    when(room.getPrice(start, end)).thenReturn(100.0);
    when(room.getPrice()).thenReturn(100.0);
    when(room.getNumber()).thenReturn(999);
    hotel.addRoom(room);
    hotel.addPerson(tom);

    when(tom.getBalance()).thenReturn(1000.0);
    double balanceBefore = tom.getBalance();
    hotel.makeReservation(tom, 999, start, end);
    when(tom.getBalance()).thenReturn(900.0);
    assertEquals(balanceBefore - room.getPrice(), tom.getBalance());
    when(room.isAvailable(start.plusDays(2), start.plusDays(2))).thenReturn(true);
    when(room.getPrice(start.plusDays(2), start.plusDays(2))).thenReturn(0.0);
    assertThrows(IllegalArgumentException.class, () -> 
        hotel.makeReservation(tom, 215, LocalDate.of(2022, 4, 3), LocalDate.of(2022, 4, 1)),
        "Booking must conform to the linear passing of time."
    );
  }

  @Test
  public void testBadMakeReservationParameters() {
    when(tom.getBalance()).thenReturn(20000.0);
    assertThrows(IllegalArgumentException.class, () -> {
      hotel.makeReservation(null, 101, today, overmorrow);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      hotel.makeReservation(tom, 101, null, overmorrow);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      hotel.makeReservation(tom, 101, today, null);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      hotel.makeReservation(tom, 101, today, overmorrow);
    });
    hotel.addPerson(tom);
    assertThrows(IllegalStateException.class, () -> {
      hotel.makeReservation(tom, 101, LocalDate.now().minusDays(1), overmorrow);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      hotel.makeReservation(tom, 101, today, overmorrow);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      hotel.makeReservation(tom, 101, today, overmorrow);
    });
    hotel.addRoom(room1);
    when(room1.getNumber()).thenReturn(101);
    when(room1.isAvailable(today, overmorrow)).thenReturn(false);
    assertThrows(IllegalStateException.class, () -> {
      hotel.makeReservation(tom, 101, today, overmorrow);
    });
  }

  @Test
  public void testReservationConstistency() {
    HotelRoom deluxeRoom = mock(HotelRoom.class);
    when(deluxeRoom.isAvailable(today, overmorrow)).thenReturn(true);
    when(deluxeRoom.getPrice(today, overmorrow)).thenReturn(900.0);
    when(deluxeRoom.getNumber()).thenReturn(105);
    hotel.addRoom(deluxeRoom);
    // need to create an instance of Person here to test consistency
    Person rick = new Person("rick");
    rick.addBalance(10000);
    hotel.addPerson(rick);

    hotel.makeReservation(rick, 105, today, overmorrow);
    assertEquals(1, rick.getReservationIds().size());
    ArrayList<Reservation> reservations = new ArrayList<>();
    rick.getReservations().forEach((r) -> reservations.add(r));
    assertEquals(deluxeRoom.getNumber(), reservations.get(0).getRoomNumber());
  }

  @Test
  public void testIterator() {
    hotel.addRoom(room1);
    hotel.addRoom(room2);
    Iterator<HotelRoom> it = hotel.iterator();
    while (it.hasNext()) {
      assertTrue(hotel.getRooms((r) -> true).contains(it.next()));
    }
  }
}