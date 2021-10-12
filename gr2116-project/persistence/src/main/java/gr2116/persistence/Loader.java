package gr2116.persistence;

import org.json.JSONObject;
import java.util.Collection;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import gr2116.core.*;

public class Loader {
    // TODO: Invalid data handling?

    // Get path for data
    private static final Path METADATA_FOLDER = Paths.get(".").toAbsolutePath().normalize().getParent().getParent().resolve("data");

    private boolean loaded = false;
    private Collection<Person> persons = new HashSet<Person>();
    private Collection<HotelRoom> rooms = new HashSet<HotelRoom>();
    private Map<String, Reservation> reservationMap = new HashMap<String, Reservation>();

    /**
     * Method to load persons from JSON data. Rooms must be loaded first.
     * @param personsData
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

            // Get reservation IDs and add the reservations to person. Rooms must be loaded first, to ensure that reservationMap
            // contains all reservations.
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
     * Method to extract reservation from JSON and connect to hotelroom.
     * @param reservationsData
     * @param id
     * @param room
     * @return
     */
    private Reservation getReservation(JSONObject reservationsData, String id, HotelRoom room) {
        JSONObject reservationData;

        if (reservationMap.containsKey(id)) {
            // This reservation has already been loaded, whcih means multiple rooms contain this reservation.
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
     * Method that loads rooms and the reservations connected to them, must be called before loadPersons(). 
     * @param roomsData
     * @param reservationsData
     */
    private void loadRoomsAndReservations(JSONObject roomsData, JSONObject reservationsData) {

        // Iterate through rooms
        roomsData.keySet().forEach((String k) -> { 
            JSONObject roomData = roomsData.getJSONObject(k);

            // Make room object from JSON data and set attributes accordingly
            HotelRoom room = new HotelRoom(HotelRoomType.valueOf(roomData.getString("type")), roomData.getInt("number"));
            room.setPrice(roomData.getInt("price"));
            roomData.getJSONArray("amenities").forEach((amenity) -> room.addAmenity(Amenity.valueOf((String) amenity)));
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
     * Call this method to enable getPersons() and getRooms(). Loads the data from JSON objects into lists containing
     * appropriate objects.
     * @param roomsData
     * @param personsData
     * @param reservationsData
     */
    public void loadData(JSONObject roomsData, JSONObject personsData, JSONObject reservationsData) {
        // Set the variable to enable use of getPersons(), getRooms()
        loaded = true;
        loadRoomsAndReservations(roomsData, reservationsData);
        loadPersons(personsData);
    }

    /**
     * Get the collection of persons that were loaded using loadData()
     * @return
     */
    public Collection<Person> getPersons() {
        if (!loaded) {
            throw new IllegalStateException("Objects must be loaded using loadData() before getting.");
        }
        return persons;
    }
    /**
     * Get the collection of rooms that were loaded using loadData()
     * @return
     */
    public Collection<HotelRoom> getRooms() {
        if (!loaded) {
            throw new IllegalStateException("Objects must be loaded using loadData() before getting.");
        }
        return rooms;
    }

    public JSONObject getAsJSON(String filename) throws IOException {
        String text = Files.readString(Paths.get(new File(METADATA_FOLDER + filename).getAbsolutePath()));
        JSONObject json = new JSONObject(text);
        return json;
    }
    /**
     * Load data from the default file locations.
     * @throws IOException
     */
    public void loadData() throws IOException {
        JSONObject personData = getAsJSON("/personData.json");
        JSONObject roomData = getAsJSON("/roomsData.json");
        JSONObject reservationData = getAsJSON("/reservationData.json");

        loadData(roomData, personData, reservationData);
    }
}
