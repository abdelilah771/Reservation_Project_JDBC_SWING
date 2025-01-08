package Test;

import Service.UserService;

import java.util.Scanner;

public class MainUserTest {
    public static void main(String[] args) {
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== User Management System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    boolean isRegistered = userService.register(username, email, password);
                    if (isRegistered) {
                        System.out.println("User registered successfully.");
                    } else {
                        System.out.println("User registration failed.");
                    }
                }
                case 2 -> {
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    boolean isLoggedIn = userService.login(email, password);
                    if (isLoggedIn) {
                        System.out.println("Login successful.");
                    } else {
                        System.out.println("Login failed. Check your credentials.");
                    }
                }
                case 3 -> {
                    System.out.println("Exiting. Goodbye!");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
