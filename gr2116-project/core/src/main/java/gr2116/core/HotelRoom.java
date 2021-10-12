package gr2116.core;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class HotelRoom {
  private final Collection<Amenity> amenities = new HashSet<Amenity>();
  private final ReservationCalendar calendar = new ReservationCalendar();
  private final HotelRoomType roomType;
  private final int number;
  private double price;

  public HotelRoom(final HotelRoomType roomType, final int number) {
    if (roomType == null) {
      throw new NullPointerException();
    }
    this.roomType = roomType;
    this.number = number;
  }

  public final HotelRoomType getRoomType() {
    return roomType;
  }

  public final int getFloor() {
    // Gets which floor the room is on,
    // which is specified by the first digit of the room number.
    return Integer.parseInt(Integer.toString(number).substring(0, 1));
  }

  public final int getNumber() {
    return number;
  }

  public final double getPrice() {
    return price;
  }

  public final double getPrice(final LocalDate startDate,
      final LocalDate endDate) {
    verifyChronology(startDate, endDate);
    return price * (startDate.until(endDate).getDays());
  }

  public final void setPrice(final double price) {
    this.price = price;
  }

  public final void addAmenity(final Amenity amenity) {
    if (amenity == null) {
      throw new NullPointerException();
    }
    amenities.add(amenity);
  }

  public final Collection<String> getAmenities() {
    return amenities.stream().map((a) -> a.name()).collect(Collectors.toList());
  }

  public final void removeAmenity(final Amenity amenity) {
    if (!amenities.contains(amenity)) {
      throw new IllegalArgumentException();
    }
    amenities.remove(amenity);
  }

  public final boolean hasAmenity(final Amenity amenity) {
    return amenities.contains(amenity);
  }

  public final boolean isAvailable(final LocalDate date) {
    return calendar.isAvailable(date);
  }

  public final boolean isAvailable(final LocalDate startDate,
                                    final LocalDate endDate) {
    verifyChronology(startDate, endDate);
    return calendar.isAvailable(startDate, endDate);
  }

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

  public final Collection<Long> getReservationIds() {
    return calendar.getReservationIds();
  }

  private void verifyChronology(final LocalDate startDate,
      final LocalDate endDate) {
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException(
        "The startDate cannot be after the endDate.");
    }
  }
}
