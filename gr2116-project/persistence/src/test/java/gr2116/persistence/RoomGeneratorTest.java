package gr2116.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import gr2116.core.HotelRoom;

public class RoomGeneratorTest {

    Collection<HotelRoom> rooms;

    @Test
    public void testGeneratedRooms() {
        rooms = RoomGenerator.generateRooms(50);
        assertEquals(50, rooms.size());
        assert(rooms.iterator().next().getAmenities().size() > 0);
        assert(rooms.iterator().next().getFloor() >= 1 && rooms.iterator().next().getFloor() <= 7);
    }
    
}
