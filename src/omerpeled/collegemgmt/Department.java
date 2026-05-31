package omerpeled.collegemgmt;

public class Department {
  private String name;
  private int studentCount;
  private Lecturer[] lecturers;
  private int lecturerCount;

  public Department(String name, int studentCount) {
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

  public boolean addLecturer(Lecturer lecturer) {
    if (hasLecturer(lecturer))
      return false;

    if (lecturerCount == lecturers.length)
      lecturers = Utils.doubleLecturersSize(lecturers);

    lecturers[lecturerCount++] = lecturer;
    if (lecturer.getDepartment() != this)
      lecturer.setDepartment(this);

    return true;
  }

  public boolean removeLecturer(Lecturer lecturer) {
    if (!hasLecturer(lecturer))
      return false; // Can't remove lecturer not in dept

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
    return removed;
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
