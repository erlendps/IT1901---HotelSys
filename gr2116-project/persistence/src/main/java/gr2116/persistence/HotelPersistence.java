package gr2116.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr2116.core.Hotel;
import gr2116.persistence.internal.HotelModule;
import java.io.File;
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
  private String dataFolder;
  private String prefix;

  /**
   * Constructor, creates an object mapper with our custom HotelModule.
   *
   * @throws IllegalStateException if the HotelSys directory couldn't be created
   */
  public HotelPersistence() {
    mapper = createObjectMapper();
    File directory = new File(System.getProperty("user.home"), "HotelSys");
    if (!directory.exists()) {
      if (!directory.mkdir()) {
        throw new IllegalStateException("HotelSys directory couldn't be created.");
      }
    }
    dataFolder = Paths.get(directory.toString()).toString();
  }

  /**
   * Constructor, creates an object mapper with our custom HotelModule.
   * Also sets a prefix.
   *
   * @param prefix the prefix to be set
   */
  public HotelPersistence(String prefix) {
    this();
    setPrefix(prefix);
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
   * @return a new instance of a ObjectMapper, registered with a HotelModule
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
   * @return Hotel object
   *
   * @throws IOException if something went wrong with I/O
   */
  public final Hotel loadHotel() throws IOException {
    if (prefix == null) {
      throw new IllegalArgumentException("Prefix is null.");
    }
    try (Reader reader = new FileReader(
        Paths.get(dataFolder, prefix + "Hotel.json").toFile(),
        StandardCharsets.UTF_8)) {
      return readHotel(reader);
    } catch (IOException e) {
      System.err.println("Could not find " + dataFolder + "/" + prefix + "Hotel.json");
      return new Hotel(RoomGenerator.generateRooms(30)); // Generate 30 rooms, can be changed
    }
  }

  /**
   * Saves a Hotel object to file with the given prefix.
   *
   * @param hotel the Hotel to be saved
   *
   * @throws IOException if something went wrong with I/O
   */
  public final void saveHotel(Hotel hotel) throws IOException {
    if (prefix == null) {
      throw new IllegalArgumentException("Prefix is null.");
    }
    if (hotel == null) {
      throw new IllegalArgumentException("Hotel is null.");
    }
    try (Writer writer = new FileWriter(
        Paths.get(dataFolder, prefix + "Hotel.json").toFile(),
        StandardCharsets.UTF_8)) {
      writeHotel(hotel, writer);
    }
  }

  /**
   * Sets the data filename prefix.
   *
   * @param prefix The prefix the be set, eg. "data" or "test"
   */
  public void setPrefix(String prefix) {
    if (!prefix.matches("^([a-z]){3,10}([A-Z]{1}[a-z]{1,8})*$")) {
      throw new IllegalArgumentException("prefix is not valid");
    }
    this.prefix = prefix;
  }

  public String getPrefix() {
    return prefix;
  }
}
