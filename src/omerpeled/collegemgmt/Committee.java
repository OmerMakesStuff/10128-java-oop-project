package omerpeled.collegemgmt;

public class Committee {
  private String name;
  private Lecturer head;
  private Lecturer[] members;
  private int membersCount;

  public Committee(String name, Lecturer head) {
    this.name = name;
    this.head = head;
    this.members = new Lecturer[1];
    this.membersCount = 0;
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

  // TODO: Add member - reuse doubling array length
  // TODO: Remove member - move others back in array

  public boolean setHead(Lecturer head) {
    if (!head.isValidCommitteeHead())
      return false;
    // TODO: Add previous head to members
    this.head = head;
    // TODO: If in members, remove from members
    return true;
  }

  public String toString() {
    StringBuilder str = new StringBuilder(this.name).append(" - ");

    if (this.head == null)
      str.append("no head");
    else
      str.append("head: ").append(this.head.getName());
    str.append("\n");

    if (membersCount < 1)
      str.append("No members.");
    else {
      str.append("Members:");
      for (int i = 0; i < membersCount; i++) {
        str.append("\n\t").append(members[i].getName());
      }
    }
    return str.toString();
  }
}
