public enum HotelRoomType {
	Single(""),
	Double(""),
	Triple(""),
	Quad(""),
	Suite(""),
	Penthouse(""); // TODO: Write descriptions
	
	private final String description;

	HotelRoomType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}
