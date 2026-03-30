package classes;

public abstract class Employee {
    private String id;
    private String name;
    private String type;
    private double basicSalary;

    public Employee(String id, String name, String type, double basicSalary) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.basicSalary = basicSalary;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getBasicSalary() { return basicSalary; }
    public void setBasicSalary(double basicSalary) { this.basicSalary = basicSalary; }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", id, name, type);
    }
}
