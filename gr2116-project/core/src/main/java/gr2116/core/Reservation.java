package gr2116.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class Reservation implements Iterable<LocalDate> {
  /**
   * The reservation's id
   */
  private final long id;

   /**
   * The reservation's room
   */
  private final HotelRoom room;

   /**
   * The reservation's start date
   */
  private final LocalDate startDate;

   /**
   * The reservation's end date
   */
  private final LocalDate endDate;

   /**
   * Construtcts a reservation with given id, room, startDate and endDate
   * @param id the given id
   * @param room the given room
   * @param startDate the given start date
   * @param endDate the given end date
   */
  public Reservation(final long id,
                      final HotelRoom room,
                      final LocalDate startDate,
                      final LocalDate endDate) {
    if (startDate == null || endDate == null) {
      throw new NullPointerException();
    }
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("startDate cannot be after endDate.");
    }
    if (room == null) {
      throw new NullPointerException("Rooms is null.");
    }
    this.room = room;
    this.startDate = startDate;
    this.endDate = endDate;
    this.id = id;
  }

   /**
   * Returns the reservation's room
   * @return room
   */
  public final HotelRoom getRoom() {
    return room;
  }

   /**
   * Returns reservation's start date
   * @return start date
   */
  public final LocalDate getStartDate() {
    return startDate;
  }

   /**
   * Returns reservation's end date
   * @return end date
   */
  public final LocalDate getEndDate() {
    return endDate;
  }

  /**
   * Returns reservation's id
   * @return id
   */
  public final long getId() {
    return id;
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
}
