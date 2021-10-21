package gr2116.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReservationTest {
    private Reservation res;
    private final LocalDate startDate = LocalDate.now();
    private final LocalDate endDate = LocalDate.now().plusDays(4);
    private final HotelRoom room = mock(HotelRoom.class);
    private final HotelRoom room2 = mock(HotelRoom.class);


    @BeforeEach
    public void setup() {
        when(room.getNumber()).thenReturn(101);
        res = new Reservation(room, startDate, endDate);
    }

    @Test
    public void testGetRoom() {
        assertEquals(room, res.getRoom());
        assertNotEquals(room2, res.getRoom());
    }

    @Test
    public void testGetStartDate() {
        assertEquals(startDate, res.getStartDate());
    }

    @Test
    public void testGetEndDate() {
        assertEquals(endDate, res.getEndDate());
    }

    @Test
    public void testGetId() {
        StringBuilder sb = new StringBuilder();
        sb.append(101);
        sb.append(startDate.toString().replace("-", ""));
        sb.append(endDate.toString().replace("-", ""));
        assertEquals(Long.parseLong(sb.toString()), res.getId());
    }

    @Test
    public void testConstructor() {
        assertThrows(NullPointerException.class, () ->
            new Reservation(null, startDate, endDate));
        
        assertThrows(NullPointerException.class, () ->
            new Reservation(room, null, endDate));
        
        assertThrows(NullPointerException.class, () ->
            new Reservation(room, startDate, null));

        assertThrows(IllegalArgumentException.class, () ->
            new Reservation(room, endDate, startDate));
    }
}
