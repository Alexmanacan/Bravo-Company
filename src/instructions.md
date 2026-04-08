# Java Payroll System Project - Week 3 Lab

## 1. Learning Objectives
At the end of the lesson(s), students must be able to:
* Write a program to illustrate OOP concepts in Java.

---

## 2. Project Overview
Develop a console-based Java Payroll program to compute and display the salary of an employee based on their specific type.

### Employee Types & Rates
* **Regular**: Monthly rate; includes leave benefits.
* **Probationary**: Monthly rate; includes leave benefits.
* **Contractual**: Monthly rate; no leave benefits.
* **Part-time**: Hourly rate; "no work, no pay".

### Work Schedule & Cut-off
* **Monday–Friday work week**: 8 hours per day (8 AM to 5 PM, 1-hour break).
* **Cut-off periods**: 1st–15th or 16th–30th of the month.

---

## 3. Program Requirements

### Input Data Needed
* Employee Number and Employee Name.
* Time Keeping (per work week): Must consider leaves for Regular employees.
* Loans (if applicable).

### Computations
* **Hours worked**: Including overtime, absences, and undertime.
* **Gross pay**.
* **Deductions**: Tax, SSS, PhilHealth, Pag-IBIG, Loans, and Undertime/Late.
* **Net pay**.

### Research Integration
Students must research and integrate the following tables into the logic:
* Holiday rates for overtime.
* Tax table.
* SSS table.
* Pag-IBIG and PhilHealth contributions.

---

## 4. Technical OOP Design
* **Classes**: At least 3 user-defined classes.
* **Encapsulation**: Attributes declared as `private`.
* **Methods**: Implement Constructors, Getters, and Setters.
* **Main Class**: Should only control the program flow.
* **Data Structures**: Use objects or
