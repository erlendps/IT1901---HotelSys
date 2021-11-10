package gr2116.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 * Hotel Class.
 */
public class Hotel implements Iterable<HotelRoom> {
  /**
   * The hotel's collection of rooms.
   */
  private final Collection<HotelRoom> rooms = new ArrayList<>();

  /**
   * The hotel's collection of Persons
   */
  private final Collection<Person> persons = new ArrayList<>(); 

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
    rooms.forEach(this.rooms::add);
  }

  /**
   * Constructs a hotel with a collection of rooms and persons.
   *
   * @param rooms a collection of rooms
   * @param persons a colletion of persons
   */
  public Hotel(final Collection<HotelRoom> rooms, final Collection<Person> persons) {
    this(rooms);
    persons.forEach((person) -> this.persons.add(person));
  }

  /**
   * Adds the given room to the hotel.
   *
   * @param room the given room.
   * 
   * @throws IllegalArgumentException if room is null
   */
  public final boolean addRoom(final HotelRoom room) {
    if (room == null) {
      throw new IllegalArgumentException("Room cannot be null");
    }
    if (rooms.contains(room)) {
      System.out.println(
          "[Warning]: Tried to add a room that was already added.");
      return false;
    } else {
      rooms.add(room);
      return true;
    }
  }

  /**
   * Removes the given room from the hotel.
   *
   * @param room the given room.
   */
  public final void removeRoom(final HotelRoom room) {
    rooms.remove(room);
  }

  /**
   * Adds a new person (user) to the Hotel.
   *
   * @param person the person to be added
   *
   * @return true if person was added, false otherwise
   *
   * @throws IllegalArgumentException if person is null
   */
  public final boolean addPerson(final Person person) {
    if (person == null) {
      throw new IllegalArgumentException("Person cant be null.");
    }
    if (persons.contains(person)) {
      System.out.println(
        "[Warning]: Tried to add a person that was already added.");
      return false;
    }
    persons.add(person);
    return true;
  }

  /**
   * Removes (deletes) the persons from the Hotel.
   *
   * @param person person to be removed from the hotel.
   */
  public final void removePerson(final Person person) {
    persons.remove(person);
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
    return rooms.stream().filter(predicate).toList();
  }

  public final Collection<HotelRoom> getRooms() {
    return new ArrayList<>(rooms);
  }

  /**
   * Returns the collection of persons connected to the hotel.
   *
   * @return new collection of the persons
   */
  public final Collection<Person> getPersons() {
    return new ArrayList<>(persons);
  }

  @Override
  public final Iterator<HotelRoom> iterator() {
    return rooms.iterator();
  }
}
