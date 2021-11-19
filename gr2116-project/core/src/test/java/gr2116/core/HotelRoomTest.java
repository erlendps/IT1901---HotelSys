package gr2116.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HotelRoomTest {
  HotelRoom roomSingle;
  HotelRoom roomDouble;
  LocalDate today = LocalDate.now();
  LocalDate tomorrow = today.plusDays(1);
  LocalDate overmorrow = today.plusDays(2);

  @BeforeEach
  public void setup() {
    roomSingle = new HotelRoom(HotelRoomType.Single, 111);
    roomDouble = new HotelRoom(HotelRoomType.Double, 794);
  }

  @Test
  public void testConstructor() {
    assertThrows(IllegalArgumentException.class, () -> {
      new HotelRoom(null, 111);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new HotelRoom(HotelRoomType.Single, -435);
    });
    HotelRoom testRoom = new HotelRoom(HotelRoomType.Quad, 143);
    assertEquals(HotelRoomType.Quad, testRoom.getRoomType());
    assertEquals(143, testRoom.getNumber());
    assertEquals(1, testRoom.getFloor());

    HotelRoom roomWithOnlyNumber = new HotelRoom(101);
    assertEquals(HotelRoomType.Single, roomWithOnlyNumber.getRoomType());
    assertEquals(101, roomWithOnlyNumber.getNumber());
  }

  @Test
  public void testFloorAndNumber() {
    assertEquals(1, roomSingle.getFloor());
    assertEquals(111, roomSingle.getNumber());

    assertEquals(7, roomDouble.getFloor());
    assertEquals(794, roomDouble.getNumber());
  }

  @Test
  public void testPrice() {
    assertEquals(0, roomSingle.getPrice());
    assertEquals(0, roomSingle.getPrice(today, tomorrow));
    roomSingle.setPrice(100);
    assertEquals(100, roomSingle.getPrice());
    assertEquals(200, roomSingle.getPrice(today, overmorrow));
    assertThrows(IllegalArgumentException.class, () -> {
      roomSingle.setPrice(-10);
    });
  }

  @Test
  public void testAddAmenity() {
    assertFalse(roomSingle.hasAmenity(Amenity.Bathtub));
    roomSingle.addAmenity(Amenity.Bathtub);
    assertTrue(roomSingle.hasAmenity(Amenity.Bathtub));
    assertThrows(IllegalArgumentException.class, () -> roomSingle.addAmenity(null));
  }

  @Test
  public void testRemoveAmenity() {
    roomSingle.addAmenity(Amenity.Bathtub);
    assertTrue(roomSingle.hasAmenity(Amenity.Bathtub));
    roomSingle.removeAmenity(Amenity.Bathtub);
    assertFalse(roomSingle.hasAmenity(Amenity.Bathtub));
  }

  @Test
  public void testGetAmenities() {
    roomSingle.addAmenity(Amenity.Bathtub);
    roomSingle.addAmenity(Amenity.Internet);
    assertTrue(roomSingle.getAmenities().contains("Bathtub"));
    assertTrue(roomSingle.getAmenities().contains("Internet"));
  }

  @Test
  public void testIsAvailable() {
    assertTrue(roomSingle.isAvailable(today));
    assertTrue(roomSingle.isAvailable(today, tomorrow));
    assertThrows(IllegalArgumentException.class, () -> roomSingle.isAvailable(tomorrow, today));
  }
  
  @Test
  public void testAddReservation() {
    Reservation res = mock(Reservation.class);
    assertThrows(IllegalArgumentException.class, () -> roomDouble.addReservation(null));
    when(res.getRoomNumber()).thenReturn(roomSingle.getNumber());
    assertThrows(IllegalArgumentException.class, () -> roomDouble.addReservation(res));
    when(res.getRoomNumber()).thenReturn(794);
    when(res.getStartDate()).thenReturn(LocalDate.now());
    when(res.getEndDate()).thenReturn(LocalDate.now().plusDays(2));
    assertDoesNotThrow(() -> roomDouble.addReservation(res));
    assertEquals(res, roomDouble.getCalendar().iterator().next());
    when(res.getId()).thenReturn("404");
    assertEquals("404", roomDouble.getReservationIds().iterator().next());
  }

  @Test
  public void testChronology() {
    LocalDate tomorrow = LocalDate.now().plusDays(1);
    assertThrows(IllegalArgumentException.class, () -> {
      roomSingle.isAvailable(null, tomorrow);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      roomSingle.isAvailable(today, null);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      roomSingle.isAvailable(tomorrow, today);
    });
  }

  @Test
  public void testEquals() {
    assertTrue(roomSingle.equals(roomSingle));
    assertFalse(roomSingle.equals(null));
    assertFalse(roomSingle.equals(new Object()));
    HotelRoom anotherRoom = new HotelRoom(HotelRoomType.Single, 101);
    assertFalse(roomSingle.equals(anotherRoom));
    anotherRoom = new HotelRoom(HotelRoomType.Double, 111);
    assertFalse(roomSingle.equals(anotherRoom));
    anotherRoom = new HotelRoom(HotelRoomType.Single, 111);
    roomSingle.setPrice(10);
    assertFalse(roomSingle.equals(anotherRoom));
    anotherRoom.setPrice(10);
    roomSingle.addAmenity(Amenity.Bathtub);
    assertFalse(roomSingle.equals(anotherRoom));
    anotherRoom.addAmenity(Amenity.Bathtub);
    assertTrue(roomSingle.equals(anotherRoom));
  }

  @Test
  public void testHashCode() {
    roomSingle.addAmenity(Amenity.Fridge);
    int hash = 13;
    int numHash = roomSingle.getNumber();
    int typeHash = roomSingle.getRoomType().hashCode();
    int amenHash = roomSingle.getAmenities().hashCode();
    int priceHash = (int) roomSingle.getPrice();
    hash = hash * 31 + numHash;
    hash = hash * 13 + typeHash;
    hash = hash * 5 + amenHash;
    hash = hash * 7 + priceHash;
    assertEquals(hash, roomSingle.hashCode());
  }
}
