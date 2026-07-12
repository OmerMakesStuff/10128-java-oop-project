package omerpeled.collegemgmt;

import java.util.Comparator;

public class LecturerArticleCountComparator
    implements Comparator<ValidCommitteeHead> {
  @Override
  public int compare(ValidCommitteeHead o1, ValidCommitteeHead o2) {
    if (o1.getArticleCount() > o2.getArticleCount())
      return 1;
    else if (o1.getArticleCount() < o2.getArticleCount())
      return -1;
    else
      return 0;
  }
}