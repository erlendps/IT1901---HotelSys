package gr2116.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import gr2116.core.Amenity;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;



/**
 * Class to generate rooms for demoing of the app.
 */
public class RoomGenerator {
  private static Random random = new Random();
  private static int[] roomNumbers = new int[7];
  
  /**
   * Create a new RoomGenerator object. This is not necessary,
   * as the methods are supposed to be accessed statically.
   */
  public RoomGenerator() {
  }

  /**
   * Generate <i>amount</i> different rooms and return them as a collection.
   * @param amount The amount of rooms to generate
   * @return A collection of HotelRoom objects
   */
  public static Collection<HotelRoom> generateRooms(int amount) {
    
    Collection<HotelRoom> rooms = new ArrayList<>();
    // Set room numbers to start at 100, 200, 300..
    for (int i = 0; i < 7; i++) {
      roomNumbers[i] = (i+1)*100;
    }

    for (int i = 0; i < amount; i++) {

      // Put room on a random floor (1 <= x <= 7) and get the next available room number on this floor
      int floor = random.nextInt(7) + 1;
      int number = getNextNumber(floor);
      
      // Get a random room type
      HotelRoomType type = getRoomType();

      // Make the room object from the generated data and set the price accordingly
      HotelRoom room = new HotelRoom(type, number);
      setPrice(room);
      
      // Add a random number of amenities
      int maxNumOfAmenities = Math.abs(random.nextInt() % 8);
      List<Amenity> amenities = new ArrayList<>(Arrays.asList(Amenity.values()));
      for (int j = maxNumOfAmenities; j > 0; j--) {
        int index = random.nextInt(8);

        // The maxNumOfAmenities only puts an upper bound on the number, as some might be added multiple times.
        // This behaviour is okay, as it does not really matter what number of amenities a room has.
        // In that case, the amenity will still only be listed once - see implementation of addAmenity.
        room.addAmenity(amenities.get(index));
      }
      rooms.add(room);
    }
    return rooms;
  }

  /**
   * Returns a random room type.
   * @return A hotel room type from the HotelRoomType enum.
   */
  private static HotelRoomType getRoomType() {
    int num = random.nextInt(6);
    switch (num) {
      case 0:
        return HotelRoomType.Single;
      case 1:
        return HotelRoomType.Double;
      case 2:
        return HotelRoomType.Triple;
      case 3:
        return HotelRoomType.Quad;
      case 4:
        return HotelRoomType.Suite;
      case 5:
        return HotelRoomType.Penthouse;
      default:
        // This should of course never happen
        throw new IllegalStateException("Implementation of random is broken - room type was set to a number x: x < 0 or x > 5");
    }
  }

  /**
   * Set the price of the room according to the room type.
   * Fancier rooms like Suites or Penthouses are more expensive than simple rooms.
   * @param room Room to set price for
   */
  private static void setPrice(HotelRoom room) {
    switch (room.getRoomType()) {
    case Double:
      room.setPrice(roundUp50(getRandomNumber(200, 500)));
      break;
    case Penthouse:
      room.setPrice(roundUp50(getRandomNumber(1300, 2000)));
      break;
    case Quad:
      room.setPrice(roundUp50(getRandomNumber(600, 1000)));
      break;
    case Single:
      room.setPrice(roundUp50(getRandomNumber(100, 300)));
      break;
    case Suite:
      room.setPrice(roundUp50(getRandomNumber(900, 1400)));
      break;
    case Triple:
      room.setPrice(roundUp50(getRandomNumber(400, 700)));
      break;
    default:
      throw new IllegalArgumentException("Room did not have a valid room type.");
    }
  }

  /**
   * Get a random integer between min and max
   * @param min Lower bound for random number
   * @param max 
   * @return
   */
  private static int getRandomNumber(int min, int max) {
    return random.nextInt(max - min) + min;
  }

  /**
   * Round to 'nearest' ceiling 50
   * @param x Number to round
   * @return Rounded number
   */
  private static double roundUp50(int x) {
    if (x%50 < 25) {
      return x - (x%50); 
    }
    else if (x%50 > 25) {
        return x + (50 - (x%50)); 
    }
    else 
        return x + 25; 
  }

  /**
   * Get the next available room number for floor <i>floor</i>.
   * For instance, with parameter 1, the rooms 101, 102, 103 ... will be generated in succession.
   * @param floor The floor to get a room number for
   * @return An available room number
   */
  private static int getNextNumber(int floor) {
    if (1 <= floor && floor <= 7) {
      roomNumbers[floor-1]++;
      return roomNumbers[floor-1];
    } else {
      throw new IllegalArgumentException("Floor must satisfy x: 1 ≤ x ≤ 7");
    }
  }
}