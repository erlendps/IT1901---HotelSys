package gr2116.ui.components;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;
import gr2116.ui.message.Message;
import gr2116.ui.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FilterPanel extends VBox {
	Collection<MessageListener> listeners = new HashSet<>();

	@FXML
	private DatePicker startDatePicker, endDatePicker;
	
	@FXML
	private ChoiceBox<HotelRoomType> roomTypeChoiceBox;

	@FXML
	private Label roomTypeDescription;

    public FilterPanel() {
        FXMLUtils.loadFXML(this);
    }
    
    @FXML
    private void initialize() {
		roomTypeDescription.setText("Velg en romtype.");

		for (HotelRoomType roomType : HotelRoomType.values()) {
			roomTypeChoiceBox.getItems().add(roomType);
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
				roomTypeDescription.setText("Velg en romtype.");
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
			Predicate<HotelRoom> roomPredicate = (room) -> {
				LocalDate startDate = startDatePicker.getValue();
				LocalDate endDate = endDatePicker.getValue();
				if (startDate != null && !room.isAvailable(startDate)) {
					return false;
				}
				if (endDate != null && !room.isAvailable(endDate)) {
					return false;
				}
				if (startDate != null && endDate != null && !room.isAvailable(startDate, endDate)) {
					return false;
				}
				if (room.getRoomType() != roomTypeChoiceBox.getValue()) {
					return false;
				}
				return true;
			};
			listener.receiveNotification(this, Message.Filter, roomPredicate);
		}
	}
}