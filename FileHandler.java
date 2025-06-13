package com.mycompany.iiumregistrationapp;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class FileHandler {

    // Save list of registrations to CSV file
    public static void saveRegistrationsToFile(List<Registration> registrations, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Registration r : registrations) {
                String line = String.join(",",
                        r.getRegID(),
                        r.getStudID(),
                        r.getCourseCode(),
                        r.getRegDate(),
                        r.getRegStatus(),
                        r.getSection() != null ? r.getSection() : ""
                );
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Registrations saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving registrations: " + e.getMessage());
        }
    }

    // Load list of registrations from CSV file
    public static List<Registration> loadRegistrationsFromFile(String filename) {
        List<Registration> registrations = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",", -1);

                if (tokens.length >= 5) {
                    String regID = tokens[0];
                    String studID = tokens[1];
                    String courseCode = tokens[2];
                    String regDate = tokens[3];
                    String regStatus = tokens[4];
                    String section = tokens.length > 5 ? tokens[5] : null;

                    Registration r = new Registration(regID, studID, courseCode, regDate, section);

                    if (!"Pending".equalsIgnoreCase(regStatus)) {
                        if ("Confirmed".equalsIgnoreCase(regStatus)) {
                            r.confirmRegistration();
                        } else if ("Cancelled".equalsIgnoreCase(regStatus)) {
                            r.cancelRegistration();
                        }
                    }

                    registrations.add(r);
                }
            }

            System.out.println("Registrations loaded from " + filename);

        } catch (IOException e) {
            System.err.println("Error loading registrations: " + e.getMessage());
        }

        return registrations;
    }

    // ✅ Save Assignation list to file
    public static void saveAssignationsToFile(List<Assignation> assignations, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Assignation a : assignations) {
                writer.write(a.toCSV());
                writer.newLine();
            }
            System.out.println("Assignations saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving assignations: " + e.getMessage());
        }
    }

    // ✅ Load Assignation list from file
    public static List<Assignation> loadAssignationsFromFile(String filename) {
        List<Assignation> assignations = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Assignation a = Assignation.fromCSV(line);
                if (a != null) {
                    assignations.add(a);
                }
            }

            System.out.println("Assignations loaded from " + filename);

        } catch (IOException e) {
            System.err.println("Error loading assignations: " + e.getMessage());
        }

        return assignations;
    }
    // Save students to file
public static void saveStudentsToFile(List<Student> students, String filename) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        for (Student student : students) {
            writer.write(student.toCSV());
            writer.newLine();
        }
        System.out.println("Students saved to " + filename);
    } catch (IOException e) {
        System.err.println("Error saving students: " + e.getMessage());
    }
}

// Load students from file
public static List<Student> loadStudentsFromFile(String filename) {
    List<Student> students = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            Student student = Student.fromCSV(line);
            if (student != null) {
                students.add(student);
            }
        }
        System.out.println("Students loaded from " + filename);
    } catch (IOException e) {
        System.err.println("Error loading students: " + e.getMessage());
    }
    return students;
}

// Save courses to file
public static void saveCoursesToFile(List<Course> courses, String filename) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        for (Course course : courses) {
            writer.write(course.toCSV());
            writer.newLine();
        }
        System.out.println("Courses saved to " + filename);
    } catch (IOException e) {
        System.err.println("Error saving courses: " + e.getMessage());
    }
}

// Load courses from file
public static List<Course> loadCoursesFromFile(String filename) {
    List<Course> courses = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            Course course = Course.fromCSV(line);
            if (course != null) {
                courses.add(course);
            }
        }
        System.out.println("Courses loaded from " + filename);
    } catch (IOException e) {
        System.err.println("Error loading courses: " + e.getMessage());
    }
    return courses;
}

// Save list of sections to CSV file
public static void saveSectionsToFile(List<Section> sections, String filename) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        for (Section section : sections) {
            writer.write(section.toCSV());
            writer.newLine();
        }
        System.out.println("Sections saved to " + filename);
    } catch (IOException e) {
        System.err.println("Error saving sections: " + e.getMessage());
    }
}

// Load list of sections from CSV file
public static List<Section> loadSectionsFromFile(String filename) {
    List<Section> sections = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            Section section = Section.fromCSV(line);
            if (section != null) {
                sections.add(section);
            }
        }
        System.out.println("Sections loaded from " + filename);
    } catch (IOException e) {
        System.err.println("Error loading sections: " + e.getMessage());
    }
    return sections;
}


}



