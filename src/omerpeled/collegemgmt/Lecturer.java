package omerpeled.collegemgmt;

public class Lecturer {
  public enum Degree {
    BSC("BSc"), MSC("MSc"), PHD("PhD"), PROF("Prof.");

    final String displayName;

    Degree(String displayName) {
      this.displayName = displayName;
    }
  }

  private String name;
  private String id;
  private Degree degree;
  private String degreeTitle;
  private double salary;
  private Department department;

  public Lecturer(
      String name,
      String id,
      Degree degree,
      String degreeTitle,
      double salary,
      Department department) {
    this.name = name;
    this.id = id;
    this.degree = degree;
    this.degreeTitle = degreeTitle;
    this.salary = salary;
    this.department = department;
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public Degree getDegree() {
    return degree;
  }

  public String getDegreeTitle() {
    return degreeTitle;
  }

  public double getSalary() {
    return salary;
  }

  public Department getDepartment() {
    return department;
  }

  // TODO: Setters?

  public String toString() {
    return name + " (" + id + "), " + degree.displayName + " in " + degreeTitle
        + "\n\t" +
        "Department: " + (department == null ? "None" : department.getName())
        + " | Salary: " + salary + "₪";
  }
}
