// Purpose: Contains the EmpProfile class which stores the employee's profile information.
// The class includes methods to update the employee's profile information and calculate the employee's age.
// The class also includes a constructor and getters and setters for the class attributes.

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmpProfile {
    private String NRIC;
    private String Password;
    private String BankAcc;
    private String Name;
    private String Gender;
    private LocalDate DOB;
    private String address;
    private String emergencyContact;
    private List<String> workingExperience;
    private String position;
    private String department;
    private double salary; 
    // private List<SalaryHistory> salaryHistory;
    // private LeaveEntitlement leaveEntitlement;
    // private double monthlyGrossSalary;

    // Constructor, getters, setters, etc.
    public EmpProfile(){
        this.NRIC = "";
        this.Password = "";
        this.BankAcc = "";
        this.Name = "";
        this.Gender = "";
        this.DOB = LocalDate.now();
        this.address = "";
        this.emergencyContact = "";
        this.workingExperience = new ArrayList<>();
        this.position = "";
        this.department = "";
        this.salary = 0.0;
        // this.salaryHistory = new ArrayList<SalaryHistory>();
        // this.leaveEntitlement = new LeaveEntitlement();
        // this.monthlyGrossSalary = 0.0;
    }

    public EmpProfile(String NRIC, String Password, String BankAcc, String Name, String Gender, LocalDate DOB, String address, String emergencyContact, List<String> workingExperience, String department, String position, double salary) {
        this.NRIC = NRIC;
        this.Password = Password;
        this.BankAcc = BankAcc;
        this.Name = Name;
        this.Gender = Gender;
        this.DOB = DOB;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.workingExperience = workingExperience;
        this.department = department;
        this.position = position;
        this.salary = salary;
    }

    public String getNRIC() {
        return NRIC;
    }

    public String getPassword() {
        return Password;
    }

    public String getBankAcc() {
        return BankAcc;
    }

    public String getName() {
        return Name;
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

    public double getSalary() {
        return salary;
    }
    
    public void updateNRIC(String NRIC) {
        this.NRIC = NRIC;
    }

    public void updatePassword(String Password) {
        this.Password = Password;
    }

    public void updateBankAcc(String BankAcc) {
        this.BankAcc = BankAcc;
    }

    public void updateName(String Name) {
        this.Name = Name;
    }

    public void updateGender(String Gender){
        this.Gender = Gender;
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

    public void updateSalary(double salary) {
        this.salary = salary;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpProfile that = (EmpProfile) o;
        return NRIC.equals(that.NRIC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(NRIC);
    }
}
