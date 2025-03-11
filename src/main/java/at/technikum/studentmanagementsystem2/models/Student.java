package at.technikum.studentmanagementsystem2.models;

import java.util.UUID;

public class Student {
    private UUID id;
    private String name;
    private int semester;

    public Student(UUID id, String name, int semester) {
        this.id = id;
        this.name = name;
        this.semester = semester;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
