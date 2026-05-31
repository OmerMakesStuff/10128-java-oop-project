package omerpeled.collegemgmt;

public class Committee {
  private String name;
  private Lecturer head;
  private Lecturer[] members;
  private int memberCount;

  public Committee(String name, Lecturer head) {
    this.name = name;
    this.head = head;
    this.members = new Lecturer[1];
    this.memberCount = 0;
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

  public boolean hasMember(Lecturer lecturer) {
    for (int i = 0; i < memberCount; i++) {
      if (members[i].getId().equals(lecturer.getId()))
        return true;
    }
    return false;
  }

  public boolean addMember(Lecturer lecturer) {
    if (hasMember(lecturer) || this.head == lecturer)
      return false;

    if (memberCount == members.length)
      members = Utils.doubleLecturersSize(members);

    members[memberCount++] = lecturer;
    // TODO: Add committee to lecturer committees array

    return true;
  }

  public boolean removeMember(Lecturer lecturer) {
    if (!hasMember(lecturer) || this.head == lecturer)
      // To remove the head, a new head must be set first
      return false;

    boolean removed = false;
    for (int i = 0; i < memberCount; i++) {
      if (members[i].getId().equals(lecturer.getId()) && !removed)
        removed = true;
      if (removed)
        members[i] = i < (memberCount - 1) ? members[i + 1] : null;
    }

    if (removed) {
      memberCount--;
      // TODO: Remove from lecturers committees
    }
    return removed;
  }

  public boolean setHead(Lecturer head) {
    if (!head.isValidCommitteeHead())
      return false;

    Lecturer prevHead = this.head;
    this.head = null;
    // Previous head stays in the committee as a member
    addMember(prevHead);

    if (hasMember(head))
      removeMember(head);

    this.head = head;
    return true;
  }

  public String toString() {
    StringBuilder str = new StringBuilder(this.name).append("\n  ");

    if (this.head == null)
      str.append("No head");
    else
      str.append("Head: ").append(this.head.getName());
    str.append("\n  ");

    if (memberCount < 1)
      str.append("No members.");
    else {
      str.append("Members: ");
      for (int i = 0; i < memberCount; i++) {
        str.append(members[i].getName());
        if (i < (memberCount - 1))
          str.append(", ");
      }
    }
    return str.toString();
  }
}
