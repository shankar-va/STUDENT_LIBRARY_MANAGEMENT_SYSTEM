package src.gui;

import src.model.Role;
import src.model.User;
import src.service.AuthService;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard(User user) {
        setTitle("Admin Dashboard - Smart Library");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Welcome, " + user.getUsername() + " (Admin)", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        add(heading, BorderLayout.NORTH);

        // Tabs for navigation
        JTabbedPane tabs = new JTabbedPane();


        tabs.add("Manage Students", new StudentTablePanel(true)); // true = editable


        JPanel userPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        userPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        JComboBox<Role> roleBox = new JComboBox<>(Role.values());
        JButton addUser = new JButton("Add User");

        addUser.addActionListener(e -> {
            boolean success = AuthService.registerUser(
                    username.getText().trim(),
                    new String(password.getPassword()),
                    (Role) roleBox.getSelectedItem(),
                    0
            );
            JOptionPane.showMessageDialog(this, success ? "✅ User added successfully" : "⚠️ Username already exists");
        });

        userPanel.add(new JLabel("Username:"));
        userPanel.add(username);
        userPanel.add(new JLabel("Password:"));
        userPanel.add(password);
        userPanel.add(new JLabel("Role:"));
        userPanel.add(roleBox);
        userPanel.add(new JLabel());
        userPanel.add(addUser);

        tabs.add("Manage Users", userPanel);

        add(tabs, BorderLayout.CENTER);
        setVisible(true);
    }
}
