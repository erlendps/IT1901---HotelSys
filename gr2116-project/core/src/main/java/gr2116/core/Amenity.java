package gr2116.core;

/**
 * Amenity enum.
 * Contains all amenities that a room could have.
 */
public enum Amenity {
  /**
   * Kitchen facilities amenity.
   */
  KitchenFacilities("Kitchen Facilities",
    "A practical kitchen with a microwave, stovetop, sink and fridge."),
  /**
   * Television amenity.
   */
  Television("Television",
    "A high-resolution TV with a global selection of channels."),
  /**
   * Internet amenity.
   */
  Internet("Internet",
    "High-speed internet for your browsing needs."),
  /**
   * Washing machine amenity.
   */
  WashingMachine("A washing machine",
    "High-tech washing machine with any feature you might want."),
  /**
   * Dryer amenity.
   */
  Dryer("Dryer",
    "A Dryer with high-speed setting"),
  /**
   * Shower amenity.
   */
  Shower("Shower",
    "Shower with a selection of complimentary luxury soap."),
  /**
   * Bathtub amenity.
   */
  Bathtub("Bathtub",
    "A large bathttub for your spa needs."),
  /**
   * Fridge amenity.
   */
  Fridge("Fridge",
    "A small fridge loaded with fine wine and our selection of beer.");

  /**
   * The display name.
   */
  private final String name;
  /**
   * The description, explaining the amenity in detail.
   */
  private final String description;

  /**
   * Constructor for Amenity.
   *
   * @param name  the name of the amenity.
   * @param description   the description of the amenity.
   */
  Amenity(final String name, final String description) {
    if (name == null || description == null) {
      throw new NullPointerException("Name or description cannot be null.");
    }
    this.name = name;
    this.description = description;
  }

  /**
   * Returns the display name.
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the description.
   *
   * @return description
   */
  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return getName();
  }
}
