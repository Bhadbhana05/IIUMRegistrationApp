package com.mycompany.iiumregistrationapp;

import javafx.application.Application; 
import javafx.geometry.Pos; 
import javafx.scene.Scene; 
import javafx.scene.control.*; 
import javafx.scene.layout.*; 
import javafx.scene.image.Image; 
import javafx.scene.image.ImageView; 
import javafx.stage.Stage; 
import java.util.ArrayList; 
import java.util.Random; 
import java.util.Objects; 
import java.util.List;

 
public class App extends Application { 
 
    private ArrayList<Student> students = new ArrayList<>(); 
    private ArrayList<Course> courses = new ArrayList<>(); 
    private ArrayList<Lecturer> lect = new ArrayList<>(); 
    private Stage primaryStage; 
    

 
    public enum UserRole { 
        ADMIN, STUDENT, LECTURER 
    } 
 
    public static void main(String[] args) { 
        launch(args); 
    } 
 
    @Override 
    public void start(Stage stage) { 
        this.primaryStage = stage; 
        initializeData(); 
        showLoginScreen(); 
    } 
 
private void initializeData() { 
    try {
        // --- Students ---
        // Load existing students from file
        List<Student> loadedStudents = FileHandler.loadStudentsFromFile("students.csv");
        // If file was empty, add default students
        if (loadedStudents.isEmpty()) {
            loadedStudents.add(new Student("2415008", "Adylia", "Sofia", "012-3456789")); 
            loadedStudents.add(new Student("2419004", "Adam", "Hafiz", "019-8765432"));
        }
        // Update the global 'students' list
        this.students.clear();
        this.students.addAll(loadedStudents);
        // Save updated list to file
        FileHandler.saveStudentsToFile(loadedStudents, "students.csv");
        // Display all students
        for (Student s : loadedStudents) {
            s.displayInfo();
        }
        
        // --- Courses ---
        List<Course> loadedCourses = FileHandler.loadCoursesFromFile("courses.csv");
        if (loadedCourses.isEmpty()) {
            loadedCourses.add(new Course("CSC101", "Introduction to Programming", 3)); 
            loadedCourses.add(new Course("MTH201", "Calculus I", 4)); 
            loadedCourses.add(new Course("PHY100", "Physics Fundamentals", 3)); 
        }
        this.courses.clear();
        this.courses.addAll(loadedCourses);
        FileHandler.saveCoursesToFile(loadedCourses, "courses.csv");
        for (Course c : loadedCourses) {
            c.displayInfo();
        }
        
        // --- Lecturers ---
        List<Lecturer> loadedLecturers = FileHandler.loadLecturersFromFile("lecturers.csv");
        if (loadedLecturers.isEmpty()) {
            loadedLecturers.add(new Lecturer("10567", "Dr. Dini", "Oktarina", "0198765432")); 
            loadedLecturers.add(new Lecturer("10000", "Dr. Wahab", "Ahmad", "014823923")); 
        }
        this.lect.clear();
        this.lect.addAll(loadedLecturers);
        FileHandler.saveLecturersToFile(loadedLecturers, "lecturers.csv");
        for (Lecturer l : loadedLecturers) {
            l.displayInfo();
        }
        
    } catch (Exception e) { 
        System.err.println("Error initializing data: " + e.getMessage()); 
    } 
}

 
 
    private void showLoginScreen() { 
        VBox loginBox = new VBox(15); 
        loginBox.setAlignment(Pos.CENTER); 
        loginBox.setStyle("-fx-padding: 30; -fx-background-color: #f0f8ff;"); 
 
        // Add IIUM Logo 
        ImageView logoImageView = new ImageView(); 
        boolean logoLoaded = false; 
        try { 
            Image logo = new Image("https://th.bing.com/th/id/OIP.zHLG-OYp8yKrjnYyBpi1UwHaB3?rs=1&pid=ImgDetMain"); 
            logoImageView.setImage(logo); 
            logoImageView.setFitWidth(200); 
            logoImageView.setFitHeight(80); 
            logoImageView.setPreserveRatio(true); 
            logoImageView.setSmooth(true); 
            logoLoaded = true; 
        } catch (Exception e) { 
            System.out.println("Could not load IIUM logo image: " + e.getMessage()); 
        } 
 
        Label titleLabel = new Label("IIUM Course Registration System"); 
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c5aa0;"); 
 
        VBox formBox = new VBox(10); 
        formBox.setAlignment(Pos.CENTER); 
        formBox.setMaxWidth(300); 
        formBox.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);"); 
 
        Label userLabel = new Label("Username:"); 
        userLabel.setStyle("-fx-font-weight: bold;"); 
        TextField usernameField = new TextField(); 
        usernameField.setStyle("-fx-padding: 8;"); 
 
        Label passLabel = new Label("Password:"); 
        passLabel.setStyle("-fx-font-weight: bold;"); 
        PasswordField passwordField = new PasswordField(); 
        passwordField.setStyle("-fx-padding: 8;"); 
 
