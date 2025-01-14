//package ui;
//
//import Entities.Chambre;
//import Service.ChambreService;
//import Service.ClientService;
//import Service.ReservationService;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.List;
//
//public class RoomListFrame extends JFrame {
//    private JTable roomTable;
//    private JButton reserveButton;
//    private ClientService clientService;
//    private int clientId; // Logged-in client ID
//
//    public RoomListFrame(int clientId) {
//        this.clientId = clientId;
//        this.clientService = new ClientService();
//
//        setTitle("Room List");
//        setSize(600, 400);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Table to display rooms
//        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Room Name", "Category"}, 0);
//        roomTable = new JTable(tableModel);
//        JScrollPane scrollPane = new JScrollPane(roomTable);
//        add(scrollPane, BorderLayout.CENTER);
//
//        // Fetch and display rooms
//        List<Chambre> rooms = ChambreService.findAll();
//        for (Chambre room : rooms) {
//            tableModel.addRow(new Object[]{room.getId(), room.getTelephone(), room.getCategorie()});
//        }
//
//        // Reserve button
//        reserveButton = new JButton("Reserve Selected Room");
//        reserveButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedRow = roomTable.getSelectedRow();
//                if (selectedRow != -1) {
//                    int roomId = (int) tableModel.getValueAt(selectedRow, 0);
//                    createReservation(roomId);
//                } else {
//                    JOptionPane.showMessageDialog(RoomListFrame.this, "Please select a room to reserve.");
//                }
//            }
//        });
//        add(reserveButton, BorderLayout.SOUTH);
//
//        setVisible(true);
//    }
//
//    // Create reservation for the selected room
//    private void createReservation(int roomId) {
//        boolean success = ReservationService.(clientId, roomId);
//        if (success) {
//            JOptionPane.showMessageDialog(this, "Reservation successful!");
//        } else {
//            JOptionPane.showMessageDialog(this, "Failed to make a reservation. Please try again.");
//        }
//    }
//
//    public static void main(String[] args) {
//        // Example client ID (replace with actual logged-in client ID)
//        int exampleClientId = 1;
//        new RoomListFrame(exampleClientId);
//    }
//}
