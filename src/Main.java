import model.Student;
import service.StudentService;


import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String path="C:\\Users\\shank\\IdeaProjects\\SMART_LIBRARY_MANAGEMENT_SYSTEM\\Student_Data\\students.csv";

        StudentService.initializeDataFile(path);
        StudentService.loadFromFile(path);
        System.out.println("\nExisting students in file");
        StudentService.listStudent();
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the number of student" +
                " information to be filled ");
        int n=sc.nextInt();
        System.out.println("Enter each student's details" +
                "in the following format\nid->name->age->grade");


        for(int a=0;a<n;a++){
            System.out.printf("Enter student %d details ",a+1);
            int id=sc.nextInt();
            sc.nextLine();
            String name=sc.nextLine();
            int Age=sc.nextInt();
            sc.nextLine();
            String grade=sc.nextLine();
             Student s=new Student(id,name,Age,grade);
            StudentService.addStudent(s);
        }


        while(true){
            System.out.println("1.Add Student\n2.list Student\n" +
                    "3.Search Student\n4.update Student details\n5.Delete Student\n6.exit");
            int choice=sc.nextInt();
            switch(choice){
                case 1:
                    System.out.println("Enter student  details \nid->name->age->grade");
                    int id=sc.nextInt();
                    sc.nextLine();
                    String name=sc.nextLine();
                    int Age=sc.nextInt();
                    sc.nextLine();
                    String grade=sc.nextLine();
                    Student s=new Student(id,name,Age,grade);
                    StudentService.addStudent(s);
                    StudentService.saveToFile(path);
                    break;
                case 2:

                    StudentService.listStudent();
                    break;
                case 3:
                    System.out.println("Enter student id ");
                    int id_no=sc.nextInt();
                    Student a=StudentService.findStudentById(id_no);
                    if(a!=null) System.out.println(a.toString(id_no));
                    else System.out.println("Student not found ");
                    break;
                case 4:
                    System.out.println("Enter Student id to update :");
                    int search_id=sc.nextInt();
                    StudentService.updateStudent_info(search_id);
                    StudentService.saveToFile(path);
                    break;
                case 5:
                    System.out.println("Enter Student id to delete");
                    int delete_id=sc.nextInt();
                    StudentService.deleteStudent(delete_id);
                    StudentService.saveToFile(path);
                    break;
                case 6:
                    StudentService.saveToFile(path);
                    System.exit(0);
            }
        }

    }
}