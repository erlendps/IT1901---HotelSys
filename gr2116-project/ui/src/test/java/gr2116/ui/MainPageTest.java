package gr2116.ui;

import java.io.IOException;

import org.assertj.core.internal.bytebuddy.dynamic.scaffold.TypeInitializer.None;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.api.FxAssert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gr2116.core.Person;
import gr2116.ui.controller.AppController;
import javafx.scene.layout.Pane;


public class MainPageTest extends ApplicationTest{
    /**
     * Start the app, load FXML and show scene.
     * @throws IOException
     */
  
    @Start
    public void start(Stage stage) throws IOException {
        Parent parent = FXMLLoader.load(
        getClass().getClassLoader().getResource("App.fxml")); //MainPage.fxml
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
        
        AppController appControllerHandle = loader.getController();
        Person person = new Person("Richard Wilkens");
        person.setEmail("RichardWilkins@gmail.com");
        person.addBalance(100.0);
        appControllerHandle.moveToMainPage(person);
        

        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    void checkfilterPanal() {
        clickOn("roomTypeChoiceBox");
    }

}
    
