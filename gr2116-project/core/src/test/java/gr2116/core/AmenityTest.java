package gr2116.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AmenityTest {

  @Test
  public void testAmenity() {
    assertEquals("Fridge", Amenity.Fridge.getName());
    assertEquals("A washing machine", Amenity.WashingMachine.getName());
    assertEquals("A Dryer with high-speed setting", Amenity.Dryer.getDescription());
  }
}
