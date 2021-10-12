package gr2116.ui.components;

import gr2116.ui.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ComponentTemplate extends VBox { // Must extend a javafx class

  public ComponentTemplate() {
    FXMLUtils.loadFXML(this);
    // Automatically connects to ComponentTemplate.fxml,
    // at the END of the constructor
  }

  @FXML
  private void initialize() { // This will run after FXMLUtils.loadFXML(this);

  }
}
