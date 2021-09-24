package gr2116.core;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {
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
        assertEquals("tom@richpeople.com", person.getEmail());
    }

    @Test
    public void testPay() {
        HotelRoom room = new HotelRoom(HotelRoomType.Double, 100);
        room.setPrice(100);
        tom.makeReservation(room, LocalDate.of(2021, 7, 6), LocalDate.of(2021, 7, 7));
        assertEquals(900, tom.getBalance());
    }
}
