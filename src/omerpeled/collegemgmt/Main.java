/**
 * Omer Peled - 209110519
 */

package omerpeled.collegemgmt;

import java.util.Scanner;

public class Main {
  static Scanner s;
  static College college;

  public static void main(String[] args) {
    s = new Scanner(System.in);

    System.out.print("Welcome!\nEnter college name: ");
    String collegeName = s.nextLine();
    college = new College(collegeName);

    MenuOption choice;
    do {
      choice = promptForMenuOption();
      if (choice != MenuOption.EXIT)
        System.out.println(); // Separate from menu

      switch (choice) {
        case EXIT:
          break;

        case ADD_LECTURER:
          addLecturer();
          break;

        case ADD_COMMITTEE:
          addCommittee();
          break;

        case ADD_COMMITTEE_MEMBER:
          addCommitteeMember();
          break;

        case SET_COMMITTEE_HEAD:
          setCommitteeHead();
          break;

        case REMOVE_COMMITTEE_MEMBER:
          removeCommitteeMember();
          break;

        case ADD_DEPARTMENT:
          addDepartment();
          break;

        case ADD_DEPARTMENT_LECTURER:
          addDeptLecturer();
          break;

        case SHOW_LECTURER_SALARY_AVG:
          showSalaryAvg();
          break;

        case SHOW_LECTURER_SALARY_DEPT_AVG:
          showDeptSalaryAvg();
          break;

        case SHOW_LECTURERS:
          showLecturers();
          break;

        case SHOW_COMMITTEES:
          showCommittees();
          break;
      }

      if (choice != MenuOption.EXIT)
        System.out.println(); // Separate from menu
    } while (choice != MenuOption.EXIT);

    s.close();
  }

  // region Messages
  private static final String MSG_PROMPT = "Enter %s: ";

  private static final String MSG_SUCCESS_CREATED = "%s added.%n";
  private static final String MSG_SUCCESS_ADDED_TO = "%s has been added to %s.%n";
  private static final String MSG_SUCCESS_REMOVED_FROM = "%s has been removed from %s.%n";
  private static final String MSG_SUCCESS_COMMITTEE_HEAD_SET = "%s is now the head of %s.%n";

  private static final String MSG_FAIL_EXISTS = "%s already exists!%n";
  private static final String MSG_FAIL_NOT_EXISTS = "%s doesn't exist!%n";
  private static final String MSG_FAIL_NONE_EXIST = "No %ss exist.%n";
  private static final String MSG_FAIL_ALREADY_ADDED = "%s is already in %s!%n";
  private static final String MSG_FAIL_NOT_ADDED = "%s is not in %s!%n";
  private static final String MSG_FAIL_NO_VALID_COMMITTEE_HEAD = "valid committee head doesn't exist.\n";
  private static final String MSG_FAIL_INVALID_COMMITTEE_HEAD = "%s cannot be a committee head! (degree must be %s or %s)%n";
  private static final String MSG_FAIL_REMOVE_COMMITTEE_HEAD = "%s is the head of %s! To remove them, a new head must be set first.%n";
  private static final String MSG_FAIL_INVALID_CHOICE = "Invalid choice!\n";
  private static final String MSG_FAIL_UNAVAILABLE_OPT = "Option unavailable - %s";

  private static final String MSG_CHOICE = "choice";
  private static final String MSG_LECTURER = "Lecturer";
  private static final String MSG_LECTURER_ID = "lecturer ID";
  private static final String MSG_LECTURER_WITH_ID = MSG_LECTURER + " with ID ";
  private static final String MSG_COMMITTEE = "Committee";
  private static final String MSG_COMMITTEE_NAME = "committee name";
  private static final String MSG_DEPARTMENT = "Department";
  private static final String MSG_DEPARTMENT_NAME = "department name";
  // endregion

  // region Menus
  public enum MenuOption {
    EXIT("Exit"),
    ADD_LECTURER("Add lecturer"),
    ADD_COMMITTEE("Add committee"),
    ADD_COMMITTEE_MEMBER("Add lecturer to committee"),
    SET_COMMITTEE_HEAD("Set committee head"),
    REMOVE_COMMITTEE_MEMBER("Remove lecturer from committee"),
    ADD_DEPARTMENT("Add department"),
    ADD_DEPARTMENT_LECTURER("Add lecturer to department"),
    SHOW_LECTURER_SALARY_AVG("Show lecturer salary average"),
    SHOW_LECTURER_SALARY_DEPT_AVG("Show lecturer salary average in department"),
    SHOW_LECTURERS("Show all lecturer details"),
    SHOW_COMMITTEES("Show all committee details");

