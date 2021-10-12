package gr2116.core;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.Collectors;

public class Person {
  private static final Random random = new Random();
  private final Collection<PersonListener> listeners = new HashSet<>();
  private final Collection<Reservation> reservations = new HashSet<>();
  private final String name;
  private String email;
  private double balance = 0;

  public Person(final String name) {
    if (name == null) {
      throw new NullPointerException();
    }
    if (!isValidName(name)) {
      throw new IllegalArgumentException("The name is not valid");
    }
    this.name = name;
  }

  public final String getName() {
    return name;
  }

  public final String getEmail() {
    return email;
  }

  public final void setEmail(final String email) {
    if (email == null) {
      throw new NullPointerException("Email is null");
    }
    if (!isValidEmail(email)) {
      throw new IllegalArgumentException("The email is not valid");
    }
    this.email = email;
    notifyListeners();
  }

  public static boolean isValidEmail(final String email) {
    String regex = "^[a-zA-Z0-9._-]{2,20}@[a-zA-Z0-9.]{2,20}.(no|com|net|org)$";
    return email.matches(regex);
  }

  public static boolean isValidName(final String name) {
    return name.matches("([A-Za-z]+.* *)+");
  }

  public final double getBalance() {
    return balance;
  }

  public final void addBalance(final double balance) {
    this.balance += balance;
    notifyListeners();
  }

  public final void subtractBalance(final double balance) {
    this.balance -= balance;
    notifyListeners();
  }

  public final void makeReservation(final HotelRoom hotelRoom,
                                    final LocalDate startDate,
                                    final LocalDate endDate) {
    if (hotelRoom == null || startDate == null || endDate == null) {
      throw new NullPointerException();
    }
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException(
        "The startDate cannot be after the endDate.");
    }
    double price = hotelRoom.getPrice(startDate, endDate);
    if (price > getBalance()) {
      throw new IllegalStateException(
        "The person cannot afford this reservation.");
    }
    if (!hotelRoom.isAvailable(startDate, endDate)) {
      throw new IllegalStateException(
        "The room is not available at this time.");
    }
    Reservation reservation = new Reservation(
        Math.abs(random.nextLong()), hotelRoom, startDate, endDate);
    hotelRoom.addReservation(reservation);
    addReservation(reservation);
    subtractBalance(price);
  }

  public final void addReservation(final Reservation reservation) {
    if (reservation == null) {
      throw new NullPointerException();
    }
    reservations.add(reservation);
    notifyListeners();
  }

  public final Collection<Long> getReservationIds() {
    return reservations.stream()
      .map((r) -> r.getId())
      .collect(Collectors.toList());
  }

  // public void removeReservation(Reservation reservation) {
  // if (!reservations.contains(reservation)) {
  // throw new IllegalArgumentException();
  // }
  // reservations.remove(reservation);
  // }

  public final boolean hasReservation(final Reservation reservation) {
    return reservations.contains(reservation);
  }

  public final Collection<Reservation> getReservations() {
    return new HashSet<>(reservations);
  }

  public final void addListener(final PersonListener listener) {
    listeners.add(listener);
  }

  public final void removeListener(final PersonListener listener) {
    listeners.remove(listener);
  }

  public final void notifyListeners() {
    for (PersonListener listener : listeners) {
      listener.receiveNotification(this);
    }
  }
}
