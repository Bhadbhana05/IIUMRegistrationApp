/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fxislit;

/**
 *
 * @author hana imani
 */
public class Assignation implements Manageable {
    private String assID;
    private String lectID;
    private String courseCode;
    private String assDate;
    private String assStatus;
    private String section; 

    public Assignation(String assID, String lectID, String courseCode, String assDate) 
            throws IllegalArgumentException {
        this(assID, lectID, courseCode, assDate, null);
    }
    
    public Assignation(String assID, String lectID, String courseCode, String assDate, String section) 
            throws IllegalArgumentException {
        if (assID == null || assID.trim().isEmpty()) {
            throw new IllegalArgumentException("Assign ID cannot be null or empty");
        }
        if (lectID == null || lectID.trim().isEmpty()) {
            throw new IllegalArgumentException("Lecturer ID cannot be null or empty");
        }
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be null or empty");
        }
        if (assDate == null || assDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Assign date cannot be null or empty");
        }
        
        this.assID = assID.trim();
        this.lectID = lectID.trim();
        this.courseCode = courseCode.trim();
        this.assDate = assDate.trim();
        this.section = (section != null) ? section.trim() : null;
        this.assStatus = "Pending";
    }

    public String getAssID() { return assID; }
    public String getLectID() { return lectID; }
    public String getCourseCode() { return courseCode; }
    public String getAssDate() { return assDate; }
    public String getAssStatus() { return assStatus; }
    public String getSection() { return section; }

    public void confirmAssignation() throws RuntimeException {
        try {
            assStatus = "Confirmed";
            String sectionInfo = (section != null) ? " section " + section : "";
            System.out.println("Assignation confirmed for " + courseCode + sectionInfo);
        } catch (Exception e) {
            throw new RuntimeException("Failed to confirm assignation: " + e.getMessage());
        }
    }

    public void cancelAssignation() throws RuntimeException {
        try {
            assStatus = "Cancelled";
            String sectionInfo = (section != null) ? " section " + section : "";
            System.out.println("Assignation cancelled for " + courseCode + sectionInfo);
        } catch (Exception e) {
            throw new RuntimeException("Failed to cancel assignation: " + e.getMessage());
        }
    }

    @Override
    public void displayInfo() throws RuntimeException {
        try {
            String sectionInfo = (section != null) ? " | Section: " + section : "";
            System.out.println("AssID: " + assID +
                               " | Lecturer: " + lectID +
                               " | Course: " + courseCode + sectionInfo +
                               " | Date: " + assDate +
                               " | Status: " + assStatus);
        } catch (Exception e) {
            throw new RuntimeException("Failed to display assignation info: " + e.getMessage());
        }
    }
}


