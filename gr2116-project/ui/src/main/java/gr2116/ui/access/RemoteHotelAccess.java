package gr2116.ui.access;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collection;


/**
 * RemoteHotelAccess class that gets the access the Hotel with HTTP-requests.
 */
public class RemoteHotelAccess implements HotelAccess {
  private final HotelPersistence hotelPersistence;
  private final URI endpointBaseUri;
  private ObjectMapper mapper;
  private Hotel hotel;

  /**
   * Constructor for the RemoteHotelAccess object.
   *
   * @param hotelPersistence the hotelPersistence object that handles saving if requested
   * @param endpointBaseUri the endpoint URL, in our case this will be http://localhost:8080/rest/hotel
   */
  public RemoteHotelAccess(HotelPersistence hotelPersistence, URI endpointBaseUri) {
    this.hotelPersistence = hotelPersistence;
    this.endpointBaseUri = endpointBaseUri;
    mapper = HotelPersistence.createObjectMapper();
  }

  /**
   * Encodes the string s to URI string.
   *
   * @param s the string to be encoded
   *
   * @return the encoded string
   */
  private String uriParam(String s) {
    return URLEncoder.encode(s, StandardCharsets.UTF_8);
  }

  /**
   * Helper method that creates a URI for the person with the given username, eg for a person with
   * the username john, the method will return a URI: http://localhost:8080/rest/hotel/person/john.
   *
   * @param username the username to create URI for
   *
   * @return the URI for the username
   */
  private URI personUri(String username) {
    return endpointBaseUri.resolve("person/").resolve(uriParam(username));
  }

  /**
   * If this.hotel is null, the method sends a GET-request to the rest api and request the Hotel
   * object stored there. If it gets a valid response, it will set this.hotel to the hotel stored
   * in the database. Else, if this.hotel is <b>not</b> null it will return the this.hotel.
   *
   * @return either the hotel stored within this, or the hotel stored in the database
   *
   * @throws RunTimeException if something goes wrong with the response. Most likely since the 
   *                          rest server is offline.
   */
  private Hotel getHotel() {
    if (hotel == null) {
      HttpRequest request = HttpRequest.newBuilder(endpointBaseUri)
          .header("Accept", "application/json")
          .GET()
          .build();
      try {
        final HttpResponse<String> response = 
            HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        this.hotel = mapper.readValue(response.body(), Hotel.class);
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
        throw new RuntimeException(
            "Error reading from the REST API.\nPerhaps the server is offline?");
      }
    }
    return hotel;
  }

  /**
   * Updates this.hotel by sending a GET-request to the restserver.
   * This is used to sync the the hotel across multiple users using the app at the same time.
   *
   * @throws RunTimeException if something goes wrong with the response. Most likely since the 
   *                          rest server is offline.
   */
  public void updateHotel() {
    HttpRequest request = HttpRequest.newBuilder(endpointBaseUri)
        .header("Accept", "application/json")
        .GET()
        .build();
    try {
      final HttpResponse<String> response = 
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      this.hotel = mapper.readValue(response.body(), Hotel.class);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException(
          "Error reading from the REST API.\nPerhaps the server is offline?");
    }
  }

  /**
   * Sends a PUT-request to the rest server with the given person. The method translates the Person
   * object to json via the objectmapper and then sends the request. The method that handles
   * PUT-requests in PersonResource returns a boolean value, if that fails it will return null
   * and we can then check if this is null in this method. If this method returns null we know our
   * database has been altered, and if it returns false we know it has failed.
   * Each time a Person is changed, the entire record of this person in the database is updated.
   *
   * @param person the person to be PUT
   *
   * @return true if the person in the database was overwritten or added, and false if an error
   *          occured.
   *
   * @throws RunTimeException if something goes wrong with the response. Most likely since the 
   *                          rest server is offline. 
   */
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
      e.printStackTrace();
      throw new RuntimeException(
          "Error reading from the REST API.\nPerhaps the server is offline?");
    }
  }

  /**
   * Checks if putPerson() returns true, if it does it alters this.hotel by adding the person to the
   * hotel's map.
   */
  @Override
  public void addPerson(Person person) {
    if (putPerson(person)) {
      getHotel().addPerson(person);
    } 
  }

  /**
   * Returns the persons that have a user in this hotel.
   */
  @Override
  public Collection<Person> getPersons() {
    return getHotel().getPersons();
  }

  /**
   * Returns a sub-collection of this.hotel's rooms that passes the filter given in hotelRoomFilter.
   * Firstly this method updates this.hotel. This is done so that the hotels rooms are always
   * updated across multiple devices.
   */
  @Override
  public Collection<HotelRoom> getRooms(HotelRoomFilter hotelRoomFilter) {
    updateHotel();
    return getHotel().getRooms(hotelRoomFilter);
  }

  /**
   * Loads the hotel.
   */
  @Override
  public void loadHotel() {
    hotel = getHotel();
  }

  /**
   * Helper method that returns a URI for the room with the given number. E.g a room with number
   * 411, the method would return http://localhost:8080/rest/hotel/rooms/411.
   *
   * @param number the roomNumber to create URI for
   *
   * @return a URI for the given number
   */
  private URI roomUri(Integer number) {
    return endpointBaseUri.resolve("rooms/").resolve(uriParam(number.toString()));
  }

  /**
   * Sends a PUT-request to the rest server with the given HotelRoom. The method translates the
   * HotelRoom to json via the objectmapper and then sends the request. The method that handles
   * PUT-requests in RoomResource returns a boolean value, if that fails it will return null
   * and we can then check if this is null in this method. If this method returns null we know our
   * database has been altered, and if it returns false we know it has failed.
   * Each time a HotelRoom is changed, the entire record of this person in the database is updated.
   *
   * @param room the room to be PUT
   *
   * @return true if the room in the database was overwritten or added, and false if an error
   *          occured
   *
   * @throws RunTimeException if something goes wrong with the response. Most likely since the 
   *                          rest server is offline
   */
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
      e.printStackTrace();
      throw new RuntimeException(
          "Error reading from the REST API.\nPerhaps the server is offline?");
    }
  }

  /**
   * Makes a reservation with the given person, hotelRoomNumber, startDate and endDate.
   * Again, this method first updates the hotel to ensure that a room is not double-booked.
   * Then, it makes the reservation in the hotel object. If any validation fails here, the
   * reservation will not be made. If nothing goes wrong, it puts the person and room to the
   * database.
   *
   * @param person the person to make the reservation
   * @param hotelRoomNumber the roomNumber of the room that should be reserved
   * @param startDate the startDate of the reservation
   * @param endDate the endDate of the reservation
   */
  @Override
  public void makeReservation(Person person, int hotelRoomNumber,
      LocalDate startDate, LocalDate endDate) {
    updateHotel();
    getHotel().makeReservation(person, hotelRoomNumber, startDate, endDate);
    putPerson(person);

    // Find list of rooms with matching IDs (which should only be one) and put first
    putRoom(getHotel().getRooms((r) -> r.getNumber() == hotelRoomNumber).iterator().next());
  }

  /**
   * Adds the given amount to the person's balance. Then it sends a PUT-request to the
   * database with the putPerson method.
   */
  @Override
  public void addBalance(Person person, double amount) {
    person.addBalance(amount);
    putPerson(person);
  }
}
