package omerpeled.collegemgmt;

public class College {
  private final String name;

  private Lecturer[] lecturers;
  private int lecturerCount;
  private Committee[] committees;
  private int committeeCount;
  private Department[] departments;
  private int departmentCount;

  public enum AddItemStatus {
    SUCCESS,
    FAIL_EXISTS,
    FAIL_HEAD_MISSING,
    FAIL_HEAD_INVALID
  }

  public College(String name) {
    this.name = name;

    this.lecturers = new Lecturer[1];
    this.lecturerCount = 0;
    this.committees = new Committee[1];
    this.committeeCount = 0;
    this.departments = new Department[1];
    this.departmentCount = 0;
  }

  public String getName() {
    return name;
  }

  // region Lecturers
  public Lecturer[] getLecturers() {
    return lecturers;
  }

  public int getLecturerCount() {
    return lecturerCount;
  }

  public Lecturer getLecturerById(String id) {
    for (int i = 0; i < lecturerCount; i++) {
      if (lecturers[i] != null && lecturers[i].getId().equals(id))
        return lecturers[i];
    }
    return null;
  }

  public AddItemStatus addLecturer(Lecturer newLecturer) {
    boolean exists = getLecturerById(newLecturer.getId()) != null;
    if (exists)
      return AddItemStatus.FAIL_EXISTS;

    if (lecturerCount == lecturers.length)
      lecturers = Utils.doubleLecturersSize(lecturers);

    lecturers[lecturerCount++] = newLecturer;
    return AddItemStatus.SUCCESS;
  }

  public boolean validCommitteeHeadExists() {
    for (int i = 0; i < lecturerCount; i++) {
      if (lecturers[i].isValidCommitteeHead())
        return true;
    }
    return false;
  }

  public double getLecturerSalaryAvg() {
    double salarySum = 0;
    for (int i = 0; i < lecturerCount; i++) {
      salarySum = salarySum + lecturers[i].getSalary();
    }

    return lecturerCount > 0 ? (salarySum / lecturerCount) : 0;
  }
  // endregion

  // region Committees
  public Committee[] getCommittees() {
    return committees;
  }

  public Committee getCommitteeByName(String name) {
    for (int i = 0; i < committeeCount; i++) {
      if (committees[i] != null && committees[i].getName().equals(name))
        return committees[i];
    }
    return null;
  }

  public AddItemStatus addCommittee(Committee newCommittee) {
    boolean exists = getCommitteeByName(newCommittee.getName()) != null;
    if (exists)
      return AddItemStatus.FAIL_EXISTS;

    Lecturer committeeHead = newCommittee.getHead();
    if (committeeHead == null)
      return AddItemStatus.FAIL_HEAD_MISSING;
    else if (!committeeHead.isValidCommitteeHead())
      return AddItemStatus.FAIL_HEAD_INVALID;

    if (committeeCount == committees.length)
      committees = Utils.doubleCommitteesSize(committees);

    committees[committeeCount++] = newCommittee;
    return AddItemStatus.SUCCESS;
  }
  // endregion

  // region Departments
  public Department[] getDepartments() {
    return departments;
  }

  public int getDepartmentCount() {
    return departmentCount;
  }

  public Department getDepartmentByName(String name) {
    for (int i = 0; i < departmentCount; i++) {
      if (departments[i] != null && departments[i].getName().equals(name))
        return departments[i];
    }
    return null;
  }

  public AddItemStatus addDepartment(Department newDepartment) {
    boolean exists = getDepartmentByName(newDepartment.getName()) != null;
    if (exists)
      return AddItemStatus.FAIL_EXISTS;

    if (departmentCount == departments.length)
      departments = Utils.doubleDepartmentsSize(departments);

    departments[departmentCount++] = newDepartment;
    return AddItemStatus.SUCCESS;
  }
  // endregion
}
