package gr2116.integrationtests;

import gr2116.core.Amenity;
import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;
import gr2116.core.Person;
import gr2116.persistence.HotelPersistence;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Helper class that sets up and saves the file that will be used by
 * jetty when running integration tests.
 */
public class SetUpFile {

  /**
   * Main method of SetUpFile.
   * Generates some persons and hotel rooms to be used in IT.
   *
   * @param args not currently in use
   */
  public static void main(String[] args) {
    HotelPersistence hotelPersistence = new HotelPersistence("testIt");
    // set up data
    Person rick = new Person("rick");
    rick.setFirstName("Richard");
    rick.setLastName("Willy");
    rick.setPassword("bananas");
    
    Person kyle = new Person("kyle");
    kyle.setFirstName("Kyllard");
    kyle.setLastName("Smarting");
    kyle.setPassword("owingyou1");
    
    Person tom = new Person("tom");
    tom.setFirstName("Thomas");
    tom.setLastName("Wonka");
    tom.setPassword("blueballing");

    rick.addBalance(1000);
    kyle.addBalance(400);
    tom.addBalance(10000);

    Collection<Person> persons = new ArrayList<Person>();
    persons.add(rick);
    persons.add(kyle);
    persons.add(tom);
    
    HotelRoom room1 = new HotelRoom(HotelRoomType.Single, 101);
    room1.setPrice(30);
    HotelRoom room2 = new HotelRoom(HotelRoomType.Double, 102);
    room2.setPrice(50);
    HotelRoom room3 = new HotelRoom(HotelRoomType.Quad, 714);
    room3.setPrice(300);
    
    Collection<HotelRoom> rooms = new ArrayList<HotelRoom>();
    rooms.add(room1);
    rooms.add(room2);
    rooms.add(room3);
    
    room1.addAmenity(Amenity.Bathtub);
    room1.addAmenity(Amenity.Television);
    room2.addAmenity(Amenity.Fridge);
    room2.addAmenity(Amenity.Internet);
    room2.addAmenity(Amenity.Shower);
    room3.addAmenity(Amenity.KitchenFacilities);
    room3.addAmenity(Amenity.Television);
    room3.addAmenity(Amenity.WashingMachine);
    room3.addAmenity(Amenity.Shower);

    Hotel hotel = new Hotel(rooms, persons);
    try {
      hotelPersistence.saveHotel(hotel);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
