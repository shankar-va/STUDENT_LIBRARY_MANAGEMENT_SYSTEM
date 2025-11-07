package src.service;

import src.Student_util.FileUtil;
import src.model.Role;
import src.model.Student;
import src.model.User;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static src.Student_util.FileUtil.userList;

public class AuthService {
    private static final String USER_FILE = "Student_Data\\Users.csv";
    private static List<User> users = new ArrayList<>();

    public static void ensureDefaultAdmin() {
        String userFile = "Student_Data\\Users.csv"; // or use absolute path if needed
        FileUtil.loadUsers(userFile);

        boolean adminExists = FileUtil.userList.stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase("admin"));

        if (!adminExists) {
            FileUtil.userList.add(new User("admin", "admin123", Role.ADMIN, null));
            FileUtil.saveUsersToFile(userFile);
            System.out.println("✅ Default admin created.");
        } else {
            System.out.println("✅ Admin already exists — skipping creation.");
        }
    }




    public static void loadUsers() {

        FileUtil.loadUsers("Student_Data\\Users.csv");
    }

    public static boolean register(String username, String password, Role role) {
        loadUsers(); // Ensure latest list

        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return false; // user already exists
            }
        }

        User newUser = new User(username, password, role);
        users.add(newUser);
        saveUsers();
        return true;
    }


    public static void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE))) {
            bw.write("username,password,role,linkedStudentId");
            bw.newLine();
            for (User u : users) {
                bw.write(u.toString());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public static User login(String username, String password, String roleStr, String studentName, String studentIdText) {
        // ✅ Always load user list first
        FileUtil.loadUsers("Student_Data" + File.separator + "Users.csv");
        Role role = Role.valueOf(roleStr.toUpperCase());

        for (User u : FileUtil.userList) {
            if (u.getUsername().equalsIgnoreCase(username)
                    && u.getPassword().equals(password)
                    && u.getRole() == role) {

                // ✅ Non-student roles (Admin, Staff)
                if (role != Role.STUDENT) {
                    return u;
                }

                // ✅ Validate Student Inputs
                if (studentName == null || studentIdText == null
                        || studentName.trim().isEmpty() || studentIdText.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter both Student Name and ID!");
                    return null;
                }

                try {
                    int studentId = Integer.parseInt(studentIdText.trim());

                    // ✅ Ensure Students.csv is loaded
                    FileUtil.getStudents(); // auto-loads if not already
                    System.out.println("Loaded students count: " + FileUtil.getStudents().size());

                    // ✅ Match by ID and Name (trim & case-insensitive)
                    Student s = FileUtil.getStudents().stream()
                            .filter(stu -> stu.getId() == studentId
                                    && stu.getName().trim().equalsIgnoreCase(studentName.trim()))
                            .findFirst()
                            .orElse(null);

                    if (s == null) {
                        JOptionPane.showMessageDialog(null, "❌ No student found with given ID and Name!");
                        return null;
                    }

                    // ✅ Check Linked Student ID (only if exists)
                    if (u.getLinkedStudentId() != null && !u.getLinkedStudentId().equals(studentId)) {
                        JOptionPane.showMessageDialog(null, "❌ This user is not linked to the entered student record!");
                        return null;
                    }

                    // ✅ Link user automatically if blank in file (optional improvement)
                    if (u.getLinkedStudentId() == null) {
                        u.setLinkedStudentId(studentId);
                        FileUtil.saveUsersToFile("Student_Data" + File.separator + "Users.csv");
                        System.out.println("Linked user " + u.getUsername() + " to student ID " + studentId);
                    }

                    // ✅ Login success
                    return u;

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid Student ID format! Please enter a number.");
                    return null;
                }
            }
        }

        JOptionPane.showMessageDialog(null, "❌ Invalid credentials!");
        return null;
    }


    public static boolean registerUser(String username, String password, Role role, int studentId) {
        String userFile = "Student_Data\\Users.csv"; // same path used elsewhere

        // Build User object (use Integer for linked id or null)
        Integer linked = (role == Role.STUDENT) ? (studentId > 0 ? studentId : null) : null;
        User newUser = new User(username, password, role, linked);

        // Delegate to FileUtil (which will load current users, check duplicates, add and save)
        boolean added = src.Student_util.FileUtil.addUserAndSave(newUser, userFile);

        if (added) {
            // Optionally refresh in-memory cache in AuthService if you have one
            // If AuthService uses its own static cache, reload it:
            loadUsers(); // call existing no-arg loader that reads Users.csv into AuthService cache
        }
        return added;
    }

    public static void changePassword(String username, String newPassword) {
        if (userList == null || userList.isEmpty()) {
            System.out.println("⚠ No users loaded in memory!");
            return;
        }

        boolean found = false;
        for (User u : userList) {
            if (u.getUsername().equals(username)) {
                u.setPassword(newPassword);
                found = true;
                break;
            }
        }

        if (found) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("Student_Data\\Users.csv"))) {
                bw.write("username,password,role,linkedStudentId");
                bw.newLine();
                for (User u : userList) {
                    bw.write(u.getUsername() + "," + u.getPassword() + "," + u.getRole()
                            + "," + (u.getLinkedStudentId() != null ? u.getLinkedStudentId() : ""));
                    bw.newLine();
                }
                System.out.println("✅ Password changed and saved successfully!");
            } catch (IOException e) {
                System.out.println("❌ Error updating user file: " + e.getMessage());
            }
        } else {
            System.out.println("❌ User not found!");
        }
    }


    public static List<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
        saveUsers();
    }
}
