package omerpeled.collegemgmt;

import static omerpeled.collegemgmt.utils.Messages.MSG_FAIL_INVALID_DEGREE;

import java.util.List;

public abstract class ValidCommitteeHead extends Lecturer {
  private List<String> articles;

  protected ValidCommitteeHead(
      String id,
      String name,
      Degree degree,
      String degreeTitle,
      double salary,
      List<String> articles,
      /**
       * Only valid degree for this lecturer. Subclasses should pass a constant
       * here and NOT allow passing in their own constructor.
       */
      Degree validDegree) {
    if (degree != validDegree)
      throw new IllegalArgumentException(String.format(
          MSG_FAIL_INVALID_DEGREE,
          validDegree.getDisplayText()));

    super(id, name, degree, degreeTitle, salary);

    this.articles = articles;
  }

  protected ValidCommitteeHead(Lecturer base, List<String> articles,
      Degree validDegree) {
    this(
        base.getId(),
        base.getName(),
        base.getDegree(),
        base.getDegreeTitle(),
        base.getSalary(),
        articles,
        validDegree);
  }

  public int getArticleCount() {
    return articles.size();
  }

  @Override
  protected StringBuilder toStringBuilder() {
    int articleCount = this.articles.size();
    StringBuilder str = super.toStringBuilder().append("\n  Published ")
        .append(articles.isEmpty() ? "no" : articleCount).append(" article");
    if (articleCount != 1)
      str.append("s");
    if (articleCount > 0) {
      str.append(": ");
      for (int i = 0; i < articleCount; i++) {
        str.append(this.articles.get(i));
        if (i < (articleCount - 1))
          str.append(", ");
      }
    }
    return str;
  }

  @Override
  public String toString() {
    return this.toStringBuilder().toString();
  }

  @Override
  public boolean equals(Object obj) {
    return (super.equals(obj)
        && this instanceof ValidCommitteeHead validHead
        && this.articles.equals(validHead.articles));
  }
}
