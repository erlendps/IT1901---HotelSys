package gr2116.core;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;


public class HotelRoom {
	private final Collection<Amenity> amenities = new HashSet<Amenity>();
	private final ReservationCalendar calendar = new ReservationCalendar();
	private final HotelRoomType roomType;
    private final int number;
	private double price;

	public HotelRoom(HotelRoomType roomType, int number) {
		if (roomType == null) {
			throw new NullPointerException();
		}
		this.roomType = roomType;
		this.number = number;
	}
	
	public HotelRoomType getRoomType() {
		return roomType;
	}

	public int getFloor() {
		// Gets which floor the room is on, which is specified by the first digit of the room number.
		return Integer.parseInt(Integer.toString(number).substring(0,1));
	}

	public int getNumber() {
		return number;
	}

	public double getPrice() {
		return price;
	}
	
	public double getPrice(LocalDate startDate, LocalDate endDate) {
		verifyChronology(startDate, endDate);
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

	public Collection<String> getAmenities() {
		return amenities.stream().map((a) -> a.name()).collect(Collectors.toList());
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
	
	public boolean isAvailable(LocalDate date) {
		return calendar.isAvailable(date);
	}

	public boolean isAvailable(LocalDate startDate, LocalDate endDate) {
		verifyChronology(startDate, endDate);
		return calendar.isAvailable(startDate, endDate);
	}

	public void addReservation(Reservation reservation) {
		if (reservation == null) {
			throw new NullPointerException("Reservation can not be null");
		}

		if (reservation.getRoom() != this) {
			throw new IllegalArgumentException("Reservation on room " + 
			Integer.toString(reservation.getRoom().getNumber()) + 
			" can not be registred on room " + 
			Integer.toString(getNumber()));
		}

		calendar.addReservation(reservation);
	}

	public Collection<Long> getReservationIds() {
		return calendar.getReservationIds();
	}

	private void verifyChronology(LocalDate startDate, LocalDate endDate) {
		if (startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("The startDate cannot be after the endDate.");
		}
	}
}
