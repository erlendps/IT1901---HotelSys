package gr2116.ui;


import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Application, the booking system application.
 */

public class AppTest extends ApplicationTest{
  /**
   * Start the app, load FXML and show scene.
   * @throws IOException
   */

  @Start
  public void start(Stage stage) throws IOException {
    Parent parent = FXMLLoader.load(
      getClass().getClassLoader().getResource("App.fxml"));
  stage.setScene(new Scene(parent));
  stage.show();
  }
  
  @Test
  void enterUserText() {
    FxAssert.verifyThat("#signInButton", LabeledMatchers.hasText("Sign in"));
  }
}