package src.gui;

import src.Student_util.FileUtil;
import src.model.Role;
import src.service.AuthService;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<Role> roleBox;

    public RegisterFrame() {
        setTitle("Smart Library - Register");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("REGISTER USER", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        roleBox = new JComboBox<>(Role.values());
        panel.add(roleBox, gbc);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> registerUser());
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void registerUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        Role role = (Role) roleBox.getSelectedItem();

        Integer linkedStudentId = null;

        // ✅ If registering a student, auto-create student record
        if (role == Role.STUDENT) {
            int newId = FileUtil.getNextStudentId();
            String name = JOptionPane.showInputDialog(this, "Enter student name:");
            int age = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter age:"));
            String grade = JOptionPane.showInputDialog(this, "Enter grade:");

            // Create and save the student
            src.model.Student newStudent = new src.model.Student(newId, name, age, grade);
            FileUtil.addStudent(newStudent);
            FileUtil.saveToFile("Student_Data\\Students.csv");

            linkedStudentId = newId; // link this student ID
        }

        // ✅ Create and register user
        src.model.User newUser = new src.model.User(username, password, role, linkedStudentId);
        boolean success = FileUtil.addUserAndSave(newUser, "Student_Data\\Users.csv");

        if (success) {
            JOptionPane.showMessageDialog(this, "User registered successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
