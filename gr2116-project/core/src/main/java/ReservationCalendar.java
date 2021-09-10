import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.lang.Iterable;

public class ReservationCalendar implements Iterable<Reservation> {
    private Collection<Reservation> reservations = new HashSet<>();

	public void addReservation(Reservation reservation) {
		if (reservation == null) {
			throw new NullPointerException();
		}
		if (!isAvailable(reservation.getStartDate(), reservation.getEndDate())) {
			throw new IllegalStateException("The room is not available at this time.");
		}
		reservations.add(reservation);
	}
	
	// public void removeReservation(Reservation reservation) {
	// 	if (!reservations.contains(reservation)) {
	// 		throw new IllegalArgumentException();
	// 	}
	// 	reservations.remove(reservation);
	// }

	public boolean isAvailable(LocalDate date) {
		return reservations.stream().noneMatch((reservation) -> {
			LocalDate start = reservation.getStartDate();
			LocalDate end = reservation.getEndDate();
			return date.isEqual(start) || date.isEqual(end) || date.isAfter(start) && date.isBefore(end);
		});
	}

	public boolean isAvailable(LocalDate startDate, LocalDate endDate) {
		LocalDate date = startDate;
		while (date.isBefore(endDate) || date.isEqual(endDate)) {
			if (!isAvailable(date)) {
				return false;
			}
			date = date.plusDays(1);
		}
		return true;
	}

	@Override
	public Iterator<Reservation> iterator() {
		return reservations.iterator();
	}
}
