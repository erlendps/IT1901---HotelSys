```plantuml
@startuml
class App {
    + void start(Stage)
    + {static} void main(String[])
}

class AppController {
    - StackPane root

    - void initialize()
    + void receiveNotification(Object, Message, Object)
    - void moveToLoginPage()
    - void moveToMainPage(Person)
    - void load()
    - void save()
}

enum Message {
    SignIn,
    SignOut,
    Filter
}

class FxmlUtils {
    + {static} void loadFxml
}

class LoginPage {
    - TextField nameTextField
    - TextField emailTextField
    - Button signInButton
    - Label emailErrorLabel
    - Label nameTitleLabel

    + loginPage()
    + void setLoadedPersons(Collection<Person>)
    - void initialize()
    - void setNameVisible(boolean)
    + void addListener(MessageListener)
    + void removeListener(MessageListener)
    + void notifyListeners(Message, Object)

}

class MainPage {
    - VBox roomItemContainer
    - AnchorPane filterPane
    - AnchorPane userPane
    + MainPage(Person)
    + void initialize()
    - void buildRoomList()
    + void addRooms(Collection<HotelRoom>)
    + void receiveNotification(Object, Message, Object)
    + void addListener(MessageListener)
    + void removeListener(MessageListener)
    + void notifyListeners(Message, Object)
}

class FilterPanel {
    - HashMap<Amenities, Boolean> amenities
    - DatePicker startDatePicker
    - DatePicker endDatePicker
    - ChoiceBox<HotelRoomType> roomTypeChoiceBox
    - Label roomTypeDescription
    - VBox amenitiesContainer
    - Spinner<Integer> floorSpinner
    - CheckBox floorCheckBox
    - Button clearFilterButton
    + FilterPanel()
    - void initialize()
    + void addListener(MessageListener)
    + void removeListener(MessageListener)
    + void notifyListeners(Message, Object)
}

class HotelRoomFilter {
    - LocalDate startDate
    - LocalDate endDate
    - HashMap<Amenity, boolean> amenities
    - Integer floor
    + HotelRoomFilter(LocalDate, LocalDate, HotelRoomType, Integer, HashMap<Amenity, boolean>)
    + boolean hasValidDates()
    + Predicate<HotelRoom> getPredicate()
    + LocalDate getStartDate()
    + LocalDate getEndDate()
    + HotelRoomType getRoomType()
}

class HotelRoomListItem {
    - Label numberLabel
    - Label typeLabel
    - Label amenitiesLabel
    - Button makeReservationButton
    + HotelRoomListItem(HotelRoom)
    - void initialize()
    + void setOnMakeReservationButtonAction()
}

class UserPanel {
    - Label nameLabel
    - Label emailLabel
    - Label balanceLabel
    - Button signOutButton
    - ListView<Label> reservationListView
    + UserPanel(Person)
    - void initialize()
    + void addListener(MessageListener)
    + void removeListener(MessageListener)
    + void notifyListeners(Message, Object)
    + void receiveNotification(Person)
    - void updatePanel(Person)

}
interface MessageListener {
    void receiveNotification(Object, Message, Object)
}

interface PersonListener
class Person
class HotelRoom
class Hotel

AppController ..|> MessageListener
AppController --> "n" Person : loadedPersons
AppController --> "n" HotelRoom : loadedRooms
LoginPage --> "n" MessageListener : listeners
LoginPage --> "n" Person : loadedPersons
MainPage --> "1" FilterPanel : filterPanel
MainPage --> "1" UserPanel : userPanel
MainPage --> "1" Hotel : hotel
MainPage --> "1" HotelRoomFilter : hotelRoomFilter
MainPage --> "1" Person : person
MainPage --> "n" MessageListener : listeners
FilterPanel --> "n" MessageListener : listeners
HotelRoomFilter --> "1" HotelRoomType : roomType
HotelRoomListItem --> "1" HotelRoom : room
UserPanel --> "n" MessageListener : listeners
UserPanel --> "1" Person : person
UserPanel ..|> PersonListener
@enduml
```