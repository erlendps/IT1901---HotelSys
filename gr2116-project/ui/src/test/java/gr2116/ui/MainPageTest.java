package gr2116.ui;

import gr2116.core.Person;
import gr2116.ui.controller.AppController;
import gr2116.ui.message.Message;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

/**
 * Tests the main page of the application.
 * Test order is needed because the money which is added in one test effects the
 * balance in the next.
 */
@TestMethodOrder(OrderAnnotation.class)
public class MainPageTest extends ApplicationTest {
  AppController appController;
  Person person;


  /**
   * Start the app, load FXML and show scene.
   * Runs before each test.
   *
   * @throws IOException if loading of fxml fails.
   */
  @Start
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("App.fxml"));
  
    final Parent parent = fxmlLoader.load();
    appController = (AppController) fxmlLoader.getController();
    appController.setPrefix("test");
    appController.load();
    
    person = new Person("RichardWilkens");
    person.setFirstName("Richard");
    person.setLastName("Wilkens");
    person.setPassword("password123");
    
    // The sign up will only be executed on the first test.
    // For the next tests, Richard is already signed up.
    appController.receiveMessage(this, Message.SignUp, person);
    appController.receiveMessage(this, Message.Login, person);
    
    stage.setScene(new Scene(parent));
    stage.show();
  }

  @Test
  @Order(1)
  public void checkUserPane() {
    FxAssert.verifyThat("#nameLabel", LabeledMatchers.hasText("Richard Wilkens"));
    FxAssert.verifyThat("#usernameLabel", LabeledMatchers.hasText("RichardWilkens"));
    FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("0.0"));
  }

  @Test
  @Order(2)
  public void checkInvalidCardNumber() {
    clickOn("#makeDepositButton");

    clickOn("#cardTextField").write("1234 5678 1234 5678");
    clickOn("#moneyAmountTextField").write("100");
    clickOn("#addFundsButton");
    FxAssert.verifyThat("#moneyErrorLabel",
        LabeledMatchers.hasText(DynamicText.InvalidCardIdentifierError.getMessage()));
    clickOn("#cardTextField").eraseText(19);

    clickOn("#cardTextField").write("4106 5778 3149 6289");
    clickOn("#moneyAmountTextField").write("100");
    clickOn("#addFundsButton");
    FxAssert.verifyThat("#moneyErrorLabel",
        LabeledMatchers.hasText(DynamicText.InvalidCardControlDigitError.getMessage()));
    clickOn("#cardTextField").eraseText(19);
    
    clickOn("#cardTextField").write("1");
    clickOn("#addFundsButton");
    FxAssert.verifyThat("#moneyErrorLabel",
        LabeledMatchers.hasText(DynamicText.WrongLengthCardNumberError.getMessage()));
    clickOn("#cardTextField").eraseText(1);
  }

  @Test
  @Order(3)
  public void checkInvalidMoney() {
    clickOn("#makeDepositButton");

    clickOn("#cardTextField").write("4106 5778 3149 6288");
    clickOn("#moneyAmountTextField").write("-100");
    clickOn("#addFundsButton");
    FxAssert.verifyThat("#moneyErrorLabel",
        LabeledMatchers.hasText(DynamicText.NonIntegerError.getMessage()));
    
    clickOn("#moneyAmountTextField").eraseText(4);
    clickOn("#moneyAmountTextField").write("100000000");
    clickOn("#addFundsButton");
    FxAssert.verifyThat("#moneyErrorLabel",
        LabeledMatchers.hasText(DynamicText.TooLargeBalanceError.getMessage()));

    clickOn("#moneyAmountTextField").eraseText(9);
    clickOn("#moneyAmountTextField").write("0");
    clickOn("#addFundsButton");
    FxAssert.verifyThat("#moneyErrorLabel",
        LabeledMatchers.hasText(DynamicText.ZeroBalanceError.getMessage()));
  }

  @Test
  @Order(4)
  public void cancelMoney() {
    clickOn("#makeDepositButton");
    clickOn("#moneyCancelButton");
    FxAssert.verifyThat("#makeDepositButton", LabeledMatchers.hasText("Make deposit"));
    // Test fails if page was not switched
  }

  @Test
  @Order(5)
  public void checkAddMoney() {
    clickOn("#makeDepositButton");
    clickOn("#cardTextField").write("4106 5778 3149 6288");
    clickOn("#moneyAmountTextField").write("100");
    clickOn("#addFundsButton");
    FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("100.0"));
  }
  
  @Test
  @Order(6)
  public void checkBookWrongDates() {
    DateTimeFormatter systemFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
    String dateFrom = systemFormat.format(LocalDate.now().plusDays(20));
    String dateTo = systemFormat.format(LocalDate.now().plusDays(18));
    
    clickOn((lookup("#startDatePicker").queryAs(DatePicker.class))
        .getEditor()).write(dateFrom + '\n');
    clickOn((lookup("#endDatePicker").queryAs(DatePicker.class))
        .getEditor()).write(dateTo + '\n');
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.TimeOrderError.getMessage()));

    dateFrom = systemFormat.format(LocalDate.now().minusDays(2));
    dateTo = systemFormat.format(LocalDate.now().plusDays(1));  
    
    clickOn((lookup("#startDatePicker").queryAs(DatePicker.class)).getEditor()).eraseText(10);
    clickOn((lookup("#endDatePicker").queryAs(DatePicker.class)).getEditor()).eraseText(10);
    clickOn((lookup("#startDatePicker").queryAs(DatePicker.class))
        .getEditor()).write(dateFrom + '\n');
    clickOn((lookup("#endDatePicker").queryAs(DatePicker.class)).getEditor()).write(dateTo + '\n');
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.BeforeNowError.getMessage()));
  }

  @Test
  @Order(7)
  public void testPriceLabelIsCorrect() {
    FxAssert.verifyThat("#hotelRoom101PricePerNightLabel", LabeledMatchers.hasText("0.0"));
    FxAssert.verifyThat("#hotelRoom714PricePerNightLabel", LabeledMatchers.hasText("300.0"));
    FxAssert.verifyThat("#hotelRoom102TotalPriceLabel", LabeledMatchers.hasText(""));

    DateTimeFormatter systemFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
    String dateFrom = systemFormat.format(LocalDate.now());
    String dateTo = systemFormat.format(LocalDate.now().plusDays(3));

    clickOn((lookup("#startDatePicker").queryAs(DatePicker.class))
        .getEditor()).write(dateFrom + '\n');
    clickOn((lookup("#endDatePicker").queryAs(DatePicker.class))
        .getEditor()).write(dateTo + '\n');

    FxAssert.verifyThat("#hotelRoom102TotalPriceLabel",
        LabeledMatchers.hasText("60.0"));
    FxAssert.verifyThat("#hotelRoom714ErrorLabel",
        LabeledMatchers.hasText(DynamicText.NotEnoughMoneyError.getMessage()));
  }

  @Test
  @Order(8)
  public void checkBookHotel() {
    DateTimeFormatter systemFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
    String dateFrom = systemFormat.format(LocalDate.now());
    String dateTo = systemFormat.format(LocalDate.now().plusDays(3));
    
    clickOn((lookup("#startDatePicker").queryAs(DatePicker.class))
        .getEditor()).write(dateFrom + '\n');
    clickOn((lookup("#endDatePicker").queryAs(DatePicker.class))
        .getEditor()).write(dateTo + '\n');
    clickOn("#amenityTelevision");

    // Richard should have money from previous test.
    FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("100.0"));

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
}
