import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

public class HotelRoom {
	private final Collection<Amenity> amenities = new HashSet<Amenity>();
	private final ReservationCalendar calendar = new ReservationCalendar();
	private final HotelRoomType roomType;
	private final int floor;
    private final int number;
	private double price;

	public HotelRoom(HotelRoomType roomType, int floor, int number) {
		if (roomType == null) {
			throw new NullPointerException();
		}
		this.roomType = roomType;
		this.floor = floor;
		this.number = number;
	}
	
	public HotelRoomType getRoomType() {
		return roomType;
	}

	public int getFloor() {
		return floor;
	}

	public int getNumber() {
		return number;
	}

	public double getPrice() {
		return price;
	}
	
	public double getPrice(LocalDate startDate, LocalDate endDate) {
		checkChronology(startDate, endDate);
		return price * (1 + startDate.until(endDate).getDays());
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void addAmenity(Amenity amenity) {
		if (amenity == null) {
			throw new NullPointerException();
		}
		amenities.add(amenity);
	}

	public void removeAmenity(Amenity amenity) {
		if (!amenities.contains(amenity)) {
			throw new IllegalArgumentException();
		}
		amenities.remove(amenity);
	}

	public boolean hasAmenity(Amenity amenity) {
		return amenities.contains(amenity);
	}

	public void makeReservation(Person person, LocalDate startDate, LocalDate endDate) {
		if (person == null || startDate == null || endDate == null) {
			throw new NullPointerException();
		}
		checkChronology(startDate, endDate);
		double price = getPrice(startDate, endDate);
		if (price > person.getBalance()) {
			throw new IllegalStateException("The person cannot afford this reservation.");
		}
		if (!isAvailable(startDate, endDate)) {
			throw new IllegalStateException("The room is not available at this time.");
		}
		Reservation reservation = new Reservation(this, startDate, endDate);
		calendar.addReservation(reservation);
		person.addReservation(reservation);
		person.subtractBalance(price);
	}
	
	public boolean isAvailable(LocalDate date) {
		return calendar.isAvailable(date);
	}

	public boolean isAvailable(LocalDate startDate, LocalDate endDate) {
		checkChronology(startDate, endDate);
		return calendar.isAvailable(startDate, endDate);
	}

	private void checkChronology(LocalDate startDate, LocalDate endDate) {
		if (startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("The startDate cannot be after the endDate.");
		}
	}
}
