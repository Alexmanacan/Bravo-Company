package classes;

public class PartTimeEmployee extends Employee {
    private double hourlyRate;

    public PartTimeEmployee(String id, String name, double hourlyRate) {
        super(id, name, "Part-Time", 0); // Monthly salary is 0, depends on hours
        this.hourlyRate = hourlyRate;
    }

    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }

    @Override
    public String toString() {
        return super.toString() + String.format(" - $%.2f/hr", hourlyRate);
    }
}
