package gr2116.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Reservation class. Implements Iterable.
 * Has an id (pseudorandom when Reservation is generated), a HotelRoom relation,
 * a startDate and an endDate.
 */
public class Reservation implements Iterable<LocalDate> {
  /**
   * The reservation's id.
   */
  private final String id;
  /**
   * The reservation's room.
   */
  private final int roomNumber;
  /**
   * The reservation's start date.
   */
  private final LocalDate startDate;
  /**
   * The reservation's end date.
   */
  private final LocalDate endDate;

  /**
   * Constructs a reservation with given room, startDate and endDate.
   * The reservation ID is generated.
   *
   * @param room the given room.
   * @param startDate the given start date.
   * @param endDate the given end date.
   *
   * @throws IllegalArgumentException if startDate or endDate is null
   * @throws IllegalArgumentException if startDate is after endDate
   * @throws IllegalArgumentException if room is null
   */
  public Reservation(final HotelRoom room,
                      final LocalDate startDate,
                      final LocalDate endDate) {
    if (startDate == null || endDate == null) {
      throw new IllegalArgumentException();
    }
    if (!startDate.isBefore(endDate)) {
      throw new IllegalArgumentException("startDate must be before endDate.");
    }
    if (room == null) {
      throw new IllegalArgumentException("Rooms cannot be null.");
    }
    this.roomNumber = room.getNumber();
    this.startDate = startDate;
    this.endDate = endDate;
    this.id = generateId();
  }

  /**
   * Returns the reservation's room.
   *
   * @return room
   */
  public final int getRoomNumber() {
    return roomNumber;
  }

  /**
   * Returns reservation's start date.
   *
   * @return start date
   */
  public final LocalDate getStartDate() {
    return startDate;
  }

  /**
   * Returns reservation's end date.
   *
   * @return end date
   */
  public final LocalDate getEndDate() {
    return endDate;
  }

  /**
   * Returns reservation's id.
   *
   * @return id
   */
  public final String getId() {
    return id;
  }

  /**
   * Generates a reservation ID based on the reservations room,
   * startDate and endDate.
   *
   * @return {@code long} the id
   */
  private final String generateId() {
    StringBuilder sb = new StringBuilder();
    sb.append(getRoomNumber());
    sb.append(startDate.toString().replace("-", ""));
    sb.append(endDate.toString().replace("-", ""));

    return sb.toString();
  }

  @Override
  public final Iterator<LocalDate> iterator() {
    ArrayList<LocalDate> dates = new ArrayList<>();
    LocalDate date = startDate;
    while (date.isBefore(endDate)) {
      dates.add(date);
      date = date.plusDays(1);
    }
    dates.add(endDate);
    return dates.iterator();
  }

  @Override
  public String toString() {
    return getId();
  }

  /**
   * Custom implementation of .equals method.
   *
   * @param o object to test against
   *
   * @return true if o and this are the same, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }
    Reservation res = (Reservation) o;
    return getId().equals(res.getId());
  }

  /**
   * Custom implementation of hashCode method. See
   * https://www.technofundo.com/tech/java/equalhash.html on why we did this.
   *
   * @return the hashCode for this Reservation
   */
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + getRoomNumber();
    hash = 31 * hash + (null == getStartDate() ? 0 : getStartDate().hashCode());
    return hash;
  }
}
