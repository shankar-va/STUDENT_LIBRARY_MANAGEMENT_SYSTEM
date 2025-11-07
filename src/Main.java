//package src;
//
//import src.model.Student;
//import src.service.StudentService;
//import src.Student_util.FileUtil;
//
//
//import java.util.Scanner;
////TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
//// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//public class Main {
//    public static void main(String[] args) {
//        String path = "Student_Data\\Students.csv";
//
//        FileUtil.initializeDataFile(path);
//        FileUtil.loadFromFile(path);
//        System.out.println("\nExisting students in file");
//        FileUtil.listStudent();
//        Scanner sc = new Scanner(System.in);
//
//        StudentService.orderById();
//        while (true) {
//            System.out.println("==============================");
//            System.out.println("SMART LIBRARY MANAGEMENT SYSTEM");
//            System.out.println("==============================\n");
//            System.out.println("1.Add Student\n2.list Student\n" +
//                    "3.Search Student\n4.update Student details\n5.Delete Student");
//            System.out.println("6.Exit");
//            System.out.println("Enter your choice:");
//            int choice;
//            while (true) {
//                try {
//                    choice = sc.nextInt();
//                    sc.nextLine();
//                    break;
//                } catch (Exception e) {
//                    System.out.println("Please enter Integer Input");
//                    sc.nextLine();
//                }
//            }
//            switch (choice) {
//                case 1:
//                    System.out.println("Enter student  details \nid->name->age->grade");
//                    int id;
//                    while (true) {
//                        try {
//                            id = sc.nextInt();
//                            sc.nextLine();
//                            if(StudentService.findStudentById(id)==null) break;
//                            else {
//                                System.out.println("Student id has already been taken");
//                            }
//                        } catch (Exception e) {
//                            System.out.println(" id Please enter Integer Input");
//                            sc.nextLine();
//                        }
//                    }
//                    sc.nextLine();
//                    String name = sc.nextLine();
//                    int Age;
//                    while (true) {
//                            try {
//                                Age = sc.nextInt();
//                                if (Age >= 18 && Age <= 25){
//                                    sc.nextLine();
//                                    break;
//                                }
//                                else System.out.println("Enter valid age");
//                            } catch (Exception e) {
//                                System.out.println("Please enter Integer Input");
//                                sc.nextLine();
//                            }
//                    }
//
//                    String grade = sc.nextLine();
//                    Student s = new Student(id, name, Age, grade);
//                    FileUtil.addStudent(s);
//                    FileUtil.orderById();
//                    FileUtil.saveToFile(path);
//                    break;
//                case 2:
//                    System.out.println("Enter which you want to display student details\nid\tname\tage\tgrade\n");
//                    String order=sc.nextLine();
//
//                    switch(order) {
//                        case "id":
//                            FileUtil.orderById();
//                            break;
//                        case "name":
//                            FileUtil.orderByname();
//                            break;
//                        case "age":
//                            FileUtil.orderByage();
//                            break;
//                        case "Grade":
//                            FileUtil.orderByGrade();
//                            break;
//                    }
//                    FileUtil.listStudent();
//                    FileUtil.orderById();
//                    break;
//                case 3:
//                    System.out.println("Enter student id ");
//
//                    int id_no = sc.nextInt();
//                    Student a = FileUtil.findStudentById(id_no);
//                    if (a != null) System.out.println(a.toString(id_no));
//                    else System.out.println("Student not found ");
//                    break;
//                case 4:
//                    System.out.println("Enter Student id to update :");
//                    int search_id = sc.nextInt();
//                    FileUtil.updateStudent_info(search_id);
//                    FileUtil.saveToFile(path);
//                    break;
//                case 5:
//                    System.out.println("Enter Student id to delete");
//                    int delete_id = sc.nextInt();
//                    FileUtil.deleteStudent(delete_id);
//                    FileUtil.saveToFile(path);
//                    break;
//                case 6:
//                    FileUtil.saveToFile(path);
//                    System.exit(0);
//            }
//        }
//
//    }
//}
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
