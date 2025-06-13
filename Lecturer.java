package com.mycompany.iiumregistrationapp;

import java.util.ArrayList; 
import java.util.List; 
import java.util.Objects;

public class Lecturer extends Person { 
    private String lectID; 
    private List<Course> assignedCourses; 

    public Lecturer(String lectID, String firstName, String lastName, String phoneNo) 
            throws IllegalArgumentException { 
        super(firstName, lastName, phoneNo);
        if (lectID == null || lectID.trim().isEmpty()) {
            throw new IllegalArgumentException("Lecturer ID cannot be null or empty");
        }
        this.lectID = lectID.trim(); 
        this.assignedCourses = new ArrayList<>(); 
    } 

    public String getLectID() { return lectID; }

    public void addLecturer() throws RuntimeException { 
        try {
            System.out.println("Lecturer " + lectID + " added."); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to add lecturer: " + e.getMessage());
        }
    } 

    public void assignCourse(Course course) throws IllegalStateException {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        
        try {
            for (Course assignedCourse : assignedCourses) {
                if (assignedCourse.getCourseCode().equals(course.getCourseCode()) &&
                    Objects.equals(assignedCourse.getSection(), course.getSection())) {
                    throw new IllegalStateException("Already assigned to course " + 
                        course.getCourseCode() + 
                        (course.getSection() != null ? " section " + course.getSection() : ""));
                }
            }
            
            assignedCourses.add(course);
            String sectionInfo = (course.getSection() != null) ? " section " + course.getSection() : "";
            System.out.println("Assigned to course " + course.getCourseCode() + sectionInfo);
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to assign course: " + e.getMessage());
        }
    }        

    public List<Course> getAssignedCourses() { 
        return new ArrayList<>(assignedCourses);
    } 

    public void viewAssignedCourse() throws RuntimeException {
        try {
            System.out.println("Courses assigned to Lecturer " + lectID + ":");
            if (assignedCourses.isEmpty()) {
                System.out.println("  No courses assigned");
            } else {
                for (Course c : assignedCourses) {
                    String sectionInfo = (c.getSection() != null) ? " [Section: " + c.getSection() + "]" : "";
                    System.out.println("  - " + c.getCourseCode() + ": " + c.getCourseName() + sectionInfo);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to view assigned courses: " + e.getMessage());
        }
    }

    @Override 
    public void viewCourse() throws Exception { 
        viewAssignedCourse(); 
    } 

    @Override 
    public void displayInfo() throws RuntimeException {
        try {
            System.out.println("Lecturer: " + firstName + " " + lastName + " (" + lectID + ")"); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to display lecturer info: " + e.getMessage());
        }
    }
    
    public String toCSV() {
    return lectID + "," + firstName + "," + lastName + "," + phoneNo;
}

public static Lecturer fromCSV(String line) {
    String[] parts = line.split(",", -1);
    if (parts.length >= 4) {
        String id = parts[0];
        String fname = parts[1];
        String lname = parts[2];
        String phone = parts[3];
        return new Lecturer(id, fname, lname, phone);
    }
    return null;
}

}


