package classes;

public class ProbationaryEmployee extends Employee {
    private double leaveCredits;

    public ProbationaryEmployee(String id, String name, double monthlyRate) {
        super(id, name, "Probationary", monthlyRate);
        this.leaveCredits = 5.0; // Default leave credits for probationary
    }

    public double getLeaveCredits() { return leaveCredits; }
    public void setLeaveCredits(double leaveCredits) { this.leaveCredits = leaveCredits; }

    @Override
    public double getRate() {
        return getBasicSalary();
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Leave: %.1f", leaveCredits);
    }
}