    public final String displayText;

    MenuOption(String displayText) {
      this.displayText = displayText;
    }
  }

  static final MenuOption[] MENU_OPTIONS = MenuOption.values();

  private static String buildMainMenuString() {
    StringBuilder str = new StringBuilder(
        "COLLEGE STAFF MANAGEMENT - %s\n\n");
    for (int i = 0; i < MENU_OPTIONS.length; i++) {
      str.append(MENU_OPTIONS[i].ordinal()).append(") ")
          .append(MENU_OPTIONS[i].displayText).append("\n");
    }
    return str.toString();
  }

  private static final String MAIN_MENU = buildMainMenuString();

  static final Lecturer.Degree[] DEGREE_OPTIONS = Lecturer.Degree.values();

  private static String buildDegreeMenuString() {
    StringBuilder buf = new StringBuilder("Choose degree:");
    for (int i = 0; i < DEGREE_OPTIONS.length; i++) {
      buf.append("\n").append(i + 1).append(") ")
          .append(DEGREE_OPTIONS[i].displayName);
    }
    return buf.toString();
  }

  private static final String DEGREE_MENU = buildDegreeMenuString();
  // endregion

  // region Prompt for types
  private static MenuOption promptForMenuOption() {
    int choiceIdx;
    boolean isValid = false;
    do {
      System.out.printf(MAIN_MENU, college.getName());
      System.out.printf("\n" + MSG_PROMPT, MSG_CHOICE);
      // Avoid \n in buffer after Enter
      choiceIdx = Integer.parseInt(s.nextLine());

      // Handle choices not covered by MenuOption enum
      isValid = choiceIdx >= 0 && choiceIdx < MENU_OPTIONS.length;
      if (!isValid)
        System.err.println(MSG_FAIL_INVALID_CHOICE);
    } while (!isValid);

    return MENU_OPTIONS[choiceIdx];
  }

  private static Lecturer.Degree promptForDegree() {
    int choiceIdx;
    boolean isValid = false;
    do {
      System.out
          .print(DEGREE_MENU + '\n' + String.format(MSG_PROMPT, MSG_CHOICE));
      choiceIdx = Integer.parseInt(s.nextLine()) - 1;
      isValid = choiceIdx >= 0 && choiceIdx < DEGREE_OPTIONS.length;
      if (!isValid)
        System.err.println(MSG_FAIL_INVALID_CHOICE);
    } while (!isValid);

    return DEGREE_OPTIONS[choiceIdx];
  }

  private static Lecturer promptForLecturer() {
    return promptForLecturer(MSG_LECTURER_ID);
  }

  private static Lecturer promptForLecturer(String promptMsg) {
    String lecturerId;
    Lecturer lecturer;

    do {
      System.out.printf(MSG_PROMPT, promptMsg);
      lecturerId = s.nextLine();
      lecturer = college.getLecturerById(lecturerId);
      if (lecturer == null)
        System.err.printf(MSG_FAIL_NOT_EXISTS,
            MSG_LECTURER_WITH_ID + lecturerId);
    } while (lecturer == null);

    return lecturer;
  }
  // endregion

  // region New item creation
  private static void addLecturer() {
    College.AddItemStatus addStatus;

    do {
      System.out.printf(MSG_PROMPT, MSG_LECTURER_ID);
      String id = s.nextLine();
      System.out.printf(MSG_PROMPT, MSG_LECTURER + " name");
      String name = s.nextLine();
      Lecturer.Degree degree = promptForDegree();
      System.out.printf(MSG_PROMPT, "degree title");
      String degreeTitle = s.nextLine();
      System.out.printf(MSG_PROMPT, "salary");
      double salary = Double.parseDouble(s.nextLine());

      Lecturer newLecturer = new Lecturer(
          id,
          name,
          degree,
          degreeTitle,
          salary);

      addStatus = college.addLecturer(newLecturer);
      if (addStatus == College.AddItemStatus.FAIL_EXISTS)
        System.err.printf(MSG_FAIL_EXISTS, MSG_LECTURER_WITH_ID + id);
      else
        System.out.printf(MSG_SUCCESS_CREATED, MSG_LECTURER);
    } while (addStatus != College.AddItemStatus.SUCCESS);
  }

