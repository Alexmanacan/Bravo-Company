package classes;

public abstract class Employee {
    private String id;
    private String name;
    private String type;
    private String position;
    private String department;
    private double basicSalary;

    public Employee(String id, String name, String type, double basicSalary) {
        this(id, name, type, "Staff", "General", basicSalary);
    }

    public Employee(String id, String name, String type, String position, String department, double basicSalary) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.position = position;
        this.department = department;
        this.basicSalary = basicSalary;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public double getBasicSalary() { return basicSalary; }
    public void setBasicSalary(double basicSalary) { this.basicSalary = basicSalary; }

    public abstract double getRate();

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - %s / %s", id, name, type, position, department);
    }
}
