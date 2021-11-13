package gr2116.ui;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
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
import gr2116.persistence.HotelPersistence;
import gr2116.ui.access.RemoteHotelAccess;


public class HotelIntegrationTest extends ApplicationTest {

    AppController appController;
    HotelPersistence hotelPersistence;

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("App.fxml"));
        Parent parent = fxmlLoader.load();
        appController = (AppController) fxmlLoader.getController();
        appController.setPrefix("test");
        appController.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @BeforeEach
    public void setUp() throws URISyntaxException {
        URI baseUri = new URI("http://localhost:8080/rest/hotel");
        System.out.println("Base RemoteTodoModelAcces URI: " + baseUri);
        appController.setHotelAccess(new RemoteHotelAccess(hotelPersistence,baseUri));
    }

    @Test
    public void testController_initial(){
        assertNotNull(this.appController);
    }
}

