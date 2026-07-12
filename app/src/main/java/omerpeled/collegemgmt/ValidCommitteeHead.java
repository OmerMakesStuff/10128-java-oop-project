package omerpeled.collegemgmt;

import static omerpeled.collegemgmt.utils.Messages.MSG_FAIL_INVALID_DEGREE;

public abstract class ValidCommitteeHead extends Lecturer {
  private String[] articles;

  protected ValidCommitteeHead(
      String id,
      String name,
      Degree degree,
      String degreeTitle,
      double salary,
      String[] articles,
      /**
       * Only valid degree for this lecturer. Subclasses should pass a constant
       * here and NOT allow passing in their own constructor.
       */
      Degree validDegree) {
    if (degree != validDegree)
      throw new IllegalArgumentException(String.format(
          MSG_FAIL_INVALID_DEGREE,
          validDegree.getDisplayName()));

    super(id, name, degree, degreeTitle, salary);

    this.articles = articles;
  }

  protected ValidCommitteeHead(Lecturer base, String[] articles,
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
    int result = 0;
    for (int i = 0; i < articles.length; i++) {
      if (this.articles[i] != null)
        result++;
    }
    return result;
  }

  @Override
  protected StringBuilder toStringBuilder() {
    int articleCount = this.getArticleCount();
    StringBuilder str = super.toStringBuilder().append("\n  Published ")
        .append(articleCount < 1 ? "no" : articleCount).append(" article");
    if (articleCount != 1)
      str.append("s");
    if (articleCount > 0) {
      str.append(": ");
      for (int i = 0; i < articleCount; i++) {
        str.append(this.articles[i]);
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
    int articleCount = this.getArticleCount();
    if (!(this instanceof ValidCommitteeHead lect) ||
        articleCount != lect.getArticleCount())
      return false;

    for (int i = 0; i < articleCount; i++) {
      if (!(this.articles[i].equals(lect.articles[i])))
        return false;
    }

    return super.equals(obj);
  }
}
