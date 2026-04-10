package Computation;
        
import Personnel.Employee;

public class GrossPayCalculator {
    private final Employee sal = new Employee();
    private final double basicSalary = sal.getBasicSalary();
    private double final_netpay;
     
    DeductionsCalculator dc = new DeductionsCalculator();
    
    public void GrosspayCalculator() {
        
    }
    
    public void processpayroll() {
        dc.calculate_philhealth();
        dc.calculate_overtime(totalOvertime);
        dc.calculate_undertime(totalUndertime);
        dc.calculate_late(totalLate);
        dc.calculate_calculate_absence(totalAbsent);
        dc.calculate_SSS();
        dc.calculate_pagibig();
        dc.calculate_loans();
        dc.calculate_W_tax();
    }
    
    public void processnetpay() {
        final_netpay = (basicSalary + dc.getOvertime()) - (dc.getPhilhealth() + dc.getUndertime() + dc.getLate() + dc.getAbsence() + dc.getSSS() + dc.getPagibig() + dc.getLoans() + dc.getWTax());
    }
    
    public double getNetpay() {
        return this.final_netpay;
    }
}