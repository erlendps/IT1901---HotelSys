package gr2116.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {

  @BeforeEach
  public final void setup() {
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
    Person person;
    person = new Person("yolonoob");
    assertEquals("yolonoob", person.getUsername());
    person = new Person("tom");
    assertEquals("tom", person.getUsername());
  }

  @Test
  public void testName() { //TODO: extend test
    assertThrows(IllegalArgumentException.class, () -> new Person(""));
  }
}
