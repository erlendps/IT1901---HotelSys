import org.json.JSONObject;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashSet;
import gr2116.core.*;

public class Loader {
    //Todo: Invalid data handling

    private Collection<Person> persons = new HashSet<Person>();
    private Collection<Reservation> reservations = new HashSet<Reservation>();
    private Collection<HotelRoom> rooms = new HashSet<HotelRoom>();



    public void loadPersons(JSONObject personsData) {
        if (reservations.size() == 0) {
            throw new IllegalStateException("Reservations not yet loaded.");
        }
        personsData.keySet().forEach((String k) -> 
        {
            JSONObject personData = personsData.getJSONObject(k);
            Person person = new Person(personData.getString("name"));
            person.setEmail(personData.getString("email"));
            person.addBalance(personData.getDouble("balance"));
            ArrayList<Long> reservationIds = new ArrayList<Long>();
            personData.getJSONArray("reservations").forEach((Object o) -> reservationIds.add((Long)o));
            
            reservations.forEach((Reservation r) -> {if (reservationIds.contains(r.getId())) {
                                                        person.addReservation(r);
                                                    }
                                                    });
            persons.add(person);
        });
        
    }

    public void makeRoomsAndReservationsFromJSON(JSONObject roomsData) {
        roomsData.keySet().forEach((String k) -> 
        { 
            JSONObject roomData = roomsData.getJSONObject(k);
            HotelRoom room = new HotelRoom(HotelRoomType.valueOf(roomData.getString("type")), roomData.getInt("number"));
            room.setPrice(roomData.getInt("price"));
            roomData.getJSONArray("amenities").forEach((Object amenity) -> room.addAmenity(Amenity.valueOf((String)amenity)));
            rooms.add(room);
        });
        /*JSONObject roomData = new JSONObject();
        roomData.put("number", room.getNumber());
        roomData.put("price", room.getPrice());
        roomData.put("type", room.getRoomType());
        roomData.put("amenities", room.getAmenities());
        roomData.put("reservations", room.getReservationIds());
        return roomData;*/
    }
    
    public Reservation makeReservationJSON(JSONObject reservationData) {
        /*JSONObject reservationData = new JSONObject();
        reservationData.put("id", reservation.getId());
        reservationData.put("room", reservation.getRoom().getNumber());
        reservationData.put("startDate", reservation.getStartDate());
        reservationData.put("endDate", reservation.getEndDate());
        return reservationData;*/
    }
}
