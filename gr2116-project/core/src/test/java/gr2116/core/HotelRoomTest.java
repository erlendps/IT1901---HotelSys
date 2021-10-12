package gr2116.core;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class HotelRoomTest {

	HotelRoom roomSingle = new HotelRoom(HotelRoomType.Single, 111);
	HotelRoom roomDouble = new HotelRoom(HotelRoomType.Double, 794);
	LocalDate today = LocalDate.now();
	LocalDate tomorrow = today.plusDays(1);
	LocalDate overmorrow = today.plusDays(2);


	@Test
	public void testFloorAndNumber() {
		assertEquals(1, roomSingle.getFloor());
		assertEquals(111, roomSingle.getNumber());

		assertEquals(7, roomDouble.getFloor());
		assertEquals(794, roomDouble.getNumber());
	}

	@Test
	public void testPrice() {
		assertEquals(0, roomSingle.getPrice());
		assertEquals(0, roomSingle.getPrice(today, tomorrow));
		roomSingle.setPrice(100);
		assertEquals(100, roomSingle.getPrice());
		assertEquals(200, roomSingle.getPrice(today, overmorrow));
	}

	@Test
	public void testAddAmenity() {
		assertFalse(roomSingle.hasAmenity(Amenity.Bathtub));
		roomSingle.addAmenity(Amenity.Bathtub);
		assertTrue(roomSingle.hasAmenity(Amenity.Bathtub));
		assertThrows(NullPointerException.class, () -> roomSingle.addAmenity(null));
	}

	@Test
	public void testRemoveAmenity() {
		assertThrows(IllegalArgumentException.class, () -> roomSingle.removeAmenity(Amenity.Bathtub));
		roomSingle.addAmenity(Amenity.Bathtub);
		assertTrue(roomSingle.hasAmenity(Amenity.Bathtub));
		roomSingle.removeAmenity(Amenity.Bathtub);
		assertFalse(roomSingle.hasAmenity(Amenity.Bathtub));
		assertThrows(IllegalArgumentException.class, () -> roomSingle.removeAmenity(Amenity.Bathtub));
	}
}
