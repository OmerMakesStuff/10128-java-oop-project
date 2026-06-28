package omerpeled.collegemgmt;

public class Utils {
  private Utils() {
    throw new UnsupportedOperationException(
        "Utility class cannot be instantiated");
  }

  // FIXME: CODE DUPLICATION due to different array types :(

  public static Lecturer[] doubleLecturersSize(Lecturer[] original) {
    Lecturer[] resized = new Lecturer[original.length * 2];
    for (int i = 0; i < original.length; i++) {
      resized[i] = original[i];
    }
    return resized;
  }

  public static Committee[] doubleCommitteesSize(Committee[] original) {
    Committee[] resized = new Committee[original.length * 2];
    for (int i = 0; i < original.length; i++) {
      resized[i] = original[i];
    }
    return resized;
  }

  public static Department[] doubleDepartmentsSize(Department[] original) {
    Department[] resized = new Department[original.length * 2];
    for (int i = 0; i < original.length; i++) {
      resized[i] = original[i];
    }
    return resized;
  }
}
