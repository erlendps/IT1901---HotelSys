package gr2116.ui.components;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import gr2116.core.Amenity;
import gr2116.core.HotelRoomType;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import gr2116.ui.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FilterPanel extends VBox {
	Collection<MessageListener> listeners = new HashSet<>();
	HashMap<Amenity, Boolean> amenities = new HashMap<>();

	@FXML
	private DatePicker startDatePicker, endDatePicker;
	
	@FXML
	private ChoiceBox<HotelRoomType> roomTypeChoiceBox;

	@FXML
	private Label roomTypeDescription;

	@FXML
	private VBox amenitiesContainer;

    public FilterPanel() {
        FXMLUtils.loadFXML(this);
    }
    
    @FXML
    private void initialize() {
		roomTypeDescription.setText("Select a room type.");

		for (HotelRoomType roomType : HotelRoomType.values()) {
			roomTypeChoiceBox.getItems().add(roomType);
		}
		for (Amenity amenity : Amenity.values()) {
			amenities.put(amenity, false);
			HBox hBox = new HBox();
			CheckBox checkBox = new CheckBox();
			Label label = new Label(amenity.getName());
			checkBox.setOnAction((event) -> {
				amenities.put(amenity, checkBox.isSelected());
				notifyListeners();
			});
			hBox.getChildren().add(checkBox);
			hBox.getChildren().add(label);
			amenitiesContainer.getChildren().add(hBox);
		}
		
		startDatePicker.setOnAction((event) -> {
			notifyListeners();
		});
		endDatePicker.setOnAction((event) -> {
			notifyListeners();
		});
        roomTypeChoiceBox.setOnAction((event) -> {
			HotelRoomType roomType = roomTypeChoiceBox.getValue();
			if (roomType == null) {
				roomTypeDescription.setText("Select a room type.");
			} else {
				roomTypeDescription.setText(roomType.getDescription());
			}
			notifyListeners();
		});
    }

	public void addListener(MessageListener listener) {
		listeners.add(listener);
	}
	public void removeListener(MessageListener listener) {
		listeners.remove(listener);
	}
	public void notifyListeners() {
		for (MessageListener listener : listeners) {
			HotelRoomFilter filter = new HotelRoomFilter(startDatePicker.getValue(), endDatePicker.getValue(), roomTypeChoiceBox.getValue(), amenities);
			listener.receiveNotification(this, Message.Filter, filter);
		}
	}
}