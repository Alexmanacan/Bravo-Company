# Important Variables for Employee Classes

## Employee (Base Class)
- `id` (String): Unique identifier for the employee.
- `name` (String): Full name of the employee.
- `type` (String): Employee classification (Contractual, Part-Time, Probationary, Regular).
- `basicSalary` (double): The fixed monthly basic pay.

## Part-Time Employee
- `hourlyRate` (double): The amount paid per hour of work.

## Probationary Employee
- `leaveCredits` (double): Accrued leave days (Default: 5.0).

## Regular Employee
- `leaveCredits` (double): Accrued leave days (Default: 15.0).
