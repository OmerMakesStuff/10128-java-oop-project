package omerpeled.collegemgmt;

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
          Messages.MSG_FAIL_INPUT_NOT_POSITIVE_INT,
          Messages.MSG_ARTICLE_COUNT));

    if (degree != validDegree)
      throw new IllegalArgumentException(String.format(
          Messages.MSG_FAIL_INVALID_DEGREE,
          validDegree.displayName));

    super(id, name, degree, degreeTitle, salary);

    this.articleCount = articleCount;
  }

  public int getArticleCount() {
    return articleCount;
  }

  @Override
  public String toString() {
    // TODO: Article count in toString
    return super.toString();
  }
}
