package gr2116.ui.components;

import gr2116.ui.utils.FxmlUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * Component template to make custom JavaFX components.
 */
public class ComponentTemplate extends VBox { // Must extend a javafx class
  /**
   * Loads fxml.
   */
  public ComponentTemplate() {
    FxmlUtils.loadFXML(this);
    // Automatically connects to ComponentTemplate.fxml,
    // at the END of the constructor
  }
  
  /**
   * Initializes the component.
   */
  @FXML
  private void initialize() { // This will run after FxmlUtils.loadFXML(this);

  }
}
