package gr2116.core;

public enum HotelRoomType {
	Single("Enkeltrom", "Et rom for én."),
	Double("Dobbeltrom", "Et rom for to."),
	Triple("Tremannsrom", "Et rom for tre."),
	Quad("Firmannsrom", "Et rom for fire."),
	Suite("Suite", "En romslig suite."), //Er det noe bedre?
	Penthouse("Toppleilighet", "En toppleilighet med vakker utsikt."); // Er det noe bedre?
	
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
