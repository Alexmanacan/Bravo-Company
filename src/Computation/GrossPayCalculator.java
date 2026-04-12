package Computation;
        
import classes.Employee;
import classes.PartTimeEmployee;
import timekeeping.DailyRecord;
import java.util.ArrayList;

public class GrossPayCalculator {
    private final Employee employee;
    private final DeductionsCalculator dc;
    private double final_netpay;
    private int cutoff = 1; // 1: Days 1-15, 2: Days 16-31
    
    private double totalOvertime = 0;
    private double totalUndertime = 0;
    private double totalLate = 0;
    private int totalAbsent = 0;
    private double totalWorked = 0;
     
    public GrossPayCalculator(Employee employee) {
        this.employee = employee;
        this.dc = new DeductionsCalculator(employee);
    }

    public void setCutoff(int cutoff) {
        this.cutoff = cutoff;
    }
    
    public void processpayroll() {
        totalOvertime = 0;
        totalUndertime = 0;
        totalLate = 0;
        totalAbsent = 0;
        totalWorked = 0;

        int startDay = (cutoff == 1) ? 1 : 16;
        int endDay = (cutoff == 1) ? 15 : 31;

        for (DailyRecord record : employee.getTimeRecords()) {
            if (record.day >= startDay && record.day <= endDay) {
                totalOvertime += record.getOvertime();
                totalUndertime += record.getUndertime();
                totalLate += record.getLate();
                totalWorked += record.getWorkedHours();
                if (record.isAbsent()) {
                    totalAbsent++;
                }
            }
        }

        dc.calculate_philhealth();
        dc.calculate_overtime(totalOvertime);
        dc.calculate_undertime(totalUndertime);
        dc.calculate_late(totalLate);
        dc.calculate_absence(totalAbsent);
        dc.calculate_SSS();
        dc.calculate_pagibig();
        dc.calculate_W_tax();
    }
    
    public void processnetpay() {
        double basicPayForPeriod;
        if (employee instanceof PartTimeEmployee pte) {
            basicPayForPeriod = totalWorked * pte.getHourlyRate();
        } else {
            // Half-month salary for regular/probationary/contractual
            basicPayForPeriod = employee.getBasicSalary() / 2;
        }

        final_netpay = (basicPayForPeriod + dc.getOvertime()) - 
                       (dc.getPhilhealth() + dc.getUndertime() + dc.getLate() + 
                        dc.getAbsence() + dc.getSSS() + dc.getPagibig() + 
                        dc.getLoans() + dc.getWTax());
    }
    
    public double getNetpay() {
        return this.final_netpay;
    }

    public double getBasicPayForPeriod() {
        if (employee instanceof PartTimeEmployee pte) {
            return totalWorked * pte.getHourlyRate();
        }
        return employee.getBasicSalary() / 2;
    }

    public DeductionsCalculator getDeductionsCalculator() {
        return dc;
    }

    public double getTotalOvertime() { return totalOvertime; }
    public double getTotalUndertime() { return totalUndertime; }
    public double getTotalLate() { return totalLate; }
    public int getTotalAbsent() { return totalAbsent; }
    public double getTotalWorked() { return totalWorked; }
    public int getCutoff() { return cutoff; }
}