  private static void addDepartment() {
    College.AddItemStatus addStatus;

    do {
      System.out.printf(MSG_PROMPT, MSG_DEPARTMENT_NAME);
      String name = s.nextLine();
      System.out.printf(MSG_PROMPT, "number of students in department");
      int studentCount = Integer.parseInt(s.nextLine());

      Department newDepartment = new Department(name, studentCount);
      addStatus = college.addDepartment(newDepartment);

      if (addStatus == College.AddItemStatus.FAIL_EXISTS)
        System.err.printf(MSG_FAIL_EXISTS, MSG_DEPARTMENT + " " + name);
      else
        System.out.printf(MSG_SUCCESS_CREATED, MSG_DEPARTMENT);
    } while (addStatus != College.AddItemStatus.SUCCESS);
  }

  private static void addCommittee() {
    if (!college.validCommitteeHeadExists()) {
      System.err.printf(
          MSG_FAIL_UNAVAILABLE_OPT, MSG_FAIL_NO_VALID_COMMITTEE_HEAD);
      return;
    }

    College.AddItemStatus addStatus;

    do {
      System.out.printf(MSG_PROMPT, MSG_COMMITTEE_NAME);
      String name = s.nextLine();
      Lecturer head = promptForLecturer("head lecturer ID");

      Committee newCommittee = new Committee(name, head);
      addStatus = college.addCommittee(newCommittee);
      switch (addStatus) {
        case FAIL_EXISTS:
          System.err.printf(MSG_FAIL_EXISTS, MSG_COMMITTEE + " " + name);
          break;
        case FAIL_HEAD_INVALID:
          System.out.printf(MSG_FAIL_INVALID_COMMITTEE_HEAD, head.getName(),
              Lecturer.Degree.PHD.displayName,
              Lecturer.Degree.PROF.displayName);
          break;
        case SUCCESS:
          System.out.printf(MSG_SUCCESS_CREATED, MSG_COMMITTEE);
          break;
      }
    } while (addStatus != College.AddItemStatus.SUCCESS);
  }
  // endregion

  // region Committees
  private static void addCommitteeMember() {
    if (college.getCommitteeCount() < 1) {
      System.err.printf(MSG_FAIL_UNAVAILABLE_OPT,
          String.format(MSG_FAIL_NONE_EXIST, MSG_COMMITTEE.toLowerCase()));
      return;
    }

    Lecturer lecturer = promptForLecturer();

    System.out.printf(MSG_PROMPT, MSG_COMMITTEE_NAME);
    String committeeName = s.nextLine();
    Committee committee = college.getCommitteeByName(committeeName);
    if (committee == null) {
      System.err.printf(MSG_FAIL_NOT_EXISTS, MSG_COMMITTEE);
      return;
    }

    boolean addSuccess = committee.addMember(lecturer);
    if (addSuccess)
      System.out.printf(MSG_SUCCESS_ADDED_TO, lecturer.getName(),
          committee.getName());
    else
      System.err.printf(MSG_FAIL_ALREADY_ADDED, lecturer.getName(),
          committee.getName());
  }

  private static void removeCommitteeMember() {
    if (college.getCommitteeCount() < 1) {
      System.err.printf(MSG_FAIL_UNAVAILABLE_OPT,
          String.format(MSG_FAIL_NONE_EXIST, MSG_COMMITTEE.toLowerCase()));
      return;
    }

    Lecturer lecturer = promptForLecturer();

    System.out.printf(MSG_PROMPT, MSG_COMMITTEE_NAME);
    String committeeName = s.nextLine();
    Committee committee = college.getCommitteeByName(committeeName);
    if (committee == null) {
      System.err.printf(MSG_FAIL_NOT_EXISTS, MSG_COMMITTEE);
      return;
    }

    boolean removeSuccess = committee.removeMember(lecturer);
    if (removeSuccess)
      System.out.printf(MSG_SUCCESS_REMOVED_FROM, lecturer.getName(),
          committee.getName());
    else if (committee.getHead() == lecturer)
      System.err.printf(MSG_FAIL_REMOVE_COMMITTEE_HEAD, lecturer.getName(),
          committee.getName());
    else
      System.err.printf(MSG_FAIL_NOT_ADDED, lecturer.getName(),
          committee.getName());
  }

