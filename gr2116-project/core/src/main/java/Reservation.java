import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class Reservation implements Iterable<LocalDate> {
    private final HotelRoom room;
	private final LocalDate startDate;
	private final LocalDate endDate;
	
	public Reservation(HotelRoom room, LocalDate startDate, LocalDate endDate) {
		if (startDate == null || endDate == null) {
			throw new NullPointerException();
		}
		if (startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("startDate cannot be after endDate.");
		}
		this.room = room;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public HotelRoom getRoom() {
		return room;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	@Override
	public Iterator<LocalDate> iterator() {
		ArrayList<LocalDate> dates = new ArrayList<>();
		LocalDate date = startDate;
		while (date.isBefore(endDate)) {
			dates.add(date);
			date = date.plusDays(1);
		}
		dates.add(endDate);
		return dates.iterator();
	}
}