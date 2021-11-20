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
 * and stores data locally. 
 */
public class DirectHotelAccess implements HotelAccess {
  private final HotelPersistence hotelPersistence;
  private Hotel hotel;
  
  public DirectHotelAccess(String prefix) {
    hotelPersistence = new HotelPersistence(prefix);
    loadHotel();
  }

  @Override
  public void addPerson(Person person) {
    hotel.addPerson(person);
    saveHotel();
  }

  @Override
  public Collection<Person> getPersons() {
    return hotel.getPersons();
  }

  @Override
  public Collection<HotelRoom> getRooms(HotelRoomFilter hotelRoomFilter) {
    return hotel.getRooms(hotelRoomFilter);
  }

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
   */
  public void saveHotel() {
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

  @Override
  public void addBalance(Person person, double amount) {
    person.addBalance(amount);
    saveHotel();
  }

  @Override
  public void setPrefix(String prefix) {
    hotelPersistence.setPrefix(prefix);
  }
}
