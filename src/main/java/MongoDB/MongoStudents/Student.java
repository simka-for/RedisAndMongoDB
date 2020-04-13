package MongoDB.MongoStudents;


import java.util.List;


public class Student {

    private String name;
    private int age;
    private List<String> courseList;

    public Student(String name, int age, List<String> courseList){
        this.name = name;
        this.age = age;
        this.courseList = courseList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<String> courseList) {
        this.courseList = courseList;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", courseList=" + courseList +
                '}';
    }
}
