package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import Entities.Reservation;
import Service.ReservationService;

public class AdminDashboardFrame extends JFrame {
    private final ReservationService reservationService;

    public AdminDashboardFrame() {
        this.reservationService = new ReservationService();

        setTitle("Admin Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Reservations", createReservationsPanel());

        add(tabbedPane);
    }

    private JPanel createReservationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        JTextField reservationIdField = new JTextField();
        formPanel.add(new JLabel("Reservation ID:"));
        formPanel.add(reservationIdField);

        JButton approveButton = new JButton("Approve Reservation");
        JButton denyButton = new JButton("Deny Reservation");
        formPanel.add(approveButton);
        formPanel.add(denyButton);

        panel.add(formPanel, BorderLayout.SOUTH);

        loadReservationsData(table);

        approveButton.addActionListener(e -> {
            try {
                int reservationId = Integer.parseInt(reservationIdField.getText());
                Reservation reservation = reservationService.findById(reservationId);
                if (reservation != null) {
                    reservation.setApproved(true);
                    reservationService.update(reservation);
                    loadReservationsData(table);
                    JOptionPane.showMessageDialog(this, "Reservation approved successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Reservation not found.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error approving reservation: " + ex.getMessage());
            }
        });

        denyButton.addActionListener(e -> {
            try {
                int reservationId = Integer.parseInt(reservationIdField.getText());
                Reservation reservation = reservationService.findById(reservationId);
                if (reservation != null) {
                    reservation.setApproved(false);
                    reservationService.update(reservation);
                    loadReservationsData(table);
                    JOptionPane.showMessageDialog(this, "Reservation denied successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Reservation not found.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error denying reservation: " + ex.getMessage());
            }
        });

        return panel;
    }

    private void loadReservationsData(JTable table) {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Client", "Room", "Status", "Start Date", "End Date"}, 0);
        List<Reservation> reservations = reservationService.findAll();
        for (Reservation reservation : reservations) {
            model.addRow(new Object[]{
                    reservation.getId(),
                    reservation.getClient().getNom(),
                    reservation.getChambre().getTelephone(),
                    reservation.getStatus(),
                    reservation.getDatedebut(),
                    reservation.getDatefin()
            });
        }
        table.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminDashboardFrame frame = new AdminDashboardFrame();
            frame.setVisible(true);
        });
    }
}
