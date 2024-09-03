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
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

public class ProfileManagement {
    private final List<EmpProfile> employees;

    public ProfileManagement() {
        this.employees = new ArrayList<>();
        loadEmployeeProfiles();
    }

    private void loadEmployeeProfiles() {
        try (BufferedReader reader = new BufferedReader(new FileReader("ProfileManagement/employee_profiles.txt"))) {
            String line;
            EmpProfile employee = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: ")) {
                    if (employee != null) {
                        employees.add(employee);
                    }
                    employee = new EmpProfile();
                    employee.updateNRIC(line.substring(4));
                } else if (employee != null) {
                    if (line.startsWith("Password: ")) {
                        employee.updatePassword(line.substring(10));
                    } else if (line.startsWith("BankAcc: ")) {
                        employee.updateBankAcc(line.substring(9));
                    } else if (line.startsWith("Name: ")) {
                        employee.updateName(line.substring(6));
                    } else if (line.startsWith("Gender: ")) {
                        employee.updateGender(line.substring(8));
                    } else if (line.startsWith("DOB: ")) {
                        employee.updateDOB(LocalDate.parse(line.substring(5)));
                    } else if (line.startsWith("Address: ")) {
                        employee.updateAddress(line.substring(9));
                    } else if (line.startsWith("Emergency Contact: ")) {
                        employee.updateEmergencyContact(line.substring(19));
                    } else if (line.startsWith("Working Experience: ")) {
                        List<String> experience = Arrays.asList(line.substring(20).split(", "));
                        employee.updateWorkingExperience(experience);
                    } else if (line.startsWith("Position: ")) {
                        employee.updatePosition(line.substring(10));
                    } else if (line.startsWith("Department: ")) {
                        employee.updateDepartment(line.substring(12));
                    } else if (line.startsWith("Gross Salary: ")) {
                        employee.updateSalary(Double.parseDouble(line.substring(14)));
                    }
                }
            }
            if (employee != null) {
                employees.add(employee);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading employee profiles.");
            e.printStackTrace(System.out);
        }
    }

    public void printAllEmployees() {
        System.out.println("Employee List:");
        for (EmpProfile emp : employees) {
            System.out.println(emp.getNRIC() + ": " + emp.getName());
        }
    }

    public void createEmployeeProfile(EmpProfile employee) {
        if (isDuplicateID(employee.getNRIC())) {
            JOptionPane.showMessageDialog(null,
                    "An employee with this ID already exists in the file. Cannot create duplicate profiles.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        employees.add(employee);
        saveEmployeeDetails();
        saveEmployeeDetails2(employee);
        JOptionPane.showMessageDialog(null, "Employee profile created successfully.", "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean isDuplicateID(String NRIC) {
        return employees.stream().anyMatch(e -> e.getNRIC().equalsIgnoreCase(NRIC));
    }

    public EmpProfile retrieveEmployeeProfile(String empID) {
        System.out.println("Retrieving employee profile for ID: " + empID);
        try (BufferedReader reader = new BufferedReader(new FileReader("ProfileManagement/employee_profiles.txt"))) {
            String line;
            EmpProfile employee = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: ") && line.substring(4).equalsIgnoreCase(empID)) {
                    employee = new EmpProfile();
                    employee.updateNRIC(empID);
                    System.out.println("Found employee with ID: " + empID);
                } else if (employee != null) {
                    if (line.startsWith("Password: ")) {
                        employee.updatePassword(line.substring(10));
                    } else if (line.startsWith("BankAcc: ")) {
                        employee.updateBankAcc(line.substring(9));
                    } else if (line.startsWith("Name: ")) {
                        employee.updateName(line.substring(6));
                    } else if (line.startsWith("Gender: ")) {
                        employee.updateGender(line.substring(8));
                    } else if (line.startsWith("DOB: ")) {
                        employee.updateDOB(LocalDate.parse(line.substring(5)));
                    } else if (line.startsWith("Address: ")) {
                        employee.updateAddress(line.substring(9));
                    } else if (line.startsWith("Emergency Contact: ")) {
                        employee.updateEmergencyContact(line.substring(19));
                    } else if (line.startsWith("Working Experience: ")) {
                        List<String> experience = Arrays.asList(line.substring(20).split(", "));
                        employee.updateWorkingExperience(experience);
                    } else if (line.startsWith("Position: ")) {
                        employee.updatePosition(line.substring(10));
                    } else if (line.startsWith("Department: ")) {
                        employee.updateDepartment(line.substring(12));
                    } else if (line.startsWith("Gross Salary: ")) {
                        employee.updateSalary(Double.parseDouble(line.substring(14)));
                        break;
                    }
                }
            }
            if (employee != null) {
                System.out.println("Retrieved employee profile for ID: " + empID);
            } else {
                System.out.println("Employee profile not found for ID: " + empID);
            }
            return employee;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while retrieving employee profiles..", "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(System.out);
        }
        return null;
    }

    public void updateEmployeeProfile(String employeeId, EmpProfile updatedEmployee) {
        System.out.println("Updating employee profile with ID: " + employeeId);
        System.out.println("Updated Employee Details: " + updatedEmployee);

        EmpProfile existingEmployee = retrieveEmployeeProfile(employeeId);

        if (existingEmployee != null) {
            // Log salary changes
            if (existingEmployee.getSalary() != updatedEmployee.getSalary()) {
                logSalaryChange(existingEmployee, updatedEmployee.getSalary());
            }

            // Log position changes
            if (!existingEmployee.getPosition().equals(updatedEmployee.getPosition())) {
                logPositionChange(existingEmployee, updatedEmployee.getPosition());
            }

            // Update employee in the list
            int index = employees.indexOf(existingEmployee);
            if (index != -1) {
                employees.set(index, updatedEmployee);
                System.out.println("Employee profile updated successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to find the employee.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Save the complete list to the file
            saveEmployeeDetails();

        } else {
            JOptionPane.showMessageDialog(null, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logSalaryChange(EmpProfile employee, double newSalary) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("ProfileManagement/SalaryIncrement.txt", true))) {
            System.out.println("Writing salary change...");
            writer.write("Date and Time: " + LocalDate.now() + " " + java.time.LocalTime.now());
            writer.write("\nICNo: " + employee.getNRIC());
            writer.write("\nName: " + employee.getName());
            writer.write("\nOriginal Gross Salary: " + employee.getSalary());
            writer.write("\nNew Gross Salary: " + newSalary);
            writer.write("\n\n");
            System.out.println("Salary change logged successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while logging salary changes.");
            e.printStackTrace(System.out);
        }
    }

    private void logPositionChange(EmpProfile employee, String newPosition) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("ProfileManagement/PositionPromote.txt", true))) {
            System.out.println("Writing position change...");
            writer.write("Date and Time: " + LocalDate.now() + " " + java.time.LocalTime.now());
            writer.write("\nICNo: " + employee.getNRIC());
            writer.write("\nName: " + employee.getName());
            writer.write("\nOriginal Position: " + employee.getPosition());
            writer.write("\nNew Position: " + newPosition);
            writer.write("\n\n");
            System.out.println("Position change logged successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while logging position changes.");
            e.printStackTrace(System.out);
        }
    }

    public List<EmpProfile> listAllEmployees() {
        return employees;
    }

    public void saveEmployeeDetails() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ProfileManagement/employee_profiles.txt"))) {
            for (EmpProfile emp : employees) {
                writer.write("ID: " + emp.getNRIC());
                writer.write("\nPassword: " + emp.getPassword());
                writer.write("\nBankAcc: " + emp.getBankAcc());
                writer.write("\nName: " + emp.getName());
                writer.write("\nGender: " + emp.getGender());
                writer.write("\nDOB: " + emp.getDOB());
                writer.write("\nAge: " + emp.getAge());
                writer.write("\nAddress: " + emp.getAddress());
                writer.write("\nEmergency Contact: " + emp.getEmergencyContact());
                writer.write("\nWorking Experience: " + String.join(", ", emp.getWorkingExperience()));
                writer.write("\nPosition: " + emp.getPosition());
                writer.write("\nDepartment: " + emp.getDepartment());
                writer.write("\nGross Salary: " + emp.getSalary());
                writer.write("\n\n");
            }
            System.out.println("Employee details saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving employee details.\n");
            e.printStackTrace(System.out);
        }
    }

    public void saveEmployeeDetails2(EmpProfile newEmployee) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(newEmployee.getName() + "," + newEmployee.getPassword() + "," + "Employee" + "," + "false"
                    + "," + "0" + "," + newEmployee.getNRIC() + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while saving employee details.\n");
            e.printStackTrace(System.out);
        }
    }
}
