package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.Student;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentViewModel {
    private final StringProperty id;
    private final StringProperty name;
    private final IntegerProperty semester;

    public StudentViewModel(Student student) {
        this.id = new SimpleStringProperty(student.getId().toString());
        this.name = new SimpleStringProperty(student.getName());
        this.semester = new SimpleIntegerProperty(student.getSemester());
    }

    public StudentViewModel(StudentViewModel studentViewModel) {
        this.id = new SimpleStringProperty(studentViewModel.getId());
        this.name = new SimpleStringProperty(studentViewModel.getName());
        this.semester = new SimpleIntegerProperty(studentViewModel.getSemester());
    }

    public String getId() {
        return id.get();
    }
    public String getName() {
        return name.get();
    }
    public int getSemester() {
        return semester.get();
    }

    public StringProperty idProperty() {
        return id;
    }
    public StringProperty nameProperty() {
        return name;
    }
    public IntegerProperty semesterProperty() {
        return semester;
    }

}
