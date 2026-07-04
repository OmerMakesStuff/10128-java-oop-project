package omerpeled.collegemgmt;

public class PhdLecturer extends ValidCommitteeHead {
  public PhdLecturer(
      String id,
      String name,
      Degree degree,
      String degreeTitle,
      double salary,
      String[] articles) {
    super(id, name, degree, degreeTitle, salary, articles, Degree.PHD);
  }

  protected PhdLecturer(Lecturer base, String[] articles) {
    this(base.getId(),
        base.getName(),
        base.getDegree(),
        base.getDegreeTitle(),
        base.getSalary(),
        articles);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(this instanceof PhdLecturer))
      return false;

    return super.equals(obj);
  }
}
