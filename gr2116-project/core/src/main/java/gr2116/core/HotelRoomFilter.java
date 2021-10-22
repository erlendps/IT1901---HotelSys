package gr2116.core;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.function.Predicate;

/**
 * Component that lets you filter on hotelRoom objects.
 */
public class HotelRoomFilter {
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
    this.amenities = amenities;
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
   * Creates a predicate that is used for filtering the rooms based on all the chocies
   * the user has made.
   *
   * @return {@code Predicate<HotelRoom>} that filters based on user choices.
   */
  public final Predicate<HotelRoom> getPredicate() {
    return (room) -> {
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
        for (Amenity amenity : amenities.keySet()) {
          if (amenities.get(amenity) && !room.hasAmenity(amenity)) {
            return false;
          }
        }
      }
      return true;
    };
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
}
