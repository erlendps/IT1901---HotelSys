package gr2116.RESTservice.restserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr2116.persistence.HotelPersistence;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

/**
 * Provides the Jackson module used for JSON serialization.
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HotelModuleObjectMapperProvider implements ContextResolver<ObjectMapper> {
  private final ObjectMapper objectMapper;

  public HotelModuleObjectMapperProvider() {
    objectMapper = HotelPersistence.createObjectMapper();
  }

  @Override
  public ObjectMapper getContext(final Class<?> type) {
    return objectMapper;
  }
}
