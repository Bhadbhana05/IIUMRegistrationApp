package com.mycompany.iiumregistrationapp;

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
    
    public String toCSV() {
    StringBuilder sb = new StringBuilder();
    sb.append(studID).append(",").append(firstName).append(",").append(lastName).append(",").append(phoneNo);
    for (Course c : registeredCourses) {
        sb.append(";").append(c.getCourseCode())
          .append("|").append(c.getCourseName())
          .append("|").append(c.getCreditHours())
          .append("|").append(c.getSection() == null ? "" : c.getSection());
    }
    return sb.toString();
}
public static Student fromCSV(String line) {
    try {
        String[] parts = line.split(",", 4);
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid student data: " + line);
        }

        Student student = new Student(parts[0], parts[1], parts[2], parts[3]);

        // If course data exists, it's after the 4th comma
        int courseDataIndex = line.indexOf(",", line.indexOf(",", line.indexOf(",", line.indexOf(",") + 1) + 1) + 1);
        if (courseDataIndex != -1 && courseDataIndex + 1 < line.length()) {
            String courseData = line.substring(courseDataIndex + 1);
            String[] courseTokens = courseData.split(";");
            for (String token : courseTokens) {
                if (!token.isEmpty()) {
                    String[] courseParts = token.split("\\|", -1);
                    if (courseParts.length >= 3) {
                        String courseCode = courseParts[0];
                        String courseName = courseParts[1];
                        int creditHours = Integer.parseInt(courseParts[2]);
                        String section = (courseParts.length > 3) ? courseParts[3] : null;

                        Course course = new Course(courseCode, courseName, creditHours, section);
                        student.registerCourse(course);
                    }
                }
            }
        }

        return student;
    } catch (Exception e) {
        throw new IllegalArgumentException("Error parsing student CSV: " + e.getMessage());
    }
}

}

