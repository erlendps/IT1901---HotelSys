package gr2116.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Hotel Class.
 */
public class Hotel implements Iterable<HotelRoom> {
  /**
   * The hotel's collection of rooms.
   */
  private final Collection<HotelRoom> rooms = new ArrayList<>();

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
    rooms.forEach((room) -> this.rooms.add(room));
  }

  /**
   * Adds the given room to the hotel.
   *
   * @param room the given room.
   */
  public final boolean addRoom(final HotelRoom room) {
    if (room == null) {
      throw new NullPointerException();
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
   * Returns the rooms of the hotel that matches the given predicate.
   * Used in search.
   *
   * @param predicate the given predicate.
   *
   * @return a collection of rooms.
   */
  public final Collection<HotelRoom> getRooms(
      final Predicate<HotelRoom> predicate) {
    return rooms.stream().filter(predicate).collect(Collectors.toList());
  }

  @Override
  public final Iterator<HotelRoom> iterator() {
    return rooms.iterator();
  }
}
