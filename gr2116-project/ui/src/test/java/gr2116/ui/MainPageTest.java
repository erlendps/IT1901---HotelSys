package gr2116.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;

import gr2116.core.Person;
import gr2116.ui.controller.AppController;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;


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
        appController.setPrefix("testUi");
        appController.load();

        
        //AppController appController = loader.getController();
        Person person = new Person("Richard Wilkens");
        person.setEmail("RichardWilkins@gmail.com");
        person.addBalance(100.0);
        appController.moveToMainPage(person);
        

        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    public void checkUserPane() {
        FxAssert.verifyThat("#nameLabel", LabeledMatchers.hasText("Richard Wilkens"));
        FxAssert.verifyThat("#emailLabel", LabeledMatchers.hasText("RichardWilkins@gmail.com"));
        FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("100.0"));
    }
    
    @Test
    public void checkBookHotel() {
        DateTimeFormatter systemFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        String dateFrom = systemFormat.format(LocalDate.now());
        String dateTo = systemFormat.format(LocalDate.now().plusDays(3));
        
        clickOn((lookup("#startDatePicker").queryAs(DatePicker.class)).getEditor()).write(dateFrom + '\n');
        clickOn((lookup("#endDatePicker").queryAs(DatePicker.class)).getEditor()).write(dateTo + '\n');
        clickOn("#amenityTelevision");

        FxAssert.verifyThat("#roomItemContainer", NodeMatchers.hasChild("#hotelRoom101ListItem"));
        clickOn("#hotelRoom101Button");
        StringBuilder sb = new StringBuilder();
        sb.append("#hotelRoom101reservation");
        sb.append(LocalDate.now());
        sb.append("to");
        sb.append(LocalDate.now().plusDays(3));
        FxAssert.verifyThat("#reservationListView", NodeMatchers.hasChild(sb.toString()));

        clickOn("#amenityTelevision");
        clickOn("#amenityInternet");
        FxAssert.verifyThat("#roomItemContainer", NodeMatchers.hasChild("#hotelRoom102ListItem"));
        clickOn("#hotelRoom102Button");
        sb = new StringBuilder();
        sb.append("#hotelRoom102reservation");
        sb.append(LocalDate.now());
        sb.append("to");
        sb.append(LocalDate.now().plusDays(3));
        FxAssert.verifyThat("#reservationListView", NodeMatchers.hasChild(sb.toString()));
        FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("40.0"));
    }
    
    @Test
    public void checkBookWrongDates() {
        DateTimeFormatter systemFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        String dateFrom = systemFormat.format(LocalDate.now().plusDays(20));
        String dateTo = systemFormat.format(LocalDate.now().plusDays(18));
        
        clickOn((lookup("#startDatePicker").queryAs(DatePicker.class)).getEditor()).write(dateFrom + '\n');
        clickOn((lookup("#endDatePicker").queryAs(DatePicker.class)).getEditor()).write(dateTo + '\n');
        FxAssert.verifyThat("#filterError", LabeledMatchers.hasText("You must choose an end date which is " 
                                                                + "after the start date to make a reservation."));

        dateFrom = systemFormat.format(LocalDate.now().minusDays(2));
        dateTo = systemFormat.format(LocalDate.now().plusDays(1));
        
        clickOn((lookup("#startDatePicker").queryAs(DatePicker.class)).getEditor()).eraseText(10);
        clickOn((lookup("#endDatePicker").queryAs(DatePicker.class)).getEditor()).eraseText(10);
        clickOn((lookup("#startDatePicker").queryAs(DatePicker.class)).getEditor()).write(dateFrom + '\n');
        clickOn((lookup("#endDatePicker").queryAs(DatePicker.class)).getEditor()).write(dateTo + '\n');
        FxAssert.verifyThat("#filterError", LabeledMatchers.hasText("You must choose a start date that is "
                                                                + "today or later to make a reservation." ));
    }

    @Test
    public void testPriceLabelIsCorrect() {
        FxAssert.verifyThat("#hotelRoom101PricePerNightLabel", LabeledMatchers.hasText("0.0"));
        FxAssert.verifyThat("#hotelRoom714PricePerNightLabel", LabeledMatchers.hasText("300.0"));
        FxAssert.verifyThat("#hotelRoom102TotalPriceLabel", LabeledMatchers.hasText(""));

        DateTimeFormatter systemFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        String dateFrom = systemFormat.format(LocalDate.now());
        String dateTo = systemFormat.format(LocalDate.now().plusDays(3));

        clickOn((lookup("#startDatePicker").queryAs(DatePicker.class)).getEditor()).write(dateFrom + '\n');
        clickOn((lookup("#endDatePicker").queryAs(DatePicker.class)).getEditor()).write(dateTo + '\n');

        FxAssert.verifyThat("#hotelRoom102TotalPriceLabel", LabeledMatchers.hasText("60.0"));
        FxAssert.verifyThat("#hotelRoom714ErrorLabel", LabeledMatchers.hasText("You don't have enough money to "
                                                                + "make this reservation."));
    }

    @Test
    public void checkInvalidCardNumber() {
        clickOn("#makeDepositButton");

        clickOn("#cardTextField").write("0100341963170010");
        clickOn("#moneyAmountTextField").write("100");
        clickOn("#addFundsButton");
        FxAssert.verifyThat("#moneyErrorLabel", LabeledMatchers.hasText("Card number has invalid format."));
        
        clickOn("#cardTextField").write("1");
        clickOn("#addFundsButton");
        FxAssert.verifyThat("#moneyErrorLabel", LabeledMatchers.hasText("Card numbers must be exactly 16 characters long."));
    }

    @Test
    public void checkInvalidMoney() {
        clickOn("#makeDepositButton");

        clickOn("#cardTextField").write("0100341963170009");
        clickOn("#moneyAmountTextField").write("-100");
        clickOn("#addFundsButton");
        FxAssert.verifyThat("#moneyErrorLabel", LabeledMatchers.hasText("Balance must be a positive integer."));
        
        clickOn("#moneyAmountTextField").eraseText(4);
        clickOn("#moneyAmountTextField").write("100000000");
        clickOn("#addFundsButton");
        FxAssert.verifyThat("#moneyErrorLabel", LabeledMatchers.hasText("Balance must be less than 1 000 000."));

        clickOn("#moneyAmountTextField").eraseText(9);
        clickOn("#moneyAmountTextField").write("0");
        clickOn("#addFundsButton");
        FxAssert.verifyThat("#moneyErrorLabel", LabeledMatchers.hasText("Balance must be strictly greater than zero."));

    }

    @Test
    public void checkAddMoney() {
        clickOn("#makeDepositButton");

        clickOn("#cardTextField").write("0100341963170009");
        clickOn("#moneyAmountTextField").write("100");
        clickOn("#addFundsButton");
        FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("200.0"));
    }

    @Test
    public void cancelMoney() {
        clickOn("#makeDepositButton");
        clickOn("#moneyCancelButton");
        FxAssert.verifyThat("#makeDepositButton", LabeledMatchers.hasText("Make deposit")); // Test fails if page was not switched
    }
}
    
