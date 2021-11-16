package gr2116.RESTservice.restserver;

import gr2116.RESTservice.restapi.HotelService;
import gr2116.core.Hotel;
import gr2116.persistence.HotelPersistence;
import java.io.IOException;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configures the rest service,
 * e.g. JSON support with Jackson and
 * injectable Hotel and HotelPersistance
 */
public class HotelConfig extends ResourceConfig {
  private Hotel hotel;
  private HotelPersistence hotelPersistence;
  
  /**
  * Initialize this HotelConfig.
  *
  * @param hotel hotel instance to serve
  */
  public HotelConfig(Hotel hotel) {
    setHotel(hotel);
    hotelPersistence = new HotelPersistence("data");
    // todoPersistence.setSaveFile("server-todolist.json");
    register(HotelService.class);
    register(HotelModuleObjectMapperProvider.class);
    register(JacksonFeature.class);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(HotelConfig.this.hotel);
        bind(HotelConfig.this.hotelPersistence);
      }
    });
  }

  /**
   * Constructor that initializes this HotelConfig with a the hotel created from
   * the createHotel() method.
   */
  public HotelConfig() {
    this(createHotel());
  }

  /**
   * Sets this HotelConfig's hotel.
   *
   * @param hotel the hotel to be set
   */
  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }

  /**
   * Returns the hotel.
   *
   * @return this hotel
   */
  public Hotel getHotel() {
    return hotel;
  }

  /**
   * It first tries to load an already saved hotel. If this fails, hotelPersistence will
   * return a default (random) empty hotel initiated with 30 or so rooms.
   *
   * @return hotel
   */
  public static Hotel createHotel() {
    HotelPersistence hotelPersistence = new HotelPersistence("data");
    try {
      return hotelPersistence.loadHotel();
    } catch (IOException e) {
      System.err.println("Hotel was not correctly loaded in hotelPersistence.");
      return null;
    }
  }
}

