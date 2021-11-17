package gr2116.ui.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Remote FXML Application, the booking system application.
 */
public class RemoteApp extends Application {
  
  /**
   * Start the app, load FXML and show scene.
   */
  @Override
  public final void start(final Stage stage) throws Exception {
    System.out.println("Running RemoteApp.");
    Parent parent = FXMLLoader.load(
        getClass().getClassLoader().getResource("RemoteApp.fxml"));
    stage.setScene(new Scene(parent));
    stage.show();
  }

  /**
   * Main method to launch app.
   *
   * @param args Main method needs to have these
   */
  public static void main(final String[] args) {
    launch(RemoteApp.class, args);
  }

}
