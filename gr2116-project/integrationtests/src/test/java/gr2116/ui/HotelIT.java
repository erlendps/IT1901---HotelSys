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
    URI baseUri = new URI("http://localhost:8080/rest/hotel");
    System.out.println("Base Hotel URI: " + baseUri);
    appController.setHotelAccess(new RemoteHotelAccess(appController.getHotelPersistence(), baseUri));
  }


  @Test
  public void testController_initial() {
    assertNotNull(this.appController);
  }

  @Test
  public void testLogin() {
    FxAssert.verifyThat("#signInButton", LabeledMatchers.hasText("Sign in"));
    clickOn("#usernameTextField").write("tom");
    clickOn("#signInButton");
    FxAssert.verifyThat("#usernameLabel", LabeledMatchers.hasText("tom"));
  }

  @AfterAll
  public static void cleanUp() {
    File file = Paths.get(System.getProperty("user.home")
        , "HotelSys", "testItHotel.json").toFile();
    file.delete();
  }
}
