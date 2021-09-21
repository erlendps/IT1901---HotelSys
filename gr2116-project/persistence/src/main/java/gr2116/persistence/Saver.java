package gr2116.persistence;

import org.json.JSONObject;
import java.util.Collection;
import gr2116.core.*;

public class Saver {


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

}
