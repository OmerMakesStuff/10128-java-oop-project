package omerpeled.collegemgmt;

import static omerpeled.collegemgmt.utils.ArrayUtils.doubleLecturersSize;
import static omerpeled.collegemgmt.utils.Messages.MSG_FAIL_INPUT_NOT_POSITIVE_INT;
import static omerpeled.collegemgmt.utils.Messages.MSG_STUDENT_COUNT;

import omerpeled.collegemgmt.exceptions.AlreadyAddedException;
import omerpeled.collegemgmt.exceptions.NotAddedException;

public class Department {
  private String name;
  private int studentCount;
  private Lecturer[] lecturers;
  private int lecturerCount;

  public Department(String name, int studentCount) {
    if (studentCount < 0)
      throw new IllegalArgumentException(String.format(
          MSG_FAIL_INPUT_NOT_POSITIVE_INT,
          MSG_STUDENT_COUNT));

    this.name = name;
    this.studentCount = studentCount;
    this.lecturers = new Lecturer[1];
    this.lecturerCount = 0;
  }

  public String getName() {
    return name;
  }

  public int getStudentCount() {
    return studentCount;
  }

  public Lecturer[] getLecturers() {
    return lecturers;
  }

  public boolean hasLecturer(Lecturer lecturer) {
    for (int i = 0; i < lecturerCount; i++) {
      if (lecturers[i].getId().equals(lecturer.getId()))
        return true;
    }
    return false;
  }

  public void addLecturer(Lecturer lecturer) {
    if (hasLecturer(lecturer))
      throw new AlreadyAddedException(lecturer.getName(), this.name);

    if (lecturerCount == lecturers.length)
      lecturers = doubleLecturersSize(lecturers);

    lecturers[lecturerCount++] = lecturer;
    if (lecturer.getDepartment() != this)
      lecturer.setDepartment(this);
  }

  public void removeLecturer(Lecturer lecturer) {
    if (!hasLecturer(lecturer))
      throw new NotAddedException(lecturer.getName(), this.name);

    boolean removed = false;
    for (int i = 0; i < lecturerCount; i++) {
      if (lecturers[i].getId().equals(lecturer.getId()) && !removed)
        removed = true;
      if (removed)
        lecturers[i] = i < (lecturerCount - 1) ? lecturers[i + 1] : null;
    }

    if (removed && lecturer.getDepartment() != null) {
      lecturerCount--;
      lecturer.setDepartment(null);
    }
  }

  public String toString() {
    StringBuilder str = new StringBuilder(
        name + " (" + studentCount + " students)");
    if (lecturerCount < 1)
      str.append("\n  No lecturers.");
    else {
      for (int i = 0; i < lecturerCount; i++) {
        str.append("\n  ").append(lecturers[i].getName()).append(" (")
            .append(lecturers[i].getId()).append("), ")
            .append(lecturers[i].getDegree().displayName).append(" in ")
            .append(lecturers[i].getDegreeTitle());
      }
    }
    return str.toString();
  }
}
