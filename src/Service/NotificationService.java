package Service;

import Connection_Project.Connexion;
import Entities.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private final Connection connection;

    public NotificationService() {
        connection = Connexion.getCnx();
    }

    public void createNotification(int userId, String message) {
        try {
            String query = "INSERT INTO notifications (user_id, message, status, created_at) VALUES (?, ?, 'unread', NOW())";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setString(2, message);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error creating notification: " + e.getMessage());
        }
    }

    public List<Notification> getNotificationsByUserId(int userId) {
        List<Notification> notifications = new ArrayList<>();
        try {
            String query = "SELECT * FROM notifications WHERE user_id = ? ORDER BY created_at DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Notification notification = new Notification();
                notification.setId(rs.getInt("id"));
                notification.setUserId(rs.getInt("user_id"));
                notification.setMessage(rs.getString("message"));
                notification.setStatus(rs.getString("status"));
                notification.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                notifications.add(notification);
            }
        } catch (Exception e) {
            System.out.println("Error fetching notifications: " + e.getMessage());
        }
        return notifications;
    }

    public void markAsRead(int notificationId) {
        try {
            String query = "UPDATE notifications SET status = 'read' WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, notificationId);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error marking notification as read: " + e.getMessage());
        }
    }
}
