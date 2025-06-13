package com.mycompany.iiumregistrationapp;

public class Registration implements Manageable { 
    private String regID; 
    private String studID; 
    private String courseCode; 
    private String regDate; 
    private String regStatus; 
    private String section; // Added section field

    public Registration(String regID, String studID, String courseCode, String regDate) 
            throws IllegalArgumentException { 
        this(regID, studID, courseCode, regDate, null);
    }
    
    public Registration(String regID, String studID, String courseCode, String regDate, String section) 
            throws IllegalArgumentException {
        if (regID == null || regID.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration ID cannot be null or empty");
        }
        if (studID == null || studID.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be null or empty");
        }
        if (regDate == null || regDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration date cannot be null or empty");
        }
        
        this.regID = regID.trim(); 
        this.studID = studID.trim(); 
        this.courseCode = courseCode.trim(); 
        this.regDate = regDate.trim(); 
        this.section = (section != null) ? section.trim() : null;
        this.regStatus = "Pending"; 
    } 

    // Getters
    public String getRegID() { return regID; }
    public String getStudID() { return studID; }
    public String getCourseCode() { return courseCode; }
    public String getRegDate() { return regDate; }
    public String getRegStatus() { return regStatus; }
    public String getSection() { return section; }

    public void confirmRegistration() throws RuntimeException { 
        try {
            regStatus = "Confirmed"; 
            String sectionInfo = (section != null) ? " section " + section : "";
            System.out.println("Registration confirmed for " + courseCode + sectionInfo); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to confirm registration: " + e.getMessage());
        }
    } 

    public void cancelRegistration() throws RuntimeException { 
        try {
            regStatus = "Cancelled"; 
            String sectionInfo = (section != null) ? " section " + section : "";
            System.out.println("Registration cancelled for " + courseCode + sectionInfo); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to cancel registration: " + e.getMessage());
        }
    } 

    @Override 
    public void displayInfo() throws RuntimeException {
        try {
            String sectionInfo = (section != null) ? " | Section: " + section : "";
            System.out.println("RegID: " + regID + " | Student: " + studID + 
                             " | Course: " + courseCode + sectionInfo + " | Status: " + regStatus); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to display registration info: " + e.getMessage());
        }
   }
}

