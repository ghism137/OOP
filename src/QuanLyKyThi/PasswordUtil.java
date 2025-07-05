package QuanLyKyThi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    private static final int SALT_LENGTH = 16; // 16 bytes for salt

    /**
     * Hashes a password using SHA-256 with a random salt.
     * The returned string will be in the format "salt:hashedPassword".
     * @param password The plain text password.
     * @return A salted and hashed password string.
     */
    public static String hashPassword(String password) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            // Combine salt and hashed password, then encode to Base64
            String saltStr = Base64.getEncoder().encodeToString(salt);
            String hashedStr = Base64.getEncoder().encodeToString(hashedPassword);

            return saltStr + ":" + hashedStr;
        } catch (NoSuchAlgorithmException e) {
            // Fallback: return plain text if hashing fails (should not happen with SHA-256)
            System.err.println("Error hashing password: " + e.getMessage());
            return password; // In a real app, you'd throw an exception or handle more robustly
        }
    }

    /**
     * Verifies a plain text password against a salted and hashed password.
     * @param plainPassword The plain text password to verify.
     * @param hashedPasswordWithSalt The stored salted and hashed password (format "salt:hashedPassword").
     * @return true if passwords match, false otherwise.
     */
    public static boolean verifyPassword(String plainPassword, String hashedPasswordWithSalt) {
        try {
            String[] parts = hashedPasswordWithSalt.split(":");
            if (parts.length != 2) {
                return false; // Invalid format
            }
            String saltStr = parts[0];
            String storedHashedPasswordStr = parts[1];

            byte[] salt = Base64.getDecoder().decode(saltStr);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedAttempt = md.digest(plainPassword.getBytes());

            String hashedAttemptStr = Base64.getEncoder().encodeToString(hashedAttempt);

            return hashedAttemptStr.equals(storedHashedPasswordStr);
        } catch (NoSuchAlgorithmException | IllegalArgumentException e) {
            System.err.println("Error verifying password: " + e.getMessage());
            return false; // Error during verification
        }
    }
}
