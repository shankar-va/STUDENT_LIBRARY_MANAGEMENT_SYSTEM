package service;
import model.Student;

import java.io.*;
import java.util.*;
public class StudentService{

    static ArrayList<Student> Student_info=new ArrayList<>();
    //add student to database
    public static void  addStudent(Student s){
        Student_info.add(s);

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
                bw.close();
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


                String [] parts=line.split(",");
                if(parts.length!=4)continue;
                int id=Integer.parseInt(parts[0]);
                String name=parts[1].trim();
                int age=Integer.parseInt(parts[2]);
                String grade=parts[3].trim();

                Student student=new Student(id,name,age,grade);
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
            for(Student s:Student_info){
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
    }

}
