# Group ? — Java Payroll System

***

## Project Overview

A TUI Java Payroll System that computes and displays employee salaries based on employee type. The program demonstrates OOP principles including encapsulation, class hierarchy, constructors, and getters/setters.

***

## Team Members

| Name | Role | GitHub Username |
|------|------|----------------|
| Paquito | Project Manager | @Alexmanacan |
| Chris | Lead Developer | Unawakened |
| <!-- Name --> | Developer | <!-- @username --> |

***

## Repository & Project Management

- **GitHub Repository:** https://github.com/Alexmanacan/Bravo-Company
- **Jira Board:** https://minoquiiiii.atlassian.net/jira/software/projects/SCRUM/boards/1

***

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java |
| Interface | Console TUI (ANSI escape codes) with the help of Gemini Pro 3.1 |
| Build | Javac / Neovim|
| Version Control | Git + GitHub |

***

## Project Structure

```
payroll-system/
├── src/
│   ├── Main.java                  # Entry point only — no logic
│   ├── PayrollSystem.java         # TUI controller + main menu loop
│   ├── TUITheme.java              # ANSI color helpers, box-drawing
│   ├── model/
│   │   ├── Employee.java          # Abstract base class
│   │   ├── RegularEmployee.java   # Monthly rate + leave benefits
│   │   ├── ProbationaryEmployee.java
│   │   ├── ContractualEmployee.java
│   │   └── PartTimeEmployee.java  # Hourly rate, no work no pay
│   ├── timekeeping/
│   │   └── Timekeeping.java       # Time-in/out input, hours computation
│   ├── computation/
│   │   ├── GrossPayCalculator.java
│   │   └── DeductionsCalculator.java
│   └── output/
│       └── PayslipRenderer.java   # CLI box-drawn payslip output
└── README.md
```

***

## Employee Types

| Type | Rate Basis | Leave Benefits | Cut-off |
|------|-----------|----------------|---------|
| Regular | Monthly | ✔ Yes | 1st–15th / 16th–30th |
| Probationary | Monthly | ✔ Yes | 1st–15th / 16th–30th |
| Contractual | Monthly | ✘ No | 1st–15th / 16th–30th |
| Part-Time | Hourly | ✘ No (no work, no pay) | Weekly |

***

## Payroll Computation

### Gross Pay
- Basic salary (monthly rate or hourly × hours worked)
- Overtime pay (holiday rate research required)

### Deductions
- Withholding Tax (per BIR tax table)
- SSS contribution (per SSS table)
- PhilHealth contribution
- Pag-IBIG contribution
- Loans (if any)
- Undertime / Absence deductions

### Net Pay
```
Net Pay = Gross Pay − Total Deductions
```

***

## Work Schedule

- **Work days:** Monday–Friday
- **Work hours:** 8:00 AM – 5:00 PM (8 hrs/day, 1 hr break)
- **Overtime:** Hours beyond 5:00 PM
- **Undertime:** Early departure / late arrival
- **Absences:** Full-day no-shows (leave credits applied for Regular/Probationary)

***

## OOP Structure Requirements

- [x] At least 3 user-defined classes
- [x] All attributes declared as `private`
- [x] Constructors implemented for all classes
- [x] Getters and setters used throughout
- [x] `Main.java` controls program flow only — no business logic
- [x] Objects or `ArrayList` used (no static variables only)

***

## Program Requirements Checklist

### Program Requirements
- [x] Console-based (no GUI)
- [x] No file I/O used
- [x] Menu-driven interface implemented

### OOP Structure
- [x] At least 3 user-defined classes
- [x] Private attributes
- [x] Constructors implemented
- [x] Getters and setters used
- [x] Main class controls flow only

### Payroll Functionality
- [ ] Regular employee logic correct
- [ ] Probationary employee logic correct
- [ ] Contractual employee logic correct
- [ ] Part-time (hourly, no work–no pay) logic correct
- [ ] Gross pay computation correct
- [ ] Deductions computation correct
- [ ] Net pay computation correct

### Output & Usability
- [ ] Follows sample run format
- [ ] Clear labels for all payroll components
- [ ] Readable prompts and layout

### Code Quality
- [ ] Proper indentation and formatting
- [ ] Meaningful variable, class, and method names
- [ ] Comments explaining major logic

***

## Sprint Plan

| Day | Focus | Assignee |
|-----|-------|----------|
| Day 1 | Repo setup, Jira board, task assignments | Project Manager |
| Day 2 | `Employee` base class + 4 subclasses | Dev |
| Day 3 | Payroll computation (gross pay + deductions) | Dev |
| Day 4 | TUI menu system + `PayslipRenderer` | Dev |
| Day 5 | Integration, testing, PR review, demo | All |

***

## How to Run

## Prerequesites
- Java JDK 17 or higher
- Terminal / Command Prompt

### Clone the Repository

```bash
git clone [link to repo]
cd [repo name]
```

```bash
# Compile
javac -d out src/**/*.java

# Run
java Main
```

> **Note:** ANSI color codes require a terminal that supports them.
> On Windows, use **Windows Terminal** or **Git Bash**.
> On Linux/macOS, any standard terminal works.

***

## References to Research

- [ ] BIR Withholding Tax Table (current year)
- [ ] SSS Contribution Table
- [ ] PhilHealth Contribution Rate
- [ ] Pag-IBIG Contribution Rate
- [ ] DOLE Holiday Pay Rules (regular vs. special holiday OT rates)

***

## License

For academic use only — NU Dasmariñas | CCOBJPGL | 3rd Term - 2026
