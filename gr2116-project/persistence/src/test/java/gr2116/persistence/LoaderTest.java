package gr2116.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import gr2116.core.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class LoaderTest {
  JSONObject personsData;
  JSONObject roomsData;
  JSONObject reservationsData;

  @BeforeEach
  public void makeData() throws FileNotFoundException {
    Person rick = new Person("Richard");
    Person kyle = new Person("Kyllard");
    Person tom = new Person("Tom");
    rick.setEmail("richard@people.com");
    kyle.setEmail("kyle@people.com");
    tom.setEmail("tom@richpeople.org");

    rick.addBalance(1000);
    kyle.addBalance(144);
    tom.addBalance(1000000000); 

    HotelRoom room1 = new HotelRoom(HotelRoomType.Single, 101);
    HotelRoom room2 = new HotelRoom(HotelRoomType.Single, 102);
    HotelRoom room3 = new HotelRoom(HotelRoomType.Quad, 714);

    room1.addAmenity(Amenity.Bathtub);
    room1.addAmenity(Amenity.Television);
    room2.addAmenity(Amenity.Fridge);
    room2.addAmenity(Amenity.Internet);
    room2.addAmenity(Amenity.Shower);

    rick.makeReservation(room1, LocalDate.of(2021, 6, 4), LocalDate.of(2021, 6, 7));
    kyle.makeReservation(room1, LocalDate.of(2021, 6, 11), LocalDate.of(2021, 6, 13));
    rick.makeReservation(room2, LocalDate.of(2021, 7, 13), LocalDate.of(2021, 7, 22));
    tom.makeReservation(room3, LocalDate.of(2021, 10, 12), LocalDate.of(2021, 10, 13));
    tom.makeReservation(room3, LocalDate.of(2021, 11, 12), LocalDate.of(2021, 11, 13));
    tom.makeReservation(room3, LocalDate.of(2022, 1, 12), LocalDate.of(2022, 1, 13));
    tom.makeReservation(room3, LocalDate.of(2022, 2, 12), LocalDate.of(2022, 2, 13));

    ArrayList<Person> persons = new ArrayList<Person>();
    persons.add(rick);
    persons.add(kyle);
    persons.add(tom);

    ArrayList<HotelRoom> rooms = new ArrayList<HotelRoom>();
    rooms.add(room1);
    rooms.add(room2);
    rooms.add(room3);

    ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    persons.forEach((person) -> 
      person.getReservations().forEach((r) -> 
      reservations.add(r)));

    Saver saver = new Saver();
    /*personsData = saver.updatePersonData(persons);
    roomsData = saver.updateRoomsData(rooms);
    reservationsData = saver.updateReservationData(reservations);*/
    saver.writeToFile(rooms, persons, reservations);
  }

  @Test
  public void load() throws IOException {
    Loader dataLoader = new Loader();
    dataLoader.loadData();
    Collection<Person> persons = dataLoader.getPersons();
    Collection<HotelRoom> rooms = dataLoader.getRooms();
    System.out.println("People:");
    persons.forEach((Person p) -> {
      System.out.println(p.getName());
      System.out.println("> Reservations:");
      p.getReservations().forEach((Reservation r) -> {
        System.out.println("\t" + r.getId() + 
        " for room " + r.getRoom().getNumber() +
        " (" + r.getStartDate() + " to " + r.getEndDate() + ")");
      });
      System.out.println();
    });
    System.out.println();
    System.out.println();
    System.out.println("Rooms:");
    rooms.forEach((HotelRoom r) -> System.out.println(r.getNumber()));
    System.out.println();
  }
}
