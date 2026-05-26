package omerpeled.collegemgmt;

public class Department {
  private String name;
  private int studentCount;
  private Lecturer[] lecturers;

  public Department(String name, int studentCount) {
    this.name = name;
    this.studentCount = studentCount;
    this.lecturers = new Lecturer[1];
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

  // TODO: Setters for name, student count? Would they ever be used?
  // TODO: Add lecturer - reuse doubling array length
  // TODO: Remove lecturer - move others back in array

  public String toString() {
    StringBuffer buf = new StringBuffer(
        name + " (" + studentCount + " students)\n\t");
    for (int i = 0; i < lecturers.length; i++) {
      buf.append("\n\t").append(lecturers[i].toString().split("\n")[0]);
    }
    return buf.toString();
  }
}
