package Test;

import Entities.Notification;

import java.time.LocalDateTime;

public class NotificationTest {
    public static void main(String[] args) {
        // Create a new Notification instance
        Notification notification = new Notification();

        // Set properties using setters
        notification.setId(1);
        notification.setUserId(101);
        notification.setMessage("Your reservation has been approved!");
        notification.setStatus("unread");
        notification.setCreatedAt(LocalDateTime.now());

        // Test getters
        System.out.println("Notification ID: " + notification.getId());
        System.out.println("User ID: " + notification.getUserId());
        System.out.println("Message: " + notification.getMessage());
        System.out.println("Status: " + notification.getStatus());
        System.out.println("Created At: " + notification.getCreatedAt());

        // Update the status to "read"
        notification.setStatus("read");

        // Print the updated status
        System.out.println("Updated Status: " + notification.getStatus());
    }
}
