package gr2116.ui.pages;
import gr2116.ui.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;
import gr2116.core.Person;
import gr2116.ui.components.UserPanel;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import gr2116.ui.components.FilterPanel;
import gr2116.ui.components.HotelRoomFilter;
import gr2116.ui.components.HotelRoomListItem;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MainPage extends VBox implements MessageListener {
	private FilterPanel filterPanel = new FilterPanel();
    private UserPanel userPanel;
	private Hotel hotel = new Hotel();
	private HotelRoomFilter hotelRoomFilter;
    private final Person person;
    private final Collection<MessageListener> listeners = new HashSet<>();

    public MainPage(Person person) {
        this.person = person;
        FXMLUtils.loadFXML(this);
    }
    
    @FXML
    private VBox roomItemContainer;

    @FXML
    private AnchorPane filterPane, userPane;

    @FXML
    void initialize() {
        userPanel = new UserPanel(person);
        userPane.getChildren().add(userPanel);
        userPanel.addListener(this);

        filterPane.getChildren().add(filterPanel);
        filterPanel.addListener(this);

        
        buildRoomList();
    }
    
    private void buildRoomList() {
        roomItemContainer.getChildren().clear();
        if (hotelRoomFilter == null) {
            return;
        }
        if (!hotelRoomFilter.isValid()) {
            HotelRoomType roomType = hotelRoomFilter.getRoomType();
            LocalDate startDate = hotelRoomFilter.getStartDate();
            LocalDate endDate = hotelRoomFilter.getEndDate();
            
            if( roomType == null){
                Label label = new Label("No room type is set yet.");
                roomItemContainer.getChildren().add(label);
            }
            if( startDate == null || endDate == null ){
                Label label = new Label("No dates are picked yet.");
                roomItemContainer.getChildren().add(label);
            }
            if( endDate.isBefore(startDate) ){
                Label label = new Label("The end date picked is before the start date.");
                roomItemContainer.getChildren().add(label);
            }
        
            return; 
        }
        
        Collection<HotelRoom> filteredRooms = hotel.getRooms(hotelRoomFilter.getPredicate());

        
        for (HotelRoom hotelRoom : filteredRooms) {
            HotelRoomListItem roomItem = new HotelRoomListItem(hotelRoom);
            roomItem.setOnMakeReservationButtonAction((event) -> {
                person.makeReservation(hotelRoom, hotelRoomFilter.getStartDate(), hotelRoomFilter.getEndDate());
                buildRoomList();
            });
            roomItemContainer.getChildren().add(roomItem);
        }
    }

    public void addRooms(Collection<HotelRoom> rooms) {
        rooms.forEach((HotelRoom room) -> hotel.addRoom(room));
        buildRoomList();
    }

    @Override
    public void receiveNotification(Object from, Message message, Object data) {
        if (message == Message.Filter && data instanceof HotelRoomFilter) {
            this.hotelRoomFilter = (HotelRoomFilter) data;
            buildRoomList();
        }
        if (message == Message.SignOut) {
            notifyListeners(Message.SignOut);
        }
    }
    
    public void addListener(MessageListener listener) {
		listeners.add(listener);
	}
	public void removeListener(MessageListener listener) {
		listeners.remove(listener);
	}
	public void notifyListeners(Message message) {
        for (MessageListener listener : listeners) {
            listener.receiveNotification(this, message, null);
        }
    }
}
