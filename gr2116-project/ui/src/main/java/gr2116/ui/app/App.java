package gr2116.ui.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

  @Override
  public final void start(final Stage stage) throws Exception {
    Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("App.fxml"));
    stage.setScene(new Scene(parent));
    stage.show();
  }

  public static void main(final String[] args) {
    System.out.println("Hello world!");
    launch(App.class, args);
  }
}
