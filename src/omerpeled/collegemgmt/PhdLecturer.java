package omerpeled.collegemgmt;

public class PhdLecturer extends ValidCommitteeHead {
  public PhdLecturer(
      String id,
      String name,
      Degree degree,
      String degreeTitle,
      double salary,
      int articleCount) {
    super(id, name, degree, degreeTitle, salary, articleCount, Degree.PHD);
  }
}
