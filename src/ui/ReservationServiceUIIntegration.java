package ui;

import Entities.Categorie;
import Entities.Chambre;
import Entities.Client;
import Entities.Reservation;
import Service.ChambreService;
import Service.ClientService;
import Service.ReservationService;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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
        frame = new JFrame("Hotel Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null); // Center the window

        // Use a tabbed pane for registration and login
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(245, 245, 245)); // Light gray background

        // Add the header
        JPanel headerPanel = createHeaderPanel("Hotel Reservation System");
        frame.add(headerPanel, BorderLayout.NORTH);

        // Registration Panel
        JPanel registerPanel = createRegisterPanel();
        tabbedPane.add("Register", registerPanel);

        // Login Panel
        JPanel loginPanel = createLoginPanel();
        tabbedPane.add("Login", loginPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createHeaderPanel(String title) {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                // Gradient background
                Color color1 = new Color(0, 120, 215); // Blue
                Color color2 = new Color(0, 80, 160);  // Darker blue
                GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(900, 80)); // Set header height

        // Add the title
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Modern font
        titleLabel.setForeground(Color.WHITE); // White text
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Padding
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245)); // Light gray background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Header
        JLabel headerLabel = new JLabel("Register", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(0, 120, 215)); // Blue color
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(headerLabel, gbc);

        // Name Field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
        styleTextField(nameField);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(nameField, gbc);

        // Prenom Field
        JLabel prenomLabel = new JLabel("Prenom:");
        prenomLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(prenomLabel, gbc);

        JTextField prenomField = new JTextField(20);
        styleTextField(prenomField);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(prenomField, gbc);

        // Telephone Field
        JLabel telephoneLabel = new JLabel("Telephone:");
        telephoneLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(telephoneLabel, gbc);

        JTextField telephoneField = new JTextField(20);
        styleTextField(telephoneField);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(telephoneField, gbc);

        // Email Field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(20);
        styleTextField(emailField);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(emailField, gbc);

        // Username Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
        styleTextField(usernameField);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(usernameField, gbc);

        // Password Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        styleTextField(passwordField);
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(passwordField, gbc);

        // Register Button
        JButton registerButton = new JButton("Register");
        styleButton(registerButton);
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
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245)); // Light gray background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Header
        JLabel headerLabel = new JLabel("Login", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(0, 120, 215)); // Blue color
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(headerLabel, gbc);

        // Username Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
        styleTextField(usernameField);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(usernameField, gbc);

        // Password Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        styleTextField(passwordField);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                boolean authenticated = clientService.authenticate(username, password);
                if (authenticated) {
                    JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    showChambresPanel();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        return panel;
    }

    private void showChambresPanel() {
        JFrame chambreFrame = new JFrame("Available Rooms");
        chambreFrame.setSize(900, 600);
        chambreFrame.setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245)); // Light gray background

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
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        // Reservation Panel
        JPanel reservationPanel = new JPanel(new GridBagLayout());
        reservationPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel chambreIdLabel = new JLabel("Room ID:");
        chambreIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        reservationPanel.add(chambreIdLabel, gbc);

        JTextField chambreIdField = new JTextField(10);
        styleTextField(chambreIdField);
        gbc.gridx = 1;
        gbc.gridy = 0;
        reservationPanel.add(chambreIdField, gbc);

        JButton reserveButton = new JButton("Reserve");
        styleButton(reserveButton);
        gbc.gridx = 2;
        gbc.gridy = 0;
        reservationPanel.add(reserveButton, gbc);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientService.logout(); // Assuming you have a logout method in ClientService
                chambreFrame.dispose(); // Close the chambreFrame
                frame.setVisible(true); // Show the main frame (login/register)
            }
        });
        gbc.gridx = 3;
        gbc.gridy = 0;
        reservationPanel.add(logoutButton, gbc);

        // Generate PDF Button (In-Memory)
        // Define the generatePdfButton
        JButton generatePdfButton = new JButton("Generate PDF");
        styleButton(generatePdfButton); // Assuming styleButton is a method for styling buttons

// Add the button to the reservationPanel
        gbc.gridx = 4; // Define the grid position
        gbc.gridy = 0;
        reservationPanel.add(generatePdfButton, gbc);

