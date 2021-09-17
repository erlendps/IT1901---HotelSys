public enum HotelRoomType {
	Single("Enkeltrom"),
	Double("Dobbeltrom"),
	Triple("Tremannsrom"),
	Quad("Firmannsrom"),
	Suite("Suite"), //Er det noe bedre?
	Penthouse("Toppleilighet"); // Er det noe bedre?
	
	private final String description;

	HotelRoomType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}
