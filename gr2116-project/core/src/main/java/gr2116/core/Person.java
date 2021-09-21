package gr2116.core;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.Random;


public class Person {
	private final Collection<Reservation> reservations = new HashSet<>();
    private final String name;
	private String email;
	private double balance = 0;

	public Person(String name) {
		if (name == null) {
			throw new NullPointerException();
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		String regex = "^[a-zA-Z0-9._-]{2,20}@[a-zA-Z0-9.]{2,20}.(no|com|net|org)$";
		if (email == null) {
			throw new NullPointerException("Email is null");
		}
		if (!Pattern.matches(regex, email)){
			throw new IllegalArgumentException("The email is not valid");
		}
		this.email = email;
	}

	public double getBalance() {
		return balance;
	}

	public void addBalance(double balance) {
		this.balance += balance;
	}

	public void subtractBalance(double balance) {
		this.balance -= balance;
	}
	
	public void makeReservation(HotelRoom hotelRoom, LocalDate startDate, LocalDate endDate) {
		if (hotelRoom == null || startDate == null || endDate == null) {
			throw new NullPointerException();
		}
		if (startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("The startDate cannot be after the endDate.");
		}
		double price = hotelRoom.getPrice(startDate, endDate);
		if (price > getBalance()) {
			throw new IllegalStateException("The person cannot afford this reservation.");
		}
		if (!hotelRoom.isAvailable(startDate, endDate)) {
			throw new IllegalStateException("The room is not available at this time.");
		}
		Random random = new Random();
		Reservation reservation = new Reservation(Math.abs(random.nextLong()), hotelRoom, startDate, endDate);
		hotelRoom.addReservation(reservation);
		addReservation(reservation);
		subtractBalance(price);
	}

	public void addReservation(Reservation reservation) {
		if (reservation == null) {
			throw new NullPointerException();
		}
		reservations.add(reservation);
	}

	public Collection<Long> getReservationIds() {
		return reservations.stream().map((r) -> r.getId()).collect(Collectors.toList());
	}

	public Collection<Reservation> getReservations() {
		return reservations;
	}

	// public void removeReservation(Reservation reservation) {
	// 	if (!reservations.contains(reservation)) {
	// 		throw new IllegalArgumentException();
	// 	}
	// 	reservations.remove(reservation);
	// }

	public boolean hasReservation(Reservation reservation) {
		return reservations.contains(reservation);
	}
}
