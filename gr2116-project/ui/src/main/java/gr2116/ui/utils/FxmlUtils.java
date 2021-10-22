package gr2116.ui.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Class that helps loading the different components of the app.
 */
public class FxmlUtils {

  /**
   * Method that loads the given component, which is a .fxml file.
   *
   * @param component the component to be loaded.
   *
   * @throws RunTimeException when file is not found.
   */
  public static <T extends Parent> void loadFxml(final T component) {
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
