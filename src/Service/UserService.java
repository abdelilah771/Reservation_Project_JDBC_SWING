package Service;

import Connection_Project.Connexion;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private Connection connection = Connexion.getCnx();

    // Register a new user
    public boolean register(String username, String email, String password) {
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, encryptPassword(password));
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    // Authenticate user login
    public boolean login(String email, String password) {
        String query = "SELECT password FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    return storedPassword.equals(encryptPassword(password));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
        return false;
    }

    // Encrypt the password using SHA-256
    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encrypting password: " + e.getMessage());
        }
    }
}
