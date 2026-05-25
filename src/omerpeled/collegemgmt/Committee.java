package omerpeled.collegemgmt;

public class Committee {
  private String name;
  private Lecturer headLecturer;
  private Lecturer[] members;

  public Committee(String name, Lecturer headLecturer) {
    this.name = name;
    this.headLecturer = headLecturer;
    this.members = new Lecturer[1];
  }

  public String getName() {
    return name;
  }

  public Lecturer getHeadLecturer() {
    return headLecturer;
  }

  public Lecturer[] getMembers() {
    return members;
  }

  // TODO: Add member - reuse doubling array length
  // TODO: Remove member - move others back in array

  public void setHeadLecturer(Lecturer headLecturer) {
    // TODO: Require headLecturer to be a PHD or PROF, otherwise return fail
    // status
    this.headLecturer = headLecturer;
    // TODO: If in members, remove from members
  }

  public String toString() {
    // TODO: toString
    return super.toString();
  }
}
