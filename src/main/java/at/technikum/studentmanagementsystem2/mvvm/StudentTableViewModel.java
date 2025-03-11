package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.helpers.UUIDv7Generator;
import at.technikum.studentmanagementsystem2.models.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentTableViewModel {
    private final ObservableList<StudentViewModel> data =
            FXCollections.observableArrayList(
                    new StudentViewModel(new Student(UUIDv7Generator.generateUUIDv7(), "First Student", 4)),
                    new StudentViewModel(new Student(UUIDv7Generator.generateUUIDv7(), "Second Student", 4))
            );

    public ObservableList<StudentViewModel> getData() {
        return data;
    }

    public void newStudent(StudentViewModel svm) {
        data.add(svm);
    }

    public void updateStudent(StudentViewModel studentViewModel) {
        for (StudentViewModel student : data) {
            if (student.getId().equals(studentViewModel.getId())) {
                student.nameProperty().set(studentViewModel.nameProperty().get());
                student.semesterProperty().set(studentViewModel.semesterProperty().get());
            }
        };
    }
}
