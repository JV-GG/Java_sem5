import java.util.Scanner;

public class EmpCheck {
    public static void checkEmployee() {
        ProfileManagement hrManager = new ProfileManagement();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        String empID;

        while (!exit) {
            System.out.println("Enter Employee ID (Enter 'exit' to exit):");
            empID = scanner.nextLine();

            if (!empID.toLowerCase().equalsIgnoreCase("exit")){
                EmpProfile emp = hrManager.retrieveEmployeeProfile(empID);

                if (emp != null) {
                    System.out.println("\nEmployee Profile:");
                    System.out.println("Employee NRIC/Passport: " + emp.getNRIC());
                    System.out.println("Employee Password: " + emp.getPassword());
                    System.out.println("Employee Bank Account: " + emp.getBankAcc());
                    System.out.println("Employee Name: " + emp.getName());
                    System.out.println("Employee Gender: " + emp.getGender());
                    System.out.println("Employee DOB: " + emp.getDOB());
                    System.out.println("Employee age: " + emp.getAge());
                    System.out.println("Employee Address: " + emp.getAddress());
                    System.out.println("Employee Emergency Contact: " + emp.getEmergencyContact());
                    System.out.println("Employee Working Experience: " + emp.getWorkingExperience());
                    System.out.println("Employee Position: " + emp.getPosition());
                    System.out.println("Employee Department: " + emp.getDepartment());
                    System.out.println("Employee Salary (RM): " + emp.getSalary());
                    System.err.println("\n");
                } else {
                    System.out.println("Employee not found.");
                }
            } else {
                exit = true;
                System.err.println("Exiting...");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        EmpCheck.checkEmployee();
    }
}

