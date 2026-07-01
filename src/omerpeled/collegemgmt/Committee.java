package omerpeled.collegemgmt;

import static omerpeled.collegemgmt.utils.ArrayUtils.doubleLecturersSize;

import omerpeled.collegemgmt.exceptions.AlreadyAddedException;
import omerpeled.collegemgmt.exceptions.InvalidCommitteeHeadException;
import omerpeled.collegemgmt.exceptions.NotAddedException;
import omerpeled.collegemgmt.exceptions.RemoveCommitteeHeadException;

public class Committee implements Cloneable {
  private String name;
  private Lecturer head;
  private Lecturer[] members;
  private int memberCount;

  public Committee(String name, Lecturer head) {
    this.name = name;
    this.members = new Lecturer[1];
    this.memberCount = 0;

    if (!(head instanceof ValidCommitteeHead))
      throw new InvalidCommitteeHeadException(head);

    this.head = head;
    head.addCommittee(this);
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

  public void setName(String name) {
    this.name = name;
  }

  public boolean hasMember(Lecturer lecturer) {
    for (int i = 0; i < memberCount; i++) {
      if (members[i].getId().equals(lecturer.getId()))
        return true;
    }
    return false;
  }

  public void addMember(Lecturer lecturer) {
    if (hasMember(lecturer) || this.head == lecturer)
      throw new AlreadyAddedException(lecturer.getName(), this.name);

    if (memberCount == members.length)
      members = doubleLecturersSize(members);

    members[memberCount++] = lecturer;
    lecturer.addCommittee(this);
  }

  public void removeMember(Lecturer lecturer) {
    if (!hasMember(lecturer) && this.head != lecturer)
      throw new NotAddedException(lecturer.getName(), this.name);
    else if (this.head == lecturer)
      // To remove the head, a new head must be set first
      throw new RemoveCommitteeHeadException(lecturer, this);

    boolean removed = false;
    for (int i = 0; i < memberCount; i++) {
      if (members[i].getId().equals(lecturer.getId()) && !removed)
        removed = true;
      if (removed)
        members[i] = i < (memberCount - 1) ? members[i + 1] : null;
    }

    if (removed) {
      memberCount--;
      lecturer.removeCommittee(this);
    }
  }

  public void setHead(Lecturer head) {
    if (!(head instanceof ValidCommitteeHead))
      throw new InvalidCommitteeHeadException(head);

    Lecturer prevHead = this.head;
    this.head = null;
    prevHead.removeCommittee(this);

    if (hasMember(head))
      removeMember(head);
    this.head = head;
    head.addCommittee(this);

    // Previous head stays in the committee as a member
    addMember(prevHead);
  }

  @Override
  public Committee clone() throws CloneNotSupportedException {
    Committee cloned = (Committee) super.clone();
    this.head.addCommittee(cloned);

    cloned.memberCount = 0; // Re-add all members
    cloned.members = new Lecturer[this.members.length];
    for (int i = 0; i < this.memberCount; i++) {
      cloned.addMember(this.members[i]);
    }

    return cloned;
  }

  @Override
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

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Committee comm) ||
        !(this.name.equals(comm.name)) ||
        !(this.head.equals(comm.head)) ||
        this.memberCount != comm.memberCount)
      return false;

    for (int i = 0; i < memberCount; i++) {
      // By reference, using lecturer.equals() could cause infinite recursion
      if (members[i] != comm.members[i])
        return false;
    }

    return true;
  }
}
