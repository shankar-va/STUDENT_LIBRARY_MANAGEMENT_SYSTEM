package service;
import model.Student;

import java.io.File;
import java.util.*;
public class StudentService{

    static ArrayList<Student> Student_info=new ArrayList<>();
    //add student to database
    public static void  addStudent(Student s){
        Student_info.add(s);
    }
    //print the student information
    public static void listStudent(){
        for(Student a:Student_info){
            //print in string format
            //System.out.println("Printing details using toString() method\n"+ a.toString());
            // print using out display method
            a.disp_st_details();
        }
    }
    // printing student according to the student id
    public static String printStudentById(int id){
        for(Student a:Student_info){
            if(a.getId()==id)return a.toString(id);
        }return "null";
    }
    //Method to find the student
    public static Student findStudentById(int id){
        for(Student a:Student_info){
            if(a.getId()==id)return a;
        }return null;
    }
    // Method to update the student
    public static void updateStudent_info(int id){
        for(Student a:Student_info){
            if(a.getId()==id){
                Scanner sc=new Scanner(System.in);
                System.out.println("Student found");
                    while(true) {
                        System.out.println("\nEnter new details of Student\n" +
                        "1.id\t2.name\t3.age\t4.grade\t5.exit" );
                        int choice = sc.nextInt();
                        sc.nextLine();
                        switch (choice) {
                            case 1:
                                System.out.println("Enter Student's new id");
                                int new_id = sc.nextInt();
                                sc.nextLine();
                                a.setId(new_id);
                                break;
                            case 2:
                                System.out.println("Enter Student's new name");
                                String new_name = sc.nextLine();
                                a.setName(new_name);
                                break;
                            case 3:
                                System.out.println("Enter Student's new age");
                                int new_age = sc.nextInt();
                                sc.nextLine();
                                a.setAge(new_age);
                                break;
                            case 4:
                                System.out.println("Enter Student's new grade");
                                String new_grade = sc.nextLine();
                                a.setGrade(new_grade);
                                break;
                            case 5:
                                return;
                        }
                    }
            }
        } System.out.println("Student Not found");
    }
    public static void deleteStudent(int id){
        for(Student s:Student_info){
            if(s.getId()==id){
                Student_info.remove(s);
                return;
            }

        }
    }
    public static void initializeDataFile(){
        File fr=new File()
    }

}
