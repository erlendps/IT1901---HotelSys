import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ReservationCalenderTest {
	ReservationCalendar calendar = new ReservationCalendar();
	LocalDate today = LocalDate.now();
	LocalDate tomorrow = today.plusDays(1);
	Reservation reservation = new Reservation(null, today, tomorrow);

	private boolean checkReservations(ReservationCalendar calendar, Collection<Reservation> reservations) {
		ArrayList<Reservation> calendarReservations = new ArrayList<>();
		calendar.forEach(calendarReservations::add);
		return reservations.containsAll(calendarReservations) && calendarReservations.containsAll(reservations);
	}

	@Test
	public void testAddReservation() {
		calendar.addReservation(reservation);
		assertTrue(checkReservations(calendar, Arrays.asList(reservation)));

		assertThrows(NullPointerException.class, () -> calendar.addReservation(null));
		assertThrows(IllegalStateException.class, () -> calendar.addReservation(reservation));
	}
	
	// @Test
	// public void testRemoveReservation() {
	// 	calendar.addReservation(reservation);
	// 	assertTrue(checkReservations(calendar, Arrays.asList(reservation)));
	// 	calendar.removeReservation(reservation);
	// 	assertTrue(checkReservations(calendar, Arrays.asList()));

	// 	assertThrows(IllegalArgumentException.class, () -> calendar.removeReservation(reservation));
	// }

	@Test
	public void testIsAvailable() {
		assertTrue(calendar.isAvailable(today));
		assertTrue(calendar.isAvailable(tomorrow));
		calendar.addReservation(reservation);
		assertTrue(calendar.isAvailable(today.minusDays(1)));
		assertFalse(calendar.isAvailable(today));
		assertFalse(calendar.isAvailable(tomorrow));
		assertTrue(calendar.isAvailable(tomorrow.plusDays(1)));
	}

	@Test
	public void testIsAvailableStartEnd() {
		calendar.addReservation(reservation);
		assertTrue(calendar.isAvailable(today.minusDays(2), tomorrow.minusDays(2)));
		assertFalse(calendar.isAvailable(today.minusDays(1), tomorrow.minusDays(1)));
		assertFalse(calendar.isAvailable(today, tomorrow));
		assertFalse(calendar.isAvailable(today.plusDays(1), tomorrow.plusDays(1)));
		assertTrue(calendar.isAvailable(today.plusDays(2), tomorrow.plusDays(2)));
	}
}
