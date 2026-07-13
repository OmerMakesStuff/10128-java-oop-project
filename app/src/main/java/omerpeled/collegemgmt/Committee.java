package omerpeled.collegemgmt;

import static omerpeled.collegemgmt.utils.Messages.MSG_MEMBER_DEGREE;
import static omerpeled.collegemgmt.utils.Messages.MSG_PROMPT_ENUM;

import java.util.ArrayList;
import java.util.List;

import omerpeled.collegemgmt.exceptions.AlreadyAddedException;
import omerpeled.collegemgmt.exceptions.AlreadyHeadException;
import omerpeled.collegemgmt.exceptions.InvalidCommitteeHeadException;
import omerpeled.collegemgmt.exceptions.NotAddedException;
import omerpeled.collegemgmt.exceptions.RemoveCommitteeHeadException;
import omerpeled.collegemgmt.utils.MenuOption;

public class Committee implements Cloneable {
  public enum MemberDegree implements MenuOption {
    BSC_OR_MSC(String.format(
        "%s or %s",
        Lecturer.Degree.BSC.getDisplayText(),
        Lecturer.Degree.MSC.getDisplayText()),
        Lecturer.Degree.BSC, Lecturer.Degree.MSC),
    PHD(Lecturer.Degree.PHD.getDisplayText(), Lecturer.Degree.PHD),
    PROF(Lecturer.Degree.PROF.getDisplayText(), Lecturer.Degree.PROF);

    private final String displayText;
    private final Lecturer.Degree[] validDegrees;

    MemberDegree(String displayName, Lecturer.Degree... validDegrees) {
      this.displayText = displayName;
      this.validDegrees = validDegrees;
    }

    @Override
    public String getDisplayText() {
      return displayText;
    }

    @Override
    public String getPromptTitle() {
      return String.format(MSG_PROMPT_ENUM, MSG_MEMBER_DEGREE);
    }
  }

  private String name;
  private ValidCommitteeHead head;
  private MemberDegree memberDegree;
  private ArrayList<Lecturer> members;

  public Committee(String name, Lecturer head, MemberDegree memberDegree) {
    if (!(head instanceof ValidCommitteeHead validHead))
      throw new InvalidCommitteeHeadException(head);

    this.name = name;
    this.memberDegree = memberDegree;
    this.members = new ArrayList<Lecturer>();
    this.head = validHead;
    head.addCommittee(this);
  }

  public String getName() {
    return name;
  }

  public Lecturer getHead() {
    return head;
  }

  public MemberDegree getMemberDegree() {
    return memberDegree;
  }

  public List<Lecturer> getMembers() {
    return members;
  }

  public int getTotalArticleCount() {
    int result = head.getArticleCount();
    for (int i = 0; i < this.members.size(); i++) {
      if (this.members.get(i) instanceof ValidCommitteeHead member)
        result += member.getArticleCount();
    }

    return result;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Deprecated(forRemoval = true)
  public boolean hasMember(Lecturer lecturer) {
    return members.contains(lecturer);
  }

  public void addMember(Lecturer lecturer) {
    if (members.contains(lecturer) || this.head == lecturer)
      throw new AlreadyAddedException(lecturer.getName(), this.name);

    // TODO: Only allow members whose degree == memberDegree

    members.add(lecturer);
    lecturer.addCommittee(this);
  }

  public void removeMember(Lecturer lecturer) {
    if (!(members.contains(lecturer)) && this.head != lecturer)
      throw new NotAddedException(lecturer.getName(), this.name);
    else if (this.head == lecturer)
      // To remove the head, a new head must be set first
      throw new RemoveCommitteeHeadException(lecturer, this);

    boolean removed = members.remove(lecturer);
    if (removed)
      lecturer.removeCommittee(this);
  }

  public void setHead(Lecturer head) {
    if (!(head instanceof ValidCommitteeHead validHead))
      throw new InvalidCommitteeHeadException(head);

    if (this.head == validHead)
      throw new AlreadyHeadException(validHead, this);

    Lecturer prevHead = this.head;
    this.head = null;
    prevHead.removeCommittee(this);

    if (members.contains(head))
      removeMember(head);

    this.head = validHead;
    head.addCommittee(this);

    // Previous head stays in the committee as a member
    // TODO: Remove them instead if their degree != memberDegree
    addMember(prevHead);
  }

  @Override
  public Committee clone() throws CloneNotSupportedException {
    Committee cloned = (Committee) super.clone();
    this.head.addCommittee(cloned);
    cloned.members = new ArrayList<Lecturer>(this.members);
    for (Lecturer member : members) {
      member.addCommittee(cloned);
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

    str.append("\n  Member degree: ")
        .append(this.getMemberDegree().getDisplayText()).append("\n  ");

    if (this.members.isEmpty())
      str.append("No members.");
    else {
      str.append("Members: ");
      for (int i = 0; i < this.members.size(); i++) {
        str.append(this.members.get(i).getName());
        if (i < (this.members.size() - 1))
          str.append(", ");
      }
    }
    return str.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Committee comm))
      return false;

    boolean headsEqual = (this.head == comm.head)
        || (this.head != null
            && this.head.equals(comm.head));

    return (this.name.equals(comm.name)
        && headsEqual
        && this.members.equals(comm.members));
  }
}
