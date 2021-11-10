package gr2116.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import gr2116.core.Amenity;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;




public class RoomGenerator {
  private static Random random = new Random();
  private static int one = 100;
  private static int two = 200;
  private static int three = 300;
  private static int four = 400;
  private static int five = 500;
  private static int six = 600;
  private static int seven = 700;
  

  public RoomGenerator() {

  }

  public static Collection<HotelRoom> generateRooms() {
    Collection<HotelRoom> rooms = new ArrayList<>();
    for (int i = 0; i < 30; i++) {
      int floor = (int) (random.nextDouble()*7) + 1;
      int number = getNextNumber(floor);
      HotelRoomType type = getRoomType();
      HotelRoom room = new HotelRoom(type, number);
      setPrice(room);
      int numOfAmenities = (int) (random.nextDouble() * 8) + 1;
      List<Amenity> amenities = new ArrayList<>(Arrays.asList(Amenity.values()));
      for (int j = numOfAmenities; j > 0; j--) {
        int index = (int) (random.nextDouble() * j);
        room.addAmenity(amenities.get(index));
      }
      rooms.add(room);
    }
    return rooms;
  }

  private static HotelRoomType getRoomType() {
    int num = (int) (random.nextDouble()*6);
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
        return HotelRoomType.Single;
    }
  }

  private static void setPrice(HotelRoom room) {
    switch (room.getRoomType()) {
    case Double:
      room.setPrice(roundUp(getRandomNumber(200, 500)));
      break;
    case Penthouse:
      room.setPrice(roundUp(getRandomNumber(1300, 2000)));
      break;
    case Quad:
      room.setPrice(roundUp(getRandomNumber(600, 1000)));
      break;
    case Single:
      room.setPrice(roundUp(getRandomNumber(100, 300)));
      break;
    case Suite:
      room.setPrice(roundUp(getRandomNumber(900, 1400)));
      break;
    case Triple:
      room.setPrice(roundUp(getRandomNumber(400, 700)));
      break;
    default:
      break;
    }
  }

  private static int getRandomNumber(int min, int max) {
    return random.nextInt(max - min) + min;
  }


  private static double roundUp(int x) {
    if (x%50 < 25) {
      return x - (x%50); 
    }
    else if (x%50 > 25) {
        return x + (50 - (x%50)); 
    }
    else 
        return x + 25; 
  }


  private static int getNextNumber(int floor) {
    switch (floor) {
      case 1:
        one++;
        return one;
      case 2:
        two++;
        return two;
      case 3:
        three++;
        return three;
      case 4:
        four++;
        return four;
      case 5:
        five++;
        return five;
      case 6:
        six++;
        return six;
      case 7:
        seven++;
        return seven;
      default:
        seven++;
        return seven;
    }
  }
}