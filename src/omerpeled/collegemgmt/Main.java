/**
 * Omer Peled - 209110519
 */

package omerpeled.collegemgmt;

import java.util.Scanner;
import static omerpeled.collegemgmt.Messages.*;

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

      try {
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

          case DUPLICATE_COMMITTEE:
            duplicateCommittee();
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

          default:
            System.err.println("Not implemented yet.");
            break;
        }
      } catch (Exception e) {
        System.err.printf(MSG_FAIL_EXCEPTION, e);
      }

      if (choice != MenuOption.EXIT)
        System.out.println(); // Separate from menu
    } while (choice != MenuOption.EXIT);

    s.close();
  }

  // region Menus
  public enum MenuOption {
    EXIT("Exit"),
    /* LECTURERS */
    ADD_LECTURER("Add lecturer"),
    COMPARE_LECTURERS(String.format("Compare lecturers (%s/%s)",
        Lecturer.Degree.PHD.displayName, Lecturer.Degree.PROF.displayName)),

    /* COMMITTEES */
    ADD_COMMITTEE("Add committee"),
    ADD_COMMITTEE_MEMBER("Add lecturer to committee"),
    SET_COMMITTEE_HEAD("Set committee head"),
    REMOVE_COMMITTEE_MEMBER("Remove lecturer from committee"),
    DUPLICATE_COMMITTEE("Duplicate committee"),
    COMPARE_COMMITTEES("Compare committees"),

    /* DEPARTMENTS */
    ADD_DEPARTMENT("Add department"),
    ADD_DEPARTMENT_LECTURER("Add lecturer to department"),
    SHOW_LECTURER_SALARY_AVG("Show lecturer salary average"),
    SHOW_LECTURER_SALARY_DEPT_AVG("Show lecturer salary average in department"),

    /* DISPLAY DATA */
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
    int choiceIdx = -1;
    boolean isValid = false;
    do {
      System.out.printf(MAIN_MENU, college.getName());
      System.out.printf("\n" + MSG_PROMPT, MSG_CHOICE);
      try {
        // Avoid \n in buffer after Enter
        choiceIdx = Integer.parseInt(s.nextLine());
      } catch (NumberFormatException _) {
        System.err.println(MSG_FAIL_INVALID_CHOICE);
        continue;
      }

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

  // FIXME: CODE DUPLICATION due to different types :(
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

  private static Committee promptForCommittee() {
    String committeeName;
    Committee committee;

    do {
      System.out.printf(MSG_PROMPT, MSG_COMMITTEE_NAME);
      committeeName = s.nextLine();
      committee = college.getCommitteeByName(committeeName);
      if (committee == null)
        System.err.printf(MSG_FAIL_NOT_EXISTS,
            MSG_COMMITTEE + " " + committeeName);
    } while (committee == null);

    return committee;
  }

  private static Department promptForDepartment() {
    String departmentName;
    Department department;

    do {
      System.out.printf(MSG_PROMPT, MSG_DEPARTMENT_NAME);
      departmentName = s.nextLine();
      department = college.getDepartmentByName(departmentName);
      if (department == null)
        System.err.printf(MSG_FAIL_NOT_EXISTS,
            MSG_DEPARTMENT + " " + departmentName);
    } while (department == null);

    return department;
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
      // TODO: Handle NumberFormatException
      double salary = Double.parseDouble(s.nextLine());

      Lecturer newLecturer = new Lecturer(
          id,
          name,
          degree,
          degreeTitle,
          salary);

      // TODO: Handle exception
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
      // TODO: Handle NumberFormatException
      int studentCount = Integer.parseInt(s.nextLine());

      Department newDepartment = new Department(name, studentCount);
      addStatus = college.addDepartment(newDepartment);

      // TODO: Handle exception
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
        // TODO: Handle exceptions
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
    Committee committee = promptForCommittee();

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
    Committee committee = promptForCommittee();

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
    Committee committee = promptForCommittee();

    boolean success = committee.setHead(lecturer);
    if (success)
      System.out.printf(MSG_SUCCESS_COMMITTEE_HEAD_SET, lecturer.getName(),
          committee.getName());
    else
      System.out.printf(MSG_FAIL_INVALID_COMMITTEE_HEAD, lecturer.getName(),
          Lecturer.Degree.PHD.displayName,
          Lecturer.Degree.PROF.displayName);
  }

  private static void duplicateCommittee() throws CloneNotSupportedException {
    if (college.getCommitteeCount() < 1) {
      System.err.printf(MSG_FAIL_UNAVAILABLE_OPT,
          String.format(MSG_FAIL_NONE_EXIST, MSG_COMMITTEE.toLowerCase()));
      return; // TODO: Throw exception here
    }

    Committee committee = promptForCommittee();
    // Already adds members, committee will be added anyway
    Committee dupCommittee = committee.clone();
    dupCommittee.setName(committee.getName() + "-new");

    int newCount = 1;
    College.AddItemStatus addStatus;
    do {
      addStatus = college.addCommittee(dupCommittee);
      // TODO: Handle exception
      if (addStatus == College.AddItemStatus.FAIL_EXISTS) {
        // Rename to "-new2", "-new3", etc, and retry adding with new name
        dupCommittee.setName(committee.getName() + "-new" + (++newCount));
      }
    } while (addStatus != College.AddItemStatus.SUCCESS);

    System.out.printf(MSG_SUCCESS_DUPLICATED, MSG_COMMITTEE,
        dupCommittee.getName());
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

    Department department = promptForDepartment();

    boolean addSuccess = lecturer.setDepartment(department);
    if (addSuccess)
      System.out.printf(MSG_SUCCESS_ADDED_TO, lecturer.getName(),
          department.getName());
    else
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

    Department department = promptForDepartment();
    double deptSalaryAvg = college.getLecturerSalaryAvg(department);
    System.out
        .println("Department average salary for " + department.getName() + ": "
            + deptSalaryAvg + "₪");
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
