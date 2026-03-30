package classes;

public class ContractualEmployee extends Employee {
    public ContractualEmployee(String id, String name, double monthlyRate) {
        super(id, name, "Contractual", monthlyRate);
    }

    @Override
    public double getRate() {
        return getBasicSalary();
    }

    @Override
    public String toString() {
        return super.toString() + " | No Benefits";
    }
}
