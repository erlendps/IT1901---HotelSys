package gr2116.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import gr2116.persistence.internal.HotelModule;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * HotelPersistence class. Responsible for saving/reading.
 */
public class HotelPersistence {
  private ObjectMapper mapper;

  /**
   * Constructor, creates an object mapper with our custom HotelModule.
   */
  public HotelPersistence() {
    mapper = createObjectMapper();
  }

  /**
   * Creates a new instance of HotelModule.
   *
   * @return HotelModule
   */
  public static final HotelModule createHotelModule() {
    return new HotelModule();
  }

  /**
   * Returns a new instance of a ObjectMapper, registered with a HotelModule.
   *
   * @return
   */
  public static final ObjectMapper createObjectMapper() {
    return new ObjectMapper().registerModule(createHotelModule());
  }

  


}
