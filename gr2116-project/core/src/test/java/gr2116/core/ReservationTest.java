package gr2116.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationTest {
  private Reservation res;
  private final LocalDate startDate = LocalDate.now();
  private final LocalDate endDate = LocalDate.now().plusDays(4);
  private final HotelRoom room = new HotelRoom(HotelRoomType.Single, 21);
  private final HotelRoom room2 = new HotelRoom(HotelRoomType.Suite, 22);

  @BeforeEach
  public final void setup() {
    res = new Reservation(11, room, startDate, endDate);
  }

  @Test
  public void testGetRoom() {
    assertEquals(room, res.getRoom());
    assertNotEquals(room2, res.getRoom());
  }

  @Test
  public void testGetStartDate() {
    assertEquals(startDate, res.getStartDate());
  }

  @Test
  public void testGetEndDate() {
    assertEquals(endDate, res.getEndDate());
  }

  @Test
  public void testGetId() {
    assertEquals(11, res.getId());
    assertNotEquals(22, res.getId());
  }

  @Test
  public void testConstructor() {
    assertThrows(NullPointerException.class, () ->
      new Reservation(1, null, startDate, endDate));

    assertThrows(NullPointerException.class, () ->
      new Reservation(1, room, null, endDate));

    assertThrows(NullPointerException.class, () ->
      new Reservation(1, room, startDate, null));

    assertThrows(IllegalArgumentException.class, () ->
      new Reservation(1, room, endDate, startDate));
  }
}