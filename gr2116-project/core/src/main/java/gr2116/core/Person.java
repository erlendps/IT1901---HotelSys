package gr2116.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Person class. A person has a collection of listerners, a collection of
 * reservaitons, a name, an username and a balance.
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
   * The Person objects username.
   */
  private String username;
  /**
   * The Person objects balance.
   */
  private double balance = 0;

  /**
   * Constructs a Person object with a given name.
   *
   * @param name - {@code String} that represents the persons name.
   *
   * @throws IllegalArgumentException if {@code name} is null.
   * @throws IllegalArgumentException if {@code name} is not valid.
   */
  public Person(final String name) {
    if (name == null) {
      throw new IllegalArgumentException();
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
   * Returns the username.
   *
   * @return {@code username} that is associated with this person.
   */
  public final String getUsername() {
    return username;
  }

  /**
   * Sets the username if some conditions are met and notifies listeners.
   *
   * @param username - the new username.
   *
   * @throws IllegalArgumentException if {@code username} is null.
   * @throws IllegalArgumentException if {@code username} is not a valid username.
   */
  public final void setUsername(final String username) {
    if (username == null) {
      throw new IllegalArgumentException("Username is null");
    }
    if (!isValidUsername(username)) {
      throw new IllegalArgumentException("The username is not valid");
    }
    this.username = username;
    notifyListeners();
  }

  /**
   * Validation method that uses regex to check if the username provided is valid.
   *
   * @param username - the username that will be validated.
   *
   * @return {@code true} if the username is valid, {@code false} otherwise.
   */
  public static boolean isValidUsername(final String username) {
    String regex = "^[a-zA-Z]+$";
    return username.matches(regex);
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
   * Subtracts the specified {@code balance} from this Peron objects balance field
   * and notifies listeners.
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
   * Adds the given reservation to the Person objects collection of reservations and 
   * notifies listeners.
   *
   * @param reservation - the {@code Reservation} to be added.
   *
   * @throws IllegalArgumentException if reservation is null.
   */
  public final void addReservation(final Reservation reservation) {
    if (reservation == null) {
      throw new IllegalArgumentException();
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
  public final Collection<String> getReservationIds() {
    List<String> ids = reservations.stream()
        .map((r) -> r.getId())
        .toList();
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
      listener.onPersonChanged(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o.getClass() != this.getClass() || o == null) {
      return false;
    }
    Person p = (Person) o;
    return this.getName().equals(p.getName()) &&
        this.getUsername().equals(p.getUsername()) &&
        this.getReservations().equals(p.getReservations());
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = hash * 17 + getName().hashCode();
    hash = hash * 31 + (getUsername() == null ? 0 : getUsername().hashCode());
    hash = hash * 5 + getReservations().hashCode();
    return hash;
  }
}
