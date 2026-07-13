package omerpeled.collegemgmt;

import java.util.List;

public class ProfLecturer extends ValidCommitteeHead {
  private String awardingBodyName;

  public ProfLecturer(
      String id,
      String name,
      Degree degree,
      String degreeTitle,
      double salary,
      List<String> articles,
      String awardingBodyName) {
    super(id, name, degree, degreeTitle, salary, articles, Degree.PROF);

    this.awardingBodyName = awardingBodyName;
  }

  public ProfLecturer(Lecturer base, List<String> articles,
      String awardingBodyName) {
    this(base.getId(),
        base.getName(),
        base.getDegree(),
        base.getDegreeTitle(),
        base.getSalary(),
        articles,
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

  @Override
  public boolean equals(Object obj) {
    return (super.equals(obj)
        && this instanceof ProfLecturer lect
        && this.awardingBodyName.equals(lect.awardingBodyName));
  }
}
