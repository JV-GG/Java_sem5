import java.util.Scanner;

public class EmpCheck {
    public static void main(String[] args) {
        ProfileManagement hrManager = new ProfileManagement();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        String empID;

        while (!exit) {
            System.out.println("Enter Employee ID:");
            empID = scanner.nextLine();
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
                System.err.println("\n");
            } else {
                System.out.println("Employee not found.");
                exit = true;
            }
        }
        scanner.close();
    }
}

