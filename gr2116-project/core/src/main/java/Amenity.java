public enum Amenity {
	KitchenFacilities("Kitchen Facilities", "A practical kitchen with a microwave, stovetop, sink and fridge."),
	Television("Television", "A high-resolution TV with a global selection of channels."),
	Internet("Internet", "High-speed internet for your browsing needs."),
	WashingMachine("A washing machine", "High-tech washing machine with any feature you might want."),
	Dryer("Dryer", "A Dryer with high-speed setting"),
	Shower("Shower", "Shower with a selection of complimentary luxury soap."),
	Bathtub("Bathtub", "A large bathttub for your spa needs."),
	Fridge("Fridge", "A small fridge loaded with fine wine and our selection of beer."); 

	private final String description;
	private final String name;

	Amenity(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}
}
