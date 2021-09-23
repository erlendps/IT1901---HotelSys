package gr2116.ui.pages;
import gr2116.ui.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

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

        hotel.addRoom(new HotelRoom(HotelRoomType.Single, 101));
        hotel.addRoom(new HotelRoom(HotelRoomType.Double, 102));
        hotel.addRoom(new HotelRoom(HotelRoomType.Single, 103));
        hotel.addRoom(new HotelRoom(HotelRoomType.Suite, 201));
        
        buildRoomList();
    }
    
    private void buildRoomList() {
        if (hotelRoomFilter == null) {
            return;
        }
        Collection<HotelRoom> filteredRooms = hotel.getRooms(hotelRoomFilter.getPredicate());

        roomItemContainer.getChildren().clear();
        for (HotelRoom hotelRoom : filteredRooms) {
            HotelRoomListItem roomItem = new HotelRoomListItem(hotelRoom);
            roomItem.setOnMakeReservationButtonAction((event) -> {
                person.makeReservation(hotelRoom, hotelRoomFilter.getStartDate(), hotelRoomFilter.getEndDate());
                buildRoomList();
            });
            roomItemContainer.getChildren().add(roomItem);
        }
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
