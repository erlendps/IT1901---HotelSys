package gr2116.core;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.Random;

public class Person {
  private final Collection<PersonListener> listeners = new HashSet<>(); 
  private final Collection<Reservation> reservations = new HashSet<>();
  private final String name;
  private String email;
  private double balance = 0;

  public Person(String name) {
    if (name == null) {
      throw new NullPointerException();
    }
    if (!isValidName(name)) {
      throw new IllegalArgumentException("The name is not valid");
    }
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    if (email == null) {
      throw new NullPointerException("Email is null");
    }
    if (!isValidEmail(email)){
      throw new IllegalArgumentException("The email is not valid");
    }
    this.email = email;
    notifyListeners();
  }

  public static boolean isValidEmail(String email) {
    String regex = "^[a-zA-Z0-9._-]{2,20}@[a-zA-Z0-9.]{2,20}.(no|com|net|org)$";
    return email.matches(regex);
  }

  public static boolean isValidName(String name) {
    return name.matches("([A-Za-z]+.* *)+");
  }

  public double getBalance() {
    return balance;
  }

  public void addBalance(double balance) {
    this.balance += balance;
    notifyListeners();
  }

  public void subtractBalance(double balance) {
    this.balance -= balance;
    notifyListeners();
  }

  public void makeReservation(HotelRoom hotelRoom, LocalDate startDate, LocalDate endDate) {
    if (hotelRoom == null || startDate == null || endDate == null) {
      throw new NullPointerException();
    }
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("The startDate cannot be after the endDate.");
    }
    double price = hotelRoom.getPrice(startDate, endDate);
    if (price > getBalance()) {
      throw new IllegalStateException("The person cannot afford this reservation.");
    }
    if (!hotelRoom.isAvailable(startDate, endDate)) {
      throw new IllegalStateException("The room is not available at this time.");
    }
    Random random = new Random();
    Reservation reservation = new Reservation(Math.abs(random.nextLong()), hotelRoom, startDate, endDate);
    hotelRoom.addReservation(reservation);
    addReservation(reservation);
    subtractBalance(price);
  }

  public void addReservation(Reservation reservation) {
    if (reservation == null) {
      throw new NullPointerException();
    }
    reservations.add(reservation);
    notifyListeners();
  }

  public Collection<Long> getReservationIds() {
    return reservations.stream().map((r) -> r.getId()).collect(Collectors.toList());
  }

  // public void removeReservation(Reservation reservation) {
  //   if (!reservations.contains(reservation)) {
  //     throw new IllegalArgumentException();
  //   }
  //   reservations.remove(reservation);
  // }

  public boolean hasReservation(Reservation reservation) {
    return reservations.contains(reservation);
  }

  public Collection<Reservation> getReservations() {
    return new HashSet<>(reservations);
  }

  public void addListener(PersonListener listener) {
    listeners.add(listener);
  }
  public void removeListener(PersonListener listener) {
    listeners.remove(listener);
  }
  public void notifyListeners() {
    for (PersonListener listener : listeners) {
      listener.receiveNotification(this);
    }
  }
}
