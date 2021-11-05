package gr2116.RESTservice.restserver;

import com.fasterxml.jackson.core.util.JacksonFeature;
import gr2116.RESTservice.restapi.HotelService;
import gr2116.core.Hotel;
import gr2116.persistence.HotelPersistence;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
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
    hotelPersistence = new HotelPersistence();
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

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }

  public Hotel getHotel() {
    return hotel;
  }
}
