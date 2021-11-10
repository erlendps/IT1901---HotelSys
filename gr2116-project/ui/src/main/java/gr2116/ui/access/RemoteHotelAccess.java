package gr2116.ui.access;

import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomFilter;
import gr2116.core.Person;
import gr2116.persistence.HotelPersistence;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 
 */
public class RemoteHotelAccess implements HotelAccess {
  private final HotelPersistence hotelPersistence;
  private final URI endpointBaseUri;
  private ObjectMapper mapper;
  private Hotel hotel;

  public RemoteHotelAccess(URI endpointBaseUri) {
    hotelPersistence = new HotelPersistence();
    this.endpointBaseUri = endpointBaseUri;
    mapper = HotelPersistence.createObjectMapper();
  }

  private String uriParam(String s) {
    return URLEncoder.encode(s, StandardCharsets.UTF_8);
  }

  private URI personUri(String name) {
    return endpointBaseUri.resolve("person").resolve(uriParam(name));
  }

  private Hotel getHotel() {
    if (hotel == null) {
      HttpRequest request = HttpRequest.newBuilder(endpointBaseUri)
          .header("Accepct", "application/json")
          .GET()
          .build();
      try {
        final HttpResponse<String> response = 
            HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        this.hotel = mapper.readValue(response.body(), Hotel.class);
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    return hotel;
  }

  private boolean putPerson(Person person) {
    try {
      String json = mapper.writeValueAsString(person);
      HttpRequest request = HttpRequest.newBuilder(personUri(person.getUsername()))
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .PUT(BodyPublishers.ofString(json))
          .build();
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      
      Boolean added = mapper.readValue(response.body(), Boolean.class);
      if (added != null) {
        return true;
      }
      return false;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void addPerson(Person person) {
    if (putPerson(person)) {
      getHotel().addPerson(person);
    } 
  }

  @Override
  public Collection<Person> getPersons() {
    return getHotel().getPersons();
  }

  @Override
  public Collection<HotelRoom> getRooms(HotelRoomFilter hotelRoomFilter) {
    return getHotel().getRooms(hotelRoomFilter);
  }

  @Override
  public void loadHotel() {
    try {
    hotelPersistence.loadHotel();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void saveHotel() {
    try {
    hotelPersistence.saveHotel(hotel);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private URI roomUri(Integer number) {
    return endpointBaseUri.resolve("rooms").resolve(uriParam(number.toString()));
  }

  private boolean putRoom(HotelRoom room) {
    try {
      String json = mapper.writeValueAsString(room);
      HttpRequest request = HttpRequest.newBuilder(roomUri(room.getNumber()))
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .PUT(BodyPublishers.ofString(json))
          .build();
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      Boolean added = mapper.readValue(response.body(), Boolean.class);
      if (added != null) {
        return true;
      }
      return false;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void makeReservation(Person person, int hotelRoomNumber,
      LocalDate startDate, LocalDate endDate) {
    getHotel().makeReservation(person, hotelRoom, startDate, endDate);
    putPerson(person);
    putRoom(hotelRoom);
  }

  @Override
  public void addBalance(Person person, double amount) {
    person.addBalance(amount);
    putPerson(person);
  }

}
