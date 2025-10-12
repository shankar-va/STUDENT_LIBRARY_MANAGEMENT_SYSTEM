package src;

import src.model.Student;
import src.service.StudentService;
import src.Student_util.FileUtil;


import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String path = "C:\\Users\\shank\\IdeaProjects\\SMART_LIBRARY_MANAGEMENT_SYSTEM\\Student_Data\\students.csv";

        FileUtil.initializeDataFile(path);
        FileUtil.loadFromFile(path);
        System.out.println("\nExisting students in file");
        FileUtil.listStudent();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of student" +
                " information to be filled ");
//        int n;
//        while (true) {
//            try {
//                n = sc.nextInt();
//                sc.nextLine();
//
//                System.out.println("Enter each student's details" +
//                        "in the following format\nid->name->age->grade");
//                break;
//            } catch (Exception e) {
//                System.out.println("Please enter Integer Input");
//                sc.nextLine();
//            }
//        }
//
//
//        for (int a = 0; a < n; a++) {
//            System.out.printf("Enter student %d details ", a + 1);
//            int id = sc.nextInt();
//            sc.nextLine();
//            String name = sc.nextLine();
//             int Age;
//            while (true) {
//                try {
//                    Age = sc.nextInt();
//                    sc.nextLine();
//
//                    break;
//                } catch (Exception e) {
//                    System.out.println("Please enter Integer Input");
//                    sc.nextLine();
//                }
//            }
//            //sc.nextLine();
//            String grade = sc.nextLine();
//            Student s = new Student(id, name, Age, grade);
//            FileUtil.addStudent(s);
//        }


        while (true) {
            System.out.println("==============================");
            System.out.println("SMART LIBRARY MANAGEMENT SYSTEM");
            System.out.println("==============================\n");
            System.out.println("1.Add Student\n2.list Student\n" +
                    "3.Search Student\n4.update Student details\n5.Delete Student");
            System.out.println("6.Exit");
            System.out.println("Enter your choice:");
            int choice;
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter Integer Input");
                    sc.nextLine();
                }
            }
            switch (choice) {
                case 1:
                    System.out.println("Enter student  details \nid->name->age->grade");
                    int id;
                    while (true) {
                        try {
                            id = sc.nextInt();
                            sc.nextLine();
                            if(StudentService.findStudentById(id)==null) break;
                            else {
                                System.out.println("Student id has already been taken");
                            }
                        } catch (Exception e) {
                            System.out.println("Please enter Integer Input");
                            sc.nextLine();
                        }
                    }
                    sc.nextLine();
                    String name = sc.nextLine();
                    int Age;
                    while (true) {
                            try {
                                Age = sc.nextInt();
                                if (Age >= 18 && Age <= 25) break;
                                else System.out.println("Enter valid age");
                            } catch (Exception e) {
                                System.out.println("Please enter Integer Input");
                            }
                    }
                    sc.nextLine();
                    String grade = sc.nextLine();
                    Student s = new Student(id, name, Age, grade);
                    FileUtil.addStudent(s);
                    FileUtil.saveToFile(path);
                    break;
                case 2:

                    FileUtil.listStudent();
                    break;
                case 3:
                    System.out.println("Enter student id ");
                    int id_no = sc.nextInt();
                    Student a = FileUtil.findStudentById(id_no);
                    if (a != null) System.out.println(a.toString(id_no));
                    else System.out.println("Student not found ");
                    break;
                case 4:
                    System.out.println("Enter Student id to update :");
                    int search_id = sc.nextInt();
                    FileUtil.updateStudent_info(search_id);
                    FileUtil.saveToFile(path);
                    break;
                case 5:
                    System.out.println("Enter Student id to delete");
                    int delete_id = sc.nextInt();
                    FileUtil.deleteStudent(delete_id);
                    FileUtil.saveToFile(path);
                    break;
                case 6:
                    FileUtil.saveToFile(path);
                    System.exit(0);
            }
        }

    }
}