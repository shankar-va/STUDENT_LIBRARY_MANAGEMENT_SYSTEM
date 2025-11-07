package src.gui;

import src.model.User;
import javax.swing.*;
import java.awt.*;

public class StaffDashboard extends JFrame {

    public StaffDashboard(User user) {
        setTitle("Staff Dashboard - Smart Library");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Welcome, " + user.getUsername() + " (Staff)", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        add(heading, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();

        // ✅ Staff can manage students too
        tabs.add("Manage Students", new StudentTablePanel(true)); // editable true for staff

        add(tabs, BorderLayout.CENTER);
        setVisible(true);
    }
}
