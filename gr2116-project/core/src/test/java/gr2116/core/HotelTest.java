package gr2116.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
 * Test class for Hotel class
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
    tom = new Person("Tom Haddleford");
    tom.addBalance(1000);
  }

  @Test
  public void testConstructor() {
    Hotel emptyHotel = new Hotel();
    assertEquals(Arrays.asList(), emptyHotel.getRooms((r) -> true));
    Hotel fullHotel = new Hotel(rooms);
    assertEquals(rooms, fullHotel.getRooms((r) -> true));
  }

  @Test
  public void testRoomsIsPersistent() {
    Hotel fullHotel = new Hotel(rooms);
    HotelRoom room3 = mock(HotelRoom.class);
    rooms.add(room3);
    assertNotEquals(rooms, fullHotel.getRooms((r) -> true));
  }

  @Test
  public void testAddRoom() {
    assertEquals(Arrays.asList(), hotel.getRooms((r) -> true));
    hotel.addRoom(room1);
    hotel.addRoom(room2);
    assertEquals(Arrays.asList(room1, room2), hotel.getRooms((r) -> true));
    assertThrows(NullPointerException.class, () -> hotel.addRoom(null));
    assertFalse(hotel.addRoom(room1));
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
    hotel.addRoom(room1);
    hotel.addRoom(room2);
    hotel.addRoom(room3);
    assertEquals(Arrays.asList(room1), hotel.getRooms((room) -> room.getRoomType() == HotelRoomType.Single));
    when(room1.getNumber()).thenReturn(2);
    when(room2.getNumber()).thenReturn(5);
    when(room3.getNumber()).thenReturn(7);
    assertEquals(Arrays.asList(room2, room3), hotel.getRooms((room) -> room.getNumber() > 3));
    
    when(room1.hasAmenity(Amenity.Internet)).thenReturn(true);
    when(room2.hasAmenity(Amenity.Internet)).thenReturn(false);
    assertEquals(Arrays.asList(room1), hotel.getRooms((room) -> room.hasAmenity(Amenity.Internet)));
  }

  @Test
  public void testMakeReservation() {
    HotelRoom room = mock(HotelRoom.class);
    double balanceBefore = tom.getBalance();
    
    LocalDate start = LocalDate.now();
    LocalDate end = LocalDate.now().plusDays(1);
    when(room.isAvailable(start, end)).thenReturn(true);
    when(room.getPrice(start, end)).thenReturn(100.0);
    when(room.getPrice()).thenReturn(100.0);
    
    hotel.makeReservation(tom, room, start, end);
    assertEquals(balanceBefore-room.getPrice(), tom.getBalance(), "Booking one night should cost the same as the price of the hotel room.");

    when(room.isAvailable(start.plusDays(2), start.plusDays(2))).thenReturn(true);
    when(room.getPrice(start.plusDays(2), start.plusDays(2))).thenReturn(0.0);
    /*
    tom.makeReservation(room, LocalDate.of(2021, 8, 6), LocalDate.of(2021, 8, 6));
    assertEquals(900, tom.getBalance(), "Booking 0 days should not cost money.");
    */
    assertThrows(IllegalArgumentException.class, () -> 
        hotel.makeReservation(tom, room, LocalDate.of(2022, 4, 3), LocalDate.of(2022, 4, 1)),
        "Booking must conform to the linear passing of time."
    );
  }
  @Test
  public void testReservationConstistency() {
    //HotelRoom deluxeRoom = new HotelRoom(HotelRoomType.Suite, 900); // The room is on the 9th floor.
    HotelRoom deluxeRoom = mock(HotelRoom.class);
    when(deluxeRoom.isAvailable(today, overmorrow)).thenReturn(true);
    when(deluxeRoom.getPrice(today, overmorrow)).thenReturn(900.0);
    when(deluxeRoom.getNumber()).thenReturn(105);

    hotel.makeReservation(tom, deluxeRoom, today, overmorrow);
    assertEquals(1, tom.getReservationIds().size(), "User should have one reservation after booking one room.");
    ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    tom.getReservations().forEach((r) -> reservations.add(r));
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