        Label roleLabel = new Label("Login As:"); 
        roleLabel.setStyle("-fx-font-weight: bold;"); 
        ComboBox<String> roleComboBox = new ComboBox<>(); 
        roleComboBox.getItems().addAll("Admin", "Student", "Lecturer"); 
        roleComboBox.setValue("Admin"); // Default selection 
        roleComboBox.setStyle("-fx-padding: 8; -fx-pref-width: 200;"); 
 
        Button loginBtn = new Button("Login"); 
        loginBtn.setStyle("-fx-background-color: #2c5aa0; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;"); 
        loginBtn.setOnMouseEntered(e -> loginBtn.setStyle("-fx-background-color: #1e3f73; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;")); 
        loginBtn.setOnMouseExited(e -> loginBtn.setStyle("-fx-background-color: #2c5aa0; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;")); 
 
        Label message = new Label(); 
        message.setStyle("-fx-text-fill: red;"); 
 
        loginBtn.setOnAction(e -> { 
            String user = usernameField.getText(); 
            String pass = passwordField.getText(); 
            String selectedRole = roleComboBox.getValue(); 
 
            boolean authenticated = false; 
            UserRole userRole = null; 
 
            if (selectedRole != null) { 
                switch (selectedRole) { 
                    case "Admin": 
                        if (user.equals("admin") && pass.equals("1234")) { 
                            authenticated = true; 
                            userRole = UserRole.ADMIN; 
                        } 
                        break; 
                    case "Student": 
                        if (user.equals("student") && pass.equals("s123")) { 
                            authenticated = true; 
                            userRole = UserRole.STUDENT; 
                        } 
                        break; 
                    case "Lecturer": 
                        if (user.equals("lecturer") && pass.equals("l123")) { 
                            authenticated = true; 
                            userRole = UserRole.LECTURER; 
                        } 
                        break; 
                } 
            } 
 
            if (authenticated) { 
                showMainApp(userRole); 
            } else { 
                message.setText("Invalid " + (selectedRole != null ? selectedRole : "login") + " credentials."); 
            } 
        }); 
 
        formBox.getChildren().addAll( 
                userLabel, usernameField, 
                passLabel, passwordField, 
                roleLabel, roleComboBox, 
                loginBtn, message 
        ); 
 
        // Add components to login box 
        if (logoLoaded) { 
            loginBox.getChildren().addAll(logoImageView, titleLabel, formBox); 
        } else { 
            Label placeholder = new Label("IIUM Logo"); 
            placeholder.setStyle("-fx-font-size: 24px; -fx-text-fill: #ccc;"); 
            loginBox.getChildren().addAll(placeholder, titleLabel, formBox); 
        } 
 
        Scene loginScene = new Scene(loginBox, 450, 550); 
        primaryStage.setTitle("IIUM Course Registration - Login"); 
        primaryStage.setScene(loginScene); 
        primaryStage.show(); 
    } 
 
    private void showMainApp(UserRole role) { 
        TabPane tabPane = new TabPane(); 
 
        if (null != role) switch (role) { 
            case ADMIN:{ 
                Tab studentTab = new Tab("Students", studentTabUI()); 
                Tab lectTab = new Tab("Lecturer", lectTabUI()); 
                Tab courseTab = new Tab("Courses", courseTabUI()); 
                Tab registrationTab = new Tab("Registrations", registrationTabUI()); 
                Tab assignTab = new Tab("Assign", assignTabUI()); 
                tabPane.getTabs().addAll(studentTab, lectTab, courseTab, registrationTab, assignTab); 
                    break; 
                } 
            case STUDENT:{ 
                Tab registrationTab = new Tab("Register/View Courses", studentRegistrationViewUI()); 
                tabPane.getTabs().addAll(registrationTab); 
                    break; 
                } 
            case LECTURER: 
                Tab lecturerTab = new Tab("My Courses (Lecturer)", lecturerViewUI()); 
                Tab detailTab = new Tab("Detail", detailTab()); 
                tabPane.getTabs().addAll(lecturerTab, detailTab); 
                break; 
            default: 
                break; 
        } 
 
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); 
 
        Button logoutButton = new Button("Logout"); 
        logoutButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-padding: 8 15; -fx-font-weight: bold;"); 
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle("-fx-background-color: #b71c1c; -fx-text-fill: white; -fx-padding: 8 15; -fx-font-weight: bold;")); 
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-padding: 8 15; -fx-font-weight: bold;")); 
        logoutButton.setOnAction(e -> showLoginScreen()); 
 
        HBox topBar = new HBox(10); 
        topBar.setAlignment(Pos.CENTER_RIGHT); 
        topBar.setStyle("-fx-padding: 10; -fx-background-color: #e0e0e0;"); 
        topBar.getChildren().addAll(new Label("Logged in as: " + role.name()), logoutButton); 
 
        BorderPane mainLayout = new BorderPane(); 
        mainLayout.setTop(topBar); 
        mainLayout.setCenter(tabPane); 
 
        Scene scene = new Scene(mainLayout, 600, 450); 
        primaryStage.setTitle("IIUM Course Registration System - " + role.name()); 
        primaryStage.setScene(scene); 
        primaryStage.show(); 
    } 
 
    private VBox studentTabUI() { 
        VBox vbox = new VBox(10); 
        vbox.setStyle("-fx-padding: 10;"); 
 
        TextField studIdField = new TextField(); 
        TextField firstNameField = new TextField(); 
        TextField lastNameField = new TextField(); 
        TextField phoneField = new TextField(); 
        TextArea output = new TextArea(); 
        Button addBtn = new Button("Add Student"); 
 
        studIdField.setPromptText("Student ID"); 
        firstNameField.setPromptText("First Name"); 
        lastNameField.setPromptText("Last Name"); 
        phoneField.setPromptText("Phone Number"); 
        output.setEditable(false); 
 
addBtn.setOnAction(e -> { 
    try { 
        String id = studIdField.getText(); 
        String first = firstNameField.getText(); 
        String last = lastNameField.getText(); 
        String phone = phoneField.getText(); 

        if (id.isEmpty() || first.isEmpty() || last.isEmpty()) { 
            output.setText("Please fill all required fields (Student ID, First Name, Last Name)."); 
        } else { 
            // 1. Load current student list from file
            List<Student> students = FileHandler.loadStudentsFromFile("students.csv");

            // 2. Add new student
            Student s = new Student(id, first, last, phone); 
            students.add(s); 

            // 3. Save updated list back to file
            FileHandler.saveStudentsToFile(students, "students.csv");

            // 4. Feedback
            output.setText("Student added:\n" + first + " " + last + " (" + id + ")"); 

            // 5. Clear fields
            studIdField.clear(); 
            firstNameField.clear(); 
            lastNameField.clear(); 
            phoneField.clear(); 
        } 
    } catch (Exception ex) { 
        output.setText("Error adding student: " + ex.getMessage()); 
    } 
}); 
 
        vbox.getChildren().addAll(studIdField, firstNameField, lastNameField, phoneField, addBtn, output); 
        return vbox; 
    } 
 
