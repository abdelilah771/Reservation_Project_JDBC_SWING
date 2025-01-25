package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import Entities.Chambre;
import Entities.Categorie;
import Entities.Reservation;
import Entities.Client;
import Service.ChambreService;
import Service.CategorieService;
import Service.ReservationService;
import Service.ClientService;

public class AdminDashboardFrame extends JFrame {
    private final ChambreService chambreService;
    private final CategorieService categorieService;
    private final ReservationService reservationService;
    private final ClientService clientService;

    public AdminDashboardFrame() {
        this.chambreService = new ChambreService();
        this.categorieService = new CategorieService();
        this.reservationService = new ReservationService();
        this.clientService = new ClientService();

        setTitle("Admin Panel - Hotel Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel headerPanel = createHeaderPanel();
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Chambres", createChambresPanel());
        tabbedPane.addTab("Categories", createCategoriesPanel());
        tabbedPane.addTab("Reservations", createReservationsPanel());
        tabbedPane.addTab("Clients", createClientsPanel());

        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Admin Panel - Hotel Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(50, 50, 150));
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createChambresPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTable table = new JTable();
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        formPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(idLabel, gbc);

        JTextField idField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(idField, gbc);

        JLabel telephoneLabel = new JLabel("Telephone:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(telephoneLabel, gbc);

        JTextField telephoneField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(telephoneField, gbc);

        JLabel categorieLabel = new JLabel("Categorie:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(categorieLabel, gbc);

        JComboBox<Categorie> categorieComboBox = new JComboBox<>();
        loadCategoriesIntoComboBox(categorieComboBox);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(categorieComboBox, gbc);

        JButton addButton = new JButton("Add Chambre");
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(addButton, gbc);

        JButton updateButton = new JButton("Update Chambre");
        updateButton.setBackground(new Color(34, 139, 34));
        updateButton.setForeground(Color.WHITE);
        gbc.gridy = 4;
        formPanel.add(updateButton, gbc);

        JButton deleteButton = new JButton("Delete Chambre");
        deleteButton.setBackground(new Color(220, 20, 60));
        deleteButton.setForeground(Color.WHITE);
        gbc.gridy = 5;
        formPanel.add(deleteButton, gbc);

        panel.add(formPanel, BorderLayout.EAST);

        loadChambresData(table);

        addButton.addActionListener(e -> {
            try {
                Chambre chambre = new Chambre();
                chambre.setId(Integer.parseInt(idField.getText()));
                chambre.setTelephone(telephoneField.getText());
                chambre.setCategorie((Categorie) categorieComboBox.getSelectedItem());
                chambreService.create(chambre);
                loadChambresData(table);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding chambre: " + ex.getMessage());
            }
        });

        updateButton.addActionListener(e -> {
            try {
                Chambre chambre = new Chambre();
                chambre.setId(Integer.parseInt(idField.getText()));
                chambre.setTelephone(telephoneField.getText());
                chambre.setCategorie((Categorie) categorieComboBox.getSelectedItem());
                chambreService.update(chambre);
                loadChambresData(table);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating chambre: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Chambre chambre = new Chambre();
                chambre.setId(id);
                chambreService.delete(chambre);
                loadChambresData(table);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting chambre: " + ex.getMessage());
            }
        });

        JButton findByIdButton = new JButton("Find by ID");
        findByIdButton.setBackground(new Color(255, 165, 0)); // Orange color
        findByIdButton.setForeground(Color.WHITE);
        gbc.gridy = 6;
        formPanel.add(findByIdButton, gbc);

        findByIdButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Chambre chambre = chambreService.findById(id);
                if (chambre != null) {
                    // Populate the form fields with the retrieved chambre data
                    idField.setText(String.valueOf(chambre.getId()));
                    telephoneField.setText(chambre.getTelephone());
                    categorieComboBox.setSelectedItem(chambre.getCategorie());
                    JOptionPane.showMessageDialog(this, "Chambre found and data populated.");
                } else {
                    JOptionPane.showMessageDialog(this, "Chambre with ID " + id + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error finding chambre: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        return panel;
    }

    private void loadChambresData(JTable table) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Telephone", "Categorie"}, 0);
        List<Chambre> chambres = chambreService.findAll();
        for (Chambre chambre : chambres) {
            String categorieName = (chambre.getCategorie() != null) ? chambre.getCategorie().getLibelle() : "No Category";
            model.addRow(new Object[]{chambre.getId(), chambre.getTelephone(), categorieName});
        }
        table.setModel(model);
    }

    private void loadCategoriesIntoComboBox(JComboBox<Categorie> comboBox) {
        comboBox.removeAllItems();
        List<Categorie> categories = categorieService.findAll();
        for (Categorie categorie : categories) {
            comboBox.addItem(categorie);
        }
    }

    private JPanel createCategoriesPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table to display categories
        JTable table = new JTable();
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Form panel on the right
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        formPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(idLabel, gbc);

        JTextField idField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(idField, gbc);

        JLabel codeLabel = new JLabel("Code:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(codeLabel, gbc);

        JTextField codeField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(codeField, gbc);

        JLabel libelleLabel = new JLabel("Libelle:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(libelleLabel, gbc);

        JTextField libelleField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(libelleField, gbc);

        JButton addButton = new JButton("Add Category");
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(addButton, gbc);

        JButton updateButton = new JButton("Update Category");
        updateButton.setBackground(new Color(34, 139, 34));
        updateButton.setForeground(Color.WHITE);
        gbc.gridy = 4;
        formPanel.add(updateButton, gbc);

        JButton deleteButton = new JButton("Delete Category");
        deleteButton.setBackground(new Color(220, 20, 60));
        deleteButton.setForeground(Color.WHITE);
        gbc.gridy = 5;
        formPanel.add(deleteButton, gbc);

        panel.add(formPanel, BorderLayout.EAST);

        // Load categories into the table
        loadCategoriesData(table);

        // Add action listeners for the buttons
        addButton.addActionListener(e -> {
            try {
                Categorie categorie = new Categorie();
                categorie.setCode(codeField.getText());
                categorie.setLibelle(libelleField.getText());
                categorieService.create(categorie);
                loadCategoriesData(table);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding category: " + ex.getMessage());
            }
        });

        updateButton.addActionListener(e -> {
            try {
                Categorie categorie = new Categorie();
                categorie.setId(Integer.parseInt(idField.getText()));
                categorie.setCode(codeField.getText());
                categorie.setLibelle(libelleField.getText());
                categorieService.update(categorie);
                loadCategoriesData(table);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating category: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Categorie categorie = new Categorie();
                categorie.setId(id);
                categorieService.delete(categorie);
                loadCategoriesData(table);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting category: " + ex.getMessage());
            }
        });
        JButton findByIdButton = new JButton("Find by ID");
        findByIdButton.setBackground(new Color(255, 165, 0)); // Orange color
        findByIdButton.setForeground(Color.WHITE);
        gbc.gridy = 6;
        formPanel.add(findByIdButton, gbc);

        findByIdButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Categorie categorie = categorieService.findById(id);
                if (categorie != null) {
                    // Populate the form fields with the retrieved category data
                    idField.setText(String.valueOf(categorie.getId()));
                    codeField.setText(categorie.getCode());
                    libelleField.setText(categorie.getLibelle());
                    JOptionPane.showMessageDialog(this, "Category found and data populated.");
                } else {
                    JOptionPane.showMessageDialog(this, "Category with ID " + id + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error finding category: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        return panel;
    }

    private void loadCategoriesData(JTable table) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Code", "Libelle"}, 0);
        List<Categorie> categories = categorieService.findAll();
        for (Categorie categorie : categories) {
            model.addRow(new Object[]{categorie.getId(), categorie.getCode(), categorie.getLibelle()});
        }
        table.setModel(model);
    }

    private JPanel createReservationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(2, 2));
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
                reservationService.approve(reservationId);
                loadReservationsData(table);
                JOptionPane.showMessageDialog(this, "Reservation approved successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error approving reservation: " + ex.getMessage());
            }
        });

        denyButton.addActionListener(e -> {
            try {
                int reservationId = Integer.parseInt(reservationIdField.getText());
                reservationService.deny(reservationId);
                loadReservationsData(table);
                JOptionPane.showMessageDialog(this, "Reservation denied successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error denying reservation: " + ex.getMessage());
            }
        });

        return panel;
    }

    private void loadReservationsData(JTable table) {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Client", "Room", "Start Date", "End Date"}, 0);
        List<Reservation> reservations = reservationService.findAll();
        for (Reservation reservation : reservations) {
            model.addRow(new Object[]{
                    reservation.getId(),
                    reservation.getClient().getNom(),
                    reservation.getChambre().getTelephone(),
                    reservation.getDatedebut(),
                    reservation.getDatefin()
            });
        }
        table.setModel(model);
    }

    private JPanel createClientsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton loadButton = new JButton("Load Clients");
        panel.add(loadButton, BorderLayout.SOUTH);

        loadButton.addActionListener(e -> loadClientsData(table));

        return panel;
    }


    private void loadClientsData(JTable table) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Name", "Email","username"}, 0);
        List<Client> clients = clientService.findAll();
        for (Client client : clients) {
            model.addRow(new Object[]{
                    client.getNom() + " " + client.getPrenom(),
                    client.getEmail(),
                    client.getTelephone()
            });
        }
        table.setModel(model);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            AdminDashboardFrame frame = new AdminDashboardFrame();
            frame.setVisible(true);
        });
    }
}
