package gr2116.ui;

import java.io.IOException;

import org.assertj.core.internal.bytebuddy.dynamic.scaffold.TypeInitializer.None;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.api.FxAssert;
import javafx.fxml.FXMLLoader;
//import javafx.fxml.FXMLController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gr2116.core.Person;
import gr2116.ui.controller.AppController;
import javafx.scene.layout.Pane;


public class MainPageTest extends ApplicationTest{
    AppController appController;
    /**
     * Start the app, load FXML and show scene.
     * @throws IOException
     */
  
    @Start
    public void start(Stage stage) throws IOException {
        //Parent parent = FXMLLoader.load(
        //getClass().getClassLoader().getResource("App.fxml")); //MainPage.fxml
        //AppController appController = parent.getController();
        //System.out.println(appController);
        
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("App.fxml"));
        //Pane p = fxmlLoader.load(getClass().getResource("App.fxml").openStream());
        Parent parent = fxmlLoader.load();
        appController = (AppController) fxmlLoader.getController();

        
        //AppController appController = loader.getController();
        Person person = new Person("Richard Wilkens");
        person.setEmail("RichardWilkins@gmail.com");
        person.addBalance(100.0);
        appController.moveToMainPage(person);
        

        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    void checkUserPane() {
        FxAssert.verifyThat("#nameLabel", LabeledMatchers.hasText("Richard Wilkens"));
        FxAssert.verifyThat("#emailLabel", LabeledMatchers.hasText("RichardWilkins@gmail.com"));
        FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("100.0"));
    }

}
    
