package gr2116.core;

public enum HotelRoomType {
	Single("Single room", "A room for one."),
	Double("Double room", "A room for two"),
	Triple("Tripple room", "A  room for three."),
	Quad("Qaud room", "A room for four."),
	Suite("Suite", "Separate living area and bedroom."),
	Penthouse("Penthouse", "A penthouse with a beautiful view."); 
	
	private final String name;
	private final String description;

	HotelRoomType(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		return getName();
	}
}
