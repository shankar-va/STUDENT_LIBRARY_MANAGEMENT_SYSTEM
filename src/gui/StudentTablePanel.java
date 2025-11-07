package src.gui;

import src.Student_util.FileUtil;
import src.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentTablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private List<Student> students;
    private boolean editable;

    public StudentTablePanel(boolean editable) {
        this.editable = editable;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"ID", "Name", "Age", "Grade"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Buttons only if editable ---
        if (editable) {
            JPanel btnPanel = new JPanel();
            JButton addBtn = new JButton("Add");
            JButton editBtn = new JButton("Edit");
            JButton deleteBtn = new JButton("Delete");

            btnPanel.add(addBtn);
            btnPanel.add(editBtn);
            btnPanel.add(deleteBtn);
            add(btnPanel, BorderLayout.SOUTH);

            // Button actions
            addBtn.addActionListener(e -> addStudent());
            editBtn.addActionListener(e -> editStudent());
            deleteBtn.addActionListener(e -> deleteStudent());
        }
    }

    /** 🔄 Refreshes table data from file */
    private void refreshTable() {
        model.setRowCount(0);
        students = FileUtil.getStudents();
        for (Student s : students) {
            model.addRow(new Object[]{s.getId(), s.getName(), s.getAge(), s.getGrade()});
        }
    }

    /**  Add new student */
    private void addStudent() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField gradeField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Grade:"));
        panel.add(gradeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Student",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String idText = idField.getText().trim();
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String grade = gradeField.getText().trim();

            if (idText.isEmpty() || name.isEmpty() || ageText.isEmpty() || grade.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Please fill all fields!");
                return;
            }

            try {
                int id = Integer.parseInt(idText);
                int age = Integer.parseInt(ageText);
                Student newStudent = new Student(id, name, age, grade);
                FileUtil.addStudent(newStudent);
                refreshTable();
                JOptionPane.showMessageDialog(this, "✅ Student added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Age must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /** ✏️ Edit selected student */
    private void editStudent() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to edit!");
            return;
        }

        Student s = students.get(selected);
        JTextField nameField = new JTextField(s.getName());
        JTextField ageField = new JTextField(String.valueOf(s.getAge()));
        JTextField gradeField = new JTextField(s.getGrade());

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Grade:"));
        panel.add(gradeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Student", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                s.setName(nameField.getText());
                s.setAge(Integer.parseInt(ageField.getText()));
                s.setGrade(gradeField.getText());
                FileUtil.updateStudent(s);
                refreshTable();
                JOptionPane.showMessageDialog(this, "✅ Student updated successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Invalid age entered!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**  Delete selected student */
    private void deleteStudent() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to delete!");
            return;
        }

        Student s = students.get(selected);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete " + s.getName() + "?", "Confirm", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            FileUtil.deleteStudentById(s.getId());
            refreshTable();
            JOptionPane.showMessageDialog(this, "✅ Student deleted successfully!");
        }
    }
}
