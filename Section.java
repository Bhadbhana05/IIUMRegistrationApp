/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fxislit;

/**
 *
 * @author hana imani
 */
public class Section implements Manageable { 
    private String sectNo; 
    private String courseCode; 
    private String sectDay; 
    private String sectTime; 
    private String sectVenue; 

    public Section(String sectNo, String courseCode, String sectDay, String sectTime, String sectVenue) 
            throws IllegalArgumentException { 
        if (sectNo == null || sectNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Section number cannot be null or empty");
        }
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be null or empty");
        }
        if (sectDay == null || sectDay.trim().isEmpty()) {
            throw new IllegalArgumentException("Section day cannot be null or empty");
        }
        if (sectTime == null || sectTime.trim().isEmpty()) {
            throw new IllegalArgumentException("Section time cannot be null or empty");
        }
        if (sectVenue == null || sectVenue.trim().isEmpty()) {
            throw new IllegalArgumentException("Section venue cannot be null or empty");
        }
        
        this.sectNo = sectNo.trim(); 
        this.courseCode = courseCode.trim(); 
        this.sectDay = sectDay.trim(); 
        this.sectTime = sectTime.trim(); 
        this.sectVenue = sectVenue.trim(); 
    } 

    public String getSection() { return sectNo; }
    public String getCourseCode() { return courseCode; }
    public String getSectDay() { return sectDay; }
    public String getSectTime() { return sectTime; }
    public String getSectVenue() { return sectVenue; }

    public void addSection() throws RuntimeException { 
        try {
            System.out.println("Section " + sectNo + " added."); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to add section: " + e.getMessage());
        }
    } 

    public void updateSection(String newDay, String newTime, String newVenue) throws IllegalArgumentException { 
        if (newDay == null || newDay.trim().isEmpty()) {
            throw new IllegalArgumentException("New day cannot be null or empty");
        }
        if (newTime == null || newTime.trim().isEmpty()) {
            throw new IllegalArgumentException("New time cannot be null or empty");
        }
        if (newVenue == null || newVenue.trim().isEmpty()) {
            throw new IllegalArgumentException("New venue cannot be null or empty");
        }
        
        try {
            this.sectDay = newDay.trim(); 
            this.sectTime = newTime.trim(); 
            this.sectVenue = newVenue.trim(); 
            System.out.println("Section " + sectNo + " updated."); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to update section: " + e.getMessage());
        }
    } 

    public void deleteSection() throws RuntimeException { 
        try {
            System.out.println("Section " + sectNo + " deleted."); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete section: " + e.getMessage());
        }
    } 

    @Override 
    public void displayInfo() throws RuntimeException {
        try {
            System.out.println("Section " + sectNo + " for " + courseCode + 
                             " on " + sectDay + " at " + sectTime + " in " + sectVenue); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to display section info: " + e.getMessage());
        }
    } 
}