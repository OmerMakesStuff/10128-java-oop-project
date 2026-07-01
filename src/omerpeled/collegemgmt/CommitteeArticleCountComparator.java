package omerpeled.collegemgmt;

import java.util.Comparator;

public class CommitteeArticleCountComparator
    implements Comparator<Committee> {
  @Override
  public int compare(Committee o1, Committee o2) {
    // Temporarily handle null, will be removed in part 4
    if (o1 == null || o2 == null)
      return 0;

    int o1Articles = o1.getTotalArticleCount();
    int o2Articles = o2.getTotalArticleCount();

    if (o1Articles > o2Articles)
      return 1;
    else if (o1Articles < o2Articles)
      return -1;
    else
      return 0;
  }
}