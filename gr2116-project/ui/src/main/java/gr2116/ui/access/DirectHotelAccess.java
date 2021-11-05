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
 * 
 */
public class DirectHotelAccess implements HotelAccess {
  private final HotelPersistence hotelPersistence;
  private Hotel hotel;
  
  public DirectHotelAccess(HotelPersistence hotelPersistence) {
    this.hotelPersistence = hotelPersistence;
    loadHotel();
  }

  @Override
  public void addPerson(Person person) {
    hotel.addPerson(person);
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

  @Override
  public void saveHotel() {
    try {
      hotelPersistence.saveHotel(hotel);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void makeReservation(Person person, HotelRoom hotelRoom,
      LocalDate startDate, LocalDate endDate) {
    hotel.makeReservation(person, hotelRoom, startDate, endDate);
  }
}
