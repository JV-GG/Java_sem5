// Purpose: Contains the EmpProfile class which stores the employee's profile information.
// The class includes methods to update the employee's profile information and calculate the employee's age.
// The class also includes a constructor and getters and setters for the class attributes.

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class EmpProfile {
    private String name;
    private String IDNo;
    private final String Gender;
    private LocalDate DOB;
    private String address;
    private String emergencyContact;
    private List<String> workingExperience;
    private String position;
    private String department;
    // private List<SalaryHistory> salaryHistory;
    // private LeaveEntitlement leaveEntitlement;
    // private double monthlyGrossSalary;

    // Constructor, getters, setters, etc.
    public EmpProfile(){
        this.name = "";
        this.IDNo = "";
        this.Gender = "";
        this.DOB = LocalDate.now();
        this.address = "";
        this.emergencyContact = "";
        this.workingExperience = new ArrayList<>();
        this.position = "";
        this.department = "";
        // this.salaryHistory = new ArrayList<SalaryHistory>();
        // this.leaveEntitlement = new LeaveEntitlement();
        // this.monthlyGrossSalary = 0.0;
    }

    public EmpProfile(String name, String IDNo, String Gender, LocalDate DOB, String address, String emergencyContact, List<String> workingExperience, String department, String position) {
        this.name = name;
        this.IDNo = IDNo;
        this.Gender = Gender;
        this.DOB = DOB;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.workingExperience = workingExperience;
        this.department = department;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getIDNo() {
        return IDNo;
    }

    public String getGender() {
        return Gender;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public String getAddress() {
        return address;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public List<String> getWorkingExperience() {
        return workingExperience;
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }

    public void updateName(String name) {
        this.name = name;
    }
    
    public void updateIDNo(String IDNo) {
        this.IDNo = IDNo;
    }
    
    public void updateDOB(LocalDate DOB) {
        this.DOB = DOB;
    }
    
    public void updateAddress(String address) {
        this.address = address;
    }
    
    public void updateEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    
    public void updateWorkingExperience(List<String> workingExperience) {
        this.workingExperience = workingExperience;
    }
    
    public void updatePosition(String position) {
        this.position = position;
    }
    
    public void updateDepartment(String department) {
        this.department = department;
    }

    // Method to calculate and return age
    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        if (DOB != null) {
            return Period.between(DOB, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
