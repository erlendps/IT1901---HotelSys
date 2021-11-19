package gr2116.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Reservation.
 */
public class ReservationTest {
  private Reservation res;
  private final LocalDate startDate = LocalDate.now();
  private final LocalDate endDate = LocalDate.now().plusDays(4);
  private final HotelRoom room = mock(HotelRoom.class);
  private final HotelRoom room2 = mock(HotelRoom.class);


  @BeforeEach
  public void setup() {
    when(room.getNumber()).thenReturn(101);
    res = new Reservation(room, startDate, endDate);
  }

  @Test
  public void testGetRoomNumber() {
    when(room2.getNumber()).thenReturn(205);
    assertEquals(room.getNumber(), res.getRoomNumber());
    assertNotEquals(room2.getNumber(), res.getRoomNumber());
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
    StringBuilder sb = new StringBuilder();
    sb.append(101);
    sb.append(startDate.toString().replace("-", ""));
    sb.append(endDate.toString().replace("-", ""));
    assertEquals(sb.toString(), res.getId());
  }

  @Test
  public void testConstructor() {
    assertThrows(IllegalArgumentException.class, () ->
        new Reservation(null, startDate, endDate));
    
    assertThrows(IllegalArgumentException.class, () ->
        new Reservation(room, null, endDate));
    
    assertThrows(IllegalArgumentException.class, () ->
        new Reservation(room, startDate, null));

    assertThrows(IllegalArgumentException.class, () ->
        new Reservation(room, endDate, startDate));
  }
}
