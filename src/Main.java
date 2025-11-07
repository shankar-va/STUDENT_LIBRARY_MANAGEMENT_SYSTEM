
package src;

import src.model.Role;
import src.model.Student;
import src.model.User;
import src.service.AuthService;
import src.service.StudentService;
import src.Student_util.FileUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String studentPath = "Student_Data\\Students.csv";
        String userPath = "Student_Data\\Users.csv";

        // Initialize data files
        FileUtil.initializeDataFile(studentPath);
        FileUtil.initializeUserFile(userPath); // new helper
        FileUtil.loadFromFile(studentPath);
        AuthService.loadUsers();

        Scanner sc = new Scanner(System.in);
        System.out.println("==============================");
        System.out.println(" STUDENT LIBRARY MANAGEMENT SYSTEM ");
        System.out.println("==============================\n");

        // -------- LOGIN PHASE --------
        User loggedInUser = null;
        while (loggedInUser == null) {
            System.out.print("Username: ");
            String username = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();

            loggedInUser = AuthService.login(username, password,"STUDENT","","");
            if (loggedInUser == null) {
                System.out.println("❌ Invalid credentials. Try again.\n");
            }
        }

        System.out.println("\n✅ Login successful! Welcome, " + loggedInUser.getUsername() + " (" + loggedInUser.getRole() + ")");
        StudentService.orderById();

        // -------- ROLE-BASED MENU --------
        boolean running = true;
        while (running) {
            System.out.println("\n==============================");
            System.out.println("MENU - " + loggedInUser.getRole());
            System.out.println("==============================");

            switch (loggedInUser.getRole()) {
                case ADMIN:
                    System.out.println("1. Add Student");
                    System.out.println("2. List Students");
                    System.out.println("3. Search Student");
                    System.out.println("4. Update Student");
                    System.out.println("5. Delete Student");
                    System.out.println("6. Create User (Staff/Student)");
                    System.out.println("7. Logout");
                    break;

                case STAFF:
                    System.out.println("1. Add Student");
                    System.out.println("2. List Students");
                    System.out.println("3. Search Student");
                    System.out.println("4. Update Student");
                    System.out.println("5. Delete Student");
                    System.out.println("6. Logout");
                    break;

                case STUDENT:
                    System.out.println("1. View My Details");
                    System.out.println("2. Change My Password");
                    System.out.println("3. Logout");
                    break;
            }

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (loggedInUser.getRole()) {
                case ADMIN:
                    running = handleAdminChoice(choice, sc, studentPath, userPath);
                    break;
                case STAFF:
                    running = handleStaffChoice(choice, sc, studentPath);
                    break;
                case STUDENT:
                    running = handleStudentChoice(choice, sc, loggedInUser, userPath);
                    break;
            }
        }
        System.out.println("Logged out successfully. Goodbye!");
    }

    // ---------- ADMIN ----------
    private static boolean handleAdminChoice(int choice, Scanner sc, String studentPath, String userPath) {
        switch (choice) {
            case 1 -> FileUtil.addStudentInteractive(sc, studentPath);
            case 2 -> FileUtil.listStudent();
            case 3 -> FileUtil.searchStudentInteractive(sc);
            case 4 -> FileUtil.updateStudentInteractive(sc, studentPath);
            case 5 -> FileUtil.deleteStudentInteractive(sc, studentPath);
            case 6 -> FileUtil.createUserInteractive(sc, userPath);
            case 7 -> { return false; }
            default -> System.out.println("Invalid choice.");
        }
        return true;
    }

    // ---------- STAFF ----------
    private static boolean handleStaffChoice(int choice, Scanner sc, String studentPath) {
        switch (choice) {
            case 1 -> FileUtil.addStudentInteractive(sc, studentPath);
            case 2 -> FileUtil.listStudent();
            case 3 -> FileUtil.searchStudentInteractive(sc);
            case 4 -> FileUtil.updateStudentInteractive(sc, studentPath);
            case 5 -> FileUtil.deleteStudentInteractive(sc, studentPath);
            case 6 -> { return false; }
            default -> System.out.println("Invalid choice.");
        }
        return true;
    }

    // ---------- STUDENT ----------
    private static boolean handleStudentChoice(int choice, Scanner sc, User user, String userPath) {
        switch (choice) {
            case 1 -> {
                if (user.getLinkedStudentId() != null) {
                    Student s = FileUtil.findStudentById(user.getLinkedStudentId());
                    if (s != null) System.out.println(s);
                    else System.out.println("Student record not found.");
                } else {
                    System.out.println("No student record linked to your account.");
                }
            }
            case 2 -> {
                System.out.print("Enter new password: ");
                String newPwd = sc.nextLine();
                AuthService.changePassword(user.getUsername(), newPwd);
                AuthService.saveUsers();
                System.out.println("✅ Password changed successfully.");
            }
            case 3 -> { return false; }
            default -> System.out.println("Invalid choice.");
        }
        return true;
    }
}
