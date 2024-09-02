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
                    employee.updateIDNo(line.substring(4));
                } else if (employee != null) {
                    if (line.startsWith("Password: ")) {
                        employee.updatePassword(line.substring(10));
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
                        List<String> experience = List.of(line.substring(20).split(", "));
                        employee.updateWorkingExperience(experience);
                    } else if (line.startsWith("Position: ")) {
                        employee.updatePosition(line.substring(10));
                    } else if (line.startsWith("Department: ")) {
                        employee.updateDepartment(line.substring(12));
                    } else if (line.startsWith("Salary: ")) {
                        employee.updateSalary(Double.parseDouble(line.substring(8)));
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

    public void createEmployeeProfile(EmpProfile employee) {
        if (isDuplicateID(employee.getIDNo())) {
            System.out.println("An employee with this ID already exists in the file. Cannot create duplicate profiles.\n");
            return;
        }
    
        // Directly write the new employee details to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ProfileManagement/employee_profiles.txt", true))) {
            writer.write("ID: " + employee.getIDNo());
            writer.write("\nPassword: " + employee.getPassword());
            writer.write("\nName: " + employee.getName());
            writer.write("\nGender: " + employee.getGender());
            writer.write("\nDOB: " + employee.getDOB());
            writer.write("\nAge: " + employee.getAge());
            writer.write("\nAddress: " + employee.getAddress());
            writer.write("\nEmergency Contact: " + employee.getEmergencyContact());
            writer.write("\nWorking Experience: " + String.join(", ", employee.getWorkingExperience()));
            writer.write("\nPosition: " + employee.getPosition());
            writer.write("\nDepartment: " + employee.getDepartment());
            writer.write("\nSalary: " + employee.getSalary());
            writer.write("\n\n");
            System.out.println("Employee profile created successfully.\n");
        } catch (IOException e) {
            System.out.println("An error occurred while saving employee details.");
            e.printStackTrace(System.out);
        }
    }

    // Method to check for duplicate names in the text file
    private boolean isDuplicateID(String IDNo) {
        try (BufferedReader reader = new BufferedReader(new FileReader("ProfileManagement/employee_profiles.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: ") && line.substring(4).equalsIgnoreCase(IDNo)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while checking for duplicate ID.");
            e.printStackTrace(System.out);
        }
        return false;
    }

    public EmpProfile retrieveEmployeeProfile(String empID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("ProfileManagement/employee_profiles.txt"))) {
            String line;
            EmpProfile employee = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: ") && line.substring(4).equalsIgnoreCase(empID)) {
                    employee = new EmpProfile();
                    employee.updateIDNo(empID);

                    // Read the rest of the employee details
                    while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                        if (line.startsWith("Password: ")) {
                            employee.updatePassword(line.substring(10));
                        }else if (line.startsWith("Name: ")) {
                            employee.updateName(line.substring(6));
                        }else if (line.startsWith("Gender: ")) {
                            employee.updateGender(line.substring(8));
                        }else if (line.startsWith("DOB: ")) {
                            employee.updateDOB(LocalDate.parse(line.substring(5)));
                        } else if (line.startsWith("Address: ")) {
                            employee.updateAddress(line.substring(9));
                        } else if (line.startsWith("Emergency Contact: ")) {
                            employee.updateEmergencyContact(line.substring(19));
                        } else if (line.startsWith("Working Experience: ")) {
                            List<String> experience = List.of(line.substring(20).split(", "));
                            employee.updateWorkingExperience(experience);
                        } else if (line.startsWith("Position: ")) {
                            employee.updatePosition(line.substring(10));
                        } else if (line.startsWith("Department: ")) {
                            employee.updateDepartment(line.substring(12));
                        } else if (line.startsWith("Salary: ")) {
                            employee.updateSalary(Double.parseDouble(line.substring(8)));
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
        EmpProfile existingEmployee = retrieveEmployeeProfile(employeeId);
        
        if (existingEmployee != null) {
            // Check and log salary changes
            if (existingEmployee.getSalary() != updatedEmployee.getSalary()) {
                logSalaryChange(existingEmployee, updatedEmployee.getSalary());
            }
        
            // Check and log position changes
            if (!existingEmployee.getPosition().equals(updatedEmployee.getPosition())) {
                logPositionChange(existingEmployee, updatedEmployee.getPosition());
            }
        
            // Save the updated employee list
            saveEmployeeDetails();
            System.out.println("Employee profile updated successfully.\n");
        } else {
            System.out.println("Employee not found.");
        }
    }    
    
    private void logSalaryChange(EmpProfile employee, double newSalary) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("SalaryIncrement.txt", true))) {
            writer.write("Date and Time: " + LocalDate.now() + " " + java.time.LocalTime.now());
            writer.write("\nICNo: " + employee.getIDNo());
            writer.write("\nName: " + employee.getName());
            writer.write("\nOriginal Salary: " + employee.getSalary());
            writer.write("\nNew Salary: " + newSalary);
            writer.write("\n\n");
        } catch (IOException e) {
            System.out.println("An error occurred while logging salary changes.");
            e.printStackTrace(System.out);
        }
    }
    
    private void logPositionChange(EmpProfile employee, String newPosition) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("PositionPromote.txt", true))) {
            writer.write("Date and Time: " + LocalDate.now() + " " + java.time.LocalTime.now());
            writer.write("\nICNo: " + employee.getIDNo());
            writer.write("\nName: " + employee.getName());
            writer.write("\nOriginal Position: " + employee.getPosition());
            writer.write("\nNew Position: " + newPosition);
            writer.write("\n\n");
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
                writer.write("ID: " + emp.getIDNo());
                writer.write("\nPassword: " + emp.getPassword());
                writer.write("\nName: " + emp.getName());
                writer.write("\nGender: " + emp.getGender());
                writer.write("\nDOB: " + emp.getDOB());
                writer.write("\nAge: " + emp.getAge());
                writer.write("\nAddress: " + emp.getAddress());
                writer.write("\nEmergency Contact: " + emp.getEmergencyContact());
                writer.write("\nWorking Experience: " + String.join(", ", emp.getWorkingExperience()));
                writer.write("\nPosition: " + emp.getPosition());
                writer.write("\nDepartment: " + emp.getDepartment());
                writer.write("\nSalary: " + emp.getSalary());
                writer.write("\n\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving employee details.");
            e.printStackTrace(System.out);
        }
    }
}
