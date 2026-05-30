package omerpeled.collegemgmt;

public class Lecturer {
  public enum Degree {
    BSC("BSc"), MSC("MSc"), PHD("PhD"), PROF("Prof.");

    final String displayName;

    Degree(String displayName) {
      this.displayName = displayName;
    }
  }

  private String id;
  private String name;
  private Degree degree;
  private String degreeTitle;
  private double salary;
  private Department department;

  public Lecturer(
      String id,
      String name,
      Degree degree,
      String degreeTitle,
      double salary) {
    this.id = id;
    this.name = name;
    this.degree = degree;
    this.degreeTitle = degreeTitle;
    this.salary = salary;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
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

  public boolean isValidCommitteeHead() {
    return this.degree == Degree.PHD || this.degree == Degree.PROF;
  }

  public boolean setDepartment(Department department) {
    if (this.department == department)
      return false; // Already in this dept

    if (this.department != null)
      this.department.removeLecturer(this);

    this.department = department;
    if (department != null)
      department.addLecturer(this);
    return true;
  }

  public String toString() {
    return name + " (" + id + "), " + degree.displayName + " in " + degreeTitle
        + "\n\t" +
        "Department: " + (department == null ? "None" : department.getName())
        + " | Salary: " + salary + "₪";
  }
}
