package gr2116.core;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Collectors;

public class ReservationCalendar implements Iterable<Reservation> {
  /**
   * The reservation calander's collection of reservations.
   */
  private Collection<Reservation> reservations = new HashSet<>();

  /**
   * Add given reservation to the reservation calandar.
   * @param reservation the given reservation.
   */
  public final void addReservation(final Reservation reservation) {
    if (reservation == null) {
      throw new NullPointerException("Reservation can not be null.");
    }
    if (!isAvailable(reservation.getStartDate(), reservation.getEndDate())) {
      throw new IllegalStateException(
        "The room is not available at this time.");
    }
    reservations.add(reservation);
  }

  /**
   * Returns collection of ids of reservation caladar's reservations.
   * @return stream of reservation ids.
   */
  public final Collection<Long> getReservationIds() {
    return reservations.stream()
      .map((r) -> r.getId())
      .collect(Collectors.toList());
  }

  /**
   * Returns wether or not the room is available on given date.
   * @param date given date to check if room id available.
   * @return true if there are no reservations on given date, false otherwise.
   */
  public final boolean isAvailable(final LocalDate date) {
    return reservations.stream().noneMatch((reservation) -> {
      LocalDate start = reservation.getStartDate();
      LocalDate end = reservation.getEndDate();
      return date.isEqual(start)
        || date.isEqual(end)
        || date.isAfter(start) && date.isBefore(end);
    });
  }

  /**
   * Returns wether or not the room is available in given time period.
   * @param startDate given start date.
   * @param endDate given end date.
   * @return true if there are no reservations in given time period, false otherwise.
   */
  public final boolean isAvailable(final LocalDate startDate,
                                    final LocalDate endDate) {
    LocalDate date = startDate;
    while (date.isBefore(endDate) || date.isEqual(endDate)) {
      if (!isAvailable(date)) {
        return false;
      }
      date = date.plusDays(1);
    }
    return true;
  }

  @Override
  public final Iterator<Reservation> iterator() {
    return reservations.iterator();
  }
}
