package classes;

public class ProbationaryEmployee extends Employee {
    private double leaveCredits;

    public ProbationaryEmployee(String id, String name, double monthlyRate) {
        super(id, name, "Probationary", monthlyRate);
        this.leaveCredits = 5.0; // Default leave credits for probationary
    }

    public double getLeaveCredits() { return leaveCredits; }
    public void setLeaveCredits(double leaveCredits) { this.leaveCredits = leaveCredits; }
}
