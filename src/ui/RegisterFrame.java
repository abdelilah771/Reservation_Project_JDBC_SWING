package ui;

import Service.UserService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegisterFrame extends JFrame {
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private UserService userService;

    public RegisterFrame() {
        userService = new UserService();
        setTitle("Register");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Register", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(this::registerAction);
        formPanel.add(registerButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        formPanel.add(backButton);

        add(formPanel, BorderLayout.CENTER);
    }

    private void registerAction(ActionEvent e) {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        try {
            userService.register(username, email, password);
            JOptionPane.showMessageDialog(this, "Registration successful! Please log in.");
            new LoginFrame().setVisible(true);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Registration failed: " + ex.getMessage());
        }
    }
}
