package gr2116.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    assertEquals(sb.toString(), res.toString());
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

  @Test
  public void testIterator() {
    Iterator<LocalDate> it = res.iterator();
    assertTrue(it.hasNext());
    assertEquals(LocalDate.now(), it.next());
    assertTrue(it.hasNext());
    it.next();
    it.next();
    it.next();
    assertTrue(it.hasNext());
    assertEquals(LocalDate.now().plusDays(4), it.next());
    assertFalse(it.hasNext());
  }

  @Test
  public void testEquals() {
    assertTrue(res.equals(res));
    assertFalse(res.equals(null));
    assertFalse(res.equals("reservation"));
    when(room2.getNumber()).thenReturn(101);
    Reservation res2 = new Reservation(room2, startDate, endDate);
    assertTrue(res.equals(res2));
  }

  @Test
  public void testHash() {
    int hash = 7;
    int roomNumHash = Integer.valueOf(101).hashCode();
    int startHash = startDate.hashCode();
    hash = hash * 31 + roomNumHash;
    hash = hash * 31 + startHash;
    assertEquals(hash, res.hashCode());
  }
}
