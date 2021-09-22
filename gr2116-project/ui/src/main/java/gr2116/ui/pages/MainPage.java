package gr2116.ui.pages;
import gr2116.ui.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;
import gr2116.core.Person;
import gr2116.ui.components.UserPanel;
import gr2116.ui.message.Message;
import gr2116.ui.components.FilterPanel;
import gr2116.ui.components.HotelRoomListItem;
import gr2116.ui.components.MessageListener;
import javafx.scene.layout.AnchorPane;

public class MainPage extends VBox implements MessageListener {
	private FilterPanel filterPanel = new FilterPanel();
    private UserPanel userPanel;
	private Hotel hotel = new Hotel();
	private Predicate<HotelRoom> roomPredicate = (room) -> true;
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
        Collection<HotelRoom> filteredRooms = hotel.getRooms(roomPredicate);

        roomItemContainer.getChildren().clear();
        for (HotelRoom hotelRoom : filteredRooms) {
            HotelRoomListItem roomItem = new HotelRoomListItem(hotelRoom);
            roomItemContainer.getChildren().add(roomItem);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void receiveNotification(Object from, Message message, Object data) {
        if (message == Message.Filter && data instanceof Predicate<?>) {
            this.roomPredicate = (Predicate<HotelRoom>) data;
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
