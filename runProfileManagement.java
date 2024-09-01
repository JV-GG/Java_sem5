// Purpose: Main class to run the ProfileManagement system.
// The user can create, retrieve, and update employee profiles.
// The user can also exit the system.
// The user can enter the employee's name, DOB, address, emergency contact, working experience, position, and department.

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

public class runProfileManagement {
    public static void main(String[] args) {
        ProfileManagement hrManager = new ProfileManagement();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        String empID;

        while (!exit) {
            System.out.println("HR Management System");
            System.out.println("1. Create Employee Profile");
            System.out.println("2. Retrieve Employee Profile");
            System.out.println("3. Update Employee Profile");
            System.out.println("4. Exit");
            System.out.println("Enter your choice:");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("\nCreating Employee Profile");
                    System.out.println("Enter Employee Name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter Employee ID:");
                    String IDNo = scanner.nextLine();
                    System.out.println("Enter Gender:");
                    String Gender = scanner.nextLine();
                    System.out.println("Enter Employee DOB (yyyy-mm-dd):");
                    LocalDate DOB = LocalDate.parse(scanner.nextLine());
                    System.out.println("Enter Employee Address:");
                    String address = scanner.nextLine();
                    System.out.println("Enter Employee Emergency Contact (601xxxxxxxx):");
                    String emergencyContact = scanner.nextLine();
                    System.out.println("Enter Employee Working Experience (comma-separated):");
                    String experienceInput = scanner.nextLine();
                    var experience = Arrays.asList(experienceInput.split(", "));
                    System.out.println("Enter Employee Position:");
                    String position = scanner.nextLine();
                    System.out.println("Enter Employee Department:");
                    String department = scanner.nextLine();

                    EmpProfile newEmployee = new EmpProfile(name, IDNo, Gender, DOB, address, emergencyContact, experience, department, position);
                    hrManager.createEmployeeProfile(newEmployee);
                }
                case 2 -> {
                    System.out.println("Enter Employee ID:");
                    empID = scanner.nextLine();
                    EmpProfile emp = hrManager.retrieveEmployeeProfile(empID);

                    if (emp != null) {
                        System.out.println("\nEmployee Profile:");
                        System.out.println("Employee Name: " + emp.getName());
                        System.out.println("Employee ID: " + emp.getIDNo());
                        System.out.println("Employee Gender: " + emp.getGender());
                        System.out.println("Employee DOB: " + emp.getDOB());
                        System.out.println("Employee age: " + emp.getAge());
                        System.out.println("Employee Address: " + emp.getAddress());
                        System.out.println("Employee Emergency Contact: " + emp.getEmergencyContact());
                        System.out.println("Employee Working Experience: " + emp.getWorkingExperience());
                        System.out.println("Employee Position: " + emp.getPosition());
                        System.out.println("Employee Department: " + emp.getDepartment());
                        System.err.println("\n");
                    } else {
                        System.out.println("Employee not found.");
                    }
                }
                case 3 -> {
                    System.out.println("Enter Employee ID:");
                    empID = scanner.nextLine();
                    EmpProfile emp = hrManager.retrieveEmployeeProfile(empID);
                
                    if (emp != null) {
                        System.out.println("Enter new name (leave blank to keep current):");
                        String name = scanner.nextLine();
                        if (!name.isBlank()) {
                            emp.updateName(name);
                        }
                
                        System.out.println("Enter new DOB (yyyy-mm-dd) (leave blank to keep current):");
                        String dob = scanner.nextLine();
                        if (!dob.isBlank()) {
                            emp.updateDOB(LocalDate.parse(dob));
                        }
                
                        System.out.println("Enter new address (leave blank to keep current):");
                        String address = scanner.nextLine();
                        if (!address.isBlank()) {
                            emp.updateAddress(address);
                        }
                
                        System.out.println("Enter new emergency contact (leave blank to keep current):");
                        String emergencyContact = scanner.nextLine();
                        if (!emergencyContact.isBlank()) {
                            emp.updateEmergencyContact(emergencyContact);
                        }
                
                        System.out.println("Enter new working experience (comma-separated, leave blank to keep current):");
                        String experience = scanner.nextLine();
                        if (!experience.isBlank()) {
                            emp.updateWorkingExperience(Arrays.asList(experience.split(",")));
                        }
                
                        System.out.println("Enter new position (leave blank to keep current):");
                        String position = scanner.nextLine();
                        if (!position.isBlank()) {
                            emp.updatePosition(position);
                        }
                
                        System.out.println("Enter new department (leave blank to keep current):");
                        String department = scanner.nextLine();
                        if (!department.isBlank()) {
                            emp.updateDepartment(department);
                        }
                
                        hrManager.updateEmployeeProfile(empID, emp);
                        System.out.println("Employee profile updated successfully.\n");
                    } else {
                        System.out.println("Employee not found.");
                    }
                }                
                case 4 -> {
                    exit = true;
                    System.out.println("Exiting...");
                }
                default -> {
                    System.out.println("Invalid choice, please try again.");
                }
            }
        }
        scanner.close();
    }
}
