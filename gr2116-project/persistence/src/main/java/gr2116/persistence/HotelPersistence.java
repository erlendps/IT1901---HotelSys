package gr2116.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;

import gr2116.core.Hotel;
import gr2116.persistence.internal.HotelModule;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * HotelPersistence class. Responsible for saving/reading.
 */
public class HotelPersistence {
  private ObjectMapper mapper;
  protected static final String DATA_FOLDER
      = Paths.get(".").toAbsolutePath()
      .normalize().getParent().getParent().resolve("data").toString();

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

  /**
   * Helper method that uses the ObjectMapper to return a Hotel object from json.
   *
   * @param reader the IOstream used.
   *
   * @return Hotel object
   *
   * @throws IOException if something went wrong with I/O
   */
  private final Hotel readHotel(Reader reader) throws IOException {
    return mapper.readValue(reader, Hotel.class);
  }

  /**
   * Helper method that used ObjectMapper to write a Hotel object to json with
   * pretty print.
   *
   * @param hotel the Hotel to be written
   * @param writer the I/O stream to be used.
   *
   * @throws IOException if something went wrong with I/O
   */
  private final void writeHotel(Hotel hotel, Writer writer) throws IOException {
    mapper.writerWithDefaultPrettyPrinter().writeValue(writer, hotel);
  }

  /**
   * Loads from file a Hotel object with the given prefix.
   *
   * @param prefix the prefix of the file, eg "data" or "test"
   *
   * @return Hotel object
   *
   * @throws IOException if something went wrong with I/O
   */
  public final Hotel loadHotel(String prefix) throws IOException {
    if (prefix == null) {
      throw new IllegalArgumentException("Prefix is null.");
    }
    try (Reader reader = new FileReader(
        Paths.get(DATA_FOLDER, prefix + "Hotel.json").toFile(),
        StandardCharsets.UTF_8)) {
      return readHotel(reader);
    }
  }

  /**
   * Saves a Hotel object to file with the given prefix.
   *
   * @param hotel the Hotel to be saved
   * @param prefix the prefix of the file
   *
   * @throws IOException if something went wrong with I/O
   */
  public final void saveHotel(Hotel hotel, String prefix) throws IOException {
    if (prefix == null) {
      throw new IllegalArgumentException("Prefix is null.");
    }
    if (hotel == null) {
      throw new IllegalArgumentException("Hotel is null.");
    }
    try (Writer writer = new FileWriter(
        Paths.get(DATA_FOLDER, prefix + "Hotel.json").toFile(),
        StandardCharsets.UTF_8)) {
      writeHotel(hotel, writer);
    }
  }
}
