package src.Student_util;
import src.model.Student;
import src.model.Role;
import src.model.User;
import java.io.*;
import java.util.*;


import java.io.*;

public class FileUtil extends src.service.StudentService {

    public static List<User> userList = new ArrayList<>();
    public static void initializeUserFile(String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        try {
            // Create folder if missing
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            // Create file if missing and write headers + default users
            if (!file.exists()) {
                file.createNewFile();
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    bw.write("username,password,role,linkedStudentId");
                    bw.newLine();

                    // default admin and staff users
                    bw.write("admin,admin123,ADMIN,");
                    bw.newLine();
                    bw.write("staff,staff123,STAFF,");
                    bw.newLine();
                    bw.write("student,student123,STUDENT,1"); // linked to student ID 1
                    bw.newLine();
                }
                System.out.println("✅ Users file created with default users.");
            } else {
                System.out.println("✅ Users file already exists.");
            }
        } catch (IOException e) {
            System.out.println("❌ Error initializing user file: " + e.getMessage());
        }
    }
    public static List<User> loadUsers(String filePath) {
        userList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;
                String username = parts[0].trim();
                String password = parts[1].trim();
                Role role = Role.valueOf(parts[2].trim());
                Integer linkedId = parts.length > 3 && !parts[3].isEmpty() ?
                        Integer.parseInt(parts[3]) : null;
                userList.add(new User(username, password, role, linkedId));
            }
        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return userList;
    }

    public static void initializeDataFile(String path){
        File dataDir=new File(path);
        File parentDir=dataDir.getParentFile();
        if(!parentDir.exists()){
            parentDir.mkdirs();
        }


        if(!dataDir.exists()){
            try {

                BufferedWriter bw = new BufferedWriter(new FileWriter(dataDir));
                bw.write("id,name,age,grade\n");
                bw.close();
                System.out.println("File created Successfully at: "+dataDir.getAbsolutePath());

            }catch(Exception e){
                System.out.println("Error creating file:"+e.getMessage());
            }
        }
        else System.out.println("File already exists");

    }
    public static void loadFromFile(String path){
        File file=new File(path);
        if(!file.exists()) {
            System.out.println("File Not found");
            return;
        }
        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String line;
            Student_info.clear();

            while((line= br.readLine())!=null){
                if(line.trim().isEmpty())continue;
                if(line.trim().startsWith("id")){
                    System.out.println(line);
                    continue;
                }


                String [] parts=line.split(",");
                if(parts.length!=4)continue;
                int id=Integer.parseInt(parts[0]);
                String name=parts[1].trim();
                int age=Integer.parseInt(parts[2]);
                String grade=parts[3].trim();

                src.model.Student student=new src.model.Student(id,name,age,grade);
                Student_info.add(student);

            }
            System.out.println("Loaded "+ Student_info.size()+" students");
        }catch (Exception e){
            System.out.println("Error:"+e.getMessage());
        }
    }
    public static void saveToFile(String path){
        File file=new File(path);

        try(BufferedWriter bw=new BufferedWriter(new FileWriter(file))){
            bw.write("id,name,age,grade");
            bw.newLine();
            for(src.model.Student s:Student_info){
                String student;
                student=s.getId()+","+s.getName()+","+s.getAge()+","+s.getGrade();
                bw.write(student);
                bw.newLine();

            }
            bw.close();
            System.out.println("Data saved successfully");
        }catch(Exception e){
            System.out.println("Error saving file: "+e.getMessage());
        }
    }public static List<Student> getStudents() {
        // Auto-load only if empty
        if (Student_info.isEmpty()) {
            loadFromFile("Student_Data" + File.separator + "Students.csv");
        }
        return Student_info;
    }

    // -------------------- INTERACTIVE METHODS -------------------- //

    public static void addStudentInteractive(Scanner sc, String studentPath) {
        System.out.println("Enter student details (id -> name -> age -> grade):");

        int id;
        while (true) {
            try {
                System.out.print("ID: ");
                id = sc.nextInt();
                sc.nextLine();
                if (findStudentById(id) == null) break;
                else System.out.println("❌ ID already exists. Try again.");
            } catch (Exception e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }

        System.out.print("Name: ");
        String name = sc.nextLine();

        int age;
        while (true) {
            try {
                System.out.print("Age: ");
                age = sc.nextInt();
                sc.nextLine();
                if (age >= 18 && age <= 25) break;
                else System.out.println("❌ Enter valid age (18–25).");
            } catch (Exception e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }

        System.out.print("Grade: ");
        String grade = sc.nextLine();

        Student s = new Student(id, name, age, grade);
        addStudent(s);
        orderById();
        saveToFile(studentPath);
        System.out.println("✅ Student added successfully!");
    }

    public static void searchStudentInteractive(Scanner sc) {
        System.out.print("Enter Student ID to search: ");
        try {
            int id = sc.nextInt();
            sc.nextLine();
            Student s = findStudentById(id);
            if (s != null) System.out.println("\n" + s.toString());
            else System.out.println("❌ Student not found.");
        } catch (Exception e) {
            System.out.println("❌ Invalid input.");
            sc.nextLine();
        }
    }

    public static void updateStudentInteractive(Scanner sc, String studentPath) {
        System.out.print("Enter Student ID to update: ");
        try {
            int id = sc.nextInt();
            sc.nextLine();
            Student s = findStudentById(id);
            if (s == null) {
                System.out.println("❌ Student not found.");
                return;
            }

            System.out.println("Editing Student: " + s.getName());
            boolean done = false;
            while (!done) {
                System.out.println("""
                What would you like to update?
                1. Name
                2. Age
                3. Grade
                4. Exit
                """);
                int ch = sc.nextInt();
                sc.nextLine();
                switch (ch) {
                    case 1 -> {
                        System.out.print("Enter new name: ");
                        s.setName(sc.nextLine());
                    }
                    case 2 -> {
                        System.out.print("Enter new age: ");
                        int newAge = sc.nextInt();
                        sc.nextLine();
                        if (newAge >= 18 && newAge <= 25) s.setAge(newAge);
                        else System.out.println("❌ Invalid age (18–25).");
                    }
                    case 3 -> {
                        System.out.print("Enter new grade: ");
                        s.setGrade(sc.nextLine());
                    }
                    case 4 -> done = true;
                    default -> System.out.println("❌ Invalid option.");
                }
            }
            saveToFile(studentPath);
            System.out.println("✅ Student updated successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            sc.nextLine();
        }
    }

    public static void deleteStudentInteractive(Scanner sc, String studentPath) {
        System.out.print("Enter Student ID to delete: ");
        try {
            int id = sc.nextInt();
            sc.nextLine();
            Student s = findStudentById(id);
            if (s == null) {
                System.out.println("❌ Student not found.");
                return;
            }

            Student_info.remove(s);
            saveToFile(studentPath);
            System.out.println("✅ Student deleted successfully!");
        } catch (Exception e) {
            System.out.println("❌ Invalid input.");
            sc.nextLine();
        }
    }

    // ---------------- USER CREATION (used by AuthService.createUserInteractive) ---------------- //
    public static void createUserInteractive(Scanner sc, String userPath) {
        System.out.println("Enter new user details (username -> password -> role [ADMIN/STAFF/STUDENT]):");
        System.out.print("Username: ");
        String username = sc.nextLine().trim();

        for (User u : userList) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                System.out.println("❌ Username already exists.");
                return;
            }
        }

        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        Role role;
        while (true) {
            System.out.print("Role: ");
            String roleStr = sc.nextLine().trim().toUpperCase();
            try {
                role = Role.valueOf(roleStr);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Invalid role. Choose ADMIN, STAFF, or STUDENT.");
            }
        }

        Integer linkedStudentId = null;
        if (role == Role.STUDENT) {
            System.out.print("Enter linked student ID: ");
            try {
                linkedStudentId = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("❌ Invalid ID. Skipping link.");
                sc.nextLine();
            }
        }

        userList.add(new User(username, password, role, linkedStudentId));
        saveUsersToFile(userPath);
        System.out.println("✅ User created successfully!");
    }

    public static void saveUsersToFile(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write("username,password,role,linkedStudentId");
            bw.newLine();
            for (User u : userList) {
                bw.write(u.getUsername() + "," + u.getPassword() + "," + u.getRole() + "," +
                        (u.getLinkedStudentId() != null ? u.getLinkedStudentId() : ""));
                bw.newLine();
            }
            System.out.println("✅ Users saved successfully!");
        } catch (IOException e) {
            System.out.println("❌ Error saving users: " + e.getMessage());
        }
    }
    // Returns a live list of users loaded from the given path (also updates internal cache)
    public static List<User> getUsersFromFile(String filePath) {
        // loadUsers already populates userList and returns it — reuse it
        return loadUsers(filePath);
    }



    // Add a user to the internal cache and persist to filePath
    public static boolean addUserAndSave(User newUser, String filePath) {
        // ensure cache is current
        loadUsers(filePath);
        // check duplicate
        for (User u : userList) {
            if (u.getUsername().equalsIgnoreCase(newUser.getUsername())) {
                return false; // duplicate
            }
        }
        userList.add(newUser);
        saveUsersToFile(filePath);
        return true;
    }
    // ✅ Delete student by ID
    public static void deleteStudentById(int id) {
        Student_info.removeIf(s -> s.getId() == id);
        saveToFile("Student_Data\\Students.csv");
    }

    // ✅ Update student by replacing old entry
    public static void updateStudent(Student updated) {
        for (int i = 0; i < Student_info.size(); i++) {
            if (Student_info.get(i).getId() == updated.getId()) {
                Student_info.set(i, updated);
                saveToFile("Student_Data\\Students.csv");
                return;
            }
        }
    }

    // ✅ Helper to auto-generate new ID
    public static int getNextStudentId() {
        if (Student_info.isEmpty()) return 1;
        return Student_info.stream().mapToInt(Student::getId).max().getAsInt() + 1;
    }



}
