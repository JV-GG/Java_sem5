import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class hrManager {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        ProfileManagement hrManager = new ProfileManagement();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("HR Management System");
            System.out.println("1. Create Employee Profile");
            System.out.println("2. Retrieve Employee Profile");
            System.out.println("3. Update Employee Profile");
            System.out.println("4. Exit");
            System.out.println("Enter your choice:");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> createEmployeeProfile(scanner, hrManager);
                case 2 -> retrieveEmployeeProfile(scanner, hrManager);
                case 3 -> updateEmployeeProfile(scanner, hrManager);
                case 4 -> {
                    exit = true;
                    System.out.println("Exiting...");
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
        scanner.close();
    }

    private static void createEmployeeProfile(Scanner scanner, ProfileManagement hrManager) {
        System.out.println("\nCreating Employee Profile");
        System.out.println("Enter Employee ID:");
        String IDNo = scanner.nextLine();
        System.out.println("Enter Employee Password:");
        String Password = scanner.nextLine();
        System.out.println("Enter Employee Name:");
        String Name = scanner.nextLine();
        System.out.println("Enter Gender:");
        String Gender = scanner.nextLine();
        System.out.println("Enter Employee DOB (yyyy-mm-dd):");
        LocalDate DOB = parseDate(scanner.nextLine());
        System.out.println("Enter Employee Address:");
        String address = scanner.nextLine();
        System.out.println("Enter Employee Emergency Contact (601xxxxxxxx):");
        String emergencyContact = scanner.nextLine();
        System.out.println("Enter Employee Working Experience (comma-separated):");
        String experienceInput = scanner.nextLine();
        var experience = Arrays.asList(experienceInput.split(",\\s*"));
        System.out.println("Enter Employee Position:");
        String position = scanner.nextLine();
        System.out.println("Enter Employee Department:");
        String department = scanner.nextLine();
        System.out.println("Enter Employee Salary:");
        double salary = parseDouble(scanner.nextLine());

        EmpProfile newEmployee = new EmpProfile(IDNo, Password, Name, Gender, DOB, address, emergencyContact, experience, department, position, salary);
        hrManager.createEmployeeProfile(newEmployee);
    }

    private static void retrieveEmployeeProfile(Scanner scanner, ProfileManagement hrManager) {
        System.out.println("Enter Employee ID:");
        String empID = scanner.nextLine();
        EmpProfile emp = hrManager.retrieveEmployeeProfile(empID);

        if (emp != null) {
            System.out.println("\nEmployee Profile:");
            System.out.println("Employee ID: " + emp.getIDNo());
            System.out.println("Employee Password: " + emp.getPassword());
            System.out.println("Employee Name: " + emp.getName());
            System.out.println("Employee Gender: " + emp.getGender());
            System.out.println("Employee DOB: " + emp.getDOB());
            System.out.println("Employee age: " + emp.getAge());
            System.out.println("Employee Address: " + emp.getAddress());
            System.out.println("Employee Emergency Contact: " + emp.getEmergencyContact());
            System.out.println("Employee Working Experience: " + emp.getWorkingExperience());
            System.out.println("Employee Position: " + emp.getPosition());
            System.out.println("Employee Department: " + emp.getDepartment());
        } else {
            System.out.println("Employee not found.");
        }
    }

    private static void updateEmployeeProfile(Scanner scanner, ProfileManagement hrManager) {
        System.out.println("Enter Employee ID:");
        String empID = scanner.nextLine();
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
                emp.updateDOB(parseDate(dob));
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
                emp.updateWorkingExperience(Arrays.asList(experience.split(",\\s*")));
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

            System.out.println("Enter new salary (leave blank to keep current):");
            String salaryInput = scanner.nextLine();
            if (!salaryInput.isBlank()) {
                emp.updateSalary(parseDouble(salaryInput));
            }

            hrManager.updateEmployeeProfile(empID, emp);
            System.out.println("Employee profile updated successfully.\n");
        } else {
            System.out.println("Employee not found.");
        }
    }

    private static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, DATE_FORMAT);
        } catch (DateTimeException e) {
            System.out.println("Invalid date format. Please use yyyy-mm-dd.");
            return null;
        }
    }

    private static double parseDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary input. Defaulting to 0.0");
            return 0.0;
        }
    }
}
