package gr2116.ui.access;

import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomFilter;
import gr2116.core.Person;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Interface to access a Hotel.
 * Implemented by DirectHotelAccess and RemoteHotelAccess.
 * Makes switching between direct/remote easy.
*/
public interface HotelAccess {

  void addPerson(Person person);

  Collection<Person> getPersons();

  Collection<HotelRoom> getRooms(HotelRoomFilter hotelRoomFilter);

  void loadHotel();

  void makeReservation(Person person, int hotelRoomNumber, LocalDate startDate, LocalDate endDate);

  void addBalance(Person person, double amount);

  void setPrefix(String prefix);
}