private VBox lectTabUI() { 
    VBox vbox = new VBox(10); 
    vbox.setStyle("-fx-padding: 10;"); 

    TextField lectIdField = new TextField(); 
    TextField firstNameField = new TextField(); 
    TextField lastNameField = new TextField(); 
    TextField phoneField = new TextField(); 
    TextArea output = new TextArea(); 
    Button addBtn = new Button("Add Lecturer"); 

    lectIdField.setPromptText("Lecturer ID"); 
    firstNameField.setPromptText("First Name"); 
    lastNameField.setPromptText("Last Name"); 
    phoneField.setPromptText("Phone Number"); 
    output.setEditable(false); 

    addBtn.setOnAction(e -> { 
        try { 
            String id = lectIdField.getText(); 
            String first = firstNameField.getText(); 
            String last = lastNameField.getText(); 
            String phone = phoneField.getText(); 

            if (id.isEmpty() || first.isEmpty() || last.isEmpty()) { 
                output.setText("Please fill all required fields (Lecturer ID, First Name, Last Name)."); 
            } else { 
                // 1. Load current lecturers from file
                List<Lecturer> lecturers = FileHandler.loadLecturersFromFile("lecturers.csv");

                // 2. Add new lecturer
                Lecturer l = new Lecturer(id, first, last, phone); 
                lecturers.add(l);

                // 3. Save updated list
                FileHandler.saveLecturersToFile(lecturers, "lecturers.csv");

                // 4. Confirmation output
                output.setText("Lecturer added:\n" + first + " " + last + " (" + id + ")"); 

                // 5. Clear fields
                lectIdField.clear(); 
                firstNameField.clear(); 
                lastNameField.clear(); 
                phoneField.clear(); 
            } 
        } catch (Exception ex) { 
            output.setText("Error adding lecturer: " + ex.getMessage()); 
        } 
    }); 

    vbox.getChildren().addAll(lectIdField, firstNameField, lastNameField, phoneField, addBtn, output); 
    return vbox; 
}

 
private VBox courseTabUI() { 
    VBox vbox = new VBox(10); 
    vbox.setStyle("-fx-padding: 10;"); 

    TextField courseCodeField = new TextField(); 
    TextField courseNameField = new TextField(); 
    TextField chrField = new TextField(); 
    TextArea output = new TextArea(); 
    Button addBtn = new Button("Add Course"); 

    courseCodeField.setPromptText("Course Code"); 
    courseNameField.setPromptText("Course Name"); 
    chrField.setPromptText("Credit Hours"); 
    output.setEditable(false); 

    addBtn.setOnAction(e -> { 
        try { 
            String code = courseCodeField.getText(); 
            String name = courseNameField.getText(); 
            String chrText = chrField.getText(); 

            if (code.isEmpty() || name.isEmpty() || chrText.isEmpty()) { 
                output.setText("Please fill all required fields (Course Code, Course Name, Credit Hours)."); 
                return; 
            } 

            int chr = Integer.parseInt(chrText); 
            Course c = new Course(code, name, chr);

            //  Properly load, add, save
            List<Course> courses = FileHandler.loadCoursesFromFile("courses.csv");
            courses.add(c);
            FileHandler.saveCoursesToFile(courses, "courses.csv");

            output.setText("Course added:\n" + code + " - " + name + " (" + chr + " CHR)");

            // Clear fields
            courseCodeField.clear(); 
            courseNameField.clear(); 
            chrField.clear(); 

        } catch (NumberFormatException ex) { 
            output.setText("Credit Hours must be a valid integer."); 
        } catch (Exception ex) { 
            output.setText("Error adding course: " + ex.getMessage()); 
        } 
    }); 

    vbox.getChildren().addAll(courseCodeField, courseNameField, chrField, addBtn, output); 
    return vbox; 
}
 
 
    private VBox registrationTabUI() { 
        VBox vbox = new VBox(10); 
        vbox.setStyle("-fx-padding: 10;"); 
 
        TextField regIdField = new TextField(); 
        TextField studIdField = new TextField(); 
        TextField courseCodeField = new TextField(); 
        TextField dateField = new TextField(); 
        TextArea output = new TextArea(); 
        Button confirmBtn = new Button("Confirm Registration"); 
        Button cancelBtn = new Button("Cancel Registration"); 
 
        regIdField.setPromptText("Registration ID"); 
        studIdField.setPromptText("Student ID"); 
        courseCodeField.setPromptText("Course Code"); 
        dateField.setPromptText("Registration Date (YYYY-MM-DD)"); 
        output.setEditable(false); 
 
        Registration[] currentReg = new Registration[1]; 
 
confirmBtn.setOnAction(e -> {
    try {
        String regId = regIdField.getText();
        String sid = studIdField.getText();
        String ccode = courseCodeField.getText();
        String date = dateField.getText();

        if (regId.isEmpty() || sid.isEmpty() || ccode.isEmpty() || date.isEmpty()) {
            output.setText("Please fill all required fields for registration.");
            return;
        }

        Student foundStudent = null;
        for (Student s : students) {
            if (s.getStudID().equals(sid)) {
                foundStudent = s;
                break;
            }
        }

        Course foundCourse = null;
        for (Course c : courses) {
            if (c.getCourseCode().equals(ccode)) {
                foundCourse = c;
                break;
            }
        }

        if (foundStudent == null) {
            output.setText("Error: Student with ID " + sid + " not found.");
            return;
        }
        if (foundCourse == null) {
            output.setText("Error: Course with code " + ccode + " not found.");
            return;
        }

        String section = foundCourse.getSection();
        if (section == null || section.isEmpty()) {
            output.setText("Error: Course " + ccode + " does not have an assigned section yet.");
            return;
        }

        if (foundStudent.getRegisteredCourses().stream().anyMatch(rc ->
                rc.getCourseCode().equals(ccode) && Objects.equals(rc.getSection(), section))) {
            output.setText("Student " + sid + " is already registered for course " + ccode +
                    " (Section: " + section + ").");
            return;
        }

        Course courseToRegister = new Course(foundCourse.getCourseCode(),
                foundCourse.getCourseName(),
                foundCourse.getCreditHours(),
                section);

        Registration reg = new Registration(regId, sid, ccode, date, section);
        reg.confirmRegistration();
        currentReg[0] = reg;
        foundStudent.registerCourse(courseToRegister);

        //  File I/O
        List<Registration> regList = FileHandler.loadRegistrationsFromFile("registrations.csv");
        regList.add(reg);
        FileHandler.saveRegistrationsToFile(regList, "registrations.csv");

        //  Display all registrations
        StringBuilder sb = new StringBuilder("Registration confirmed:\n");
        for (Registration r : FileHandler.loadRegistrationsFromFile("registrations.csv")) {
            sb.append("RegID: ").append(r.getRegID())
              .append(", Student: ").append(r.getStudID())
              .append(", Course: ").append(r.getCourseCode())
              .append(" (Section: ").append(r.getSection()).append(")\n");
        }

        output.setText(sb.toString());

        // Clear input fields
        regIdField.clear();
        studIdField.clear();
        courseCodeField.clear();
        dateField.clear();

    } catch (Exception ex) {
        output.setText("Error during registration: " + ex.getMessage());
    }
}); 
 
cancelBtn.setOnAction(e -> {
    try {
        if (currentReg[0] != null) {
            String sid = currentReg[0].getStudID();
            String ccode = currentReg[0].getCourseCode();
            String section = currentReg[0].getSection();
            String regId = currentReg[0].getRegID();

            // Drop course from student
            for (Student s : students) {
                if (s.getStudID().equals(sid)) {
                    if (section != null) {
                        s.dropCourse(ccode, section);
                    } else {
                        s.dropCourse(ccode);
                    }
                    break;
                }
            }

            currentReg[0].cancelRegistration();

            // ✅ File I/O: Remove the registration from the file
            List<Registration> regList = FileHandler.loadRegistrationsFromFile("registrations.csv");
            regList.removeIf(r -> r.getRegID().equals(regId));
            FileHandler.saveRegistrationsToFile(regList, "registrations.csv");

            output.setText("Registration cancelled for RegID: " + regId + ".");
            currentReg[0] = null;
        } else {
            output.setText("No active registration to cancel.");
        }
    } catch (Exception ex) {
        output.setText("Error cancelling registration: " + ex.getMessage());
    }
});
 
 
        vbox.getChildren().addAll(regIdField, studIdField, courseCodeField, dateField, confirmBtn, cancelBtn, output); 
        return vbox; 
    } 
 
