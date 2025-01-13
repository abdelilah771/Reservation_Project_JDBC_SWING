package ui;

import Entities.Client;
import Service.ClientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientServiceTestInterface {

    private ClientService clientService;

    public ClientServiceTestInterface() {
        clientService = new ClientService();
        createUI();
    }

    private void createUI() {
        JFrame frame = new JFrame("Client Service Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Registration Tab
        JPanel registerPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        JLabel nameLabel = new JLabel("Nom:");
        JTextField nameField = new JTextField();
        JLabel prenomLabel = new JLabel("Prénom:");
        JTextField prenomField = new JTextField();
        JLabel usernameLabel = new JLabel("Telephone:");
        JTextField usernameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel telephoneLabel = new JLabel("Username:");
        JTextField telephoneField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton registerButton = new JButton("Register");

        registerPanel.add(nameLabel);
        registerPanel.add(nameField);
        registerPanel.add(prenomLabel);
        registerPanel.add(prenomField);
        registerPanel.add(usernameLabel);
        registerPanel.add(usernameField);
        registerPanel.add(emailLabel);
        registerPanel.add(emailField);
        registerPanel.add(telephoneLabel);
        registerPanel.add(telephoneField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(new JLabel());
        registerPanel.add(registerButton);

        tabbedPane.add("Register", registerPanel);

        // Login Tab
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel loginUsernameLabel = new JLabel("Username:");
        JTextField loginUsernameField = new JTextField();
        JLabel loginPasswordLabel = new JLabel("Password:");
        JPasswordField loginPasswordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginPanel.add(loginUsernameLabel);
        loginPanel.add(loginUsernameField);
        loginPanel.add(loginPasswordLabel);
        loginPanel.add(loginPasswordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        tabbedPane.add("Login", loginPanel);

        frame.add(tabbedPane);
        frame.setVisible(true);

        // Registration Button Action
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String prenom = prenomField.getText();
                String telephone = usernameField.getText();
                String email = emailField.getText();
                String username = telephoneField.getText();
                String password = new String(passwordField.getPassword());

                if (name.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Client client = new Client(0, name, prenom, username, email, password, telephone);
                    boolean success = clientService.register(client);
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Login Button Action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginUsernameField.getText();
                String password = new String(loginPasswordField.getPassword());

                boolean authenticated = clientService.authenticate(username, password);
                if (authenticated) {
                    JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        new ClientServiceTestInterface();
    }
}
