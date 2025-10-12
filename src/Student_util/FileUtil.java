package src.Student_util;

import java.io.*;

public class FileUtil extends src.service.StudentService {
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
    }
}
