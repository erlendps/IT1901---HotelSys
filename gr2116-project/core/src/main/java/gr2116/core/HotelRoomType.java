package gr2116.core;

/**
 * RoomType enum. Contains the different types of rooms a room can be.
 */
public enum HotelRoomType {
  /**
   * Single hotel room type.
   */
  Single("Single room", "A room for one."),
  /**
   * Double hotel room type.
   */
  Double("Double room", "A room for two"),
  /**
   * Triple hotel room type.
   */
  Triple("Triple room", "A  room for three."),
  /**
   * Quad hotel room type.
   */
  Quad("Qaud room", "A room for four."),
  /**
   * Suite hotel room type.
   */
  Suite("Suite", "Separate living area and bedroom."),
  /**
   * Penthouse hotel room type.
   */
  Penthouse("Penthouse", "A penthouse with a beautiful view.");

  /**
   * The display name.
   */
  private final String name;
  /**
   * The description, explaining the hotel room type in detail.
   */
  private final String description;

  HotelRoomType(final String name, final String description) {
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
