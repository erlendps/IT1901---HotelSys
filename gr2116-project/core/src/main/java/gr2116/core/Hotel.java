package gr2116.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Hotel Class.
 */
public class Hotel implements Iterable<HotelRoom> {
  /**
   * The hotel's collection of rooms.
   */
  private final Map<String, HotelRoom> rooms = new LinkedHashMap<>();

  /**
   * The hotel's collection of Persons.
   */
  private final Map<String, Person> persons = new LinkedHashMap<>(); 

  /**
   * Constructs an empty hotel.
   */
  public Hotel() {

  }

  /**
   * Constructs a hotel with a collection of rooms.
   *
   * @param rooms a collection of rooms.
   */
  public Hotel(final Collection<HotelRoom> rooms) {
    rooms.forEach((room) -> this.rooms.put(String.valueOf(room.getNumber()), room));
  }

  /**
   * Constructs a hotel with a collection of rooms and persons.
   *
   * @param rooms a collection of rooms
   * @param persons a colletion of persons
   */
  public Hotel(final Collection<HotelRoom> rooms, final Collection<Person> persons) {
    this(rooms);
    persons.forEach((person) -> this.persons.put(person.getUsername(), person));
  }

  /**
   * Adds the given room to the hotel.
   *
   * @param room the HotelRoom to add
   * 
   * @throws IllegalArgumentException if room is null
   */
  public final HotelRoom addRoom(final HotelRoom room) {
    if (room == null) {
      throw new IllegalArgumentException("Room cannot be null");
    }
    return rooms.put(String.valueOf(room.getNumber()), room);
  }

  /**
   * Removes the given room from the hotel.
   *
   * @param room the HotelRoom to remove
   */
  public final void removeRoom(final HotelRoom room) {
    rooms.remove(String.valueOf(room.getNumber()));
  }

  /**
   * Adds a new person (user) to the Hotel.
   *
   * @param person the Person to be added
   *
   * @return true if person was added, false otherwise
   *
   * @throws IllegalArgumentException if person is null
   */
  public final Person addPerson(final Person person) {
    if (person == null) {
      throw new IllegalArgumentException("Person cant be null.");
    }
    return persons.put(person.getUsername(), person);
  }

  /**
   * Removes (deletes) the persons from the Hotel.
   *
   * @param person person to be removed from the hotel.
   */
  public final void removePerson(final Person person) {
    persons.remove(person.getUsername());
  }

  /**
   * Returns the rooms of the hotel that matches the given predicate.
   * Used in search.
   *
   * @param predicate the given predicate.
   *
   * @return a collection of rooms.
   */
  public final Collection<HotelRoom> getRooms(
      final Predicate<HotelRoom> predicate) {
    return getRooms().stream().filter(predicate).toList();
  }

  public final Collection<HotelRoom> getRooms() {
    return new ArrayList<>(rooms.values());
  }

  /**
   * Returns the collection of persons connected to the hotel.
   *
   * @return new collection of the persons
   */
  public final Collection<Person> getPersons() {
    return new ArrayList<>(persons.values());
  }

  public final Collection<Person> getPersons(Predicate<Person> pred) {
    return getPersons().stream().filter(pred).toList();
  }
  
  /**
   * <p>
   * Makes a reservation on the room with the specified {@code hotelRoomNumber}, starting from 
   * {@code startDate} and ending on {@code endDate}. makeReservation() does
   * a series of validations to ensure that the Person object e.g does not 
   * book a room that is occupied. 
   * </p>
   * <p>
   * If everything is valid, the method creates a new Reservation object with
   * a (pseudorandom) id, the room with the given {@code hotelRoomNumber} and start/endDate.
   * It then adds the reservation in the hotelroom
   * with {@code hotelRoomNumber} collection of reservations,
   * and then it adds the reservation in this Person objects reservation collection.
   * Finally it subtracs the price of the booking.
   * </p>
   *
   * @param person - the person to make the reservation
   * @param hotelRoomNumber - the room number of the room that the Person object wants to book.
   * @param startDate - {@code LocalDate} of when the reservation should start.
   * @param endDate - {@code LocalDate} of when the reservation should end.
   *
   * @throws IllegalArgumentException if startDate or endDate is null.
   * @throws IllegalStateException if the start date is before today.
   * @throws IllegalArgumentException if startDate is chronologically after endDate
   * @throws IllegalArgumentException if the hotel room number is not a room in the hotel.
   * @throws IllegalStateException  if the {@code Person} does not have enough balance
   *                                to pay for the reservation.
   * @throws IllegalStateException  if hotelRoom is unavailable, e.g already booked, in
   *                                some period between startDate and endDate. 
   */
  public final void makeReservation(final Person person,
                                    final int hotelRoomNumber,
                                    final LocalDate startDate,
                                    final LocalDate endDate) {
    if (person == null || startDate == null || endDate == null) {
      throw new IllegalArgumentException();
    }
    if (!getPersons().contains(person)) {
      throw new IllegalArgumentException("Person is not a user.");
    }
    if (startDate.isBefore(LocalDate.now())) {
      throw new IllegalStateException("Cant make a reservation backwards in time.");
    }
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException(
        "The startDate cannot be after the endDate.");
    }
    Collection<HotelRoom> roomMatches = getRooms((r) -> r.getNumber() == hotelRoomNumber);
    if (roomMatches.size() == 0) {
      throw new IllegalArgumentException(
        "The specified room number is not the number of a room in the hotel.");
    }

    HotelRoom hotelRoom = roomMatches.iterator().next();
    double price = hotelRoom.getPrice(startDate, endDate);
    if (price > person.getBalance()) {
      throw new IllegalStateException(
        "The person cannot afford this reservation.");
    }
    if (!hotelRoom.isAvailable(startDate, endDate)) {
      throw new IllegalStateException(
        "The room is not available at this time.");
    }

    Reservation reservation = new Reservation(hotelRoom, startDate, endDate);
    hotelRoom.addReservation(reservation);
    person.addReservation(reservation);
    person.subtractBalance(price);
  }

  @Override
  public final Iterator<HotelRoom> iterator() {
    return getRooms().iterator();
  }
}
