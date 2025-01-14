package ui;

import Entities.Categorie;
import Entities.Chambre;
import Entities.Client;
import Entities.Reservation;
import Service.ChambreService;
import Service.ClientService;
import Service.ReservationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReservationServiceUIIntegration {

    private ClientService clientService;
    private ChambreService chambreService;
    private ReservationService reservationService;
    private JFrame frame;

    public ReservationServiceUIIntegration() {
        clientService = new ClientService();
        chambreService = new ChambreService();
        reservationService = new ReservationService();
        createUI();
    }

    private void createUI() {
        frame = new JFrame("Client Service Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Registration Tab
        JPanel registerPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        JLabel nameLabel = new JLabel("Nom:");
        JTextField nameField = new JTextField();
        JLabel prenomLabel = new JLabel("Prénom:");
        JTextField prenomField = new JTextField();
        JLabel telephoneLabel = new JLabel("Telephone:");
        JTextField telephoneField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton registerButton = new JButton("Register");

        registerPanel.add(nameLabel);
        registerPanel.add(nameField);
        registerPanel.add(prenomLabel);
        registerPanel.add(prenomField);
        registerPanel.add(telephoneLabel);
        registerPanel.add(telephoneField);
        registerPanel.add(emailLabel);
        registerPanel.add(emailField);
        registerPanel.add(usernameLabel);
        registerPanel.add(usernameField);
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
                String telephone = telephoneField.getText();
                String email = emailField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (name.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Client client = new Client(0, name, prenom, telephone, email, username, password);
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
                    showChambresPanel();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void showChambresPanel() {
        JFrame chambreFrame = new JFrame("Available Chambres");
        chambreFrame.setSize(800, 600);

        JPanel panel = new JPanel(new BorderLayout());

        // Table to display chambres
        String[] columnNames = {"ID", "Telephone", "Categorie Code", "Categorie Libelle"};
        List<Chambre> chambres = chambreService.findAll();
        String[][] data = new String[chambres.size()][4];

        for (int i = 0; i < chambres.size(); i++) {
            Chambre chambre = chambres.get(i);
            data[i][0] = String.valueOf(chambre.getId());
            data[i][1] = chambre.getTelephone();
            data[i][2] = chambre.getCategorie().getCode();
            data[i][3] = chambre.getCategorie().getLibelle();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        // Reservation Panel
        JPanel reservationPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel chambreIdLabel = new JLabel("Chambre ID:");
        JTextField chambreIdField = new JTextField();
        JButton reserveButton = new JButton("Reserve");

        reservationPanel.add(chambreIdLabel);
        reservationPanel.add(chambreIdField);
        reservationPanel.add(new JLabel());
        reservationPanel.add(reserveButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(reservationPanel, BorderLayout.SOUTH);

        chambreFrame.add(panel);
        chambreFrame.setVisible(true);

        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int chambreId = Integer.parseInt(chambreIdField.getText());
                    Chambre chambre = chambreService.findById(chambreId);
                    if (chambre == null) {
                        JOptionPane.showMessageDialog(chambreFrame, "Chambre not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JSpinner startDateSpinner = new JSpinner(new SpinnerDateModel());
                    JSpinner endDateSpinner = new JSpinner(new SpinnerDateModel());
                    startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd"));
                    endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd"));

                    JPanel datePanel = new JPanel(new GridLayout(2, 2, 10, 10));
                    datePanel.add(new JLabel("Start Date:"));
                    datePanel.add(startDateSpinner);
                    datePanel.add(new JLabel("End Date:"));
                    datePanel.add(endDateSpinner);

                    int result = JOptionPane.showConfirmDialog(chambreFrame, datePanel, "Select Reservation Dates", JOptionPane.OK_CANCEL_OPTION);
                    if (result != JOptionPane.OK_OPTION) {
                        return;
                    }

                    java.util.Date startDate = (java.util.Date) startDateSpinner.getValue();
                    java.util.Date endDate = (java.util.Date) endDateSpinner.getValue();

                    if (endDate.before(startDate)) {
                        JOptionPane.showMessageDialog(chambreFrame, "End date must be after start date!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Client loggedInClient = clientService.getLoggedInClient(); // Assumes this method returns the logged-in client
                    Reservation reservation = new Reservation(0, loggedInClient, chambre, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));

                    boolean isReserved = reservationService.isRoomReserved(chambreId, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
                    if (isReserved) {
                        JOptionPane.showMessageDialog(chambreFrame, "Chambre is already reserved for the selected dates!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    boolean success = reservationService.create(reservation);
                    if (success) {
                        JOptionPane.showMessageDialog(chambreFrame, "Reservation successful for Chambre ID: " + chambreId, "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(chambreFrame, "Failed to create reservation!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(chambreFrame, "Invalid Chambre ID!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        new ReservationServiceUIIntegration();
    }
}
