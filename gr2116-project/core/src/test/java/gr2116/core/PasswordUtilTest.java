package gr2116.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for PasswordUtil
 */
public class PasswordUtilTest {
  
  @Test
  public void passwordHashTest() {
    String password = "password123";
    String password1 = "verysafepassword";
    String hashed = PasswordUtil.hashPassword(password);
    String hashed1 = PasswordUtil.hashPassword(password1);
    assertNotEquals(password, hashed);
    assertNotEquals(hashed, hashed1);
    assertEquals(hashed, PasswordUtil.hashPassword(password));
    assertEquals(password.getClass(), hashed.getClass());
  }
}
