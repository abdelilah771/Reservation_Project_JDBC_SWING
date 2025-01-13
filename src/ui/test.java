package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import Entities.Chambre;
import Entities.Categorie;
import Entities.Client;
import Entities.Reservation;
import Service.ChambreService;
import Service.CategorieService;
import Service.ClientService;
import Service.ReservationService;

public class ClientLoginFrame extends JFrame {
    private final ClientService clientService;

    public ClientLoginFrame() {
        this.clientService = new ClientService();

        setTitle("Client Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (clientService.authenticate(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful.");
                new ClientDashboardFrame(username).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        });

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            Client newClient = new Client();
            newClient.setUsername(username);
            newClient.setPassword(password);

            if (clientService.register(newClient)) {
                JOptionPane.showMessageDialog(this, "Registration successful. You can now log in.");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Username might already exist.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientLoginFrame().setVisible(true));
    }
}

class ClientDashboardFrame extends JFrame {
    private final ChambreService chambreService;
    private final ReservationService reservationService;

    public ClientDashboardFrame(String username) {
        this.chambreService = new ChambreService();
        this.reservationService = new ReservationService();

        setTitle("Client Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Rooms", createRoomsPanel(username));

        add(tabbedPane);
    }

    private JPanel createRoomsPanel(String username) {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadRoomsData(table);

        JPanel reservationPanel = new JPanel(new GridLayout(3, 2));
        JTextField roomIdField = new JTextField();
        JTextField startDateField = new JTextField("yyyy-MM-dd");
        JTextField endDateField = new JTextField("yyyy-MM-dd");

        reservationPanel.add(new JLabel("Room ID:"));
        reservationPanel.add(roomIdField);
        reservationPanel.add(new JLabel("Start Date:"));
        reservationPanel.add(startDateField);
        reservationPanel.add(new JLabel("End Date:"));
        reservationPanel.add(endDateField);

        JButton reserveButton = new JButton("Reserve Room");
        reservationPanel.add(reserveButton);

        panel.add(reservationPanel, BorderLayout.SOUTH);

        reserveButton.addActionListener(e -> {
            try {
                int roomId = Integer.parseInt(roomIdField.getText());
                Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateField.getText());
                Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateField.getText());

                if (reservationService.isRoomReserved(roomId, startDate, endDate)) {
                    JOptionPane.showMessageDialog(this, "Room is already reserved for the selected dates.");
                } else {
                    Reservation reservation = new Reservation();
//                    reservation.setClientUsername(username);
//                    reservation.setChambreId(roomId);
                    reservation.setDatedebut(startDate);
                    reservation.setDatefin(endDate);

                    reservationService.create(reservation);
                    JOptionPane.showMessageDialog(this, "Room reserved successfully.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error reserving room: " + ex.getMessage());
            }
        });

        return panel;
    }

    private void loadRoomsData(JTable table) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Telephone", "Category"}, 0);
        List<Chambre> rooms = chambreService.findAll();
        for (Chambre room : rooms) {
            String category = (room.getCategorie() != null) ? room.getCategorie().getLibelle() : "No Category";
            model.addRow(new Object[]{room.getId(), room.getTelephone(), category});
        }
        table.setModel(model);
    }
}
