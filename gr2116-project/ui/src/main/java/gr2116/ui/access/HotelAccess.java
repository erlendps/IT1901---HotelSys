package gr2116.ui.access;

import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomFilter;
import gr2116.core.Person;
import java.time.LocalDate;
import java.util.Collection;

/**
 * 
*/
public interface HotelAccess {

  void addPerson(Person person);

  Collection<Person> getPersons();

  Collection<HotelRoom> getRooms(HotelRoomFilter hotelRoomFilter);

  void loadHotel();

  void saveHotel();

  void makeReservation(Person person, HotelRoom hotelRoom, LocalDate startDate, LocalDate endDate);

}
