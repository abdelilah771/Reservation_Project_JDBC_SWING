package Service;

import Connection_Project.Connexion;
import Entities.Client;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private Connection connection = Connexion.getCnx();

    // Register a new client
    public boolean register(Client client) {
        String query = "INSERT INTO clients (username, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getUsername());
            statement.setString(2, client.getEmail());
            statement.setString(3, encryptPassword(client.getPassword()));
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error registering client: " + e.getMessage());
            return false;
        }
    }

    // Authenticate client login
    public boolean authenticate(String username, String password) {
        String query = "SELECT password FROM clients WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
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

    // Create a new client
    public boolean create(Client client) {
        String query = "INSERT INTO clients (username, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getUsername());
            statement.setString(2, client.getEmail());
            statement.setString(3, encryptPassword(client.getPassword()));
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error creating client: " + e.getMessage());
            return false;
        }
    }

    // Update client information
    public boolean update(Client client) {
        String query = "UPDATE clients SET username = ?, email = ?, password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getUsername());
            statement.setString(2, client.getEmail());
            statement.setString(3, encryptPassword(client.getPassword()));
            statement.setInt(4, client.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating client: " + e.getMessage());
            return false;
        }
    }

    // Delete a client
    public boolean delete(Client client) {
        String query = "DELETE FROM clients WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, client.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting client: " + e.getMessage());
            return false;
        }
    }

    // Find a client by ID
    public Client findById(int id) {
        String query = "SELECT * FROM clients WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Client(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding client by ID: " + e.getMessage());
        }
        return null;
    }

    // Find all clients
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM clients";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                clients.add(new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error finding all clients: " + e.getMessage());
        }
        return clients;
    }
}
