package gr2116.restapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr2116.RESTservice.restapi.HotelService;
import gr2116.RESTservice.restserver.HotelConfig;
import gr2116.RESTservice.restserver.HotelModuleObjectMapperProvider;
import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collection;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 */
public class HotelServiceTest extends JerseyTest {
  private ObjectMapper mapper;

  @Override
  protected ResourceConfig configure() {
    final HotelConfig config = new HotelConfig();
    enable(TestProperties.LOG_TRAFFIC);
    enable(TestProperties.DUMP_ENTITY);
    config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
    return config;
  }

  @BeforeEach
  @Override
  public void setUp() throws Exception {
    super.setUp();
    mapper = new HotelModuleObjectMapperProvider().getContext(getClass());
  }

  @AfterEach
  @Override
  public void tearDown() throws Exception {
    super.tearDown();
  }

  @Test
  public void testGetHotel() {
    Response response = target(HotelService.HOTEL_SERVICE_PATH)
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
        .get();
    assertEquals(200, response.getStatus());
    try {
      // Simple methods to check if the hotel contains some correct data.
      // Test will also fail if data is not correctly found and loaded.
      Hotel hotel = mapper.readValue(response.readEntity(String.class), Hotel.class);
      assertEquals(30, hotel.getRooms().size());
      assertEquals(HotelRoom.class, hotel.getRooms().iterator().next().getClass());

    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void testGetRoom() {
    Response hotelResponse = target(HotelService.HOTEL_SERVICE_PATH)
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
        .get();
    assertEquals(200, hotelResponse.getStatus());
    try {
      Hotel hotel = mapper.readValue(hotelResponse.readEntity(String.class), Hotel.class);

      HotelRoom someRoom = hotel.getRooms().iterator().next();

      final int someRoomNumber = someRoom.getNumber();
      final double someRoomPrice = someRoom.getPrice();
      final HotelRoomType someRoomType = someRoom.getRoomType();
      final Collection<String> someRoomAmenities = someRoom.getAmenities();

      Response roomResponse =
          target(HotelService.HOTEL_SERVICE_PATH + "/rooms/" + Integer.toString(someRoomNumber))
          .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
          .get();
      HotelRoom receivedRoom = mapper.readValue(
          roomResponse.readEntity(String.class), HotelRoom.class);

      assertEquals(someRoomNumber, receivedRoom.getNumber());
      assertEquals(someRoomPrice, receivedRoom.getPrice());
      assertEquals(someRoomType, receivedRoom.getRoomType());
      assertEquals(someRoomAmenities, receivedRoom.getAmenities());

    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }
}
