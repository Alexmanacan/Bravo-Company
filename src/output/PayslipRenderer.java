package output;

import Computation.DeductionsCalculator;
import classes.Employee;
import Computation.GrossPayCalculator;
import java.util.ArrayList;
import java.util.List;

public class PayslipRenderer {

    public static List<String> getPayslipLines(Employee employee, int cutoff) {
        List<String> lines = new ArrayList<>();
        GrossPayCalculator gpc = new GrossPayCalculator(employee);
        gpc.setCutoff(cutoff);
        gpc.processpayroll();
        gpc.processnetpay();
        DeductionsCalculator dc = gpc.getDeductionsCalculator();

        lines.add(String.format("%-24s: %s", "Employee ID", employee.getId()));
        lines.add(String.format("%-24s: %s", "Employee Name", employee.getName()));
        lines.add(String.format("%-24s: %s", "Cut-off Period", cutoff == 1 ? "Days 1-15" : "Days 16-31"));
        lines.add("");
        lines.add("--- Timekeeping Summary ---");
        lines.add(String.format("%-24s: %.2f hours", "Total Worked", gpc.getTotalWorked()));
        lines.add(String.format("%-24s: %.2f hours", "Overtime", gpc.getTotalOvertime()));
        lines.add(String.format("%-24s: %.2f hours", "Late", gpc.getTotalLate()));
        lines.add(String.format("%-24s: %d days", "Absent", gpc.getTotalAbsent()));
        lines.add("");
        lines.add(String.format("%-24s: %.2f Pesos", "Basic Pay (Period)", gpc.getBasicPayForPeriod()));
        lines.add(String.format("%-24s: %.2f Pesos", "Overtime Pay", dc.getOvertime()));
        lines.add("");
        lines.add("--- Deductions ---");
        lines.add(String.format("%-24s: -%.2f Pesos", "SSS", dc.getSSS()));
        lines.add(String.format("%-24s: -%.2f Pesos", "Philhealth", dc.getPhilhealth()));
        lines.add(String.format("%-24s: -%.2f Pesos", "Pag-IBIG", dc.getPagibig()));
        lines.add(String.format("%-24s: -%.2f Pesos", "W/Tax", dc.getWTax()));
        lines.add(String.format("%-24s: -%.2f Pesos", "Late/UT/Absence", dc.getLate() + dc.getUndertime() + dc.getAbsence()));
        lines.add("");
        lines.add(String.format("%-24s: %.2f Pesos", "Net Pay", gpc.getNetpay()));

        return lines;
    }

    public static void render(Employee employee, int cutoff) {
        GrossPayCalculator gpc = new GrossPayCalculator(employee);
        gpc.setCutoff(cutoff);
        gpc.processpayroll();
        gpc.processnetpay();
        DeductionsCalculator dc = gpc.getDeductionsCalculator();

        String green = "\u001B[32m";
        String red = "\u001B[31m";
        String cyan = "\u001B[36m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        String bold = "\u001B[1m";

        System.out.println(cyan + "╔═══════════════════════════════════════════════════════════════════════════════════╗" + reset);
        System.out.println(cyan + "║" + reset + bold + yellow + "                          BRAVO COMPANY - OFFICIAL PAYSLIP                         " + reset + cyan + "║" + reset);
        System.out.println(cyan + "╠═══════════════════════════════════════════════════════════════════════════════════╣" + reset);
        
        System.out.printf(cyan + "║" + reset + " %-20s: %-58s " + cyan + "║\n" + reset, "Employee ID", employee.getId());
        System.out.printf(cyan + "║" + reset + " %-20s: %-58s " + cyan + "║\n" + reset, "Employee Name", employee.getName());
        System.out.printf(cyan + "║" + reset + " %-20s: %-58s " + cyan + "║\n" + reset, "Cut-off Period", cutoff == 1 ? "Days 1-15 (Paid on 16th)" : "Days 16-31 (Paid on 26th)");
        
        System.out.println(cyan + "╟───────────────────────────────────────────────────────────────────────────────────╢" + reset);
        System.out.println(cyan + "║" + reset + bold + " EARNINGS                                                                          " + cyan + "║" + reset);
        System.out.printf(cyan + "║" + reset + "   %-40s %15.2f Pesos %-18s " + cyan + "║\n" + reset, "Basic Pay (Cut-off)", gpc.getBasicPayForPeriod(), "");
        System.out.printf(cyan + "║" + reset + "   %-40s " + green + "%15.2f" + reset + " Pesos %-18s " + cyan + "║\n" + reset, "Overtime Pay (" + String.format("%.1f", gpc.getTotalOvertime()) + " hrs)", dc.getOvertime(), "");
        
        System.out.println(cyan + "╟───────────────────────────────────────────────────────────────────────────────────╢" + reset);
        System.out.println(cyan + "║" + reset + bold + " DEDUCTIONS                                                                        " + cyan + "║" + reset);
        System.out.printf(cyan + "║" + reset + "   %-40s " + red + "%15.2f" + reset + " Pesos %-18s " + cyan + "║\n" + reset, "Lates/Undertime", (dc.getLate() + dc.getUndertime()), "");
        System.out.printf(cyan + "║" + reset + "   %-40s " + red + "%15.2f" + reset + " Pesos %-18s " + cyan + "║\n" + reset, "Absences (" + gpc.getTotalAbsent() + " days)", dc.getAbsence(), "");
        System.out.printf(cyan + "║" + reset + "   %-40s " + red + "%15.2f" + reset + " Pesos %-18s " + cyan + "║\n" + reset, "SSS Contribution", dc.getSSS(), "");
        System.out.printf(cyan + "║" + reset + "   %-40s " + red + "%15.2f" + reset + " Pesos %-18s " + cyan + "║\n" + reset, "PhilHealth Contribution", dc.getPhilhealth(), "");
        System.out.printf(cyan + "║" + reset + "   %-40s " + red + "%15.2f" + reset + " Pesos %-18s " + cyan + "║\n" + reset, "Pag-IBIG Contribution", dc.getPagibig(), "");
        System.out.printf(cyan + "║" + reset + "   %-40s " + red + "%15.2f" + reset + " Pesos %-18s " + cyan + "║\n" + reset, "Withholding Tax", dc.getWTax(), "");
        
        System.out.println(cyan + "╠═══════════════════════════════════════════════════════════════════════════════════╣" + reset);
        double totalDeductions = dc.getLate() + dc.getUndertime() + dc.getAbsence() + dc.getSSS() + dc.getPhilhealth() + dc.getPagibig() + dc.getWTax() + dc.getLoans();
        System.out.printf(cyan + "║" + reset + bold + " %-42s %15.2f Pesos %-18s " + cyan + "║\n" + reset, "TOTAL DEDUCTIONS", totalDeductions, "");
        System.out.printf(cyan + "║" + reset + bold + yellow + " %-42s " + green + "%15.2f" + yellow + " Pesos %-18s " + cyan + "║\n" + reset, "NET PAY", gpc.getNetpay(), "");
        System.out.println(cyan + "╚═══════════════════════════════════════════════════════════════════════════════════╝" + reset);
    }
}