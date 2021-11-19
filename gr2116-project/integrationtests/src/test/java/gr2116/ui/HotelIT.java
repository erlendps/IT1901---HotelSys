package gr2116.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import gr2116.ui.controller.AppController;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

/**
 * 
 */
public class HotelIT extends ApplicationTest {
  AppController appController;

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
    System.out.println("Base Hotel URI: http://localhost:8080/rest/hotel/");
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
    clickOn("#passwordTextField").write("blueball");
    clickOn("#loginButton");
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.UsernameHasNoMatches.getMessage()));
    clickOn("#usernameTextField").eraseText(8);
    clickOn("#usernameTextField").write("tom");
    clickOn("#loginButton");
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.WrongPassword.getMessage()));
    clickOn("#passwordTextField").write("ing");
    clickOn("#loginButton");
    FxAssert.verifyThat("#usernameLabel", LabeledMatchers.hasText("tom"));
    FxAssert.verifyThat("#nameLabel", LabeledMatchers.hasText("Thomas Wonka"));
  }

  @Test
  public void testCreateUser() {
    FxAssert.verifyThat("#startSignUpButton", LabeledMatchers.hasText("Sign up"));
    clickOn("#startSignUpButton");
    clickOn("#usernameTextField").write("tom");
    clickOn("#firstNameTextField").write("Thomas");
    clickOn("#passwordTextField").write("bananana");
    clickOn("#signUpButton");
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.InvalidLastName.getMessage()));
    clickOn("#firstNameTextField").eraseText(6);
    clickOn("#lastNameTextField").write("Bruhman");
    clickOn("#signUpButton");
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.InvalidFirstName.getMessage()));
    clickOn("#firstNameTextField").write("Thomas");
    clickOn("#signUpButton");
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.UsernameTaken.getMessage()));
    clickOn("#usernameTextField").eraseText(3);
    clickOn("#usernameTextField").write("tomas");
    clickOn("#signUpButton");
    FxAssert.verifyThat("#nameLabel", LabeledMatchers.hasText("Thomas Bruhman"));
  }

  @Test
  public void testCreateUserWithBadPasswordOrUsername() {
    clickOn("#startSignUpButton");
    clickOn("#usernameTextField").write("babby?3");
    clickOn("#firstNameTextField").write("Thomas");
    clickOn("#lastNameTextField").write("DaBaby");
    clickOn("#passwordTextField").write("bananana");
    clickOn("#signUpButton");
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.InvalidUsername.getMessage()));
    clickOn("#usernameTextField").eraseText(2);
    clickOn("#passwordTextField").eraseText(6);
    clickOn("#signUpButton");
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.InvalidPassword.getMessage()));
    clickOn("#passwordTextField").write("nana");
    clickOn("#signUpButton");
    FxAssert.verifyThat("#nameLabel", LabeledMatchers.hasText("Thomas DaBaby"));
  }

  @Test
  public void testAddBalance() {
    clickOn("#startLoginButton");
    clickOn("#usernameTextField").write("kyle");
    clickOn("#passwordTextField").write("owingyou1");
    clickOn("#loginButton");

    FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("400.0"));

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
    
    clickOn("#moneyAmountTextField").eraseText(1);
    clickOn("#moneyAmountTextField").write("200");
    clickOn("#addFundsButton");
    FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("600.0"));
  }

  @Test
  public void testMakeReservation() {
    clickOn("#startLoginButton");
    clickOn("#usernameTextField").write("tom");
    clickOn("#passwordTextField").write("blueballing");
    clickOn("#loginButton");

    DateTimeFormatter systemFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
    String dateFrom = systemFormat.format(LocalDate.now());
    String dateTo = systemFormat.format(LocalDate.now().plusDays(3));
    
    clickOn((lookup("#startDatePicker").queryAs(DatePicker.class))
        .getEditor()).write(dateFrom + '\n');
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
    FxAssert.verifyThat("#roomItemContainer", NodeMatchers.hasChildren(0, "#hotelRoom101ListItem"));
  }

  @Test
  public void testLoginThenLogoutThenLoginIsPersistent() {
    clickOn("#startLoginButton");
    clickOn("#usernameTextField").write("rick");
    clickOn("#passwordTextField").write("bananas");
    clickOn("#loginButton");

    DateTimeFormatter systemFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
    String dateFrom = systemFormat.format(LocalDate.now());
    String dateTo = systemFormat.format(LocalDate.now().plusDays(3));

    clickOn((lookup("#startDatePicker").queryAs(DatePicker.class))
        .getEditor()).write(dateFrom + '\n');
    clickOn((lookup("#endDatePicker").queryAs(DatePicker.class))
        .getEditor()).write(dateTo + '\n');

    clickOn("#amenityInternet");
    FxAssert.verifyThat("#roomItemContainer", NodeMatchers.hasChild("#hotelRoom102ListItem"));
    clickOn("#hotelRoom102Button");
    StringBuilder sb = new StringBuilder();
    sb.append("#hotelRoom102reservation");
    sb.append(LocalDate.now());
    sb.append("to");
    sb.append(LocalDate.now().plusDays(3));
    FxAssert.verifyThat("#reservationListView", NodeMatchers.hasChild(sb.toString()));
    FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("850.0"));

    clickOn("#signOutButton");
    clickOn("#startLoginButton");
    clickOn("#loginButton");
    FxAssert.verifyThat("#reservationListView", NodeMatchers.hasChild(sb.toString()));
    FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("850.0"));
  }

  @Test
  public void testExperienceAsWhole() {
    clickOn("#startSignUpButton");
    clickOn("#usernameTextField").write("frank");
    clickOn("#firstNameTextField").write("Franklin");
    clickOn("#lastNameTextField").write("DeRoosevelt");
    clickOn("#passwordTextField").write("almostFDR");
    clickOn("#signUpButton");
    FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("0.0"));

    DateTimeFormatter systemFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
    String dateFrom = systemFormat.format(LocalDate.now().plusDays(5));
    String dateTo = systemFormat.format(LocalDate.now().plusDays(8));

    clickOn((lookup("#startDatePicker").queryAs(DatePicker.class))
        .getEditor()).write(dateFrom + '\n');
    clickOn((lookup("#endDatePicker").queryAs(DatePicker.class))
        .getEditor()).write(dateTo + '\n');
    clickOn("#amenityTelevision");
    clickOn("#amenityShower");
    FxAssert.verifyThat("#roomItemContainer", NodeMatchers.hasChild("#hotelRoom714ListItem"));
    FxAssert.verifyThat("#hotelRoom714ErrorLabel",
        LabeledMatchers.hasText(DynamicText.NotEnoughMoneyError.getMessage()));

    clickOn("#makeDepositButton");
    clickOn("#cardTextField").write("4106 5778 3149 6288");
    clickOn("#moneyAmountTextField").write("30000");
    clickOn("#addFundsButton");
    FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("30000.0"));

    clickOn("#hotelRoom714Button");
    StringBuilder sb = new StringBuilder();
    sb.append("#hotelRoom714reservation");
    sb.append(LocalDate.now().plusDays(5));
    sb.append("to");
    sb.append(LocalDate.now().plusDays(8));
    FxAssert.verifyThat("#reservationListView", NodeMatchers.hasChild(sb.toString()));
    FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("29100.0"));

    clickOn("#signOutButton");
    clickOn("#startLoginButton");
    clickOn("#usernameTextField").write("frank");
    clickOn("#passwordTextField").write("almostFDR");
    clickOn("#loginButton");

    FxAssert.verifyThat("#reservationListView", NodeMatchers.hasChild(sb.toString()));
    FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("29100.0"));
    FxAssert.verifyThat("#usernameLabel", LabeledMatchers.hasText("frank"));
    FxAssert.verifyThat("#nameLabel", LabeledMatchers.hasText("Franklin DeRoosevelt"));
  }

  @AfterAll
  public static void cleanUp() {
    File file = Paths.get(System.getProperty("user.home"), "HotelSys", "testItHotel.json").toFile();
    file.delete();
  }
}
