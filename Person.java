package com.mycompany.iiumregistrationapp;

public abstract class Person implements Manageable { 
    protected String firstName; 
    protected String lastName; 
    protected String phoneNo; 

    public Person(String firstName, String lastName, String phoneNo) throws IllegalArgumentException {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (phoneNo == null || phoneNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        
        this.firstName = firstName.trim(); 
        this.lastName = lastName.trim(); 
        this.phoneNo = phoneNo.trim(); 
    } 

    public abstract void viewCourse() throws Exception; 
    
    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNo() { return phoneNo; }
} 

