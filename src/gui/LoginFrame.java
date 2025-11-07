package src.gui;

import src.Student_util.FileUtil;
import src.model.Role;
import src.model.User;
import src.service.AuthService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JTextField studentNameField;
    private JTextField studentIdField;
    private JPanel studentExtraPanel;

    public LoginFrame() {
        setTitle("Smart Library Management - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 380);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("SMART LIBRARY LOGIN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // --- Username ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // --- Password ---
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // --- Role Selector ---
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        roleBox = new JComboBox<>(new String[]{"ADMIN", "STAFF", "STUDENT"});
        panel.add(roleBox, gbc);

        // --- Student Extra Fields (hidden initially) ---
        studentExtraPanel = new JPanel(new GridBagLayout());
        GridBagConstraints sgbc = new GridBagConstraints();
        sgbc.insets = new Insets(5, 5, 5, 5);
        sgbc.fill = GridBagConstraints.HORIZONTAL;

        sgbc.gridx = 0;
        sgbc.gridy = 0;
        studentExtraPanel.add(new JLabel("Student Name:"), sgbc);

        sgbc.gridx = 1;
        studentNameField = new JTextField(15);
        studentExtraPanel.add(studentNameField, sgbc);

        sgbc.gridx = 0;
        sgbc.gridy = 1;
        studentExtraPanel.add(new JLabel("Student ID:"), sgbc);

        sgbc.gridx = 1;
        studentIdField = new JTextField(15);
        studentExtraPanel.add(studentIdField, sgbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(studentExtraPanel, gbc);
        studentExtraPanel.setVisible(false); // hidden initially

        // --- Login Button ---
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        // --- Register Button ---
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> new RegisterFrame());

        gbc.gridy = 5;
        panel.add(registerButton, gbc);

        // --- Role Selection Change Listener ---
        roleBox.addActionListener(e -> {
            boolean isStudent = roleBox.getSelectedItem().toString().equalsIgnoreCase("STUDENT");
            studentExtraPanel.setVisible(isStudent);
            pack(); // resize window automatically
        });

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String roleStr = roleBox.getSelectedItem().toString();
        Role role = Role.valueOf(roleStr.toUpperCase());

        String studentName = null;
        String studentId = null;

        if (role == Role.STUDENT) {
            studentName = studentNameField.getText().trim();
            studentId = studentIdField.getText().trim();
        }

        User u = AuthService.login(username, password, roleStr, studentName, studentId);
        if (u == null) {
            JOptionPane.showMessageDialog(this, "Invalid credentials or student details", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Welcome " + u.getUsername() + " (" + u.getRole() + ")");
        dispose();

        switch (u.getRole()) {
            case ADMIN -> new AdminDashboard(u);
            case STAFF -> new StaffDashboard(u);
            case STUDENT -> {
                if (u.getLinkedStudentId() != null) {
                    src.model.Student linkedStudent = FileUtil.getStudents().stream()
                            .filter(s -> s.getId() == u.getLinkedStudentId())
                            .findFirst()
                            .orElse(null);

                    if (linkedStudent != null) {
                        new StudentDashboard(u, linkedStudent);
                    } else {
                        JOptionPane.showMessageDialog(this, "⚠️ No student record found for this user.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "⚠️ No linked student ID found for this user.");
                }
            }
        }
    }
}
