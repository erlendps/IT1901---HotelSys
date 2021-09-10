public enum Amenity {
	KitchenFacilities(""),
	Television(""),
	Internet(""),
	WashingMachine(""),
	Dryer(""),
	Shower(""),
	Bathtub(""),
	Fridge(""); // TODO: Write descriptions

	private final String description;

	Amenity(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}
