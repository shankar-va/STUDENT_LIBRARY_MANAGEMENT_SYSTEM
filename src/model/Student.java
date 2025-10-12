package model;

public class Student {
    private int id;
    private String name;
    private int age;
    private String grade;

    public Student(int id, String name, int age, String grade){
        this.id=id;
        this.name=name;
        this.age=age;
        this.grade=grade;
    }
    public void setId(int id){
        this.id=id;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setAge(int age){
        this.age=age;
    }
    public void setGrade(String grade){
        this.grade=grade;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public String getGrade(){
        return grade;
    }
    public void disp_st_details(){
        System.out.println("Student");
        System.out.println("\t id :"+id);
        System.out.println("\t Name :"+name);
        System.out.println("\t Age :"+age);
        System.out.println("\t grade :"+grade);
    }

    public String toString(){
        return "Student"+
                "\nid="+id+
                "\nname="+name+
                "\nAge="+age+
                "\nGrade="+grade;
    }
    public String toString(int id){
        return "Student"+
                "\nid="+id+
                "\nname="+name+
                "\nAge="+age+
                "\nGrade="+grade;
    }

}

