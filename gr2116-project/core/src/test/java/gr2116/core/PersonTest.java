package gr2116.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonTest {
  private LocalDate today = LocalDate.now();
  private LocalDate overmorrow = today.plusDays(2);

  private Person person;
  private Person tom;

  @BeforeEach
  public final void setup() {
    person = new Person("Mr. Game and Watch");
    tom = new Person("Tom Haddleford");
    tom.addBalance(1000);
  }

  @Test
  public void testSetUsername() {
    assertThrows(IllegalArgumentException.class, () -> {
      person.setUsername("yolo!");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      person.setUsername("y sdf");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      person.setUsername("yolo_K");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      person.setUsername("yolo(23");
    });
    assertThrows(NullPointerException.class, () -> {
      person.setUsername(null);
    });
    person.setUsername("yolonoob");
    assertEquals("yolonoob", person.getUsername());
    tom.setUsername("tom");
    assertEquals("tom", tom.getUsername());
  }

    @Test
    public void testPay() {
        HotelRoom room = mock(HotelRoom.class);

        double balanceBefore = tom.getBalance();
        
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(1);
        when(room.isAvailable(start, end)).thenReturn(true);
        when(room.getPrice(start, end)).thenReturn(100.0);
        when(room.getPrice()).thenReturn(100.0);
        
        tom.makeReservation(room, start, end);
        assertEquals(balanceBefore-room.getPrice(), tom.getBalance(), "Booking one night should cost the same as the price of the hotel room.");

        when(room.isAvailable(start.plusDays(2), start.plusDays(2))).thenReturn(true);
        when(room.getPrice(start.plusDays(2), start.plusDays(2))).thenReturn(0.0);
        /*
        tom.makeReservation(room, LocalDate.of(2021, 8, 6), LocalDate.of(2021, 8, 6));
        assertEquals(900, tom.getBalance(), "Booking 0 days should not cost money.");
        */
        assertThrows(IllegalArgumentException.class, () -> 
            tom.makeReservation(room, LocalDate.of(2022, 4, 3), LocalDate.of(2022, 4, 1)),
            "Booking must conform to the linear passing of time."
        );
    }
    @Test
    public void testReservationConstistency() {
        //HotelRoom deluxeRoom = new HotelRoom(HotelRoomType.Suite, 900); // The room is on the 9th floor.
        HotelRoom deluxeRoom = mock(HotelRoom.class);
        when(deluxeRoom.isAvailable(today, overmorrow)).thenReturn(true);
        when(deluxeRoom.getPrice(today, overmorrow)).thenReturn(900.0);
        when(deluxeRoom.getNumber()).thenReturn(105);

        tom.makeReservation(deluxeRoom, today, overmorrow);
        assertEquals(1, tom.getReservationIds().size(), "User should have one reservation after booking one room.");
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        tom.getReservations().forEach((r) -> reservations.add(r));
        assertEquals(deluxeRoom.getNumber(), reservations.get(0).getRoomNumber());
    }

  @Test
  public void testName() {
    assertThrows(IllegalArgumentException.class, () -> new Person(""));
  }
}
