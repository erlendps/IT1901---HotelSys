package gr2116.persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import gr2116.core.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class SaveTest {
    @Test
    public void testSave() {
        Person rick = new Person("Richard");
        Person kyle = new Person("Kyllard");
        rick.setEmail("richard@people.com");
        kyle.setEmail("kyle@people.com");

        HotelRoom room1 = new HotelRoom(HotelRoomType.Single, 101);
        HotelRoom room2 = new HotelRoom(HotelRoomType.Single, 102);
        room1.addAmenity(Amenity.Bathtub);
        room1.addAmenity(Amenity.Television);
        room2.addAmenity(Amenity.Fridge);
        room2.addAmenity(Amenity.Internet);
        room2.addAmenity(Amenity.Shower);

        rick.makeReservation(room1, LocalDate.of(2021, 6, 4), LocalDate.of(2021, 6, 7));
        kyle.makeReservation(room1, LocalDate.of(2021, 6, 11), LocalDate.of(2021, 6, 13));
        rick.makeReservation(room2, LocalDate.of(2021, 7, 13), LocalDate.of(2021, 7, 22));

        ArrayList<Person> persons = new ArrayList<Person>();
        persons.add(rick);
        persons.add(kyle);

        ArrayList<HotelRoom> rooms = new ArrayList<HotelRoom>();
        rooms.add(room1);
        rooms.add(room2);

        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        persons.forEach((person) -> 
            person.getReservations().forEach((r) -> 
            reservations.add(r)));


        Saver saver = new Saver();
        JSONObject personsData = saver.updatePersonData(persons);
        JSONObject roomsData = saver.updateRoomsData(rooms);
        JSONObject reservationsData = saver.updateReservationData(reservations);

        System.out.println(personsData);
        System.out.println(roomsData);
        System.out.println(reservationsData);
    }
}
