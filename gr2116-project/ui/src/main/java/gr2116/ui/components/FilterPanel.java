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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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

	@FXML
	private Spinner<Integer> floorSpinner;

	@FXML
	private CheckBox floorCheckBox;
	
	@FXML
	private Button clearFilterButton;
	
    public FilterPanel() {
        FXMLUtils.loadFXML(this);
    }
    
    @FXML
    private void initialize() {
		floorSpinner.setDisable(true);
		roomTypeDescription.setText("Select a room type.");
		floorSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));
		
		for (HotelRoomType roomType : HotelRoomType.values()) {
			roomTypeChoiceBox.getItems().add(roomType);
		}
		for (Amenity amenity : Amenity.values()) {
			amenities.put(amenity, false);
			AmenityCheckBox amenityCheckBox = new AmenityCheckBox(amenity);
			amenitiesContainer.getChildren().add(amenityCheckBox);
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
		floorSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
			notifyListeners();
		});
		floorCheckBox.selectedProperty().addListener((obs, oldValue, newValue) -> {
			floorSpinner.setDisable(!newValue);
			notifyListeners();
		});
		clearFilterButton.setOnAction((event) -> {
			startDatePicker.setValue(null);
			endDatePicker.setValue(null);
			roomTypeChoiceBox.setValue(null);
			floorCheckBox.setSelected(false);
			for (Node child : amenitiesContainer.getChildren()) {
				AmenityCheckBox amenityCheckBox = (AmenityCheckBox) child;
				amenityCheckBox.setSelected(false);
			}
			notifyListeners();
		});
    }

	private class AmenityCheckBox extends HBox {
		private CheckBox checkBox = new CheckBox();

		public AmenityCheckBox(Amenity amenity) {
			Label label = new Label(amenity.getName());
			checkBox.selectedProperty().addListener((obs, oldValue, newValue) -> {
				amenities.put(amenity, newValue);
				notifyListeners();
			});
			getChildren().add(checkBox);
			getChildren().add(label);
		}
		public void setSelected(boolean value) {
			checkBox.setSelected(value);
		} 
	}
	
	public void addListener(MessageListener listener) {
		listeners.add(listener);
	}
	public void removeListener(MessageListener listener) {
		listeners.remove(listener);
	}
	public void notifyListeners() {
		for (MessageListener listener : listeners) {
			HotelRoomFilter filter = new HotelRoomFilter(startDatePicker.getValue(),
											endDatePicker.getValue(),
											roomTypeChoiceBox.getValue(),
											floorSpinner.isDisable() ? null : floorSpinner.getValue(),
											amenities);
			listener.receiveNotification(this, Message.Filter, filter);
		}
	}
}