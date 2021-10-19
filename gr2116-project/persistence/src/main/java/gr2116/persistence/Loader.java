package gr2116.persistence;

import gr2116.core.Amenity;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;
import gr2116.core.Person;
import gr2116.core.Reservation;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.json.JSONObject;

/**
 * Loader class used to load persons, rooms and reservations from JSON files.
 */
public class Loader {
  // TODO: Invalid data handling?

  // Get path for data
  private static final Path DATA_FOLDER
      = Paths.get(".").toAbsolutePath().normalize().getParent().getParent().resolve("data");

  private boolean loaded = false;
  private Collection<Person> persons = new HashSet<Person>();
  private Collection<HotelRoom> rooms = new HashSet<HotelRoom>();
  private Map<String, Reservation> reservationMap = new HashMap<String, Reservation>();

  /**
   * Loads persons from JSON data. Rooms must be loaded first.
   *
   * @param personsData JSONObject with persons data, must be formatted as from Saver class
   */
  private void loadPersons(JSONObject personsData) {
    if (rooms.size() == 0) {
      throw new IllegalStateException("Reservations not yet loaded.");
    }
    // Get data about each individual person from JSON and make person objects with this data
    personsData.keySet().forEach((String k) -> {
      JSONObject personData = personsData.getJSONObject(k);
      Person person = new Person(personData.getString("name"));
      person.setEmail(personData.getString("email"));
      person.addBalance(personData.getDouble("balance"));

      // Get reservation IDs and add the reservations to person.
      // Rooms must be loaded first, to ensure that reservationMap contains all reservations.
      personData.getJSONArray("reservations").forEach((reservationId) -> {
        String id = Long.toString((Long) reservationId);
        if (reservationMap.containsKey(id)) {
          person.addReservation(reservationMap.get(id));
        } else {
          throw new IllegalStateException("Reservation " + id + " was not created before person.");
        }
      });

      // Add the person to persons list
      persons.add(person);
    });
  }
  
  /**
   * Extracts reservation from JSON and connect to hotelroom.
   *
   * @param reservationsData JSONObject with reservation data
   * @param id ID of reservation to be connected to room and saved
   * @param room room to be connected to reservation
   *
   * @return The created reservation object
   */
  private Reservation getReservation(JSONObject reservationsData, String id, HotelRoom room) {
    JSONObject reservationData;

    if (reservationMap.containsKey(id)) {
      // This reservation has already been loaded,
      // which means multiple rooms contain this reservation.
      throw new IllegalStateException("Multiple rooms for reservation " + id);
    } else if (reservationsData.has(id)) {
      // Fetch reservation from JSON, if it exists
      reservationData = reservationsData.getJSONObject(id);
    } else {
      throw new IllegalStateException("Missing data for reservation " + id);
    }

    // Make the reservation using provided room data and data from reservations JSON
    Reservation reservation = new Reservation(
        Long.parseLong(id),
        room,
        LocalDate.parse(reservationData.getString("startDate")),
        LocalDate.parse(reservationData.getString("endDate"))
    );

    // Put it in map to be used for connecting to Person objects later.
    reservationMap.put(id, reservation);
    return reservation;
  }

  /**
   * Loads rooms and the reservations connected to them,
   * must be called before loadPersons().
   *
   * @param roomsData Room data, JSONObject, must be formatted as in Saver class
   * @param reservationsData Reservation data, JSONObject, must be formatted as in Saver class
   */
  private void loadRoomsAndReservations(JSONObject roomsData, JSONObject reservationsData) {

    // Iterate through rooms
    roomsData.keySet().forEach((String k) -> { 
      JSONObject roomData = roomsData.getJSONObject(k);

      // Make room object from JSON data and set attributes accordingly
      HotelRoom room = new HotelRoom(
          HotelRoomType.valueOf(roomData.getString("type")), roomData.getInt("number"));
      room.setPrice(roomData.getInt("price"));
      roomData.getJSONArray("amenities").forEach((amenity) -> {
        room.addAmenity(Amenity.valueOf((String) amenity));
      });
      // Get reservations from this room and use getReservation() to load the reservation.
      roomData.getJSONArray("reservations").forEach((reservationId) -> {
        String id = Long.toString((Long) reservationId);
        Reservation reservation = getReservation(reservationsData, id, room);
        room.addReservation(reservation);
      });
      // Add the room to rooms list
      rooms.add(room);
    });
  }

  /**
   * Call this method to enable getPersons() and getRooms().
   * Loads the data from JSON objects into lists containing appropriate objects.
   *
   * @param roomsData Room data, JSONObject, must be formatted as in Saver class
   * @param personsData Person data, JSONObject, must be formatted as in Saver class
   * @param reservationsData Reservation data, JSONObject, must be formatted as in Saver class
   */
  private void loadData(JSONObject roomsData, JSONObject personsData, JSONObject reservationsData) {
    // Set the variable to enable use of getPersons(), getRooms()
    loaded = true;
    loadRoomsAndReservations(roomsData, reservationsData);
    loadPersons(personsData);
  }

  /**
   * Load data from the default file locations.
   *
   * @throws IOException if something with Input/Output went wrong.
   */
  public void loadData(String prefix) throws IOException {
    JSONObject personData = getAsJson("/" + prefix + "Person.json");
    JSONObject roomData = getAsJson("/" + prefix + "Rooms.json");
    JSONObject reservationData = getAsJson("/" + prefix + "Reservation.json");

    loadData(roomData, personData, reservationData);
  }

  /**
   * Get the collection of persons that were loaded using loadData().
   *
   * @return Person objects made by loadData()
   */
  public Collection<Person> getPersons() {
    if (!loaded) {
      throw new IllegalStateException("Objects must be loaded using loadData() before getting.");
    }
    return new ArrayList<>(persons);
  }

  /**
   * Get the collection of rooms that were loaded using loadData().
   *
   * @return Room objects made by loadData()
   */
  public Collection<HotelRoom> getRooms() {
    if (!loaded) {
      throw new IllegalStateException("Objects must be loaded using loadData() before getting.");
    }
    return new ArrayList<>(rooms);
  }
  
  /**
   * Takes a String and returns a JSONObject. Filename will be the name of the file to read from.
   *
   * @param filename the name of the file to read and convert to JSON
   * @return {@code JSONObject} of the text in the file.
   * @throws IOException if something with Input/Output went wrong.
   */
  private JSONObject getAsJson(String filename) throws IOException {
    String text = Files.readString(
        Paths.get(new File(DATA_FOLDER + filename).getAbsolutePath()));
    JSONObject json = new JSONObject(text);
    return json;
  }
}
