package omerpeled.collegemgmt;

public class Committee {
  private String name;
  private Lecturer head;
  private Lecturer[] members;

  public Committee(String name, Lecturer head) {
    this.name = name;
    this.head = head;
    this.members = new Lecturer[1];
  }

  public String getName() {
    return name;
  }

  public Lecturer getHead() {
    return head;
  }

  public Lecturer[] getMembers() {
    return members;
  }

  // TODO: Add member - reuse doubling array length
  // TODO: Remove member - move others back in array

  public void setHead(Lecturer head) {
    // TODO: Require head lecturer to be a PHD or PROF, or return fail status
    // TODO: Add previous head to members
    this.head = head;
    // TODO: If in members, remove from members
  }

  public String toString() {
    // TODO: toString
    return this.name; // TEMP
  }
}
