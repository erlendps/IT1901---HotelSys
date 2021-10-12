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
    private final Integer floor;

    public HotelRoomFilter(
            final LocalDate startDate,
            final LocalDate endDate,
            final HotelRoomType roomType,
            final Integer floor,
            final HashMap<Amenity, Boolean> amenities) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.roomType = roomType;
        this.floor = floor;
        this.amenities = amenities;
    }

    public final boolean hasValidDates() {
        if (startDate == null || endDate == null) {
            return false;
        }
        if (startDate != null && endDate != null
        && endDate.isBefore(startDate)) {
            return false;
        }
        return true;
    }

    public final Predicate<HotelRoom> getPredicate() {
        return (room) -> {
            if (hasValidDates() && !room.isAvailable(startDate, endDate)) {
                return false;
            }
            if (roomType != null && room.getRoomType() != roomType) {
                return false;
            }
            if (floor != null && room.getFloor() != floor) {
                return false;
            }
            if (amenities != null) {
                for (Amenity amenity : amenities.keySet()) {
                    if (amenities.get(amenity) && !room.hasAmenity(amenity)) {
                        return false;
                    }
                }
            }
            return true;
        };
    }

    public final LocalDate getStartDate() {
        return startDate;
    }

    public final LocalDate getEndDate() {
        return endDate;
    }

    public final HotelRoomType getRoomType() {
        return roomType;
    }
}
