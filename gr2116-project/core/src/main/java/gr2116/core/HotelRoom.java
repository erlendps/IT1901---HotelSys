package gr2116.core;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class HotelRoom {
  /**
   * A collection of amenities included in the hotel room.
   */
  private final Collection<Amenity> amenities = new HashSet<Amenity>();
  /**
   * A calendar keeping track of when the room is reserved.
   */
  private final ReservationCalendar calendar = new ReservationCalendar();
  /**
   * The type of the room.
   */
  private final HotelRoomType roomType;
  /**
   * The room number.
   * The first digit is the floor.
   */
  private final int number;
  /**
   * The daily price of the room.
   */
  private double price;

  /**
   * Constructs a hotel room, of the given type and with the given room number.
   * @param roomType
   * @param number
   */
  public HotelRoom(final HotelRoomType roomType, final int number) {
    if (roomType == null) {
      throw new NullPointerException();
    }
    this.roomType = roomType;
    this.number = number;
  }

  /**
   * Returns the room type.
   * @return room type
   */
  public final HotelRoomType getRoomType() {
    return roomType;
  }

  /**
   * Returns what floor the room is on.
   * @return floor
   */
  public final int getFloor() {
    return Integer.parseInt(Integer.toString(number).substring(0, 1));
  }

  /**
   * Returns the room number.
   * @return number
   */
  public final int getNumber() {
    return number;
  }

  /**
   * Returns the room daily price.
   * @return price
   */
  public final double getPrice() {
    return price;
  }

  /**
   * Returns the cost of staying from the given
   * start date to the given end date.
   * @param startDate
   * @param endDate
   * @return price
   */
  public final double getPrice(final LocalDate startDate,
      final LocalDate endDate) {
    verifyChronology(startDate, endDate);
    return price * (startDate.until(endDate).getDays());
  }

  /**
   * Sets the daily price.
   * @param price
   */
  public final void setPrice(final double price) {
    this.price = price;
  }

  /**
   * Adds the given amenity to the room's collection of amenities.
   * @param amenity
   */
  public final void addAmenity(final Amenity amenity) {
    if (amenity == null) {
      throw new NullPointerException();
    }
    amenities.add(amenity);
  }

  /**
   * Returns a collection of the names of the room's amenities.
   * @return amenities
   */
  public final Collection<String> getAmenities() {
    return amenities.stream().map((a) -> a.name()).collect(Collectors.toList());
  }

  /**
   * Removes the given amenity to the room's collection of amenities.
   * @param amenity
   */
  public final void removeAmenity(final Amenity amenity) {
    if (!amenities.contains(amenity)) {
      throw new IllegalArgumentException();
    }
    amenities.remove(amenity);
  }

  /**
   * Returns whether or not the room includes the given amenity.
   * @param amenity
   * @return true if the room includes the amenity, false otherwise.
   */
  public final boolean hasAmenity(final Amenity amenity) {
    return amenities.contains(amenity);
  }

  /**
   * Returns whether or not the room is available on the given date.
   * @param date
   * @return true if the room is available, false otherwise.
   */
  public final boolean isAvailable(final LocalDate date) {
    return calendar.isAvailable(date);
  }

  /**
   * Returns whether or not the room is available between the given
   * start date and end date.
   * @param startDate
   * @param endDate
   * @return true if the room is available, false otherwise.
   */
  public final boolean isAvailable(final LocalDate startDate,
                                    final LocalDate endDate) {
    verifyChronology(startDate, endDate);
    return calendar.isAvailable(startDate, endDate);
  }

  /**
   * Adds the given reservation to the room's reservation calendar.
   * @param reservation
   */
  public final void addReservation(final Reservation reservation) {
    if (reservation == null) {
      throw new NullPointerException("Reservation can not be null");
    }

    if (reservation.getRoom() != this) {
      throw new IllegalArgumentException(
        "Reservation on room "
        + Integer.toString(reservation.getRoom().getNumber())
        + " can not be registred on room " + Integer.toString(getNumber()));
    }

    calendar.addReservation(reservation);
  }

  /**
   * Returns a collection of the ids
   * of the reservations in the room's calendar.
   * @return reservation ids
   */
  public final Collection<Long> getReservationIds() {
    return calendar.getReservationIds();
  }

  /**
   * Verifies that the start date is before the end date.
   * Throws and IllegalArgumentException this is not the case.
   * @param startDate
   * @param endDate
   * @throws IllegalArgumentException
   */
  private void verifyChronology(final LocalDate startDate,
      final LocalDate endDate) {
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException(
        "The startDate cannot be after the endDate.");
    }
  }
}
