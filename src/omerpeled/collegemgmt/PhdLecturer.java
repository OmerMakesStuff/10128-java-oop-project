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

  protected PhdLecturer(Lecturer base, int articleCount) {
    this(base.getId(),
        base.getName(),
        base.getDegree(),
        base.getDegreeTitle(),
        base.getSalary(),
        articleCount);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(this instanceof PhdLecturer))
      return false;

    return super.equals(obj);
  }
}
