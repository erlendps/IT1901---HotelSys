package gr2116.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {
  private Person person;
  private Person tom;

  @BeforeEach
  public final void setup() {
    person = new Person("MrGameandWatch");
    tom = new Person("TomHaddleford");
    tom.addBalance(1000);
  }

  // TODO: change tests, username is final, name is not.
  // @Test
  // public void testSetUsername() {
  //   assertThrows(IllegalArgumentException.class, () -> {
  //     person.setUsername("yolo!");
  //   });
  //   assertThrows(IllegalArgumentException.class, () -> {
  //     person.setUsername("y sdf");
  //   });
  //   assertThrows(IllegalArgumentException.class, () -> {
  //     person.setUsername("yolo_K");
  //   });
  //   assertThrows(IllegalArgumentException.class, () -> {
  //     person.setUsername("yolo(23");
  //   });
  //   assertThrows(IllegalArgumentException.class, () -> {
  //     person.setUsername(null);
  //   });
  //   person.setUsername("yolonoob");
  //   assertEquals("yolonoob", person.getUsername());
  //   tom.setUsername("tom");
  //   assertEquals("tom", tom.getUsername());
  // }

  @Test
  public void testName() {
    assertThrows(IllegalArgumentException.class, () -> new Person(""));
  }
}
