package gr2116.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import gr2116.core.*;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersistenceTest {
    private static Path DATA_FOLDER
            = Paths.get(".").toAbsolutePath()
            .normalize().getParent().getParent().resolve("data");
    JSONObject personsData;
    JSONObject roomsData;
    JSONObject reservationsData;
    static Loader dataLoader;
    static Saver saver;

    @BeforeEach
    public void setup() throws IOException {
        saver = new Saver();
        makeData();
        dataLoader = new Loader();
        dataLoader.loadData("testWrite");
    }

    private void makeData() throws IOException {
        Person rick = new Person("Richard");
        Person kyle = new Person("Kyllard");
        Person tom = new Person("Tom");
        rick.setEmail("richard@people.com");
        kyle.setEmail("kyle@people.com");
        tom.setEmail("tom@richpeople.org");

        rick.addBalance(1000);
        kyle.addBalance(144);
        tom.addBalance(1000000000); 

        HotelRoom room1 = new HotelRoom(HotelRoomType.Single, 101);
        HotelRoom room2 = new HotelRoom(HotelRoomType.Single, 102);
        HotelRoom room3 = new HotelRoom(HotelRoomType.Quad, 714);

        room1.addAmenity(Amenity.Bathtub);
        room1.addAmenity(Amenity.Television);
        room2.addAmenity(Amenity.Fridge);
        room2.addAmenity(Amenity.Internet);
        room2.addAmenity(Amenity.Shower);

        rick.makeReservation(room1, LocalDate.of(2121, 6, 4), LocalDate.of(2121, 6, 7));
        kyle.makeReservation(room1, LocalDate.of(2121, 6, 11), LocalDate.of(2121, 6, 13));
        rick.makeReservation(room2, LocalDate.of(2121, 7, 13), LocalDate.of(2121, 7, 22));
        tom.makeReservation(room3, LocalDate.of(2121, 10, 12), LocalDate.of(2121, 10, 13));
        tom.makeReservation(room3, LocalDate.of(2121, 11, 12), LocalDate.of(2121, 11, 13));
        tom.makeReservation(room3, LocalDate.of(2122, 1, 12), LocalDate.of(2122, 1, 13));
        tom.makeReservation(room3, LocalDate.of(2122, 2, 12), LocalDate.of(2122, 2, 13));

        ArrayList<Person> persons = new ArrayList<Person>();
        persons.add(rick);
        persons.add(kyle);
        persons.add(tom);

        ArrayList<HotelRoom> rooms = new ArrayList<HotelRoom>();
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);

        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        persons.forEach((person) -> 
            person.getReservations().forEach((r) -> 
            reservations.add(r)));

        Saver saver = new Saver();
        assertDoesNotThrow(() -> saver.writeToFile(rooms, persons, "testWrite"), "Something went wrong with saving.");
    }

    /* Since JSONObject stores in a sort of random order, its hard to test if the Saver class writes correctly
        since it can change from time to time. This test then only 
    @Test
    public void testSaver() throws IOException {
        // person data
        byte[] writtenToFile = Files.readAllBytes(Path.of(DATA_FOLDER + "/testWriteToFilePerson.json"));
        byte[] controlFile = Files.readAllBytes(Path.of(DATA_FOLDER + "/testWritePerson.json"));
        assertNotNull(writtenToFile);
        assertNotNull(controlFile);
        assertTrue(Arrays.equals(writtenToFile, controlFile));

        // reservations data
        writtenToFile = Files.readAllBytes(Path.of(DATA_FOLDER + "/testWriteToFileReservation.json"));
        assertNotNull(writtenToFile);
        // commented out since you cannot sort a JSONObject by keys.
        // assertTrue(Arrays.equals(writtenToFile, controlFile));

        // rooms data
        writtenToFile = Files.readAllBytes(Path.of(DATA_FOLDER + "/testWriteToFileRooms.json"));
        controlFile = Files.readAllBytes(Path.of(DATA_FOLDER + "/testWriteRooms.json"));
        assertNotNull(writtenToFile);
        assertNotNull(controlFile);
        assertTrue(Arrays.equals(writtenToFile, controlFile));
    }
    */

    @Test
    public void testLoadPersonsAndReservations() {
        Collection<Person> persons = dataLoader.getPersons();

        // Checks if the persons saved are in the list.
        // Also checks if there are multiple instances of them in the list.
        List<Person> rickList = persons.stream().filter((Person p) -> p.getName().equals("Richard")).collect(Collectors.toList());
        List<Person> kyleList = persons.stream().filter((Person p) -> p.getName().equals("Kyllard")).collect(Collectors.toList());
        List<Person> tomList = persons.stream().filter((Person p) -> p.getName().equals("Tom")).collect(Collectors.toList());
        assertEquals(1, rickList.size(), "There was not 1 instance of person Rick in the loaded data, there were " + 
        Integer.toString(rickList.size()));
        assertEquals(1, kyleList.size(), "There was not 1 instance of person Kyle in the loaded data, there were " + 
        Integer.toString(kyleList.size()));
        assertEquals(1, tomList.size(), "There was not 1 instance of person Tom in the loaded data, there were " +
        Integer.toString(tomList.size()));

        // Saves the persons.
        Person rick = rickList.get(0);
        Person kyle = kyleList.get(0);
        Person tom = tomList.get(0);

        // Check if a selection of attributes were preserved
        assertEquals(1000, rick.getBalance(), "Balance was saved incorrectly for Rick.");
        assertEquals(1000000000, tom.getBalance(), "Balance was saved incorrectly for Tom.");

        assertEquals("kyle@people.com", kyle.getEmail());

        assertEquals(1, kyle.getReservations().size(), "Kyle has the wrong number of reservations, should be 1. Was " + 
        Integer.toString(kyle.getReservations().size()));
        assertEquals(4, tom.getReservations().size(), "Tom has the wrong number of reservations, should be 1. Was " + 
        Integer.toString(tom.getReservations().size()));
    }

    @Test
    public void testLoadRooms() throws IOException {

        Collection<HotelRoom> rooms = dataLoader.getRooms();

        // Checks if the rooms saved are in the list.
        // Also checks if there are multiple instances of them in the list.
        List<HotelRoom> room1List = rooms.stream().filter((HotelRoom r) -> r.getNumber() == 101).collect(Collectors.toList());
        List<HotelRoom> room2List = rooms.stream().filter((HotelRoom r) -> r.getNumber() == 102).collect(Collectors.toList());
        List<HotelRoom> room3List = rooms.stream().filter((HotelRoom r) -> r.getNumber() == 714).collect(Collectors.toList());
        assertEquals(1, room1List.size(), "There was not 1 instance of room '101' in the loaded data, there were " + 
        Integer.toString(room1List.size()));
        assertEquals(1, room2List.size(), "There was not 1 instance of room '102' in the loaded data, there were " + 
        Integer.toString(room2List.size()));
        assertEquals(1, room3List.size(), "There was not 1 instance of room '714' in the loaded data, there were " +
        Integer.toString(room3List.size()));
        
        // Saves the rooms
        HotelRoom room1 = room1List.get(0);
        HotelRoom room2 = room2List.get(0);
        HotelRoom room3 = room3List.get(0);

        // Test if selection of attributes were preserved
        assertTrue(room1.hasAmenity(Amenity.Bathtub), "Room 101 should contain bathtub, this was not loaded.");
        assertTrue(room1.hasAmenity(Amenity.Television), "Room 101 should contain television, this was not loaded.");
        assertEquals(0, room3.getAmenities().size(), "Loaded room 714 contains amenities even though none were set.");
        assertEquals(1, room2.getReservationIds().size(), "Room 102 was reserved 1 time, but loaded value was " 
        + Integer.toString(room2.getReservationIds().size()));
        assertEquals(HotelRoomType.Quad, room3.getRoomType());
    }

    @Test
    public void testGettersBeforeLoaded() {
        Loader emptyLoader = new Loader();
        assertThrows(IllegalStateException.class, () -> emptyLoader.getPersons());
        assertThrows(IllegalStateException.class,  () -> emptyLoader.getRooms());
    }

    @Test
    public void testLoadNonExistingFile() {
        assertThrows(IOException.class, () -> dataLoader.loadData("nothing"));
    }

    @Test
    public void testSaveWithEmptyAndNull() {
        Collection<Person> persons = new ArrayList<>();
        Collection<Reservation> reservations = new ArrayList<>();
        Collection<HotelRoom> rooms = new ArrayList<>();
        assertDoesNotThrow(() -> saver.writeToFile(rooms, persons, reservations, "empty"));
        assertThrows(NullPointerException.class, () -> saver.writeToFile(null, persons, reservations, "null"));
        assertThrows(NullPointerException.class, () -> saver.writeToFile(rooms, null, reservations, "null"));
        assertThrows(NullPointerException.class, () -> saver.writeToFile(rooms, persons, null, "null"));
        assertThrows(NullPointerException.class, () -> saver.writeToFile(rooms, persons, reservations, null));
    }
    

    @AfterAll
    public static void cleanUp() {
        File personFile = new File(DATA_FOLDER + "/testWritePerson.json");
        File roomsFile = new File(DATA_FOLDER + "/testWriteRooms.json");
        File reservationFile = new File(DATA_FOLDER + "/testWriteReservation.json");
        personFile.delete();
        roomsFile.delete();
        reservationFile.delete();
        File emptyPerson = new File(DATA_FOLDER + "/emptyPerson.json");
        File emptyRooms = new File(DATA_FOLDER + "/emptyRooms.json");
        File emptyReservation = new File(DATA_FOLDER + "/emptyReservation.json");
        emptyPerson.delete();
        emptyRooms.delete();
        emptyReservation.delete();
    }   
}
