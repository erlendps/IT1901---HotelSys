package gr2116.persistence;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import gr2116.core.*;

public class Saver {
    private static final Path METADATA_FOLDER = Paths.get(".").toAbsolutePath().normalize().getParent().getParent().resolve("data");

    private JSONObject makePersonJSON(Person person) {
        JSONObject personData = new JSONObject();
        personData.put("name", person.getName());
        personData.put("email", person.getEmail());
        personData.put("balance", person.getBalance());
        personData.put("reservations", person.getReservationIds());
        return personData;
    }

    private JSONObject makeRoomJSON(HotelRoom room) {
        JSONObject roomData = new JSONObject();
        roomData.put("number", room.getNumber());
        roomData.put("price", room.getPrice());
        roomData.put("type", room.getRoomType().name());
        roomData.put("amenities", room.getAmenities());
        roomData.put("reservations", room.getReservationIds());
        return roomData;
    }
    
    private JSONObject makeReservationJSON(Reservation reservation) {
        JSONObject reservationData = new JSONObject();
        reservationData.put("id", reservation.getId());
        reservationData.put("room", reservation.getRoom().getNumber());
        reservationData.put("startDate", reservation.getStartDate().toString());
        reservationData.put("endDate", reservation.getEndDate().toString());
        return reservationData;
    }

    private JSONObject updatePersonData(Collection<Person> persons) {
        JSONObject personsData = new JSONObject();
        persons.forEach((person) -> {
            personsData.put(person.getEmail(), makePersonJSON(person));
        });
        return personsData;
    }

    private JSONObject updateRoomsData(Collection<HotelRoom> rooms) {
        JSONObject roomsData = new JSONObject();
        rooms.forEach((room) -> {
            roomsData.put(Integer.toString(room.getNumber()), makeRoomJSON(room));
        });
        return roomsData;
    }

    private JSONObject updateReservationData(Collection<Reservation> reservations) {
        JSONObject reservationsData = new JSONObject();
        reservations.forEach((reservation) -> {
            reservationsData.put(Long.toString(reservation.getId()), makeReservationJSON(reservation));
        });
        return reservationsData;
    }

    public void writeToFile(Collection<HotelRoom> rooms,
                            Collection<Person> persons) throws FileNotFoundException {
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        persons.forEach((person) -> reservations.addAll(person.getReservations()));
        writeToFile(rooms, persons, reservations);
    }
    
    public void writeToFile(Collection<HotelRoom> rooms,
                            Collection<Person> persons,
                            Collection<Reservation> reservations) throws FileNotFoundException {
        File personDataJSON = new File(METADATA_FOLDER + "/personData.json");
        File roomsDataJSON = new File(METADATA_FOLDER + "/roomsData.json");
        File reservationDataJSON = new File(METADATA_FOLDER + "/reservationData.json");
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
}