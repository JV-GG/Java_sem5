

import java.util.Scanner;

public class testing {
    public static void calculateAndPrintFinalSalary() {
        Scanner scanner = new Scanner(System.in);
        
        // need to read from zonghao salaryIncrement.txt instead of prompt
        while (true) {
            System.out.print("Enter the salary for calculations: RM");
            try {
                double salary = scanner.nextDouble();

                
                // Need change to auto read from,booon txt file instead of prompt
                System.out.print("Enter the number of hours worked: ");
                int hoursWorked = scanner.nextInt();
                double otRate = 8.0;
                double otPay = 0.0;

                
                if (hoursWorked > 160) {
                    int otHours = hoursWorked - 160;
                    otPay = otHours * otRate;
                    System.out.printf("OT Pay: RM%.2f%n", otPay);
                } else {
                    System.out.println("OT Pay: RM0.00");
                }


                // insert inside dropbox of number of day leave taken
                System.out.print("Enter the number of unpaid leave days: ");
                int unpaidLeaveDays = scanner.nextInt();
                double unpaidLeaveDeduction = 0.0;



                if (unpaidLeaveDays > 14) {
                    int OutstandingLeave = unpaidLeaveDays - 14;
                    unpaidLeaveDeduction = (salary / 20) * OutstandingLeave;
                    System.out.printf("Unpaid Leave Deduction: RM%.2f%n", unpaidLeaveDeduction);
                } else {
                    System.out.println("No Unpaid Leave Deduction");
                }
                
                
                // EPF 
                double epfRate = 0.11;
                double epfContribution = salary * epfRate;
                System.out.printf("Payable EPF: RM%.2f%n", epfContribution);
                
                // SOCSO 
                double socsoRate = 0.005;
                double socsoContribution = salary * socsoRate;
                System.out.printf("Payable SOCSO: RM%.2f%n", socsoContribution);

                // EIS
                double Eisrate = 0.002;
                double EisContribution = salary * Eisrate;
                System.out.printf("Payable EIS: RM%.2f%n", EisContribution);

                // PCB
                double PCB = 0.00416;
                double PCBContribution = salary * PCB;
                System.out.printf("Payable PCB: RM%.2f%n", PCBContribution);

                // Total Payable
                double totalpayable = epfContribution + socsoContribution + EisContribution + PCBContribution;
                System.out.printf("Total Payable: RM%.2f%n", totalpayable);

                // Gross Allowance
                double Allowance=300;
                System.out.printf("Gross Allowance: RM%.2f%n", Allowance);


                // Final Salary
                double NetSalary = salary - totalpayable + Allowance + otPay - unpaidLeaveDeduction;
                System.out.printf("Net Salary: RM%.2f%n", NetSalary);

                
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        
        scanner.close();
    }

    public static void main(String[] args) {
        calculateAndPrintFinalSalary();
    }
}


