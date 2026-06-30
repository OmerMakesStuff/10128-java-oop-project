package omerpeled.collegemgmt;

public class ProfLecturer extends ValidCommitteeHead {
  private String awardingBodyName;

  public ProfLecturer(
      String id,
      String name,
      Degree degree,
      String degreeTitle,
      double salary,
      int articleCount,
      String awardingBodyName) {
    super(id, name, degree, degreeTitle, salary, articleCount, Degree.PROF);

    this.awardingBodyName = awardingBodyName;
  }

  public ProfLecturer(Lecturer base, int articleCount,
      String awardingBodyName) {
    this(base.getId(),
        base.getName(),
        base.getDegree(),
        base.getDegreeTitle(),
        base.getSalary(),
        articleCount,
        awardingBodyName);
  }

  public String getAwardingBodyName() {
    return awardingBodyName;
  }

  @Override
  public StringBuilder toStringBuilder() {
    return super.toStringBuilder().append("\n  Prof. title awarded by ")
        .append(this.awardingBodyName);
  }

  @Override
  public String toString() {
    return this.toStringBuilder().toString();
  }
}
