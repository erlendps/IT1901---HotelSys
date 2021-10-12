package gr2116.core;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Collectors;

public class ReservationCalendar implements Iterable<Reservation> {
  private Collection<Reservation> reservations = new HashSet<>();

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

  // public void removeReservation(Reservation reservation) {
  // if (!reservations.contains(reservation)) {
  // throw new IllegalArgumentException();
  // }
  // reservations.remove(reservation);
  // }

  public final Collection<Long> getReservationIds() {
    return reservations.stream()
      .map((r) -> r.getId())
      .collect(Collectors.toList());
  }

  public final boolean isAvailable(final LocalDate date) {
    return reservations.stream().noneMatch((reservation) -> {
      LocalDate start = reservation.getStartDate();
      LocalDate end = reservation.getEndDate();
      return date.isEqual(start)
        || date.isEqual(end)
        || date.isAfter(start) && date.isBefore(end);
    });
  }

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
