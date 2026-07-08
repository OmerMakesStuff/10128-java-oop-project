package omerpeled.collegemgmt;

import java.util.Comparator;

public class CommitteeMemberCountComparator
    implements Comparator<Committee> {
  @Override
  public int compare(Committee o1, Committee o2) {
    // Temporarily handle null, will be removed in part 4
    // TODO: REMOVE
    if (o1 == null || o2 == null)
      return 0;

    int count1 = o1.getMembers().size();
    int count2 = o2.getMembers().size();

    if (count1 > count2)
      return 1;
    else if (count1 < count2)
      return -1;
    else
      return 0;
  }
}
