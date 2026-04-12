package Computation;

import classes.Employee;
import java.util.Scanner;

public class DeductionsCalculator {
    
    private final Employee employee;
    private final double basicSalary;
    
    private double philhealth_amount;
    private double overtime_amount;
    private double undertime_amount;
    private double late_amount;
    private double absence_amount;
    private double SSS_amount;
    private double pagibig_amount;
    private double loans_amount;
    private double W_tax_amount;

    public DeductionsCalculator(Employee employee) {
        this.employee = employee;
        this.basicSalary = employee.getBasicSalary();
    }
    
    public void calculate_overtime(double otHours) {
        double dailyRate = this.basicSalary / 22;
        double hourlyRate = dailyRate / 8;
        double otRate = hourlyRate * 1.25;
        this.overtime_amount = otHours * otRate;
    }

    public double getOvertime() {
        return this.overtime_amount;
    }

    public void calculate_philhealth() {
        double salary = this.basicSalary;
        double totalPremium;

        if (salary <= 10000) {
            totalPremium = 500.00;
        } else if (salary >= 100000) {
            totalPremium = 5000.00;
        } else {
            totalPremium = salary * 0.05;
        }
        // Split across two cut-offs
        this.philhealth_amount = (totalPremium / 2); 
    }
    
    public double getPhilhealth() {
        return this.philhealth_amount;
    }

    public void calculate_undertime(double hoursUndertime) {
        double dailyRate = this.basicSalary / 22;
        double hourlyRate = dailyRate / 8;
        this.undertime_amount = hoursUndertime * hourlyRate;
    }

    public double getUndertime() {
        return this.undertime_amount;
    }

    public void calculate_late(double totalLate) {
        double dailyRate = this.basicSalary / 22;
        double hourlyRate = dailyRate / 8;
        this.late_amount = totalLate * hourlyRate;
    }

    public double getLate() {
        return this.late_amount;
    }

    public void calculate_absence(double daysAbsent) {
        double dailyRate = this.basicSalary / 22;
        this.absence_amount = daysAbsent * dailyRate;
    }
    
    public double getAbsence() {
        return this.absence_amount;
    }

    public void calculate_SSS() {
        double salary = this.basicSalary;
        if (salary > 35000) salary = 35000;
        double totalSSS = (salary < 5000) ? 5000 * 0.045 : salary * 0.045;
        // Split across two cut-offs
        this.SSS_amount = totalSSS / 2;
    }
    
    public double getSSS() {
        return this.SSS_amount;
    }

    public void calculate_pagibig() {
        double salary = this.basicSalary;
        double totalPagibig = (salary > 10000) ? 200.00 : salary * 0.02; 
        // Split across two cut-offs
        this.pagibig_amount = totalPagibig / 2;
    }
    
    public double getPagibig() {
        return this.pagibig_amount;
    }

    public void setLoans(double amount) {
        this.loans_amount = amount;
    }

    public double getLoans() {
        return this.loans_amount;
    }

    public void calculate_W_tax() {
        // Taxable income for half month
        double semiMonthlySalary = this.basicSalary / 2;
        double taxableIncome = semiMonthlySalary - (this.SSS_amount + this.philhealth_amount + this.pagibig_amount);

        if (taxableIncome <= 10417) {
            this.W_tax_amount = 0;
        } 
        else if (taxableIncome <= 16666) {
            this.W_tax_amount = (taxableIncome - 10417) * 0.15;
        } 
        else if (taxableIncome <= 33332) {
            this.W_tax_amount = 937.50 + (taxableIncome - 16667) * 0.20;
        } 
        else {
            this.W_tax_amount = 4270.83 + (taxableIncome - 33333) * 0.25;
        }
    }
    
    public double getWTax() {
        return this.W_tax_amount;
    }
}
