/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fxislit;

/**
 *
 * @author hana imani
 */
import java.util.ArrayList;
import java.util.Objects;

public class Student extends Person { 
    private String studID; 
    private ArrayList<Course> registeredCourses; 

    public Student(String studID, String firstName, String lastName, String phoneNo) 
            throws IllegalArgumentException { 
        super(firstName, lastName, phoneNo);
        if (studID == null || studID.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        this.studID = studID.trim(); 
        this.registeredCourses = new ArrayList<>(); 
    } 

    public String getStudID() { return studID; }

    public void registerCourse(Course course) throws IllegalStateException { 
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        
        try {
            for (Course registeredCourse : registeredCourses) {
                if (registeredCourse.getCourseCode().equals(course.getCourseCode()) &&
                    Objects.equals(registeredCourse.getSection(), course.getSection())) {
                    throw new IllegalStateException("Already registered for course " + 
                        course.getCourseCode() + 
                        (course.getSection() != null ? " section " + course.getSection() : ""));
                }
            }
            
            registeredCourses.add(course); 
            String sectionInfo = (course.getSection() != null) ? " section " + course.getSection() : "";
            System.out.println(course.getCourseCode() + sectionInfo + " registered successfully."); 
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to register course: " + e.getMessage());
        }
    } 

    public void dropCourse(String courseCode) throws IllegalArgumentException { 
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be null or empty");
        }
        
        try {
            boolean removed = registeredCourses.removeIf(course -> 
                course.getCourseCode().equals(courseCode.trim()));
            
            if (removed) {
                System.out.println(courseCode + " dropped successfully."); 
            } else {
                throw new IllegalStateException("Course " + courseCode + " not found in registered courses");
            }
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to drop course: " + e.getMessage());
        }
    }
    
    public void dropCourse(String courseCode, String section) throws IllegalArgumentException { 
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be null or empty");
        }
        
        try {
            boolean removed = registeredCourses.removeIf(course -> 
                course.getCourseCode().equals(courseCode.trim()) &&
                Objects.equals(course.getSection(), section));
            
            if (removed) {
                String sectionInfo = (section != null) ? " section " + section : "";
                System.out.println(courseCode + sectionInfo + " dropped successfully."); 
            } else {
                throw new IllegalStateException("Course " + courseCode + 
                    (section != null ? " section " + section : "") + " not found in registered courses");
            }
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to drop course: " + e.getMessage());
        }
    }

    @Override 
    public void viewCourse() throws RuntimeException {
        try {
            System.out.println("Courses for student " + studID + ":"); 
            if (registeredCourses.isEmpty()) {
                System.out.println("  No courses registered");
            } else {
                for (Course course : registeredCourses) {
                    String sectionInfo = (course.getSection() != null) ? " [Section: " + course.getSection() + "]" : "";
                    System.out.println("  - " + course.getCourseCode() + ": " + course.getCourseName() + sectionInfo);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to view courses: " + e.getMessage());
        }
    } 

    @Override 
    public void displayInfo() throws RuntimeException {
        try {
            System.out.println("Student: " + firstName + " " + lastName + " (" + studID + ")"); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to display student info: " + e.getMessage());
        }
    } 

    public ArrayList<Course> getRegisteredCourses() { 
        return new ArrayList<>(registeredCourses);
    }
}
