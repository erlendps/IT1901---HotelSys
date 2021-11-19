package gr2116.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Person.
 */
public class PersonTest {
  Person tom;

  @BeforeEach
  public final void setup() {
    tom = new Person("tom");
  }

  @Test
  public void testUsername() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Person("yolo!");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new Person("y sdf");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new Person("yolo(23");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new Person(null);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new Person("");
    });
    Person
    person = new Person("yolonoob");
    assertEquals("yolonoob", person.getUsername());
    person = new Person("tom");
    assertEquals("tom", person.getUsername());
  }

  @Test
  public void testName() {
    assertThrows(IllegalArgumentException.class, () -> {
      tom.setFirstName("");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      tom.setLastName("");
    });
    tom.setFirstName("Thomas");
    assertEquals("Thomas", tom.getFirstName());
    tom.setLastName("Watford");
    assertEquals("Watford", tom.getLastName());
    assertEquals("Thomas Watford", tom.getName());
  }

  @Test
  public void testBalance() {
    assertEquals(0, tom.getBalance());
    tom.addBalance(100);
    assertEquals(100, tom.getBalance());
    assertThrows(IllegalArgumentException.class, () -> {
      tom.addBalance(-100);
    });
    tom.subtractBalance(50);
    assertEquals(50, tom.getBalance());
    assertThrows(IllegalArgumentException.class, () -> {
      tom.subtractBalance(-25);
    });
  }

  @Test
  public void testPassword() {
    tom.setHashedPassword("niefsshlk45bhod/");
    Person person = new Person("bruhman");
    assertThrows(IllegalStateException.class, () -> {
      tom.setHashedPassword("hahahahah");
    });
    assertFalse(Person.isValidPassword("kjdrA"));
    assertFalse(Person.isValidPassword(null));
    assertTrue(Person.isValidPassword("hahahahahah"));
    assertThrows(IllegalArgumentException.class, () -> {
      person.setPassword("jaja");
    });
    person.setPassword("jacoco");
    assertEquals(PasswordUtil.hashPassword("jacoco"), person.getHashedPassword());
  }

  @Test
  public void testListener() {
    PersonListener pl = mock(PersonListener.class);
    tom.addListener(pl);
    tom.notifyListeners();
    tom.removeListener(pl);
  }

  @Test
  public void testReservation() {
    Reservation res = mock(Reservation.class);
    assertThrows(IllegalArgumentException.class, () -> {
      tom.addReservation(null);
    });
    tom.addReservation(res);
    assertTrue(tom.hasReservation(res));
    assertEquals(res, tom.getReservations().iterator().next());
    when(res.getId()).thenReturn("19523");
    assertEquals(Arrays.asList("19523"), tom.getReservationIds());
  }

  @Test 
  public void testEquals() {
    assertTrue(tom.equals(tom));
    assertFalse(tom.equals(null));
    assertFalse(tom.equals(new Object()));
    Person person = new Person("tom");
    tom.setFirstName("Thomas");
    tom.setLastName("Watford");
    assertFalse(tom.equals(person));
    person.setFirstName("Thomas");
    person.setLastName("Hallow");
    assertFalse(tom.equals(person));
    person.setLastName("Watford");
    assertTrue(tom.equals(person));
    Reservation res = mock(Reservation.class);
    tom.addReservation(res);
    assertFalse(tom.equals(person));
    person.addReservation(res);
    assertTrue(tom.equals(person));
  }

  @Test
  public void testHash() {
    tom.setFirstName("Thomas");
    tom.setFirstName("Watford");
    int nameHash = tom.getName().hashCode();
    int userHash = tom.getUsername().hashCode();
    int resHash = tom.getReservations().hashCode();
    int hash = 5;
    hash = hash * 17 + nameHash;
    hash = hash * 31 + userHash;
    hash = hash * 5 + resHash;
    assertEquals(hash, tom.hashCode());
  }
}
