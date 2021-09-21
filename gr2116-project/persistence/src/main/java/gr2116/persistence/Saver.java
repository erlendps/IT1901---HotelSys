package gr2116.persistence;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.io.PrintWriter;
import java.nio.file.Path;

import gr2116.core.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class Saver {
    private static final String METADATA_FOLDER = "gr2116-project/persistence/src/main/resources/";


    public JSONObject makePersonJSON(Person person) {
        JSONObject personData = new JSONObject();
        personData.put("name", person.getName());
        personData.put("email", person.getEmail());
        personData.put("balance", person.getBalance());
        personData.put("reservations", person.getReservationIds());
        return personData;
    }

    public JSONObject makeRoomJSON(HotelRoom room) {
        JSONObject roomData = new JSONObject();
        roomData.put("number", room.getNumber());
        roomData.put("price", room.getPrice());
        roomData.put("type", room.getRoomType());
        roomData.put("amenities", room.getAmenities());
        roomData.put("reservations", room.getReservationIds());
        return roomData;
    }
    
    public JSONObject makeReservationJSON(Reservation reservation) {
        JSONObject reservationData = new JSONObject();
        reservationData.put("id", reservation.getId());
        reservationData.put("room", reservation.getRoom().getNumber());
        reservationData.put("startDate", reservation.getStartDate());
        reservationData.put("endDate", reservation.getEndDate());
        return reservationData;
    }

    public JSONObject updatePersonData(Collection<Person> persons) {
        JSONObject personsData = new JSONObject();
        persons.forEach((person) -> personsData.
                                    put(
                                        person.getEmail(),
                                        makePersonJSON(person)
                                        )   
                                    );
        return personsData;
    }

    public JSONObject updateRoomsData(Collection<HotelRoom> rooms) {
        JSONObject roomsData = new JSONObject();
        rooms.forEach((room) -> roomsData.
                                put(
                                    Integer.toString(room.getNumber()),
                                    makeRoomJSON(room)
                                    )
                                );                  
        /*
        for (int i=0; i < rooms.size(); i++) {
            roomsData.put(Integer.toString(rooms.get(i).getNumber()), makeRoomJSON(rooms.get(i)));
        }
        */
        return roomsData;

    }

    public JSONObject updateReservationData(Collection<Reservation> reservations) {
        JSONObject reservationsData = new JSONObject();
        reservations.forEach((reservation) -> reservationsData.
                                                put(
                                                    Long.toString(reservation.getId()),
                                                    makeReservationJSON(reservation)
                                                    )
                                                );
        /*
        for (int i=0; i < reservations.size(); i++) {
            reservationsData.put(Long.toString(reservations.get(i).getId()), makeReservationJSON(reservations.get(i)));
        }
        */
        return reservationsData;
    }

    public void WriteToFile(Collection<Person> persons,
                            Collection<HotelRoom> rooms,
                            Collection<Reservation> reservations) throws FileNotFoundException {
        File personDataJSON = new File(METADATA_FOLDER + "personData.json");
        File roomsDataJSON = new File(METADATA_FOLDER + "roomsData.json");
        File reservationDataJSON = new File(METADATA_FOLDER + "reservationData.json");
        try {
            Path.of(personDataJSON.getAbsolutePath()).toFile().createNewFile();
            Path.of(roomsDataJSON.getAbsolutePath()).toFile().createNewFile();
            Path.of(reservationDataJSON.getAbsolutePath()).toFile().createNewFile();
        } catch (IOException e) {
            System.err.println("Something went wrong with file creation");
        }
        try (PrintWriter pw = new PrintWriter(personDataJSON.getAbsolutePath())) {
            pw.print(updatePersonData(persons).toString(2));
            pw.flush();
        }
        try (PrintWriter pw = new PrintWriter(roomsDataJSON.getAbsolutePath())) {
            pw.print(updateRoomsData(rooms).toString(2));
            pw.flush();
        }
        try (PrintWriter pw = new PrintWriter(reservationDataJSON.getAbsolutePath())) {
            pw.print(updateReservationData(reservations).toString(2));
            pw.flush();
        }
    }

    public static void main(String[] args) {
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
        try {
        saver.WriteToFile(persons, rooms, reservations);
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

}