  private static void setCommitteeHead() {
    if (college.getCommitteeCount() < 1) {
      System.err.printf(MSG_FAIL_UNAVAILABLE_OPT,
          String.format(MSG_FAIL_NONE_EXIST, MSG_COMMITTEE.toLowerCase()));
      return;
    }

    Lecturer lecturer = promptForLecturer();

    System.out.printf(MSG_PROMPT, MSG_COMMITTEE_NAME);
    String committeeName = s.nextLine();
    Committee committee = college.getCommitteeByName(committeeName);
    if (committee == null) {
      System.err.printf(MSG_FAIL_NOT_EXISTS, MSG_COMMITTEE);
      return;
    }

    boolean success = committee.setHead(lecturer);
    if (success)
      System.out.printf(MSG_SUCCESS_COMMITTEE_HEAD_SET, lecturer.getName(),
          committee.getName());
    else
      System.out.printf(MSG_FAIL_INVALID_COMMITTEE_HEAD, lecturer.getName(),
          Lecturer.Degree.PHD.displayName,
          Lecturer.Degree.PROF.displayName);
  }
  // endregion

  // region Departments
  private static void addDeptLecturer() {
    boolean lecturersExist = college.getLecturerCount() > 0;
    boolean departmentsExist = college.getDepartmentCount() > 0;
    if (!lecturersExist || !departmentsExist) {
      String msgReason = !lecturersExist ? MSG_LECTURER : MSG_DEPARTMENT;
      System.err.printf(MSG_FAIL_UNAVAILABLE_OPT,
          String.format(MSG_FAIL_NONE_EXIST, msgReason.toLowerCase()));
      return;
    }

    Lecturer lecturer = promptForLecturer();

    // If left empty, lecturer is removed from their current department and not
    // assigned to a new one
    System.out.printf(MSG_PROMPT,
        MSG_DEPARTMENT_NAME + " (leave empty for no department)");
    String deptName = s.nextLine();
    boolean setNoDept = deptName.isEmpty();
    Department department = setNoDept
        ? null
        : college.getDepartmentByName(deptName);
    if (department == null && !setNoDept) {
      System.err.printf(MSG_FAIL_NOT_EXISTS, MSG_DEPARTMENT);
      return;
    }

    boolean addSuccess = lecturer.setDepartment(department);
    if (addSuccess) {
      if (setNoDept)
        System.out.printf(MSG_SUCCESS_REMOVED_FROM, lecturer.getName(),
            "their department");
      else
        System.out.printf(MSG_SUCCESS_ADDED_TO, lecturer.getName(), deptName);
    } else if (department != null)
      System.err.printf(MSG_FAIL_ALREADY_ADDED, lecturer.getName(),
          department.getName());
  }
  // endregion

  // region Printing info
  private static void showSalaryAvg() {
    double salaryAvg = college.getLecturerSalaryAvg();
    System.out.println("College average salary: " + salaryAvg + "₪");
  }

  private static void showDeptSalaryAvg() {
    boolean departmentsExist = college.getDepartmentCount() > 0;
    if (!departmentsExist) {
      System.err.printf(MSG_FAIL_UNAVAILABLE_OPT,
          String.format(MSG_FAIL_NONE_EXIST, MSG_DEPARTMENT.toLowerCase()));
      return;
    }

    System.out.printf(MSG_PROMPT, MSG_DEPARTMENT_NAME);
    String deptName = s.nextLine();
    Department department = college.getDepartmentByName(deptName);
    if (department == null)
      System.err.printf(MSG_FAIL_NOT_EXISTS, MSG_DEPARTMENT);
    else {
      double deptSalaryAvg = college.getLecturerSalaryAvg(department);
      System.out.println("Department average salary for " + deptName + ": "
          + deptSalaryAvg + "₪");
    }
  }

  // FIXME: CODE DUPLICATION due to different array types :(
  private static void showLecturers() {
    System.out.println("ALL LECTURERS");

    Lecturer[] lecturers = college.getLecturers();
    if (lecturers[0] == null)
      System.err.printf(MSG_FAIL_NONE_EXIST, MSG_LECTURER.toLowerCase());
    else {
      for (int i = 0; i < college.getLecturerCount(); i++) {
        if (lecturers[i] != null)
          System.out.println(lecturers[i]);
      }
    }
  }

  private static void showCommittees() {
    System.out.println("ALL COMMITTEES");

    Committee[] committees = college.getCommittees();
    if (committees[0] == null)
      System.err.printf(MSG_FAIL_NONE_EXIST, MSG_COMMITTEE.toLowerCase());
    else {
      for (int i = 0; i < college.getCommitteeCount(); i++) {
        if (committees[i] != null)
          System.out.println(committees[i]);
      }
    }
  }
  // endregion
}
