package omerpeled.collegemgmt;

import static omerpeled.collegemgmt.utils.ArrayUtils.*;
import static omerpeled.collegemgmt.utils.Messages.MSG_COMMITTEE;
import static omerpeled.collegemgmt.utils.Messages.MSG_DEPARTMENT;
import static omerpeled.collegemgmt.utils.Messages.MSG_LECTURER_WITH_ID;

import java.util.ArrayList;
import java.util.List;

import omerpeled.collegemgmt.exceptions.ItemExistsException;
import omerpeled.collegemgmt.exceptions.ItemNotExistsException;

public class College {
  private final String name;

  private Lecturer[] lecturers;
  private int lecturerCount;
  private ArrayList<Committee> committees;
  private ArrayList<Department> departments;

  public College(String name) {
    this.name = name;

    this.lecturers = new Lecturer[1];
    this.lecturerCount = 0;
    this.committees = new ArrayList<Committee>();
    this.departments = new ArrayList<Department>();
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
      if (department == null
          || department.getLecturers().contains(lecturers[i])) {
        salarySum = salarySum + lecturers[i].getSalary();
        deptLecturerCount++;
      }
    }

    return deptLecturerCount > 0 ? (salarySum / deptLecturerCount) : 0;
  }
  // endregion

  // region Committees
  public List<Committee> getCommittees() {
    return committees;
  }

  public Committee getCommitteeByName(String name) {
    for (int i = 0; i < committees.size(); i++) {
      Committee curr = committees.get(i);
      if (curr.getName().equals(name))
        return curr;
    }
    return null;
  }

  public void addCommittee(Committee newCommittee) {
    boolean exists = getCommitteeByName(newCommittee.getName()) != null;
    if (exists)
      throw new ItemExistsException(
          MSG_COMMITTEE + ' ' + newCommittee.getName());

    committees.add(newCommittee);
  }

  public Committee duplicateCommittee(Committee committee)
      throws CloneNotSupportedException {
    boolean exists = getCommitteeByName(committee.getName()) != null;
    if (!exists)
      throw new ItemNotExistsException(
          MSG_COMMITTEE + ' ' + committee.getName());

    // Already adds members, committee will be added anyway
    Committee dupCommittee = committee.clone();
    dupCommittee.setName(committee.getName() + "-new");

    int newCount = 1;
    boolean addSuccess = false;
    do {
      try {
        this.addCommittee(dupCommittee);
        addSuccess = true;
      } catch (ItemExistsException _) {
        // Rename to "-new2", "-new3", etc, and retry adding with new name
        dupCommittee.setName(committee.getName() + "-new" + (++newCount));
      }
    } while (!addSuccess);

    return dupCommittee;
  }
  // endregion

  // region Departments
  public List<Department> getDepartments() {
    return departments;
  }

  public Department getDepartmentByName(String name) {
    for (int i = 0; i < departments.size(); i++) {
      Department curr = departments.get(i);
      if (curr.getName().equals(name))
        return curr;
    }
    return null;
  }

  public void addDepartment(Department newDepartment) {
    boolean exists = getDepartmentByName(newDepartment.getName()) != null;
    if (exists)
      throw new ItemExistsException(
          MSG_DEPARTMENT + ' ' + newDepartment.getName());

    departments.add(newDepartment);
  }
  // endregion
}
