package gr2116.core;

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
  }
}
