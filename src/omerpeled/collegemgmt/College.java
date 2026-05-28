package omerpeled.collegemgmt;

public class College {
  private final String name;

  private Lecturer[] lecturers;
  private int lecturerCount;
  private Committee[] committees;
  private int committeeCount;
  // TODO: Use Department[] instead of String[]
  private String[] departments;
  private int departmentCount;

  enum ItemType {
    LECTURER("Lecturer"),
    COMMITTEE("Committee"),
    DEPARTMENT("Department");

    public final String displayName;

    ItemType(String displayName) {
      this.displayName = displayName;
    }
  }

  public enum AddItemStatus {
    SUCCESS,
    FAIL_EXISTS
  }

  public College(String name) {
    this.name = name;

    this.lecturers = new Lecturer[1];
    this.lecturerCount = 0;
    this.committees = new Committee[1];
    this.committeeCount = 0;
    this.departments = new String[1];
    this.departmentCount = 0;
  }

  public String getName() {
    return name;
  }

  // region Lecturers
  public Lecturer[] getLecturers() {
    return lecturers;
  }

  public Lecturer getLecturerById(String id) {
    for (int i = 0; i < lecturerCount; i++) {
      if (lecturers[i] != null && lecturers[i].getId().equals(id))
        return lecturers[i];
    }
    return null;
  }

  public AddItemStatus addLecturer(Lecturer newLecturer) {
    boolean exists = getLecturerById(newLecturer.getId()) != null;
    if (exists)
      return AddItemStatus.FAIL_EXISTS;

    // Double array size if too small
    // FIXME: CODE DUPLICATION :( Extract when I can use generics
    if (lecturerCount == lecturers.length) {
      Lecturer[] resizedItems = new Lecturer[lecturers.length * 2];
      for (int i = 0; i < lecturers.length; i++) {
        resizedItems[i] = lecturers[i];
      }
      lecturers = resizedItems;
    }

    lecturers[lecturerCount] = newLecturer;
    this.lecturerCount++;
    return AddItemStatus.SUCCESS;
  }
  // endregion

  // region Committees
  public Committee[] getCommittees() {
    return committees;
  }

  public Committee getCommitteeByName(String name) {
    for (int i = 0; i < committeeCount; i++) {
      if (committees[i] != null && committees[i].getName().equals(name))
        return committees[i];
    }
    return null;
  }

  public AddItemStatus addCommittee(String name) {
    boolean exists = getCommitteeByName(name) != null;
    if (exists)
      return AddItemStatus.FAIL_EXISTS;

    // Double array size if too small
    // FIXME: CODE DUPLICATION :( Extract when I can use generics
    if (committeeCount == committees.length) {
      Committee[] resizedItems = new Committee[committees.length * 2];
      for (int i = 0; i < committees.length; i++) {
        resizedItems[i] = committees[i];
      }
      committees = resizedItems;
    }

    committees[committeeCount] = new Committee(name, null);
    this.committeeCount++;
    return AddItemStatus.SUCCESS;
  }
  // endregion

  // region Departments
  public String[] getDepartments() {
    return departments;
  }

  public AddItemStatus addDepartment(String name) {
    for (int i = 0; i < this.departments.length; i++) {
      if (this.departments[i] != null && this.departments[i].equals(name))
        return AddItemStatus.FAIL_EXISTS;
    }

    departments = addItem(this.departments, this.departmentCount, name);
    this.departmentCount++;
    return AddItemStatus.SUCCESS;
  }
  // endregion

  // region Internal utils
  private static String[] addItem(String[] items, int logicalSize,
      String newItem) {
    // If array is too small, double its length and copy existing items
    if (logicalSize == items.length) {
      String[] resizedItems = new String[items.length * 2];
      for (int i = 0; i < items.length; i++) {
        resizedItems[i] = items[i];
      }
      items = resizedItems;
    }

    items[logicalSize] = newItem;
    return items;
  }

  private static String getItem(String[] items, String item) {
    for (int i = 0; i < items.length; i++) {
      if (items[i] != null && items[i].equals(item))
        return items[i];
    }
    return null;
  }
  // endregion
}
