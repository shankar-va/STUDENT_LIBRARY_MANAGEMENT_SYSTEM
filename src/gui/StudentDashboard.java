package src.gui;

import src.Student_util.FileUtil;
import src.model.Student;
import src.model.User;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {
    private Student student;

    public StudentDashboard(User user, Student student) {
        this.student = student;

        setTitle("Student Dashboard - " + user.getUsername());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        panel.add(new JLabel("Student ID:"));
        panel.add(new JLabel(String.valueOf(student.getId())));

        panel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(student.getName());
        panel.add(nameField);

        panel.add(new JLabel("Age:"));
        JTextField ageField = new JTextField(String.valueOf(student.getAge()));
        panel.add(ageField);

        panel.add(new JLabel("Grade:"));
        JTextField gradeField = new JTextField(student.getGrade());
        panel.add(gradeField);

        JButton updateBtn = new JButton("Update Info");
        updateBtn.addActionListener(e -> {
            student.setName(nameField.getText());
            student.setAge(Integer.parseInt(ageField.getText()));
            student.setGrade(gradeField.getText());
            FileUtil.updateStudent(student);
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
        });

        panel.add(new JLabel()); // spacer
        panel.add(updateBtn);

        add(panel);
        setVisible(true);
    }
}
