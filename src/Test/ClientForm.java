package Test;

import javax.swing.*;

public class ClientForm extends JInternalFrame {

    public ClientForm() {
        setTitle("Client Form");
        setSize(400, 300);
        setClosable(true);
        setResizable(true);
        setIconifiable(true);

        // Form Components
        JLabel lblName = new JLabel("Name:");
        JTextField txtName = new JTextField(20);

        JLabel lblPhone = new JLabel("Phone:");
        JTextField txtPhone = new JTextField(20);

        JButton btnSave = new JButton("Save");

        // Layout
        JPanel panel = new JPanel();
        panel.add(lblName);
        panel.add(txtName);
        panel.add(lblPhone);
        panel.add(txtPhone);
        panel.add(btnSave);

        add(panel);
    }
}
