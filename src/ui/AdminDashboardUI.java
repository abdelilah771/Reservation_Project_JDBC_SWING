package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardUI {

    private JFrame frame;

    public AdminDashboardUI() {
        frame = new JFrame("Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Create Navigation Panel
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new GridLayout(1, 5));

        JButton btnClients = new JButton("Manage Clients");
        JButton btnRooms = new JButton("Manage Rooms");
        JButton btnCategories = new JButton("Manage Categories");
        JButton btnReservations = new JButton("Manage Reservations");
        JButton btnLogout = new JButton("Logout");

        navigationPanel.add(btnClients);
        navigationPanel.add(btnRooms);
        navigationPanel.add(btnCategories);
        navigationPanel.add(btnReservations);
        navigationPanel.add(btnLogout);

        frame.add(navigationPanel, BorderLayout.NORTH);

        // Main Panel for Dynamic Content
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());
        frame.add(mainPanel, BorderLayout.CENTER);

        // Panels for Each Section
        JPanel clientsPanel = new JPanel();
        clientsPanel.add(new JLabel("Client Management"));

        JPanel roomsPanel = new JPanel();
        roomsPanel.add(new JLabel("Room Management"));

        JPanel categoriesPanel = new JPanel();
        categoriesPanel.add(new JLabel("Category Management"));

        JPanel reservationsPanel = new JPanel();
        reservationsPanel.add(new JLabel("Reservation Management"));

        // Adding Panels to Main Panel
        mainPanel.add(clientsPanel, "Clients");
        mainPanel.add(roomsPanel, "Rooms");
        mainPanel.add(categoriesPanel, "Categories");
        mainPanel.add(reservationsPanel, "Reservations");

        // Action Listeners for Buttons
        btnClients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) mainPanel.getLayout();
                cl.show(mainPanel, "Clients");
            }
        });

        btnRooms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) mainPanel.getLayout();
                cl.show(mainPanel, "Rooms");
            }
        });

        btnCategories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) mainPanel.getLayout();
                cl.show(mainPanel, "Categories");
            }
        });

        btnReservations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) mainPanel.getLayout();
                cl.show(mainPanel, "Reservations");
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(frame, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    frame.dispose();
                }
            }
        });

        // Display the Frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminDashboardUI();
            }
        });
    }
}
