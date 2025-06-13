package com.mycompany.iiumregistrationapp;

import java.util.Objects;

public class Course implements Manageable { 
    private String courseCode; 
    private String courseName; 
    private int courseChr; 
    private String section;

    public Course(String courseCode, String courseName, int courseChr) throws IllegalArgumentException { 
        this(courseCode, courseName, courseChr, null);
    }
    
    public Course(String courseCode, String courseName, int courseChr, String section) throws IllegalArgumentException {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be null or empty");
        }
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty");
        }
        if (courseChr <= 0) {
            throw new IllegalArgumentException("Credit hours must be positive");
        }
        
        this.courseCode = courseCode.trim(); 
        this.courseName = courseName.trim(); 
        this.courseChr = courseChr;
        this.section = (section != null) ? section.trim() : null;
    } 

    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public int getCreditHours() { return courseChr; }
    public String getSection() { return section; }
    
    public void setSection(String section) throws IllegalArgumentException {
        if (section != null && section.trim().isEmpty()) {
            throw new IllegalArgumentException("Section cannot be empty string");
        }
        this.section = (section != null) ? section.trim() : null;
    }

    public void addCourse() throws RuntimeException { 
        try {
            System.out.println("Course " + courseCode + " added."); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to add course: " + e.getMessage());
        }
    } 

    public void deleteCourse() throws RuntimeException { 
        try {
            System.out.println("Course " + courseCode + " deleted."); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete course: " + e.getMessage());
        }
    } 

    public void modifyCourse(String newName, int newChr) throws IllegalArgumentException { 
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("New course name cannot be null or empty");
        }
        if (newChr <= 0) {
            throw new IllegalArgumentException("New credit hours must be positive");
        }
        
        this.courseName = newName.trim(); 
        this.courseChr = newChr; 
        System.out.println("Course modified."); 
    } 

    @Override 
    public void displayInfo() throws RuntimeException {
        try {
            String sectionInfo = (section != null) ? " [Section: " + section + "]" : "";
            System.out.println(courseCode + " - " + courseName + " (" + courseChr + " CHR)" + sectionInfo); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to display course info: " + e.getMessage());
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return courseCode.equals(course.courseCode) && 
               Objects.equals(section, course.section);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(courseCode, section);
    }
    
    // Convert a Course object to a CSV line
public String toCSV() {
    return courseCode + "," + courseName + "," + courseChr + "," + (section != null ? section : "");
}

// Create a Course object from a CSV line
public static Course fromCSV(String line) {
    String[] parts = line.split(",", -1); // -1 keeps empty strings
    if (parts.length >= 3) {
        String code = parts[0];
        String name = parts[1];
        int chr = Integer.parseInt(parts[2]);
        String section = (parts.length > 3 && !parts[3].isEmpty()) ? parts[3] : null;
        return new Course(code, name, chr, section);
    }
    return null;
}

}

