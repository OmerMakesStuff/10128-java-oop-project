package omerpeled.collegemgmt;

import java.util.List;

public class PhdLecturer extends ValidCommitteeHead {
  public PhdLecturer(
      String id,
      String name,
      Degree degree,
      String degreeTitle,
      double salary,
      List<String> articles) {
    super(id, name, degree, degreeTitle, salary, articles, Degree.PHD);
  }

  protected PhdLecturer(Lecturer base, List<String> articles) {
    this(base.getId(),
        base.getName(),
        base.getDegree(),
        base.getDegreeTitle(),
        base.getSalary(),
        articles);
  }

  @Override
  public boolean equals(Object obj) {
    return (super.equals(obj) && this instanceof PhdLecturer);
  }
}
