package gr2116.ui.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class FxmlUtils {
  public static <T extends Parent> void loadFXML(final T component) {
    FXMLLoader loader = new FXMLLoader();
    loader.setRoot(component);
    loader.setControllerFactory(theClass -> component);

    String fileName = component.getClass().getSimpleName() + ".fxml";

    try {
      loader.load(
          component.getClass().getClassLoader().getResourceAsStream(fileName));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
