// Purpose: Class to manage employee profiles (CRUD operations)
// The class can create, retrieve, update, and list all employee profiles.
// The class can also save employee details to a text file.

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProfileManagement {
    private final List<EmpProfile> employees;

    public ProfileManagement() {
        this.employees = new ArrayList<>();
    }

    public void createEmployeeProfile(EmpProfile employee) {
        // Check if the name already exists in the text file
        if (isDuplicateName(employee.getName())) {
            System.out.println("An employee with this name already exists in the file. Cannot create duplicate profiles.\n");
            return;
        }

        // If no duplicate, add the employee to the list and save to the file
        employees.add(employee);
        saveEmployeeDetails();
        System.out.println("Employee profile created successfully.\n");
    }

    // Method to check for duplicate names in the text file
    private boolean isDuplicateName(String name) {
        try (BufferedReader reader = new BufferedReader(new FileReader("employee_profiles.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name: ") && line.substring(6).equalsIgnoreCase(name)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while checking for duplicate names.");
            e.printStackTrace(System.out);
        }
        return false;
    }

    public EmpProfile retrieveEmployeeProfile(String empID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("employee_profiles.txt"))) {
            String line;
            EmpProfile employee = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: ") && line.substring(4).equalsIgnoreCase(empID)) {
                    employee = new EmpProfile();
                    employee.updateIDNo(empID);

                    // The name line comes before the ID line
                    if (line.startsWith("Name: ")) {
                        employee.updateName(line.substring(6));
                    }

                    // Read the rest of the employee details
                    while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                        if (line.startsWith("DOB: ")) {
                            employee.updateDOB(LocalDate.parse(line.substring(5)));
                        } else if (line.startsWith("Address: ")) {
                            employee.updateAddress(line.substring(9));
                        } else if (line.startsWith("Emergency Contact: ")) {
                            employee.updateEmergencyContact(line.substring(19));
                        } else if (line.startsWith("Working Experience: ")) {
                            List<String> experience = List.of(line.substring(19).split(", "));
                            employee.updateWorkingExperience(experience);
                        } else if (line.startsWith("Position: ")) {
                            employee.updatePosition(line.substring(10));
                        } else if (line.startsWith("Department: ")) {
                            employee.updateDepartment(line.substring(12));
                        }
                    }
                    return employee;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while retrieving the employee profile.");
            e.printStackTrace(System.out);
        }
        return null; // If employee is not found
    }

    public void updateEmployeeProfile(String employeeId, EmpProfile updatedEmployee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getIDNo().equals(employeeId)) {
                employees.set(i, updatedEmployee);
                saveEmployeeDetails();
                return;
            }
        }
    }
    

    public List<EmpProfile> listAllEmployees() {
        return employees;
    }

    public void saveEmployeeDetails() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employee_profiles.txt", true))) {
            for (EmpProfile emp : employees) {
                writer.write("Name: " + emp.getName());
                writer.write("\nID: " + emp.getIDNo());
                writer.write("\nGender: " + emp.getGender());
                writer.write("\nDOB: " + emp.getDOB());
                writer.write("\nAge: " + emp.getAge());
                writer.write("\nAddress: " + emp.getAddress());
                writer.write("\nEmergency Contact: " + emp.getEmergencyContact());
                writer.write("\nWorking Experience: " + String.join(", ", emp.getWorkingExperience()));
                writer.write("\nPosition: " + emp.getPosition());
                writer.write("\nDepartment: " + emp.getDepartment());
                writer.write("\n\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving employee details.");
            e.printStackTrace(System.out);
        }
    }
}
