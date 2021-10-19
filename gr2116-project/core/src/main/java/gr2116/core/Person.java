package gr2116.core;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Person class. A person has a collection of listerners, a collection of
 * reservaitons, a name, an email and a balance.
 */
public class Person {
  /**
   * Collection of PersonListener's listening to this Person object.
   */
  private final Collection<PersonListener> listeners = new HashSet<>();
  /**
   * Collection of Reservation objects that this person has.
   */
  private final Collection<Reservation> reservations = new HashSet<>();
  /**
   * The Person objects name.
   */
  private final String name;
  /**
   * The Person objects email.
   */
  private String email;
  /**
   * The Person objects balance.
   */
  private double balance = 0;

  /**
   * Constructs a Person object with a given name.
   *
   * @param name - {@code String} that represents the persons name.
   *
   * @throws NullPointerException if {@code name} is null.
   * @throws IllegalArgumentException if {@code name} is not valid.
   */
  public Person(final String name) {
    if (name == null) {
      throw new NullPointerException();
    }
    if (!isValidName(name)) {
      throw new IllegalArgumentException("The name is not valid");
    }
    this.name = name;
  }

  /**
   * Returns the name.
   *
   * @return {@code name}
   */
  public final String getName() {
    return name;
  }

  /**
   * Returns the email.
   *
   * @return {@code email} that is associated with this person.
   */
  public final String getEmail() {
    return email;
  }

  /**
   * Sets the email if some conditions are met.
   *
   * @param email - the new email.
   *
   * @throws NullPointerException if {@code email} is null.
   * @throws IllegalArgumentException if {@code email} is not a valid email.
   */
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

  /**
   * Validation method that uses regex to check if the email provided is valid.
   *
   * @param email - the email that will be validated.
   *
   * @return {@code true} if the email is valid, {@code false} otherwise.
   */
  public static boolean isValidEmail(final String email) {
    String regex = "^[a-zA-Z0-9._-]{2,20}@[a-zA-Z0-9.]{2,20}.(no|com|net|org)$";
    return email.matches(regex);
  }

  /**
   * Validation method that uses regex to check if the name provided is valid.
   *
   * @param name - the name that will be validated.
   *
   * @return {@code true} if the name is valid, {@code false} otherwise.
   */
  public static boolean isValidName(final String name) {
    return name.matches("([A-Za-z]+.* *)+");
  }

  /**
   * Returns the balance of this Person object.
   *
   * @return {@code balance} of this person.
   */
  public final double getBalance() {
    return balance;
  }

  /**
   * Adds the specified {@code balance} to this Peron objects balance field.
   *
   * @param balance - balance (the amount) to be added.
   *
   * @throws IllegalArgumentException if {@code balance} is below 0.
   */
  public final void addBalance(final double balance) {
    if (balance < 0) {
      throw new IllegalArgumentException("Balance must be positive.");
    }
    this.balance += balance;
    notifyListeners();
  }

  /**
   * Subtracts the specified {@code balance} from this Peron objects balance field.
   *
   * @param balance - balance (the amount) to be subtracted.
   *
   * @throws IllegalArgumentException if {@code balance} is below 0.
   */
  public final void subtractBalance(final double balance) {
    if (balance < 0) {
      throw new IllegalArgumentException("Balance must be positive.");
    }
    this.balance -= balance;
    notifyListeners();
  }

  /**
   * <p>
   * Makes a reservation on the specified {@code hotelRoom}, starting from 
   * {@code startDate} and ending on {@code endDate}. makeReservation() does
   * a series of validations to ensure that the Person object e.g does not 
   * book a room that is occupied. 
   * </p>
   * <p>
   * If everything is valid, the method creates a new Reservation object with
   * a (pseudorandom) id, the given {@code hotelRoom} and start/endDate.
   * It then adds the reservation in {@code hotelRoom} collection of reservations,
   * and then it adds the reservation in this Person objects reservation collection.
   * Finally it subtracs the price of the booking.
   * </p>
   *
   * @param hotelRoom - the room the Person object wants to book.
   * @param startDate - {@code LocalDate} of when the reservation should start.
   * @param endDate - {@code LocalDate} of when the reservation should end.
   *
   * @throws NullPointerException if hotelRoom, startDate or endDate is null.
   * @throws IllegalArgumentException if startDate is chronologically after endDate.
   * @throws IllegalStateException  if the {@code Person} does not have enough balance
   *                                to pay for the reservation.
   * @throws IllegalStateException  if hotelRoom is unavailable, e.g already booked, in
   *                                some period between startDate and endDate. 
   */
  public final void makeReservation(final HotelRoom hotelRoom,
                                    final LocalDate startDate,
                                    final LocalDate endDate) {
    if (hotelRoom == null || startDate == null || endDate == null) {
      throw new NullPointerException();
    }
    if (startDate.isBefore(LocalDate.now())) {
      throw new IllegalStateException("Cant make a reservation backwards in time.");
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

    Reservation reservation = new Reservation(hotelRoom, startDate, endDate);
    hotelRoom.addReservation(reservation);
    addReservation(reservation);
    subtractBalance(price);
  }

  /**
   * Adds the given reservation to the Person objects collection of reservations and 
   * notifies listeners.
   *
   * @param reservation - the {@code Reservation} to be added.
   *
   * @throws NullPointerException if reservation is null.
   */
  public final void addReservation(final Reservation reservation) {
    if (reservation == null) {
      throw new NullPointerException();
    }
    reservations.add(reservation);
    notifyListeners();
  }

  /**
   * Returns a Collection of the reservation IDs for this Person objects. E.g all
   * reservation IDs that belong to this Person. Needs to be sorted in increasing order.
   *
   * @return {@code Collection<Long>} of reservation IDs.
   */
  public final Collection<Long> getReservationIds() {
    List<Long> ids = reservations.stream()
      .map((r) -> r.getId())
      .collect(Collectors.toList());
    Collections.sort(ids);
    return ids;
  }

  /**
   * Returns a boolean value depending on if the Person has made the given reservation.
   *
   * @param reservation - the {@code Reservation} to be checked.
   *
   * @return {@code true} if this Person has made the reservation, {@code false} otherwise.
   */
  public final boolean hasReservation(final Reservation reservation) {
    return reservations.contains(reservation);
  }

  /**
   * Returns a Collection of the reservations this Person has made.
   *
   * @return {@code Collection<Reservation>} of the reservations.
   */
  public final Collection<Reservation> getReservations() {
    return new HashSet<>(reservations);
  }

  /**
   * Adds a {@code PersonListener} to Person listeners collection.
   *
   * @param listener - the listener that needs to listen to Person.
   */
  public final void addListener(final PersonListener listener) {
    listeners.add(listener);
  }

  /**
   * Removes the listener stated from the Person objects listeners Collection.
   *
   * @param listener - the listener to be removed.
   */
  public final void removeListener(final PersonListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notifies all listeners that is listening to this Person object.
   */
  public final void notifyListeners() {
    for (PersonListener listener : listeners) {
      listener.receiveNotification(this);
    }
  }
}
