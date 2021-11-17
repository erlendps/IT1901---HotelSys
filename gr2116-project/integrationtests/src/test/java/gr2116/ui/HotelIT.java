package gr2116.ui;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxAssert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gr2116.ui.controller.AppController;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javax.imageio.plugins.tiff.FaxTIFFTagSet;

import gr2116.persistence.HotelPersistence;
import gr2116.ui.access.RemoteHotelAccess;

public class HotelIT extends ApplicationTest {

  AppController appController;
  HotelPersistence hotelPersistence;

  @Start
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("AppIT.fxml"));
    Parent parent = fxmlLoader.load();
    appController = (AppController) fxmlLoader.getController();
    appController.setPrefix("testIt");
    stage.setScene(new Scene(parent));
    stage.show();
  }

  @BeforeEach
  public void setUp() throws URISyntaxException {
    System.out.println("Base Hotel URI: http://localhost:8080/rest/hotel" );
  }


  @Test
  public void testController_initial() {
    assertNotNull(this.appController);
  }

  @Test
  public void testLogin() {
    FxAssert.verifyThat("#startLoginButton", LabeledMatchers.hasText("Login"));
    clickOn("#startLoginButton");
    clickOn("#usernameTextField").write("hahahaha");
    clickOn("#passwordTextField").write("blueballing");
    clickOn("#loginButton");
    FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("The username is not in use."));
    clickOn("#usernameTextField").eraseText(8);
    clickOn("#usernameTextField").write("tom");
    clickOn("#loginButton");
    FxAssert.verifyThat("#usernameLabel", LabeledMatchers.hasText("tom"));
    FxAssert.verifyThat("#nameLabel", LabeledMatchers.hasText("Thomas Wonka"));
  }

  @Test
  public void createUser() throws InterruptedException {
    FxAssert.verifyThat("#startSignUpButton", LabeledMatchers.hasText("Sign up"));
    clickOn("#startSignUpButton");
    clickOn("#usernameTextField").write("tom");
    clickOn("#firstNameTextField").write("Thomas");
    clickOn("#passwordTextField").write("bananana");
    clickOn("#signUpButton");
    FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Invalid last name!"));
    clickOn("#firstNameTextField").eraseText(6);
    clickOn("#lastNameTextField").write("Bruhman");
    clickOn("#signUpButton");
    FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Invalid first name!"));
    clickOn("#firstNameTextField").write("Thomas");
    clickOn("#signUpButton");
    FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("The username is taken."));
    clickOn("#usernameTextField").eraseText(3);
    clickOn("#usernameTextField").write("tomas");
    clickOn("#signUpButton");
    FxAssert.verifyThat("#nameLabel", LabeledMatchers.hasText("Thomas Bruhman"));
  }

  @AfterAll
  public static void cleanUp() {
    File file = Paths.get(System.getProperty("user.home")
        , "HotelSys", "testItHotel.json").toFile();
    file.delete();
  }
}
