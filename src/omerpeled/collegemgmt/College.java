package omerpeled.collegemgmt;

public class College {
  private final String name;

  private Lecturer[] lecturers;
  private int lecturerCount;
  // TODO: Use Committee, Department instead of strings
  private String[] committees;
  private int committeeCount;
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
    this.committees = new String[1];
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

  public Lecturer getLecturerByName(String name) {
    for (int i = 0; i < lecturers.length; i++) {
      if (lecturers[i] != null && lecturers[i].getName().equals(name))
        return lecturers[i];
    }
    return null;
  }

  public Lecturer getLecturerById(String id) {
    for (int i = 0; i < lecturers.length; i++) {
      if (lecturers[i] != null && lecturers[i].getId().equals(id))
        return lecturers[i];
    }
    return null;
  }

  public AddItemStatus addLecturer(Lecturer newLecturer) {
    Lecturer existingByName = getLecturerByName(newLecturer.getName());
    if (existingByName != null)
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
  public String[] getCommittees() {
    return committees;
  }

  public String getCommitteeByName(String name) {
    return getItem(committees, name);
  }

  public AddItemStatus addCommittee(String name) {
    for (int i = 0; i < committees.length; i++) {
      if (committees[i] != null && committees[i].equals(name))
        return AddItemStatus.FAIL_EXISTS;
    }

    committees = addItem(committees, this.committeeCount, name);
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
