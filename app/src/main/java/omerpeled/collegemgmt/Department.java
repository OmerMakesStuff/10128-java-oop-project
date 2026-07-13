package omerpeled.collegemgmt;

import static omerpeled.collegemgmt.utils.Messages.MSG_FAIL_INPUT_NOT_POSITIVE_INT;
import static omerpeled.collegemgmt.utils.Messages.MSG_STUDENT_COUNT;

import java.util.ArrayList;
import java.util.List;

import omerpeled.collegemgmt.exceptions.AlreadyAddedException;
import omerpeled.collegemgmt.exceptions.NotAddedException;

public class Department {
  private String name;
  private int studentCount;
  private ArrayList<Lecturer> lecturers;

  public Department(String name, int studentCount) {
    if (studentCount < 0)
      throw new IllegalArgumentException(String.format(
          MSG_FAIL_INPUT_NOT_POSITIVE_INT,
          MSG_STUDENT_COUNT));

    this.name = name;
    this.studentCount = studentCount;
    this.lecturers = new ArrayList<Lecturer>();
  }

  public String getName() {
    return name;
  }

  public int getStudentCount() {
    return studentCount;
  }

  public List<Lecturer> getLecturers() {
    return lecturers;
  }

  public void addLecturer(Lecturer lecturer) {
    if (lecturers.contains(lecturer))
      throw new AlreadyAddedException(lecturer.getName(), this.name);

    lecturers.add(lecturer);
    if (lecturer.getDepartment() != this)
      lecturer.setDepartment(this);
  }

  public void removeLecturer(Lecturer lecturer) {
    if (!lecturers.contains(lecturer))
      throw new NotAddedException(lecturer.getName(), this.name);

    boolean removed = lecturers.remove(lecturer);
    if (removed && lecturer.getDepartment() != null)
      lecturer.setDepartment(null);
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder(
        name + " (" + studentCount + " students)");
    if (lecturers.isEmpty())
      str.append("\n  No lecturers.");
    else {
      for (int i = 0; i < lecturers.size(); i++) {
        Lecturer currLecturer = lecturers.get(i);
        str.append("\n  ").append(currLecturer.getName()).append(" (")
            .append(currLecturer.getId()).append("), ")
            .append(currLecturer.getDegree().getDisplayText()).append(" in ")
            .append(currLecturer.getDegreeTitle());
      }
    }
    return str.toString();
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Department dept &&
        this.name.equals(dept.name) &&
        this.studentCount == dept.studentCount &&
        // TODO: Check for infinite recursion here
        this.lecturers.equals(dept.lecturers));
  }
}
