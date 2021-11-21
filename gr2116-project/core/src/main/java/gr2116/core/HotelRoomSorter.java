package gr2116.core;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Sorts a collection of Hotelrooms.
 */
public class HotelRoomSorter {
  private SortProperty sortProperty = SortProperty.ByRoomNumber;

  /**
   * Used for selecting what property to sort by when sorting rooms.
   */
  public enum SortProperty {
    ByPrice((HotelRoom roomA, HotelRoom roomB) -> {
      return roomA.getPrice() < roomB.getPrice() ? -1 : 1;
    }),
    ByPriceDecreasing((HotelRoom roomA, HotelRoom roomB) -> {
      return roomA.getPrice() > roomB.getPrice() ? -1 : 1;
    }),
    ByRoomNumber((HotelRoom roomA, HotelRoom roomB) -> {
      return roomA.getNumber() < roomB.getNumber() ? -1 : 1;
    }),
    ByRoomNumberDecreasing((HotelRoom roomA, HotelRoom roomB) -> {
      return roomA.getNumber() > roomB.getNumber() ? -1 : 1;
    }),
    ByAmenityCount((HotelRoom roomA, HotelRoom roomB) -> {
      return roomA.getAmenities().size() < roomB.getAmenities().size() ? -1 : 1;
    }),
    ByAmenityCountDecreasing((HotelRoom roomA, HotelRoom roomB) -> {
      return roomA.getAmenities().size() > roomB.getAmenities().size() ? -1 : 1;
    });

    private Comparator<HotelRoom> comparator;

    private SortProperty(Comparator<HotelRoom> comparator) {
      this.comparator = comparator;
    }

    public Comparator<HotelRoom> getComparator() {
      return comparator;
    }
  }

  /**
   * Sorts hotelRooms on this sortProperty's comparator.
   *
   * @param hotelRooms the collection to sort
   *
   * @return the sorted collection
   */
  public List<HotelRoom> sortRooms(Collection<HotelRoom> hotelRooms) {
    return hotelRooms.stream().sorted(sortProperty.getComparator()).toList();
  }

  /**
   * Returns the current sort property.
   *
   * @param sortProperty the current sort property
   *
   * @throws IllegalArgumentException if sortProperty is null
   */
  public void setSortProperty(SortProperty sortProperty) {
    if (sortProperty == null) {
      throw new IllegalArgumentException("Sort property cannot be null!");
    }
    this.sortProperty = sortProperty;
  }

  /**
   * Returns the sort property
   * @return this.sortProperty
   */
  public SortProperty getSortProperty() {
    return sortProperty;
  }
}
