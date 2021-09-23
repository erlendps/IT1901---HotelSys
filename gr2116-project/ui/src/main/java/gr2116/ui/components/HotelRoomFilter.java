package gr2116.ui.components;

import java.time.LocalDate;
import java.util.function.Predicate;

import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;

public class HotelRoomFilter {
	private final LocalDate startDate;
	private final LocalDate endDate;
	private final HotelRoomType roomType;

	public HotelRoomFilter(LocalDate startDate, LocalDate endDate, HotelRoomType roomType) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.roomType = roomType;
	}
	
	public Predicate<HotelRoom> getPredicate() {
		return (room) -> {
			if (startDate != null && !room.isAvailable(startDate)) {
				return false;
			}
			if (endDate != null && !room.isAvailable(endDate)) {
				return false;
			}
			if (startDate != null && endDate != null && !room.isAvailable(startDate, endDate)) {
				return false;
			}
			if (room.getRoomType() != roomType) {
				return false;
			}
			return true;
		};
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public HotelRoomType getRoomType() {
		return roomType;
	}
}
