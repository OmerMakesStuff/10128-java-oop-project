package omerpeled.collegemgmt;

import static omerpeled.collegemgmt.utils.Messages.MSG_ARTICLE_COUNT;
import static omerpeled.collegemgmt.utils.Messages.MSG_FAIL_INPUT_NOT_POSITIVE_INT;
import static omerpeled.collegemgmt.utils.Messages.MSG_FAIL_INVALID_DEGREE;

public abstract class ValidCommitteeHead extends Lecturer {
  private int articleCount;

  protected ValidCommitteeHead(
      String id,
      String name,
      Degree degree,
      String degreeTitle,
      double salary,
      int articleCount,
      /**
       * Only valid degree for this lecturer. Subclasses should pass a constant
       * here and NOT allow passing in their own constructor.
       */
      Degree validDegree) {
    if (articleCount < 0)
      throw new IllegalArgumentException(String.format(
          MSG_FAIL_INPUT_NOT_POSITIVE_INT,
          MSG_ARTICLE_COUNT));

    if (degree != validDegree)
      throw new IllegalArgumentException(String.format(
          MSG_FAIL_INVALID_DEGREE,
          validDegree.getDisplayName()));

    super(id, name, degree, degreeTitle, salary);

    this.articleCount = articleCount;
  }

  protected ValidCommitteeHead(Lecturer base, int articleCount,
      Degree validDegree) {
    if (articleCount < 0)
      throw new IllegalArgumentException(String.format(
          MSG_FAIL_INPUT_NOT_POSITIVE_INT,
          MSG_ARTICLE_COUNT));

    this(
        base.getId(),
        base.getName(),
        base.getDegree(),
        base.getDegreeTitle(),
        base.getSalary(),
        articleCount,
        validDegree);
  }

  public int getArticleCount() {
    return articleCount;
  }

  @Override
  protected StringBuilder toStringBuilder() {
    StringBuilder str = super.toStringBuilder().append("\n  Published ")
        .append(this.articleCount < 1 ? "no" : articleCount).append(" article");
    if (this.articleCount != 1)
      str.append("s");
    return str;
  }

  @Override
  public String toString() {
    return this.toStringBuilder().toString();
  }
}