// Add action listener for PDF generation
        generatePdfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int chambreId = Integer.parseInt(chambreIdField.getText());
                    Chambre chambre = chambreService.findById(chambreId);
                    if (chambre == null) {
                        JOptionPane.showMessageDialog(chambreFrame, "Room not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Create a PDF document in memory
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    Document document = new Document();
                    PdfWriter writer = PdfWriter.getInstance(document, outputStream);
                    document.open();

                    // Add a title
                    com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 16, com.itextpdf.text.Font.BOLD);
                    Paragraph title = new Paragraph("Reservation Details", titleFont);
                    title.setAlignment(Element.ALIGN_CENTER);
                    title.setSpacingAfter(20);
                    document.add(title);

                    // Create a table for reservation details
                    PdfPTable table = new PdfPTable(2); // 2 columns
                    table.setWidthPercentage(100); // Full width
                    table.setSpacingBefore(10f);
                    table.setSpacingAfter(10f);

                    // Set column widths
                    float[] columnWidths = {1f, 3f};
                    table.setWidths(columnWidths);

                    // Add table header
                    com.itextpdf.text.Font headerFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.BOLD);
                    PdfPCell cell1 = new PdfPCell(new Phrase("Field", headerFont));
                    PdfPCell cell2 = new PdfPCell(new Phrase("Details", headerFont));
                    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(cell1);
                    table.addCell(cell2);

                    // Add reservation details to the table
                    com.itextpdf.text.Font detailFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12);
                    table.addCell(new PdfPCell(new Phrase("Room ID", detailFont)));
                    table.addCell(new PdfPCell(new Phrase(String.valueOf(chambre.getId()))));

                    table.addCell(new PdfPCell(new Phrase("Room Telephone", detailFont)));
                    table.addCell(new PdfPCell(new Phrase(chambre.getTelephone())));

                    table.addCell(new PdfPCell(new Phrase("Category", detailFont)));
                    table.addCell(new PdfPCell(new Phrase(chambre.getCategorie().getLibelle())));

                    table.addCell(new PdfPCell(new Phrase("Category Code", detailFont)));
                    table.addCell(new PdfPCell(new Phrase(chambre.getCategorie().getCode())));

                    table.addCell(new PdfPCell(new Phrase("Client Name", detailFont)));
                    table.addCell(new PdfPCell(new Phrase(clientService.getLoggedInClient().getNom())));

                    table.addCell(new PdfPCell(new Phrase("Client prenom", detailFont)));
                    table.addCell(new PdfPCell(new Phrase(clientService.getLoggedInClient().getPrenom())));

                    document.add(table);

                    // Add footer
                    com.itextpdf.text.Font footerFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 10, com.itextpdf.text.Font.ITALIC, BaseColor.GRAY);
                    Paragraph footer = new Paragraph("Generated by Application Reservation", footerFont);
                    footer.setAlignment(Element.ALIGN_CENTER);
                    footer.setSpacingBefore(20);
                    document.add(footer);

                    // Close the document
                    document.close();
                    writer.close();

                    // Open the PDF in the default viewer
                    byte[] pdfBytes = outputStream.toByteArray();
                    openPdfInViewer(pdfBytes);

                    JOptionPane.showMessageDialog(chambreFrame, "PDF generated and opened!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(chambreFrame, "Error generating PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


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
                        JOptionPane.showMessageDialog(chambreFrame, "Room not found!", "Error", JOptionPane.ERROR_MESSAGE);
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
                        JOptionPane.showMessageDialog(chambreFrame, "Room is already reserved for the selected dates!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    boolean success = reservationService.create(reservation);
                    if (success) {
                        JOptionPane.showMessageDialog(chambreFrame, "Reservation successful for Room ID: " + chambreId, "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(chambreFrame, "Failed to create reservation!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(chambreFrame, "Invalid Room ID!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Method to open PDF in the default viewer
    private void openPdfInViewer(byte[] pdfBytes) {
        try {
            // Save the PDF to a temporary file
            java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("reservation", ".pdf");
            java.nio.file.Files.write(tempFile, pdfBytes);

            // Open the PDF with the default viewer
            Desktop.getDesktop().open(tempFile.toFile());
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error opening PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 120, 215)); // Blue color
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
    }

    public static void main(String[] args) {
        new ReservationServiceUIIntegration();
    }
}