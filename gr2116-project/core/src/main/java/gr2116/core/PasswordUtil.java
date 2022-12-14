package gr2116.core;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Utility class for hashing Person passwords.
 */
public class PasswordUtil {
  private static byte[] salt = new byte[16];

  /**
   * Hashes the given password string with PBKDF2WithHmacSHA1.
   *
   * @param password the password to hash
   *
   * @return the hashed password
   */
  public static String hashPassword(String password) {
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
    SecretKeyFactory f;
    try {
      f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    } catch (NoSuchAlgorithmException e) {
      return null;
    }
    byte[] hash;
    try {
      hash = f.generateSecret(spec).getEncoded();
    } catch (InvalidKeySpecException e) {
      return null;
    }
    Base64.Encoder enc = Base64.getEncoder();
    return enc.encodeToString(hash);
  } 
}
