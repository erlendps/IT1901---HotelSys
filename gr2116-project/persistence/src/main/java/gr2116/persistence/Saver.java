package gr2116.persistence;

import gr2116.core.HotelRoom;
import gr2116.core.Person;
import gr2116.core.Reservation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import org.json.JSONObject;


/**
 * Saver class to save person, room and reservation objects to JSON file.
 */
public class Saver {
  private static final Path METADATA_FOLDER
      = Paths.get(".").toAbsolutePath()
      .normalize().getParent().getParent().resolve("data");

  /**
   * Method to make JSON from a single person object
   * @param person The person object
   * @return JSONObject containing person attributes
   */
  private JSONObject makePersonJson(final Person person) {
    JSONObject personData = new JSONObject();
    personData.put("name", person.getName());
    personData.put("email", person.getEmail());
    personData.put("balance", person.getBalance());
    personData.put("reservations", person.getReservationIds());
    return personData;
  }
  /**
   * Method to make JSON from a single HotelRoom object
   * @param room The hotel room object
   * @return JSONObject containing room attributes
   */
  private JSONObject makeRoomJson(final HotelRoom room) {
    JSONObject roomData = new JSONObject();
    roomData.put("number", room.getNumber());
    roomData.put("price", room.getPrice());
    roomData.put("type", room.getRoomType().name());
    roomData.put("amenities", room.getAmenities());
    roomData.put("reservations", room.getReservationIds());
    return roomData;
  }

  /**
   * Method to make JSON from a single reservation object
   * @param reservation The reservation object
   * @return The JSONObject containing reservation attributes
   */
  private JSONObject makeReservationJson(final Reservation reservation) {
    JSONObject reservationData = new JSONObject();
    reservationData.put("id", reservation.getId());
    reservationData.put("room", reservation.getRoom().getNumber());
    reservationData.put("startDate", reservation.getStartDate().toString());
    reservationData.put("endDate", reservation.getEndDate().toString());
    return reservationData;
  }

  /**
   * Take in a collection of persons and save each as a JSONObject, then arrange them in a JSON Map
   * @param persons Collection of persons
   * @return JSON Map of persons, with email as key.
   */
  private JSONObject updatePersonData(final Collection<Person> persons) {
    JSONObject personsData = new JSONObject();
    persons.forEach((person) -> {
      personsData.put(person.getEmail(), makePersonJson(person));
    });
    return personsData;
  }

  /**
   * Take in a collection of rooms and save each as a JSONObject, then arrange them in a JSON Map
   * @param rooms Collection of rooms
   * @return JSON Map of rooms, with number as key.
   */
  private JSONObject updateRoomsData(final Collection<HotelRoom> rooms) {
    JSONObject roomsData = new JSONObject();
    rooms.forEach((room) -> {
      roomsData.put(Integer.toString(room.getNumber()), makeRoomJson(room));
    });
    return roomsData;
  }
  /**
   * Take in a collection of reservations and save each as a JSONObject, then arrange them in a JSON Map
   * @param reservations Collection of reservations
   * @return JSON Map of reservations, with ID as string
   */
  private JSONObject updateReservationData(
      final Collection<Reservation> reservations) {
    JSONObject reservationsData = new JSONObject();
    reservations.forEach((reservation) -> {
      reservationsData.put(Long.toString(reservation.getId()),
                          makeReservationJson(reservation));
    });
    return reservationsData;
  }

  /**
   * Write JSON data to file from collections of rooms and persons.
   * This method automatically extracts reservations from persons.
   * @param rooms Collection of room objects
   * @param persons Collection of person objects 
   * @throws FileNotFoundException
   */
  public final void writeToFile(final Collection<HotelRoom> rooms,
      final Collection<Person> persons)
      throws FileNotFoundException {
    ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    persons.forEach((person) -> reservations.addAll(person.getReservations()));
    writeToFile(rooms, persons, reservations);
  }
  /**
   * Write JSON data to file from collections of rooms, persons and reservations.
   * @param rooms Collection of room objects
   * @param persons Collection of person objects
   * @param reservations Collection of reservation objects
   * @throws FileNotFoundException
   */
  public final void writeToFile(final Collection<HotelRoom> rooms,
      final Collection<Person> persons,
      final Collection<Reservation> reservations)
      throws FileNotFoundException {
    File personDataJSON = new File(METADATA_FOLDER + "/personData.json");
    File roomsDataJSON = new File(METADATA_FOLDER + "/roomsData.json");
    File reservationDataJSON = new File(
        METADATA_FOLDER + "/reservationData.json");
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
    try (PrintWriter pw = new PrintWriter(
                              reservationDataJSON.getAbsolutePath())) {
      pw.print(updateReservationData(reservations).toString(2));
      pw.flush();
    }
  }
}
