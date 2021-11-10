package gr2116.core;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Test class for Hotel class
 */
public class HotelTest {
    Hotel hotel;
    HotelRoom room1 = mock(HotelRoom.class);
    HotelRoom room2 = mock(HotelRoom.class);
    Collection<HotelRoom> rooms = new ArrayList<>(Arrays.asList(room1, room2));

    @BeforeEach
    public void setup() {
        hotel = new Hotel();
    }

    @Test
    public void testConstructor() {
        Hotel emptyHotel = new Hotel();
        assertEquals(Arrays.asList(), emptyHotel.getRooms((r) -> true));
        Hotel fullHotel = new Hotel(rooms);
        assertEquals(rooms, fullHotel.getRooms((r) -> true));
    }

    @Test
    public void testRoomsIsPersistent() {
        Hotel fullHotel = new Hotel(rooms);
        HotelRoom room3 = mock(HotelRoom.class);
        rooms.add(room3);
        assertNotEquals(rooms, fullHotel.getRooms((r) -> true));
    }

    @Test
    public void testAddRoom() {
        assertEquals(Arrays.asList(), hotel.getRooms((r) -> true));
        hotel.addRoom(room1);
        hotel.addRoom(room2);
        assertEquals(Arrays.asList(room1, room2), hotel.getRooms((r) -> true));
        assertThrows(IllegalArgumentException.class, () -> hotel.addRoom(null));
        assertFalse(hotel.addRoom(room1));
    }

    @Test
    public void testRemoveRoom() {
        hotel.addRoom(room1);
        hotel.removeRoom(room1);
        assertEquals(Arrays.asList(), hotel.getRooms((r) -> true));
    }

    @Test
    public void testGetRoomsPredicate() {
        HotelRoom room3 = mock(HotelRoom.class);
        when(room1.getRoomType()).thenReturn(HotelRoomType.Single);
        when(room2.getRoomType()).thenReturn(HotelRoomType.Double);
        hotel.addRoom(room1);
        hotel.addRoom(room2);
        hotel.addRoom(room3);
        assertEquals(Arrays.asList(room1), hotel.getRooms((room) -> room.getRoomType() == HotelRoomType.Single));
        when(room1.getNumber()).thenReturn(2);
        when(room2.getNumber()).thenReturn(5);
        when(room3.getNumber()).thenReturn(7);
        assertEquals(Arrays.asList(room2, room3), hotel.getRooms((room) -> room.getNumber() > 3));
        
        when(room1.hasAmenity(Amenity.Internet)).thenReturn(true);
        when(room2.hasAmenity(Amenity.Internet)).thenReturn(false);
        assertEquals(Arrays.asList(room1), hotel.getRooms((room) -> room.hasAmenity(Amenity.Internet)));
    }

    @Test
    public void testIterator() {
        hotel.addRoom(room1);
        hotel.addRoom(room2);
        Iterator<HotelRoom> it = hotel.iterator();
        while (it.hasNext()) {
            assertTrue(hotel.getRooms((r) -> true).contains(it.next()));
        }
    }
}