package omerpeled.collegemgmt;

import java.util.Comparator;

public class CommitteeMemberCountComparator
    implements Comparator<Committee> {
  @Override
  public int compare(Committee o1, Committee o2) {
    // Temporarily handle null, will be removed in part 4
    if (o1 == null || o2 == null)
      return 0;

    if (o1.getMemberCount() > o2.getMemberCount())
      return 1;
    else if (o1.getMemberCount() < o2.getMemberCount())
      return -1;
    else
      return 0;
  }
}
