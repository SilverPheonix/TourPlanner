//module at.technikum.studentmanagementsystem2 {
//    // Requires for Spring Boot and Spring Data JPA
//    requires spring.boot;
//    requires spring.boot.autoconfigure;
//    requires spring.context;
//    requires spring.beans;
//    requires spring.data.jpa;
//    requires spring.web;
//    requires java.sql;
//    requires jakarta.persistence;
//    requires javafx.base;
//    requires javafx.controls;
//    requires javafx.fxml;
//
//    // Open packages for Spring reflection
//    opens at.technikum.studentmanagementsystem2 to spring.core, spring.beans, spring.context;
//    opens at.technikum.studentmanagementsystem2.controller to spring.core, spring.beans, spring.context;
//    opens at.technikum.studentmanagementsystem2.service to spring.core, spring.beans, spring.context;
//    opens at.technikum.studentmanagementsystem2.models to spring.core, spring.beans, org.hibernate.orm.core;
//
//    // Exported packages (optional, controls public API that other modules can use)
//    exports at.technikum.studentmanagementsystem2;
//    exports at.technikum.studentmanagementsystem2.controller;
//    exports at.technikum.studentmanagementsystem2.service;
//
//
//}

open module at.technikum.studentmanagementsystem2 {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;
    requires spring.data.jpa;
    requires spring.web;
    requires jakarta.persistence;

    requires javafx.fxml;

    requires spring.core; // Ensure spring-core is explicitly required
    // Fix for JavaFX
    requires org.apache.logging.log4j;


    requires org.hibernate.orm.core;
    requires javafx.web;
    requires jdk.jsobject;
    requires javafx.swing;
    requires kernel;
    requires layout;
    requires io;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;

}
