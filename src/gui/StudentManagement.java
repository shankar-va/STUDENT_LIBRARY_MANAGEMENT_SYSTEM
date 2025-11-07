package src.gui;

import src.Student_util.FileUtil;
import src.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentManagement extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private List<Student> students;

    public StudentManagement() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table setup
        String[] columns = {"ID", "Name", "Age", "Grade"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel();
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Edit button action
        editBtn.addActionListener(e -> editStudent());
        // Delete button action
        deleteBtn.addActionListener(e -> deleteStudent());
    }

    private void refreshTable() {
        model.setRowCount(0);
        students = FileUtil.getStudents();
        for (Student s : students) {
            model.addRow(new Object[]{s.getId(), s.getName(), s.getAge(), s.getGrade()});
        }
    }

    private void editStudent() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student first!");
            return;
        }

        Student s = students.get(selected);

        JTextField nameField = new JTextField(s.getName());
        JTextField ageField = new JTextField(String.valueOf(s.getAge()));
        JTextField gradeField = new JTextField(s.getGrade());

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Grade:"));
        panel.add(gradeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Student",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            s.setName(nameField.getText());
            s.setAge(Integer.parseInt(ageField.getText()));
            s.setGrade(gradeField.getText());
            FileUtil.updateStudent(s);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Student updated successfully!");
        }
    }

    private void deleteStudent() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete!");
            return;
        }

        Student s = students.get(selected);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete " + s.getName() + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            FileUtil.deleteStudentById(s.getId());
            refreshTable();
            JOptionPane.showMessageDialog(this, "Student deleted successfully!");
        }
    }
}
