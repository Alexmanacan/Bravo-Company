package Computation;

import Personnel.Employee;
import java.util.Scanner;

public class DeductionsCalculator {
    
    private final Employee sal = new Employee();
    private final double basicSalary = sal.getBasicSalary();
    
    private double philhealth_amount;
    private double overtime_amount;
    private double undertime_amount;
    private double late_amount;
    private double absence_amount;
    private double SSS_amount;
    private double pagibig_amount;
    private double loans_amount;
    private double W_tax_amount;

    Scanner sc = new Scanner(System.in);

    public void DeductionsCalculator() {
        
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
        this.philhealth_amount = (totalPremium / 2) / 2; 
    }
    
    public double getPhilhealth() {
        return this.philhealth_amount;
    }
    
    
        
    public void calculate_undertime(double hoursUndertime) {

    double DailyRate = this.basicSalary / 22;
    double hourlyRate = DailyRate / 8;

    this.undertime_amount = hoursUndertime * hourlyRate;
    }

    public double getUndertime() {
        return this.undertime_amount;
    }
    
    
    
    public void calculate_late(double totalLate) {

        double DailyRate = this.basicSalary / 22;
        double hourlyRate = DailyRate / 8;

        this.undertime_amount = totalLate * hourlyRate;
    }

    public double getLate() {
        return this.late_amount;
    }
    
    
    
    private double dailyRate;
    public void calculate_absence(int daysAbsent, int availableCredits) {
        if (daysAbsent <= availableCredits) {
            this.absence_amount = 0;
        } else {
            int unpaidDays = daysAbsent - availableCredits;
            this.absence_amount = unpaidDays * this.dailyRate;
        }
    }
    
    public double getAbsence() {
        return this.absence_amount;
    }
    
    
    
    public void calculate_SSS() {
        double salary = this.basicSalary;

        if (salary > 35000) {
            salary = 35000;
        }

        if (salary < 5000) {
            this.SSS_amount = 5000 * 0.05;
        } else {
            this.SSS_amount = salary * 0.05;
        }
    }
    
    public double getSSS() {
        return this.SSS_amount;
    }
    
    
    
    public void calculate_pagibig() {
        double salary = this.basicSalary;

        if (salary > 10000) {
            pagibig_amount = 200.00;
        } else {
            pagibig_amount = salary * 0.02; 
        }
    }
    
    public double getPagibig() {
        return this.pagibig_amount;
    }
  
    
    
    public void calculate_loans() {
        System.out.println("Loans: ");
        loans_amount = sc.nextDouble();
    }

    public double getLoans() {
        return this.loans_amount;
    }
    
    
    
    public void calculate_W_tax() {
        double taxableIncome = this.basicSalary - (this.SSS_amount + this.philhealth_amount + this.pagibig_amount);

        if (taxableIncome <= 20833) {
            this.W_tax_amount = 0;
        } 
        else if (taxableIncome <= 33332) {
            this.W_tax_amount = (taxableIncome - 20833) * 0.15;
        } 
        else if (taxableIncome <= 66666) {
            this.W_tax_amount = 1875 + (taxableIncome - 33333) * 0.20;
        } 
        else {
            this.W_tax_amount = 8541.67 + (taxableIncome - 66667) * 0.25;
        }
    }
    
    public double getWTax() {
        return this.W_tax_amount;
    }
}
