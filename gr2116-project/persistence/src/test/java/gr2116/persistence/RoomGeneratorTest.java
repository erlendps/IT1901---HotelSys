package gr2116.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import gr2116.core.HotelRoom;
import java.util.Collection;
import org.junit.jupiter.api.Test;

/**
 * Tests RoomGenerator.
 */
public class RoomGeneratorTest {

  @Test
  public void testGeneratedRooms() {
    Collection<HotelRoom> rooms = RoomGenerator.generateRooms(50);
    assertEquals(50, rooms.size());
    for (HotelRoom room : rooms) {
      assertTrue(room.getAmenities().size() > 0);
      assertTrue(0 < room.getFloor() && room.getFloor() < 8);
    }
  }
}
