package omerpeled.collegemgmt;

import static omerpeled.collegemgmt.utils.Messages.MSG_DEPARTMENT;
import static omerpeled.collegemgmt.utils.Messages.MSG_FAIL_INPUT_NOT_POSITIVE_NUM;
import static omerpeled.collegemgmt.utils.Messages.MSG_SALARY;

import java.util.ArrayList;

import omerpeled.collegemgmt.exceptions.AlreadyAddedException;
import omerpeled.collegemgmt.exceptions.RemoveCommitteeHeadException;

public class Lecturer {
  public enum Degree {
    BSC("BSc"),
    MSC("MSc"),
    PHD("PhD"),
    PROF("Prof.");

    private final String displayText;

    Degree(String displayName) {
      this.displayText = displayName;
    }

    public String getDisplayText() {
      return displayText;
    }
  }

  private String id;
  private String name;
  private Degree degree;
  private String degreeTitle;
  private double salary;
  private Department department;
  private ArrayList<Committee> committees;

  public Lecturer(
      String id,
      String name,
      Degree degree,
      String degreeTitle,
      double salary) {
    if (salary < 0)
      throw new IllegalArgumentException(String.format(
          MSG_FAIL_INPUT_NOT_POSITIVE_NUM,
          MSG_SALARY));

    this.id = id;
    this.name = name;
    this.degree = degree;
    this.degreeTitle = degreeTitle;
    this.salary = salary;

    this.committees = new ArrayList<Committee>();
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

  public void setDepartment(Department department) {
    if (this.department == department)
      throw new AlreadyAddedException(this.name, department.getName());

    if (this.department != null) {
      Department prevDepartment = this.department;
      this.department = null;
      prevDepartment.removeLecturer(this);
    }

    this.department = department;
    if (department != null)
      department.addLecturer(this);
  }

  /// Unlike `committees.contains` which uses `equals`, this checks if
  /// `committees` includes a committee **by reference.** Good for checking
  /// inclusion of identical, but not equal committees.
  public boolean hasCommitteeRef(Committee committee) {
    for (int i = 0; i < this.committees.size(); i++) {
      if (committees.get(i) == committee)
        return true;
    }
    return false;
  }

  public void addCommittee(Committee committee) {
    if (hasCommitteeRef(committee))
      throw new AlreadyAddedException(this.name, committee.getName());

    this.committees.add(committee);
  }

  public void removeCommittee(Committee committee) {
    if (!hasCommitteeRef(committee))
      // To remove the head, a new head must be set first
      throw new RemoveCommitteeHeadException(this, committee);

    this.committees.remove(committee);
  }

  protected StringBuilder toStringBuilder() {
    StringBuilder str = new StringBuilder(
        String.format("%s (%s), %s in %s%n  %s: %s | %s: %s₪%n  ",
            name,
            id,
            degree.getDisplayText(),
            degreeTitle,
            MSG_DEPARTMENT,
            department == null ? "None" : department.getName(),
            MSG_SALARY,
            salary));

    if (this.committees.isEmpty())
      str.append("No committees");
    else {
      str.append("Committees: ");
      for (int i = 0; i < this.committees.size(); i++) {
        str.append(this.committees.get(i).getName());
        if (i < (this.committees.size() - 1))
          str.append(", ");
      }
    }

    return str;
  }

  @Override
  public String toString() {
    return this.toStringBuilder().toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Lecturer lect))
      return false;

    boolean departmentsEqual = (this.department == lect.department)
        || (this.department != null
            && this.department.equals(lect.department));

    if (!(this.id.equals(lect.id))
        || !(this.name.equals(lect.name))
        || this.degree != lect.degree
        || !(this.degreeTitle.equals(lect.degreeTitle))
        || this.salary != lect.salary
        || !departmentsEqual)
      return false;

    for (int i = 0; i < this.committees.size(); i++) {
      // By reference, using committee.equals() could cause infinite recursion
      if (this.committees.get(i) != lect.committees.get(i))
        return false;
    }

    return true;
  }
}
