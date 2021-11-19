package gr2116.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HotelRoomFilterTest {
  private LocalDate today = LocalDate.now();
  private LocalDate tomorrow = LocalDate.now().plusDays(1);
  private HotelRoomType type;
  private HashMap<Amenity, Boolean> amenities;
  private Integer floor;
  
  private HotelRoomFilter filter;

  @BeforeEach
  public void setup() {
    amenities = new HashMap<>();
    amenities.put(Amenity.Bathtub, true);
    amenities.put(Amenity.Fridge, true);
    type = HotelRoomType.Double;
    floor = 2;
  }

  @Test
  public void testConstructor() {
    filter = new HotelRoomFilter(null, null, null, null, null);
    assertFalse(filter.hasValidDates());
    assertNull(filter.getRoomType());
    assertNull(filter.getStartDate());
    assertNull(filter.getEndDate());
    filter = new HotelRoomFilter(today, tomorrow, null, null, null);
    assertTrue(filter.hasValidDates());
    filter = new HotelRoomFilter(tomorrow, today, null, null, null);
    assertFalse(filter.hasValidDates());
    filter = new HotelRoomFilter(LocalDate.now().minusDays(2), tomorrow, null, null, null);
    assertFalse(filter.hasValidDates());

    amenities = new HashMap<>();
    amenities.put(Amenity.Bathtub, true);
    amenities.put(Amenity.Fridge, true);
    filter = new HotelRoomFilter(today, tomorrow, type, floor, amenities);
    assertEquals(today, filter.getStartDate());
    assertEquals(tomorrow, filter.getEndDate());
    assertEquals(type, filter.getRoomType());
  }

  @Test
  public void testPredicate() {
    HotelRoom room = mock(HotelRoom.class);
    when(room.isAvailable(today, tomorrow)).thenReturn(false);
    filter = new HotelRoomFilter(tomorrow, today, type, floor, amenities);
    assertFalse(filter.test(room));
    filter = new HotelRoomFilter(today, today, type, floor, amenities);
    assertFalse(filter.test(room));
    when(room.isAvailable(today, tomorrow)).thenReturn(true);
    when(room.getRoomType()).thenReturn(HotelRoomType.Single);
    assertFalse(filter.test(room));
    when(room.getRoomType()).thenReturn(HotelRoomType.Double);
    when(room.getFloor()).thenReturn(1);
    assertFalse(filter.test(room));

    when(room.getFloor()).thenReturn(2);
    when(room.hasAmenity(Amenity.Bathtub)).thenReturn(true);
    when(room.hasAmenity(Amenity.Fridge)).thenReturn(false);
    assertFalse(filter.test(room));
    when(room.hasAmenity(Amenity.Fridge)).thenReturn(true);
    assertTrue(filter.test(room));
    filter = new HotelRoomFilter(null, null, null, null, null);
    assertTrue(filter.test(room));
  }
}
