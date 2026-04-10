package output;

import Computation.DeductionsCalculator;
import Personnel.Employee;
import Computation.GrossPayCalculator;
import Timekeeping.TimeKeepingSystem;

public class PayslipRenderer {
    private final Employee sal = new Employee();
    private final double basicSalary = sal.getBasicSalary();
    
    DeductionsCalculator dc = new DeductionsCalculator();
    GrossPayCalculator gpc = new GrossPayCalculator();

    public void Payslip() {
      gpc.processpayroll();
      gpc.processnetpay();
      
      String id = "123-456-789";
      String name = "Juan Dela Cruz";
      int num = 1237;
      System.out.println("╔═════ Bravo Company Salary Slip ═══════════════════════════════════════════════════╗");
      System.out.printf( "║ %-24s  %-56s║" , "" , "");
      System.out.printf("\n║ \u001B[32m%-24s:\u001B[0m %-56s║" , "Employee ID" , id);
      System.out.printf("\n║ \u001B[32m%-24s:\u001B[0m %-56s║" , "Employee Name" , name);
      System.out.printf("\n║ \u001B[32m%-24s:\u001B[0m %-56s║" , "Employee Type" , "Probationary");
      System.out.printf("\n║ \u001B[32m%-24s:\u001B[0m %-56s║" , "Cut-off Period", "Monthly Rate");
      System.out.printf("\n║ %-24s  %-56s║" , "", "");
      System.out.printf("\n║ \u001B[32m%-24s\u001B[0m  %-56s║" , "Timekeeping" , "");
      System.out.printf("\n║ %-19s %-14s %-18s %-28s║" , "" , "TIME IN" , "" , "TIME OUT");
      for (int i = 0; i <= 15; i++) {
          System.out.printf("\n%-19s %-14d %-18s %-28d" , "" , Day_IN, "" , Day_OUT);
      }

      System.out.printf("\n║ \u001B[32m%-24s\u001B[0m %-57s║" ,"Total Hours:", "");
      System.out.printf("\n║ %-4s \u001B[32m%-19s:\u001B[0m %.2f %-53s║" , "" , "Worked", TotalWorked , "hours");
      System.out.printf("\n║ %-4s \u001B[32m%-19s:\u001B[0m %.2f %-53s║" , "" , "Overtime" , totalOvertime ,"hours");
      System.out.printf("\n║ %-4s \u001B[32m%-19s:\u001B[0m %.2f %-53s║" , "" , "Undertime" , totalUndertime , "hours");
      System.out.printf("\n║ %-4s \u001B[32m%-19s:\u001B[0m %.2f %-53s║" , "" , "Late" , totalLate , "hours");
      System.out.printf("\n║ %-4s \u001B[32m%-19s:\u001B[0m %.2f %-53s║" , "" , "Absent" , totalAbsent , "hours");

      System.out.printf("\n║ %-24s  %-56s║" , "" , "");
      System.out.printf("\n║ \u001B[32m%-24s:\u001B[0m %-56s║" , "Basic Salary" , (basicSalary + " Pesos"));
      System.out.printf("\n║ \u001B[32m%-24s\u001B[0m %-57s║" , "Additional:" , "");
      System.out.printf("\n║ \u001B[32m%-4s %-19s:\u001B[0m %-56s║" , "" , "Overtime" , (dc.getOvertime() + " Pesos"));

      System.out.printf("\n║ %-24s  %-56s║" , "" , "");
      System.out.printf("\n║ \u001B[32m%-24s\u001B[0m %-57s║" , "Deductions:" , "");
      System.out.printf("\n║ %-4s \u001B[32m%-19s:\u001B[0m \u001B[31m-%-55s\u001B[0m║" , "" , "Undertime" , (dc.getUndertime() + " Pesos"));
      System.out.printf("\n║ %-4s \u001B[32m%-19s:\u001B[0m \u001B[31m-%-55s\u001B[0m║" , "" , "Late" , (dc.getLate()+ " Pesos"));
      System.out.printf("\n║ %-4s \u001B[32m%-19s:\u001B[0m \u001B[31m-%-55s\u001B[0m║" , "" , "Absences" , (dc.getAbsence() + " Pesos"));
      System.out.printf("\n║ %-4s \u001B[32m%-19s:\u001B[0m \u001B[31m-%-55s\u001B[0m║" , "" , "SSS" , (dc.getSSS() + " Pesos"));
      System.out.printf("\n║ %-4s \u001B[32m%-19s:\u001B[0m \u001B[31m-%-55s\u001B[0m║" , "" , "Pagibig" , (dc.getPagibig() + " Pesos"));
      System.out.printf("\n║ %-4s \u001B[32m%-19s:\u001B[0m \u001B[31m-%-55s\u001B[0m║" , "" , "Philhealth" , (dc.getPhilhealth() + " Pesos"));
      System.out.printf("\n║ %-4s \u001B[32m%-19s:\u001B[0m \u001B[31m-%-55s\u001B[0m║" , "" , "Loans" , (dc.getLoans() + " Pesos"));
      System.out.printf("\n║ %-4s \u001B[32m%-19s:\u001B[0m \u001B[31m-%-55s\u001B[0m║" , "" , "W/Tax" , (dc.getWTax() + " Pesos"));

      System.out.printf("\n║ %-24s  %-56s║" , "" , "");
      System.out.printf("\n║ \u001B[32m%-24s:\u001B[0m %-56s║" , "Netpay" , (gpc.getNetpay() + " Pesos"));
      System.out.printf("\n║ %-24s  %-56s║" , "" , "");
      System.out.printf("\n╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }

}