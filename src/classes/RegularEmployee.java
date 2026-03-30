package classes;

public class RegularEmployee extends Employee {
    private double leaveCredits;

    public RegularEmployee(String id, String name, double monthlyRate) {
        super(id, name, "Regular", monthlyRate);
        this.leaveCredits = 15.0; // Default leave credits
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