private VBox assignTabUI() {
    VBox vbox = new VBox(10);
    vbox.setStyle("-fx-padding: 10;");

    TextField assIdField = new TextField();
    TextField lectIdField = new TextField();
    TextField courseCodeField = new TextField();
    TextField dateField = new TextField();
    TextArea output = new TextArea();
    Button confirmBtn = new Button("Confirm Assignment");
    Button cancelBtn = new Button("Cancel Assignment");

    assIdField.setPromptText("Assignment ID");
    lectIdField.setPromptText("Lecturer ID");
    courseCodeField.setPromptText("Course Code");
    dateField.setPromptText("Assignment Date (YYYY-MM-DD)");
    output.setEditable(false);

    Assignation[] currentAss = new Assignation[1];
    Random random = new Random();

    confirmBtn.setOnAction(e -> {
        try {
            String assId = assIdField.getText();
            String lid = lectIdField.getText();
            String ccode = courseCodeField.getText();
            String date = dateField.getText();

            if (assId.isEmpty() || lid.isEmpty() || ccode.isEmpty() || date.isEmpty()) {
                output.setText("Please fill all required fields for assignment.");
                return;
            }

            Lecturer foundLecturer = null;
            for (Lecturer l : lect) {
                if (l.getLectID().equals(lid)) {
                    foundLecturer = l;
                    break;
                }
            }

            Course foundCourse = null;
            for (Course c : courses) {
                if (c.getCourseCode().equals(ccode)) {
                    foundCourse = c;
                    break;
                }
            }

            if (foundLecturer == null) {
                output.setText("Error: Lecturer with ID " + lid + " not found.");
                return;
            }

            if (foundCourse == null) {
                output.setText("Error: Course with code " + ccode + " not found.");
                return;
            }

            if (foundLecturer.getAssignedCourses().stream().anyMatch(ac ->
                    ac.getCourseCode().equals(ccode) && ac.getSection() != null)) {
                output.setText("Lecturer " + lid + " is already assigned to course " + ccode + " with a section.");
                return;
            }

            String section = "S" + String.format("%02d", random.nextInt(10) + 1);

            Course courseWithSection = new Course(foundCourse.getCourseCode(),
                                                  foundCourse.getCourseName(),
                                                  foundCourse.getCreditHours(),
                                                  section);

            Assignation ass = new Assignation(assId, lid, ccode, date, section);
            ass.confirmAssignation();
            currentAss[0] = ass;

            foundLecturer.assignCourse(courseWithSection);

            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).getCourseCode().equals(ccode)) {
                    courses.set(i, courseWithSection);
                    break;
                }
            }

            // 🔄 FILE HANDLING: save and verify
            List<Assignation> assList = FileHandler.loadAssignationsFromFile("assignations.csv");
            assList.add(ass);
            FileHandler.saveAssignationsToFile(assList, "assignations.csv");

            List<Assignation> lo6 = FileHandler.loadAssignationsFromFile("assignations.csv");
            StringBuilder sb = new StringBuilder("Assignment confirmed:\n");
            for (Assignation a : lo6) {
                sb.append(a.getAssID()).append(" | ")
                  .append(a.getLectID()).append(" | ")
                  .append(a.getCourseCode()).append(" | ")
                  .append(a.getSection()).append(" | ")
                  .append(a.getAssDate()).append("\n");
            }
            output.setText(sb.toString());

            assIdField.clear();
            lectIdField.clear();
            courseCodeField.clear();
            dateField.clear();

        } catch (Exception ex) {
            output.setText("Error during assignment: " + ex.getMessage());
        }
    });

    cancelBtn.setOnAction(e -> {
        try {
            if (currentAss[0] != null) {
                String lid = currentAss[0].getLectID();
                String ccode = currentAss[0].getCourseCode();

                for (Lecturer l : lect) {
                    if (l.getLectID().equals(lid)) {
                        List<Course> assignedCourses = l.getAssignedCourses();
                        assignedCourses.removeIf(c -> c.getCourseCode().equals(ccode));
                        break;
                    }
                }

                // 🔄 FILE HANDLING: remove and update file
                List<Assignation> assList = FileHandler.loadAssignationsFromFile("assignations.csv");
                assList.removeIf(a -> a.getAssID().equals(currentAss[0].getAssID()));
                FileHandler.saveAssignationsToFile(assList, "assignations.csv");

                currentAss[0].cancelAssignation();
                output.setText("Assignment cancelled for Assignment ID: " + currentAss[0].getAssID());
                currentAss[0] = null;
            } else {
                output.setText("No active assignment to cancel.");
            }
        } catch (Exception ex) {
            output.setText("Error cancelling assignment: " + ex.getMessage());
        }
    });

    vbox.getChildren().addAll(assIdField, lectIdField, courseCodeField, dateField, confirmBtn, cancelBtn, output);
    return vbox;
}
 
 
    private VBox studentRegistrationViewUI() { 
        VBox vbox = new VBox(10); 
        vbox.setStyle("-fx-padding: 10;"); 
 
        Label title = new Label("Student Course Registration"); 
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); 
 
        TextField studentIdInput = new TextField(); 
        studentIdInput.setPromptText("Your Student ID"); 
        TextField courseCodeInput = new TextField(); 
        courseCodeInput.setPromptText("Course Code to Register"); 
        Button registerCourseBtn = new Button("Register for Course"); 
        Button viewRegisteredCoursesBtn = new Button("View My Registered Courses"); 
        TextArea studentOutput = new TextArea(); 
        studentOutput.setEditable(false); 
        studentOutput.setPromptText("Registration messages and course list will appear here."); 
 
        registerCourseBtn.setOnAction(e -> { 
            try { 
                String studentID = studentIdInput.getText(); 
                String courseCode = courseCodeInput.getText(); 
 
                if (studentID.isEmpty() || courseCode.isEmpty()) { 
                    studentOutput.setText("Please enter your Student ID and Course Code."); 
                    return; 
                } 
 
                Student foundStudent = null; 
                for (Student s : students) { 
                    if (s.getStudID().equals(studentID)) { 
                        foundStudent = s; 
                        break; 
                    } 
                } 
 
                Course foundCourse = null; 
                String assignedSection = null; 
                for (Course c : courses) { 
                    if (c.getCourseCode().equals(courseCode)) { 
                        foundCourse = c; 
                        assignedSection = c.getSection(); // Get the section from the global courses list 
                        break; 
                    } 
                } 
 
                if (foundStudent != null && foundCourse != null) { 
                    if (assignedSection == null || assignedSection.isEmpty()) { 
                        studentOutput.setText("Error: Course " + courseCode + " does not have an assigned section yet. Please wait for an admin to assign a lecturer and section."); 
                        return; 
                    } 
 
                    boolean alreadyRegistered = false; 
                    for (Course registeredCourse : foundStudent.getRegisteredCourses()) { 
                        if (registeredCourse.getCourseCode().equals(courseCode) && 
                                Objects.equals(assignedSection, registeredCourse.getSection())) { 
                            alreadyRegistered = true; 
                            break; 
                        } 
                    } 
 
                    if (alreadyRegistered) { 
                        studentOutput.setText("You are already registered for " + courseCode + 
                                            " (Section: " + assignedSection + ")."); 
                    } else { 
                        Course courseToRegister = new Course(foundCourse.getCourseCode(), 
                                                           foundCourse.getCourseName(), 
                                                           foundCourse.getCreditHours(), 
                                                           assignedSection); 
                        foundStudent.registerCourse(courseToRegister); 
                        studentOutput.setText("Successfully registered student " + studentID + " for " + courseCode + 
                                            " (Section: " + assignedSection + ")."); 
                        studentIdInput.clear(); 
                        courseCodeInput.clear(); 
                    } 
                } else { 
                    studentOutput.setText("Error: Student ID or Course Code not found."); 
                } 
            } catch (Exception ex) { 
                studentOutput.setText("Error during registration: " + ex.getMessage()); 
            } 
        }); 
 
        viewRegisteredCoursesBtn.setOnAction(e -> { 
            try { 
                String studentID = studentIdInput.getText(); 
                if (studentID.isEmpty()) { 
                    studentOutput.setText("Please enter your Student ID to view registered courses."); 
                    return; 
                } 
 
                Student foundStudent = null; 
                for (Student s : students) { 
                    if (s.getStudID().equals(studentID)) { 
                        foundStudent = s; 
                        break; 
                    } 
                } 
 
                if (foundStudent != null) { 
                    List<Course> registeredCourses = foundStudent.getRegisteredCourses(); 
                    if (registeredCourses.isEmpty()) { 
                        studentOutput.setText("Student " + studentID + " has no registered courses."); 
                    } else { 
                        StringBuilder sb = new StringBuilder("Courses registered by " + studentID + ":\n"); 
                        for (Course course : registeredCourses) { 
                            sb.append("- ").append(course.getCourseCode()) 
                                    .append(" - ").append(course.getCourseName()); 
                            if (course.getSection() != null && !course.getSection().isEmpty()) { 
                                sb.append(" (Section: ").append(course.getSection()).append(")"); 
                            } 
                            sb.append("\n"); 
                        } 
                        studentOutput.setText(sb.toString()); 
                    } 
                } else { 
                    studentOutput.setText("Error: Student with ID " + studentID + " not found."); 
                } 
            } catch (Exception ex) { 
                studentOutput.setText("Error viewing courses: " + ex.getMessage()); 
            } 
        }); 
 
        vbox.getChildren().addAll(title, studentIdInput, courseCodeInput, registerCourseBtn, viewRegisteredCoursesBtn, studentOutput); 
        return vbox; 
    } 
 
    private VBox lecturerViewUI() { 
        VBox vbox = new VBox(10); 
    vbox.setStyle("-fx-padding: 10;"); 
 
    Label title = new Label("Lecturer Dashboard"); 
    title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); 
    Label info = new Label("This section displays all available courses, regardless of assignment."); 
 
    TextArea coursesList = new TextArea(); 
    coursesList.setEditable(false); 
    coursesList.setPromptText("List of all courses available."); 
 
    StringBuilder sb = new StringBuilder("All Available Courses (with assigned sections):\n"); 
    for (Course c : courses) { 
        sb.append(c.getCourseCode()).append(" - ").append(c.getCourseName()) 
          .append(" (").append(c.getCreditHours()).append(" CHR)"); 
        if (c.getSection() != null && !c.getSection().isEmpty()) { 
            sb.append(" [Section: ").append(c.getSection()).append("]"); 
        } 
        sb.append("\n"); 
    } 
    coursesList.setText(sb.toString()); 
 
    Button refreshBtn = new Button("Refresh Course List"); 
    refreshBtn.setOnAction(e -> { 
        StringBuilder refreshedSb = new StringBuilder("All Available Courses (with assigned sections):\n"); 
        for (Course c : courses) { 
            refreshedSb.append(c.getCourseCode()).append(" - ").append(c.getCourseName()) 
                      .append(" (").append(c.getCreditHours()).append(" CHR)"); 
            if (c.getSection() != null && !c.getSection().isEmpty()) { 
                refreshedSb.append(" [Section: ").append(c.getSection()).append("]"); 
            } 
            refreshedSb.append("\n"); 
        } 
        coursesList.setText(refreshedSb.toString()); 
        System.out.println("Course list refreshed."); 
    }); 
 
    // -- Edit Section -- 
    Label editLabel = new Label("Edit Assigned Course Section"); 
    editLabel.setStyle("-fx-font-weight: bold;"); 
    TextField courseCodeToEdit = new TextField(); 
    courseCodeToEdit.setPromptText("Course Code"); 
    TextField newSectionField = new TextField(); 
    newSectionField.setPromptText("New Section Name"); 
    Button updateSectionBtn = new Button("Update Section"); 
 
    // -- Remove Assigned Course -- 
    Label removeLabel = new Label("Remove Assigned Course"); 
    removeLabel.setStyle("-fx-font-weight: bold;"); 
    TextField courseCodeToRemove = new TextField(); 
    courseCodeToRemove.setPromptText("Course Code"); 
    Button removeCourseBtn = new Button("Remove Course"); 
 
    // -- Shared Output -- 
    TextArea updateOutput = new TextArea(); 
    updateOutput.setEditable(false); 
 
    updateSectionBtn.setOnAction(e -> { 
        String courseCode = courseCodeToEdit.getText(); 
        String newSection = newSectionField.getText(); 
        boolean updated = false; 
 
        for (Lecturer l : lect) { 
            for (Course c : l.getAssignedCourses()) { 
                if (c.getCourseCode().equals(courseCode)) { 
                    c.setSection(newSection); 
                    updateOutput.setText("Updated section for " + courseCode + " to " + newSection); 
                    System.out.println("Updated section: " + courseCode + " -> " + newSection); 
                    updated = true; 
 
                    // Update section in global list 
                    for (Course gc : courses) { 
                        if (gc.getCourseCode().equals(courseCode)) { 
                            gc.setSection(newSection); 
                            break; 
                        } 
                    } 
 
                    break; 
                } 
            } 
        } 
 
        if (!updated) { 
            updateOutput.setText("Course code not found in your assignments."); 
            System.out.println("Update failed: Course code " + courseCode + " not found in assignments."); 
        } 
 
        courseCodeToEdit.clear(); 
        newSectionField.clear(); 
    }); 
 
    removeCourseBtn.setOnAction(e -> { 
        String courseCode = courseCodeToRemove.getText(); 
        boolean removed = false; 
 
        for (Lecturer l : lect) { 
            List<Course> assignedCourses = l.getAssignedCourses(); 
            removed = assignedCourses.removeIf(c -> c.getCourseCode().equals(courseCode)); 
            if (removed) { 
                updateOutput.setText("Removed assigned course: " + courseCode); 
                System.out.println("Removed course from lecturer assignment: " + courseCode); 
                break; 
            } 
        } 
 
        if (!removed) { 
            updateOutput.setText("Course code not found in your assignments."); 
            System.out.println("Remove failed: Course code " + courseCode + " not found."); 
        } 
 
        courseCodeToRemove.clear(); 
    }); 
 
    // Add all components to the VBox 
    vbox.getChildren().addAll( 
        title, info, coursesList, refreshBtn, editLabel, courseCodeToEdit, newSectionField, updateSectionBtn, removeLabel, courseCodeToRemove, removeCourseBtn, updateOutput 
    ); 
 
    return vbox; 
 
    } 
 
    private VBox detailTab() { 
        VBox vbox = new VBox(10); 
        vbox.setStyle("-fx-padding: 10;"); 
 
        Label title = new Label("Course and Student Details"); 
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); 
 
        TextField lectIdInput = new TextField(); 
        lectIdInput.setPromptText("Enter Lecturer ID"); 
        Button viewAssignedBtn = new Button("View My Assigned Courses"); 
        Button viewStudentsBtn = new Button("View Students in My Courses"); 
        TextArea detailOutput = new TextArea(); 
        detailOutput.setEditable(false); 
        detailOutput.setPromptText("Course and student details will appear here."); 
 
        viewAssignedBtn.setOnAction(e -> { 
            try { 
                String lectId = lectIdInput.getText(); 
                if (lectId.isEmpty()) { 
                    detailOutput.setText("Please enter your Lecturer ID."); 
                    return; 
                } 
 
                Lecturer foundLecturer = null; 
                for (Lecturer l : lect) { 
                    if (l.getLectID().equals(lectId)) { 
                        foundLecturer = l; 
                        break; 
                    } 
                } 
 
                if (foundLecturer != null) { 
                    List<Course> assignedCourses = foundLecturer.getAssignedCourses(); 
                    if (assignedCourses.isEmpty()) { 
                        detailOutput.setText("Lecturer " + lectId + " has no assigned courses."); 
                    } else { 
                        StringBuilder sb = new StringBuilder("Courses assigned to Lecturer " + lectId + ":\n"); 
                        for (Course course : assignedCourses) { 
                            sb.append("- ").append(course.getCourseCode()) 
                              .append(" - ").append(course.getCourseName()) 
                              .append(" (").append(course.getCreditHours()).append(" CHR)"); 
                            if (course.getSection() != null && !course.getSection().isEmpty()) { 
                                sb.append(" [Section: ").append(course.getSection()).append("]"); 
                            } 
                            sb.append("\n"); 
                        } 
                        detailOutput.setText(sb.toString()); 
                    } 
                } else { 
                    detailOutput.setText("Error: Lecturer with ID " + lectId + " not found."); 
                } 
            } catch (Exception ex) { 
                detailOutput.setText("Error viewing assigned courses: " + ex.getMessage()); 
            } 
        }); 
 
        viewStudentsBtn.setOnAction(e -> { 
            try { 
                String lectId = lectIdInput.getText(); 
                if (lectId.isEmpty()) { 
                    detailOutput.setText("Please enter your Lecturer ID."); 
                    return; 
                } 
 
                Lecturer foundLecturer = null; 
                for (Lecturer l : lect) { 
                    if (l.getLectID().equals(lectId)) { 
                        foundLecturer = l; 
                        break; 
                    } 
                } 
 
                if (foundLecturer != null) { 
                    List<Course> assignedCourses = foundLecturer.getAssignedCourses(); 
                    if (assignedCourses.isEmpty()) { 
                        detailOutput.setText("Lecturer " + lectId + " has no assigned courses to show students for."); 
                        return; 
                    } 
 
                    StringBuilder sb = new StringBuilder("Students enrolled in courses taught by Lecturer " + lectId + ":\n\n"); 
                    boolean hasStudents = false; 
 
                    for (Course assignedCourse : assignedCourses) { 
                        sb.append("Course: ").append(assignedCourse.getCourseCode()) 
                          .append(" - ").append(assignedCourse.getCourseName()); 
                        if (assignedCourse.getSection() != null) { 
                            sb.append(" [Section: ").append(assignedCourse.getSection()).append("]"); 
                        } 
                        sb.append("\n"); 
 
                        boolean foundStudentsInCourse = false; 
                        for (Student student : students) { 
                            for (Course registeredCourse : student.getRegisteredCourses()) { 
                                if (registeredCourse.getCourseCode().equals(assignedCourse.getCourseCode()) && 
                                    Objects.equals(registeredCourse.getSection(), assignedCourse.getSection())) { 
                                    sb.append("  - ").append(student.getFirstName()).append(" ") 
                                      .append(student.getLastName()).append(" (").append(student.getStudID()).append(")\n"); 
                                    foundStudentsInCourse = true; 
                                    hasStudents = true; 
                                } 
                            } 
                        } 
 
                        if (!foundStudentsInCourse) { 
                            sb.append("  No students enrolled in this course.\n"); 
                        } 
                        sb.append("\n"); 
                    } 
 
                    if (!hasStudents) { 
                        sb.append("No students are currently enrolled in any of your courses."); 
                    } 
 
                    detailOutput.setText(sb.toString()); 
                } else { 
                    detailOutput.setText("Error: Lecturer with ID " + lectId + " not found."); 
                } 
            } catch (Exception ex) { 
                detailOutput.setText("Error viewing students: " + ex.getMessage()); 
            } 
        }); 
 
        vbox.getChildren().addAll(title, lectIdInput, viewAssignedBtn, viewStudentsBtn, detailOutput); 
        return vbox; 
    } 
} 