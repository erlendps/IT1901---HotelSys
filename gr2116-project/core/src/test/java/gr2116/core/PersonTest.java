package gr2116.core;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonTest {

    LocalDate today = LocalDate.now();
	LocalDate tomorrow = today.plusDays(1);
	LocalDate overmorrow = today.plusDays(2);

    Person person;
    Person tom;

    @BeforeEach
    public void setup() {
        person = new Person("Mr. Game and Watch");
        tom = new Person("Tom Haddleford");
        tom.addBalance(1000);
    }

    @Test
    public void testSetEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            person.setEmail("yolo@noob.home");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            person.setEmail("y@noob.no");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            person.setEmail("yolo@n.com");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            person.setEmail("yolo(at)noob.net");
        });
        assertThrows(NullPointerException.class, () -> {
            person.setEmail(null);
        });
        person.setEmail("yolo.noob@noob.com");
        assertEquals("yolo.noob@noob.com", person.getEmail());
        tom.setEmail("tom@richpeople.com");
        assertEquals("tom@richpeople.com", tom.getEmail());
    }

    @Test
    public void testPay() {
        HotelRoom room = mock(HotelRoom.class);

        double balanceBefore = tom.getBalance();
        
        LocalDate start = LocalDate.of(2021,7,6);
        LocalDate end = LocalDate.of(2021, 7, 7);
        when(room.isAvailable(start, end)).thenReturn(true);
        when(room.getPrice(start, end)).thenReturn(100.0);
        when(room.getPrice()).thenReturn(100.0);
        
        tom.makeReservation(room, LocalDate.of(2021, 7, 6), LocalDate.of(2021, 7, 7));
        assertEquals(balanceBefore-room.getPrice(), tom.getBalance(), "Booking one night should cost the same as the price of the hotel room.");

        assertThrows(IllegalStateException.class, () -> 
            person.makeReservation(room, LocalDate.of(2021, 7, 6), LocalDate.of(2021, 7, 7)),
            "Booking should only be possible when the hotel room is free."
        );

        when(room.isAvailable(LocalDate.of(2021, 8, 6), LocalDate.of(2021, 8 ,6))).thenReturn(true);
        when(room.getPrice(LocalDate.of(2021, 8, 6), LocalDate.of(2021, 8 ,6))).thenReturn(0.0);

        tom.makeReservation(room, LocalDate.of(2021, 8, 6), LocalDate.of(2021, 8, 6));
        assertEquals(900, tom.getBalance(), "Booking 0 days should not cost money.");

        assertThrows(IllegalArgumentException.class, () -> 
            tom.makeReservation(room, LocalDate.of(2022, 4, 3), LocalDate.of(2022, 4, 1)),
            "Booking must conform to the linear passing of time."
        );
    }
    @Test
    public void testReservationConstistency() {
        HotelRoom deluxeRoom = new HotelRoom(HotelRoomType.Suite, 900); // The room is on the 9th floor.
        tom.makeReservation(deluxeRoom, today, overmorrow);
        assertEquals(1, tom.getReservationIds().size(), "User should have one reservation after booking one room.");
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        tom.getReservations().forEach((r) -> reservations.add(r));
        assertEquals(deluxeRoom, reservations.get(0).getRoom());
    }

    @Test
    public void testName() {
        assertThrows(IllegalArgumentException.class, () -> new Person(""));
    }
}
