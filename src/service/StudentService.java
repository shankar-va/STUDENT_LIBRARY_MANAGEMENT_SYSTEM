package src.service;
import src.Student_util.FileUtil;
import src.model.Student;

import java.io.*;
import java.util.*;
public class StudentService{

    public static ArrayList<Student> Student_info=new ArrayList<>();
    //add student to database
    public static void  addStudent(Student s){
        if(StudentService.findStudentById(s.getId())==null) Student_info.add(s);
        else {
            System.out.println("Student id has already been taken");
        }

    }
    //print the student information
    public static void listStudent(){
        if(Student_info.isEmpty()){
            System.out.println("No student records found.");
            return;
        }

        System.out.println("id|name|age|grade");
        for(Student a:Student_info){

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
                                if(new_age>=18 && new_age<=25) a.setAge(new_age);
                                else System.out.println("Enter Valid age");
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
    static String path="Student_Data\\Students.csv";
    public static void  orderById(){
        Collections.sort(Student_info,Comparator.comparing(Student::getId));
        FileUtil.saveToFile(path);
    }
    public static void orderByname(){
        Collections.sort(Student_info,Comparator.comparing(Student::getName,String.CASE_INSENSITIVE_ORDER));
        FileUtil.saveToFile(path);
    }
    public static void orderByage(){
        Collections.sort(Student_info,Comparator.comparing(Student::getAge));
        FileUtil.saveToFile(path);
    }
    public static void  orderByGrade(){
        Collections.sort(Student_info,Comparator.comparing(Student::getGrade,String.CASE_INSENSITIVE_ORDER));

        FileUtil.saveToFile(path);
    }

}
