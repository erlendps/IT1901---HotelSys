package gr2116.ui.components;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.function.Predicate;

import gr2116.core.Amenity;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;

public class HotelRoomFilter {
	private final LocalDate startDate;
	private final LocalDate endDate;
	private final HotelRoomType roomType;
	private final HashMap<Amenity, Boolean> amenities;

	public HotelRoomFilter(LocalDate startDate, LocalDate endDate, HotelRoomType roomType, HashMap<Amenity, Boolean> amenities) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.roomType = roomType;
		this.amenities = amenities;
	}
	
	public boolean isValid() {
		if (roomType == null) {
			return false;
		}
		if (startDate == null || endDate == null) {
			return false;
		}
		if (endDate.isBefore(startDate)) {
			return false;
		}
		if (amenities == null) {
			return false;
		}
		return true;
	}
	
	public Predicate<HotelRoom> getPredicate() {
		return (room) -> {
			if (!isValid()) {
				return false;
			}
			if (!room.isAvailable(startDate, endDate)) {
				return false;
			}
			if (room.getRoomType() != roomType) {
				return false;
			}
			for (Amenity amenity : amenities.keySet()) {
				if (amenities.get(amenity) && !room.hasAmenity(amenity)) {
					return false;
				}
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
