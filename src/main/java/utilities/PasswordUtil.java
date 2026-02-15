package utilities;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    /**
     * Hash a password using BCrypt.
     * @param plainTextPassword The password to hash.
     * @return The hashed password.
     */
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    /**
     * Check that a plain text password matches a previously hashed one.
     * @param plainTextPassword The candidate password.
     * @param hashedPassword The stored hash.
     * @return True if passwords match, false otherwise.
     */
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        if (hashedPassword == null || !hashedPassword.startsWith("$2a$")) {
             // Handle legacy plain text passwords or invalid hashes gracefullly
             // For migration: if it matches plain text, you might want to re-hash it, 
             // but here we simply return false or check plain text depending on policy.
             // For security, we strictly fail unless it equals plain text (TEMPORARY MIGRATION STRATEGY ONLY)
             return plainTextPassword.equals(hashedPassword);
        }
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
