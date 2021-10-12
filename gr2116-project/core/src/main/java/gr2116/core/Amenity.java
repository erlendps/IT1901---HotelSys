package gr2116.core;

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

  Amenity(final String name, final String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * Returns the display name.
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the description.
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
