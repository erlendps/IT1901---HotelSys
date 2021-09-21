package gr2116.persistence;
import org.json.JSONObject;
import java.util.Collection;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import gr2116.core.*;

public class Loader {
    //Todo: Invalid data handling

    private boolean loaded = false;

    private Collection<Person> persons = new HashSet<Person>();

    private Collection<HotelRoom> rooms = new HashSet<HotelRoom>();
    private Map<String, Reservation> reservationMap = new HashMap<String, Reservation>();



    private void loadPersons(JSONObject personsData) {
        if (rooms.size() == 0) {
            throw new IllegalStateException("Reservations not yet loaded.");
        }
        personsData.keySet().forEach((String k) -> 
        {
            JSONObject personData = personsData.getJSONObject(k);
            Person person = new Person(personData.getString("name"));
            person.setEmail(personData.getString("email"));
            person.addBalance(personData.getDouble("balance"));
            ArrayList<String> reservationIds = new ArrayList<String>();
            personData.getJSONArray("reservations").forEach((Object o) -> reservationIds.add(Long.toString((Long)o)));
            
            reservationIds.forEach((String id) -> {
                if (reservationMap.containsKey(id)) {
                    person.addReservation(reservationMap.get(id));
                } else {
                    throw new IllegalStateException("Reservation " + id + " was not created before person.");
                }
            });

            persons.add(person);
        });
        
    }

    private Reservation getReservation(JSONObject reservationsData, String id, HotelRoom room) {
        JSONObject reservationData;

        if (reservationMap.containsKey(id)){
            return reservationMap.get(id);
        } else if (reservationsData.has(id)) {
            reservationData = reservationsData.getJSONObject(id);
        } else {
            throw new IllegalStateException("Missing data for reservation " + id);
        }

        Reservation reservation = new Reservation(
            Long.parseLong(id), 
            room, 
            LocalDate.parse(reservationData.getString("startDate")),
            LocalDate.parse(reservationData.getString("endDate"))
            );
        reservationMap.put(id, reservation);
        return reservation;
    }

    private void loadRoomsAndReservations(JSONObject roomsData, JSONObject reservationsData) {

        roomsData.keySet().forEach((String k) -> 
        { 
            JSONObject roomData = roomsData.getJSONObject(k);
            HotelRoom room = new HotelRoom(HotelRoomType.valueOf(roomData.getString("type")), roomData.getInt("number"));
            room.setPrice(roomData.getInt("price"));
            roomData.getJSONArray("amenities").forEach((Object amenity) -> room.addAmenity(Amenity.valueOf((String)amenity)));
            
            ArrayList<String> reservationIds = new ArrayList<String>();
            roomData.getJSONArray("reservations").forEach((Object o) -> reservationIds.add(Long.toString((Long)o)));

            reservationIds.forEach((String id) -> {
                                                Reservation reservation = getReservation(reservationsData, id, room);
                                                room.addReservation(reservation);
            });

            rooms.add(room);

        });
    }

    public void loadData(JSONObject roomsData, JSONObject personsData, JSONObject reservationsData) {
        loaded = true;
        loadRoomsAndReservations(roomsData, reservationsData);
        loadPersons(personsData);
    }

    public Collection<Person> getPersons() {
        if (!loaded) {
            throw new IllegalStateException("Objects must be loaded using loadData() before getting.");
        }
        return persons;
    }

    public Collection<HotelRoom> getRooms() {
        if (!loaded) {
            throw new IllegalStateException("Objects must be loaded using loadData() before getting.");
        }
        return rooms;
    }

        
}
