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

  public String getAwardingBodyName() {
    return awardingBodyName;
  }

  @Override
  public String toString() {
    // TODO: Awarding body name in toString
    return super.toString();
  }
}
