package omerpeled.collegemgmt;

import omerpeled.collegemgmt.exceptions.AlreadyAddedException;

public class Lecturer {
  public enum Degree {
    BSC("BSc"),
    MSC("MSc"),
    PHD("PhD"),
    PROF("Prof.");

    final String displayName;

    Degree(String displayName) {
      this.displayName = displayName;
    }

    public String getDisplayName() {
      return displayName;
    }
  }

  private String id;
  private String name;
  private Degree degree;
  private String degreeTitle;
  private double salary;
  private Department department;
  private Committee[] committees;
  private int committeeCount;

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

    this.committees = new Committee[1];
    this.committeeCount = 0;
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

    if (this.department != null)
      this.department.removeLecturer(this);

    this.department = department;
    if (department != null)
      department.addLecturer(this);
  }

  public boolean isValidCommitteeHead() {
    return this.degree == Degree.PHD || this.degree == Degree.PROF;
  }

  public boolean hasCommittee(Committee committee) {
    for (int i = 0; i < committeeCount; i++) {
      if (committees[i] == committee)
        return true;
    }
    return false;
  }

  public void addCommittee(Committee committee) {
    if (hasCommittee(committee))
      throw new AlreadyAddedException(this.name, committee.getName());

    if (committeeCount == committees.length)
      committees = Utils.doubleCommitteesSize(committees);

    committees[committeeCount++] = committee;
  }

  public void removeCommittee(Committee committee) {
    if (!hasCommittee(committee))
      // To remove the head, a new head must be set first
      throw new UnsupportedOperationException(
          String.format(Messages.MSG_FAIL_REMOVE_COMMITTEE_HEAD, this.name,
              committee.getName()));

    boolean removed = false;
    for (int i = 0; i < committeeCount; i++) {
      if (committees[i] == committee && !removed)
        removed = true;
      if (removed)
        committees[i] = i < (committeeCount - 1) ? committees[i + 1] : null;
    }

    if (removed)
      committeeCount--;
  }

  public String toString() {
    StringBuilder str = new StringBuilder(
        String.format("%s (%s), %s in %s%n  Department: %s | Salary: %s₪%n  ",
            name,
            id,
            degree.getDisplayName(),
            degreeTitle,
            department == null ? "None" : department.getName(),
            salary));

    if (committeeCount < 1)
      str.append("No commiteees.");
    else {
      str.append("Committees: ");
      for (int i = 0; i < committeeCount; i++) {
        str.append(committees[i].getName());
        if (i < (committeeCount - 1))
          str.append(", ");
      }
    }

    return str.toString();
  }
}
