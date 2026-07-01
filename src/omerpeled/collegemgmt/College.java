package omerpeled.collegemgmt;

import static omerpeled.collegemgmt.utils.ArrayUtils.*;
import static omerpeled.collegemgmt.utils.Messages.MSG_COMMITTEE;
import static omerpeled.collegemgmt.utils.Messages.MSG_DEPARTMENT;
import static omerpeled.collegemgmt.utils.Messages.MSG_LECTURER_WITH_ID;

import omerpeled.collegemgmt.exceptions.ItemExistsException;

public class College {
  private final String name;

  private Lecturer[] lecturers;
  private int lecturerCount;
  private Committee[] committees;
  private int committeeCount;
  private Department[] departments;
  private int departmentCount;

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

  public ValidCommitteeHead[] getValidCommitteeHeads() {
    int count = 0;
    for (int i = 0; i < lecturerCount; i++) {
      if (lecturers[i] instanceof ValidCommitteeHead)
        count++;
    }

    // Avoid returning an array too long with null items
    int nextIdx = 0;
    ValidCommitteeHead[] result = new ValidCommitteeHead[count];
    for (int i = 0; i < lecturerCount; i++) {
      if (lecturers[i] instanceof ValidCommitteeHead validCommitteeHead)
        result[nextIdx++] = validCommitteeHead;
    }

    return result;
  }

  public void addLecturer(Lecturer newLecturer) {
    boolean exists = getLecturerById(newLecturer.getId()) != null;
    if (exists)
      throw new ItemExistsException(
          String.format(MSG_LECTURER_WITH_ID, newLecturer.getId()));

    if (lecturerCount == lecturers.length)
      lecturers = doubleLecturersSize(lecturers);

    lecturers[lecturerCount++] = newLecturer;
  }

  public boolean validCommitteeHeadExists() {
    for (int i = 0; i < lecturerCount; i++) {
      if (lecturers[i] instanceof ValidCommitteeHead)
        return true;
    }
    return false;
  }

  public double getLecturerSalaryAvg() {
    return getLecturerSalaryAvg(null);
  }

  public double getLecturerSalaryAvg(Department department) {
    double salarySum = 0;
    int deptLecturerCount = 0;
    for (int i = 0; i < lecturerCount; i++) {
      if (department == null || department.hasLecturer(lecturers[i])) {
        salarySum = salarySum + lecturers[i].getSalary();
        deptLecturerCount++;
      }
    }

    return deptLecturerCount > 0 ? (salarySum / deptLecturerCount) : 0;
  }
  // endregion

  // region Committees
  public Committee[] getCommittees() {
    return committees;
  }

  public int getCommitteeCount() {
    return committeeCount;
  }

  public Committee getCommitteeByName(String name) {
    for (int i = 0; i < committeeCount; i++) {
      if (committees[i] != null && committees[i].getName().equals(name))
        return committees[i];
    }
    return null;
  }

  public void addCommittee(Committee newCommittee) {
    boolean exists = getCommitteeByName(newCommittee.getName()) != null;
    if (exists)
      throw new ItemExistsException(
          MSG_COMMITTEE + ' ' + newCommittee.getName());

    if (committeeCount == committees.length)
      committees = doubleCommitteesSize(committees);

    committees[committeeCount++] = newCommittee;
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

  public void addDepartment(Department newDepartment) {
    boolean exists = getDepartmentByName(newDepartment.getName()) != null;
    if (exists)
      throw new ItemExistsException(
          MSG_DEPARTMENT + ' ' + newDepartment.getName());

    if (departmentCount == departments.length)
      departments = doubleDepartmentsSize(departments);

    departments[departmentCount++] = newDepartment;
  }
  // endregion
}
