package gr2116.ui.main;

import gr2116.core.Amenity;
import gr2116.core.HotelRoomFilter;
import gr2116.core.HotelRoomType;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import gr2116.ui.utils.FxmlUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The panel where users select filters for hotel rooms.
 */
public class FilterPanel extends VBox {

  private Collection<MessageListener> listeners = new HashSet<>();
  private HashMap<Amenity, Boolean> amenities = new HashMap<>();

  @FXML
  private DatePicker startDatePicker;

  @FXML
  private DatePicker endDatePicker;

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

  /**
   * Constructor for FilterPanel.
   */
  public FilterPanel() {
    FxmlUtils.loadFxml(this);
  }

  /**
   * Initializes the component. Sets action for buttons and adds data for filtering.
   */
  @FXML
  private void initialize() {

    // Does not sort by floor.
    floorSpinner.setDisable(true);
    roomTypeDescription.setText("Select a room type.");
    floorSpinner.setValueFactory(
        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));
    
    // Adds roomtypes and amenities to list of choices.
    for (HotelRoomType roomType : HotelRoomType.values()) {
      roomTypeChoiceBox.getItems().add(roomType);
    }
    for (Amenity amenity : Amenity.values()) {
      amenities.put(amenity, false);
      AmenityCheckBox amenityCheckBox = new AmenityCheckBox(amenity);
      amenitiesContainer.getChildren().add(amenityCheckBox);
    }

    // When user presses chose date, update filter.  
    startDatePicker.setOnAction((event) -> {
      notifyListeners();
    });
    endDatePicker.setOnAction((event) -> {
      notifyListeners();
    });

    startDatePicker.setDayCellFactory(param -> new DateCell() {
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
      }
    });

    endDatePicker.setDayCellFactory(param -> new DateCell() {
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
      }
    });

    // When user presses a roomtype box the site updates.
    // If there is not selected a roomType, give message "select a room type".
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

    //When user presses clear filter button, the filters clears.
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

  /**
   * Amenity check box, to select an amenity.
   */
  private class AmenityCheckBox extends HBox {
    private CheckBox checkBox = new CheckBox();

    /**
     * Constructor, specify which amenity this is a checkbox for.
     */
    AmenityCheckBox(final Amenity amenity) {
      Label label = new Label(amenity.getName());
      checkBox.selectedProperty().addListener((obs, oldValue, newValue) -> {
        amenities.put(amenity, newValue);
        notifyListeners();
      });
      checkBox.setId("amenity" + amenity.toString().replace(" ", ""));
      getChildren().add(checkBox);
      getChildren().add(label);
    }

    /**
     * Set whether to search for rooms with this amenity.
     *
     * @param value true if amenity is selected
     */
    public void setSelected(final boolean value) {
      checkBox.setSelected(value);
    }
  }

  /**
   * Add a listener.
   *
   * @param listener The listener
   */
  public final void addListener(final MessageListener listener) {
    listeners.add(listener);
  }

  /**
   * Remove a listener.
   *
   * @param listener The listener
   */
  public final void removeListener(final MessageListener listener) {
    listeners.remove(listener);
  }
  
  /**
   * Notify listenrs that the filter has been updated.
   * Includes dates, room type, floor and amenities.
   */
  public final void notifyListeners() {
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
