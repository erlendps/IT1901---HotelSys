package gr2116.restapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gr2116.RESTservice.restapi.HotelService;
import gr2116.RESTservice.restserver.HotelConfig;
import gr2116.RESTservice.restserver.HotelModuleObjectMapperProvider;
import gr2116.core.Hotel;

public class HotelServiceTest extends JerseyTest {

  private ObjectMapper mapper;
  private static URI uri;

  @Override
  protected ResourceConfig configure() {
    final HotelConfig config = new HotelConfig();
    enable(TestProperties.LOG_TRAFFIC);
    enable(TestProperties.DUMP_ENTITY);
    config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
    return config;
  }

  @BeforeAll
  public static void setURI() throws URISyntaxException {
    uri = new URI("http://localhost:9998/rest/hotel");  
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
    HttpRequest request = HttpRequest.newBuilder(uri)
          .header("Accept", "application/json")
          .GET()
          .build();
    try {
      final HttpResponse<String> response = 
            HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      assertEquals(200, response.statusCode());
      Hotel hotel = mapper.readValue(response.body(), Hotel.class);
      System.out.println(hotel.getPersons().size());
      // TODO: Check if hotel contains correct stuff
    } catch (IOException e) {
      e.printStackTrace();
      fail(e.getMessage());
    } catch (InterruptedException e) {
      fail(e.getMessage());
    }
  }
}
