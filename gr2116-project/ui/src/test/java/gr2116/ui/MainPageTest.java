package gr2116.ui;

import gr2116.core.Person;
import gr2116.ui.controller.AppController;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

/**
 * 
 */
public class MainPageTest extends ApplicationTest{
  AppController appController;
  
  /**
   * Start the app, load FXML and show scene.
   * @throws IOException
   */
  @Start
  public void start(Stage stage) throws IOException {
    
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("App.fxml"));
  
    Parent parent = fxmlLoader.load();
    appController = (AppController) fxmlLoader.getController();
    appController.setPrefix("test");
    appController.load();

    Person person = new Person("RichardWilkens");
    person.setFirstName("Richard");
    person.setLastName("Wilkens");
    person.addBalance(100.0);
    appController.getHotelAccess().addPerson(person);
    appController.moveToMainPage(person);
    
    stage.setScene(new Scene(parent));
    stage.show();
  }

  @Test
  public void checkUserPane() {
    FxAssert.verifyThat("#nameLabel", LabeledMatchers.hasText("Richard Wilkens"));
    FxAssert.verifyThat("#usernameLabel", LabeledMatchers.hasText("RichardWilkens"));
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
    FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText(DynamicText.TimeOrderError.getMessage()));

    dateFrom = systemFormat.format(LocalDate.now().minusDays(2));
    dateTo = systemFormat.format(LocalDate.now().plusDays(1));  
    
    clickOn((lookup("#startDatePicker").queryAs(DatePicker.class)).getEditor()).eraseText(10);
    clickOn((lookup("#endDatePicker").queryAs(DatePicker.class)).getEditor()).eraseText(10);
    clickOn((lookup("#startDatePicker").queryAs(DatePicker.class)).getEditor()).write(dateFrom + '\n');
    clickOn((lookup("#endDatePicker").queryAs(DatePicker.class)).getEditor()).write(dateTo + '\n');
    FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText(DynamicText.BeforeNowError.getMessage()));
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
    FxAssert.verifyThat("#hotelRoom714ErrorLabel", LabeledMatchers.hasText(DynamicText.NotEnoughMoneyError.getMessage()));
  }

  @Test
  public void checkInvalidCardNumber() {
    clickOn("#makeDepositButton");

    clickOn("#cardTextField").write("1234 5678 1234 5678");
    clickOn("#moneyAmountTextField").write("100");
    clickOn("#addFundsButton");
    FxAssert.verifyThat("#moneyErrorLabel", LabeledMatchers.hasText(DynamicText.InvalidCardIdentifierError.getMessage()));
    clickOn("#cardTextField").eraseText(19);

    clickOn("#cardTextField").write("4106 5778 3149 6289");
    clickOn("#moneyAmountTextField").write("100");
    clickOn("#addFundsButton");
    FxAssert.verifyThat("#moneyErrorLabel", LabeledMatchers.hasText(DynamicText.InvalidCardControlDigitError.getMessage()));
    clickOn("#cardTextField").eraseText(19);
    
    clickOn("#cardTextField").write("1");
    clickOn("#addFundsButton");
    FxAssert.verifyThat("#moneyErrorLabel", LabeledMatchers.hasText(DynamicText.WrongLengthCardNumberError.getMessage()));
    clickOn("#cardTextField").eraseText(1);
  }

  @Test
  public void checkInvalidMoney() {
    clickOn("#makeDepositButton");

    clickOn("#cardTextField").write("4106 5778 3149 6288");
    clickOn("#moneyAmountTextField").write("-100");
    clickOn("#addFundsButton");
    FxAssert.verifyThat("#moneyErrorLabel", LabeledMatchers.hasText(DynamicText.NonIntegerError.getMessage()));
    
    clickOn("#moneyAmountTextField").eraseText(4);
    clickOn("#moneyAmountTextField").write("100000000");
    clickOn("#addFundsButton");
    FxAssert.verifyThat("#moneyErrorLabel", LabeledMatchers.hasText(DynamicText.TooLargeBalanceError.getMessage()));

    clickOn("#moneyAmountTextField").eraseText(9);
    clickOn("#moneyAmountTextField").write("0");
    clickOn("#addFundsButton");
    FxAssert.verifyThat("#moneyErrorLabel", LabeledMatchers.hasText(DynamicText.ZeroBalanceError.getMessage()));
  }

  @Test
  public void checkAddMoney() {
    clickOn("#makeDepositButton");

    clickOn("#cardTextField").write("4106 5778 3149 6288");
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
