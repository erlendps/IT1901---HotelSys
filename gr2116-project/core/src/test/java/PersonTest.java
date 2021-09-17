import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {
    Person person;

    @BeforeEach
    public void setup() {
        person = new Person("Mr. Game and Watch");
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
    }
}
