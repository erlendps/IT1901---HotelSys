package gr2116.ui.access;

import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomFilter;
import gr2116.core.Person;
import gr2116.persistence.HotelPersistence;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

/**
 * An implementation of HotelAccess which uses accesses a Hotel directly,
 * and stores data locally using HotelPersistence.
 */
public class DirectHotelAccess implements HotelAccess {
  private final HotelPersistence hotelPersistence;
  private Hotel hotel;
  
  /**
   * Constructs a DirectHotelAccess with the given prefix,
   * and loads a hotel.
   *
   * @param prefix the prefix to be used - see HotelPersistence
   */
  public DirectHotelAccess(String prefix) {
    hotelPersistence = new HotelPersistence(prefix);
    loadHotel();
  }

  /**
   * Adds a person to the hotel, and saves.
   *
   * @param person the person the be added
   */
  @Override
  public void addPerson(Person person) {
    hotel.addPerson(person);
    saveHotel();
  }

  /**
   * Returns the people who are signed up at this hotel.
   *
   * @return a collection of persons
   */
  @Override
  public Collection<Person> getPersons() {
    return hotel.getPersons();
  }

  /**
   * Returns the hotel rooms of this hotel.
   *
   * @return a collection of hotel rooms
   */
  @Override
  public Collection<HotelRoom> getRooms(HotelRoomFilter hotelRoomFilter) {
    return hotel.getRooms(hotelRoomFilter);
  }

  /**
   * Tries to load a hotel, using HotelPersistence.
   * Prints a stack trace if unsuccessful.
   * Is called automatically in the constructor.
   * Should be called if prefix is changed.
   */
  @Override
  public void loadHotel() {
    try {
      hotel = hotelPersistence.loadHotel();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Saves the hotel to file.
   * Is called automatically after every change.
   */
  private void saveHotel() {
    try {
      hotelPersistence.saveHotel(hotel);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void makeReservation(Person person, int hotelRoomNumber,
      LocalDate startDate, LocalDate endDate) {
    hotel.makeReservation(person, hotelRoomNumber, startDate, endDate);
    saveHotel();
  }

  /**
   * Adds balance to a person and saves the change.
   *
   * @param person the person to receive the money
   * @param amount the amount to be added
   */
  @Override
  public void addBalance(Person person, double amount) {
    person.addBalance(amount);
    saveHotel();
  }

  /**
   * Sets the prefix - see HotelPersistence.
   *
   * @param prefix the prefix to be set
   */
  @Override
  public void setPrefix(String prefix) {
    hotelPersistence.setPrefix(prefix);
  }
}
