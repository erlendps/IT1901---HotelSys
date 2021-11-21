package gr2116.core;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.function.Predicate;

/**
 * Component that lets you filter on hotelRoom objects.
 */
public class HotelRoomFilter implements Predicate<HotelRoom> {
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final HotelRoomType roomType;
  private final HashMap<Amenity, Boolean> amenities;
  private final Integer floor;

  /**
   * Constructor for this component.
   *
   * @param startDate the startDate you are filtering on.
   * @param endDate the endDate you are filtering on.
   * @param roomType  the roomType you are filtering on.
   * @param floor the floor number to filter on.
   * @param amenities the amenities you are filerting on.
   */
  public HotelRoomFilter(
      final LocalDate startDate,
      final LocalDate endDate,
      final HotelRoomType roomType,
      final Integer floor,
      final HashMap<Amenity, Boolean> amenities) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.roomType = roomType;
    this.floor = floor;
    if (amenities != null) {
      this.amenities = new HashMap<>(amenities);
    } else {
      this.amenities = null;
    }
  }

  /**
   * Method that checks if the user has chosen a valid time period.
   *
   * @return {@code true} if the period is valid, {@code false} otherwise.
   */
  public final boolean hasValidDates() {
    if (startDate == null || endDate == null) {
      return false;
    }
    if (!startDate.isBefore(endDate)) {
      return false;
    }
    if (startDate.isBefore(LocalDate.now())) {
      return false;
    }
    return true;
  }

  /**
   * Returns the start date of this filter.
   *
   * @return startDate
   */
  public final LocalDate getStartDate() {
    return startDate;
  }

  /**
   * Returns the end date of this filter.
   *
   * @return endDate
   */
  public final LocalDate getEndDate() {
    return endDate;
  }

  /**
   * Returns the roomType for this filter.
   *
   * @return roomType
   */
  public final HotelRoomType getRoomType() {
    return roomType;
  }

  
  /**
   * Tests if a hotel room matches this filter.
   *
   * @param room the room to be tested.
   *
   * @return whether or not the hotel room matches the filter
   */
  @Override
  public boolean test(HotelRoom room) {
    if (hasValidDates() && !room.isAvailable(startDate, endDate)) {
      return false;
    }
    if (roomType != null && room.getRoomType() != roomType) {
      return false;
    }
    if (floor != null && room.getFloor() != floor) {
      return false;
    }
    if (amenities != null) {
      for (Entry<Amenity, Boolean> entry : amenities.entrySet()) {
        if (amenities.get(entry.getKey()) && !room.hasAmenity(entry.getKey())) {
          return false;
        }
      }
    }
    return true;
  }
}
