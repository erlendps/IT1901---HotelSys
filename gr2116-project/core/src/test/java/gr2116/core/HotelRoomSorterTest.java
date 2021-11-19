package gr2116.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gr2116.core.HotelRoomSorter.SortProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests HotelRoomSorter.
 */
public class HotelRoomSorterTest {
  private HotelRoomSorter hotelRoomSorter = new HotelRoomSorter();
  private Collection<HotelRoom> hotelRooms = new ArrayList<>();
  private HotelRoom room0;
  private HotelRoom room1;
  private HotelRoom room2;

  /**
   * Setup for the tests.
   * Creates mock HotelRooms and adds to hotelRooms collection.
   */
  @BeforeEach
  public void setup() {
    room0 = mock(HotelRoom.class);
    when(room0.getPrice()).thenReturn(300.0);
    when(room0.getNumber()).thenReturn(101);
    when(room0.getAmenities()).thenReturn(
        Arrays.asList(Amenity.Shower.getName(), Amenity.Bathtub.getName()));
    hotelRooms.add(room0);
    room1 = mock(HotelRoom.class);
    when(room1.getPrice()).thenReturn(200.0);
    when(room1.getNumber()).thenReturn(102);
    when(room1.getAmenities()).thenReturn(Arrays.asList(
        Amenity.Shower.getName(), Amenity.Bathtub.getName(), Amenity.Internet.getName()));
    hotelRooms.add(room1);
    room2 = mock(HotelRoom.class);
    when(room2.getPrice()).thenReturn(100.0);
    when(room2.getNumber()).thenReturn(103);
    when(room2.getAmenities()).thenReturn(
        Arrays.asList(Amenity.Shower.getName()));
    hotelRooms.add(room2);
  }

  @Test
  public void testDefaultIsSortByRoomNumber() {
    List<HotelRoom> sorted = hotelRoomSorter.sortRooms(hotelRooms);
    assertEquals(sorted.get(0), room0);
    assertEquals(sorted.get(1), room1);
    assertEquals(sorted.get(2), room2);
  }

  @Test
  public void testSortByRoomNumber() {
    hotelRoomSorter.setSortProperty(SortProperty.ByRoomNumber);
    List<HotelRoom> sorted = hotelRoomSorter.sortRooms(hotelRooms);
    assertEquals(sorted.get(0), room0);
    assertEquals(sorted.get(1), room1);
    assertEquals(sorted.get(2), room2);
  }

  @Test
  public void testSortByRoomNumberDecreasing() {
    hotelRoomSorter.setSortProperty(SortProperty.ByRoomNumberDecreasing);
    List<HotelRoom> sorted = hotelRoomSorter.sortRooms(hotelRooms);
    assertEquals(sorted.get(0), room2);
    assertEquals(sorted.get(1), room1);
    assertEquals(sorted.get(2), room0);
  }

  @Test
  public void testSortByPrice() {
    hotelRoomSorter.setSortProperty(SortProperty.ByPrice);
    List<HotelRoom> sorted = hotelRoomSorter.sortRooms(hotelRooms);
    assertEquals(sorted.get(0), room2);
    assertEquals(sorted.get(1), room1);
    assertEquals(sorted.get(2), room0);
  }

  @Test
  public void testSortByPriceDecreasing() {
    hotelRoomSorter.setSortProperty(SortProperty.ByPriceDecreasing);
    List<HotelRoom> sorted = hotelRoomSorter.sortRooms(hotelRooms);
    assertEquals(sorted.get(0), room0);
    assertEquals(sorted.get(1), room1);
    assertEquals(sorted.get(2), room2);
  }

  @Test
  public void testSortByAmenityCount() {
    hotelRoomSorter.setSortProperty(SortProperty.ByAmenityCount);
    List<HotelRoom> sorted = hotelRoomSorter.sortRooms(hotelRooms);
    assertEquals(sorted.get(0), room2);
    assertEquals(sorted.get(1), room0);
    assertEquals(sorted.get(2), room1);
  }

  @Test
  public void testSortByAmenityCountDecreasing() {
    hotelRoomSorter.setSortProperty(SortProperty.ByAmenityCountDecreasing);
    List<HotelRoom> sorted = hotelRoomSorter.sortRooms(hotelRooms);
    assertEquals(sorted.get(0), room1);
    assertEquals(sorted.get(1), room0);
    assertEquals(sorted.get(2), room2);
  }
}
