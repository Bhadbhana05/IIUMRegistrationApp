package com.mycompany.iiumregistrationapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main { 
    public static void main(String[] args) { 
        try {
            Student s1 = new Student("2420919", "Ali", "Ahmad", "0123456789"); 
            
            Course c1 = new Course("BICS1304", "OOP", 3, "S01"); 
            Course c2 = new Course("BICS1303", "Computer Networking", 3, "S02"); 
            
            s1.registerCourse(c1); 
            s1.viewCourse(); 
            c1.displayInfo(); 

            Lecturer l1 = new Lecturer("10567", "Dr.", "Dini Oktarina", "0198765432"); 
            l1.assignCourse(c1); 
            l1.viewAssignedCourse(); 

            Registration r1 = new Registration("R001", "2420919", "BICS1304", "2025-05-02", "S01"); 
            r1.confirmRegistration(); 
            r1.displayInfo(); 

            Section sect = new Section("S01", "BICS1304", "Monday", "10AM", "ICT LAB 6"); 
            sect.displayInfo(); 
            
            // Try File I/O
            
            // Student
            ArrayList<Student> studList = new ArrayList<>();
            studList.add(new Student("2420910", "Alea", "Aina", "0123456789")); 
            // Load from file
            List<Student> students = FileHandler.loadStudentsFromFile("students.csv");

            // Save after changes
            FileHandler.saveStudentsToFile(students, "students.csv");
            
            // Course
            ArrayList<Course> courseList = new ArrayList<>();
            courseList.add(new Course("BICS1303", "Computer Networking", 3, "S02")); 
            // Load from file
            List<Course> courses = FileHandler.loadCoursesFromFile("courses.csv");

            // Save after changes
            FileHandler.saveCoursesToFile(courses, "courses.csv");           
            
            // Registration
            ArrayList<Registration> regList = new ArrayList<>();
            regList.add(new Registration("R001", "2420919", "BICS1304", "2025-06-13", "S01"));
            regList.add(new Registration("R002", "2420919", "BICS1305", "2025-06-13", "S02"));

            FileHandler.saveRegistrationsToFile(regList, "registrations.csv");

            List<Registration> loaded = FileHandler.loadRegistrationsFromFile("registrations.csv");
            for (Registration r : loaded) {
                r.displayInfo();
            }
            
            // Section
            ArrayList<Section> sectList = new ArrayList<>();
            sectList.add(new Section("S02", "BICS1303", "Monday", "10AM", "Cisco Lab")); 
            List<Section> sections = FileHandler.loadSectionsFromFile("sections.csv");
            
            FileHandler.saveSectionsToFile(sections, "sections.csv");

            
            /**List<Assignation> assigns = FileHandler.loadAssignationsFromFile("assignations.csv");
            for (Assignation a : loaded) {
                a.displayInfo();
            }
            
            
            FileHandler.saveAssignationsToFile(assigns, "assignations.csv");*/

            
            System.out.println("\n--- Testing Exception Handling ---");
            
            try {
                s1.registerCourse(c1);
            } catch (IllegalStateException e) {
                System.out.println("Caught expected exception: " + e.getMessage());
            }
            
            try {
                Student invalidStudent = new Student("", "John", "Doe", "123456789");
            } catch (IllegalArgumentException e) {
                System.out.println("Caught expected exception: " + e.getMessage());
            }
            
            try {
                s1.dropCourse("NONEXISTENT");
            } catch (IllegalStateException e) {
                System.out.println("Caught expected exception: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    } 
}


