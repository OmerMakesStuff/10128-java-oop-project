/**
 * Omer Peled - 209110519
 */

package omerpeled.collegemgmt;

import static omerpeled.collegemgmt.utils.Messages.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import omerpeled.collegemgmt.exceptions.*;

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
            showItems(college.getLecturers());
            break;

          case COMPARE_LECTURERS:
            compareLecturers();
            break;

          case SHOW_COMMITTEES:
            showItems(college.getCommittees());
            break;

          case COMPARE_COMMITTEES:
            compareCommittees();
            break;
        }
      } catch (CollegeException e) {
        // Errors from this app, only message is needed
        System.err.println(e.getMessage());
      } catch (Exception e) {
        System.err.printf(MSG_FAIL_EXCEPTION, e);
      }

      if (choice != MenuOption.EXIT)
        System.out.println(); // Separate from menu
    } while (choice != MenuOption.EXIT);

    s.close();
  }

  // region Menus
  private enum MenuOption {
    EXIT("Exit"),
    /* LECTURERS */
    ADD_LECTURER("Add lecturer"),

    /* COMMITTEES */
    ADD_COMMITTEE("Add committee"),
    ADD_COMMITTEE_MEMBER("Add lecturer to committee"),
    SET_COMMITTEE_HEAD("Set committee head"),
    REMOVE_COMMITTEE_MEMBER("Remove lecturer from committee"),
    DUPLICATE_COMMITTEE("Duplicate committee"),

    /* DEPARTMENTS */
    ADD_DEPARTMENT("Add department"),
    ADD_DEPARTMENT_LECTURER("Add lecturer to department"),
    SHOW_LECTURER_SALARY_AVG("Show lecturer salary average"),
    SHOW_LECTURER_SALARY_DEPT_AVG("Show lecturer salary average in department"),

    /* DISPLAY DATA */
    SHOW_LECTURERS("Show all lecturer details"),
    COMPARE_LECTURERS(String.format(
        "Compare lecturers (%s/%s)",
        Lecturer.Degree.PHD.getDisplayText(),
        Lecturer.Degree.PROF.getDisplayText())),
    SHOW_COMMITTEES("Show all committee details"),
    COMPARE_COMMITTEES("Compare committees");

    private final String displayText;

    MenuOption(String displayText) {
      this.displayText = displayText;
    }

    public String getDisplayText() {
      return displayText;
    }
  }

  static final MenuOption[] MENU_OPTIONS = MenuOption.values();

  private static String buildMainMenuString() {
    StringBuilder str = new StringBuilder(
        "COLLEGE STAFF MANAGEMENT - %s\n\n");
    for (int i = 0; i < MENU_OPTIONS.length; i++) {
      str.append(MENU_OPTIONS[i].ordinal()).append(") ")
          .append(MENU_OPTIONS[i].getDisplayText()).append("\n");
    }
    return str.toString();
  }

  private static final String MAIN_MENU = buildMainMenuString();

  static final Lecturer.Degree[] DEGREE_OPTIONS = Lecturer.Degree.values();

  private static String buildDegreeMenuString() {
    StringBuilder buf = new StringBuilder(
        String.format(MSG_PROMPT_ENUM, MSG_DEGREE.toLowerCase()));
    for (int i = 0; i < DEGREE_OPTIONS.length; i++) {
      buf.append("\n").append(i + 1).append(") ")
          .append(DEGREE_OPTIONS[i].getDisplayText());
    }
    return buf.toString();
  }

  private static final String DEGREE_MENU = buildDegreeMenuString();

  private enum CommitteeSortType {
    MEMBER_COUNT("By number of members (not including head)",
        new CommitteeMemberCountComparator(), "member(s)"),
    ARTICLE_COUNT("By total number of articles published (incl. by head)",
        new CommitteeArticleCountComparator(), MSG_ARTICLE_COUNT_SHORT);

    private String displayText;
    private Comparator<Committee> comparator;
    private String rowSuffix;

    CommitteeSortType(String displayText, Comparator<Committee> comparator,
        String rowSuffix) {
      this.displayText = displayText;
      this.comparator = comparator;
      this.rowSuffix = rowSuffix;
    }

    public String getDisplayText() {
      return displayText;
    }

    public Comparator<Committee> getComparator() {
      return comparator;
    }

    public String getRowSuffix() {
      return rowSuffix;
    }
  }

  static final CommitteeSortType[] COMMITTEE_SORT_OPTIONS = CommitteeSortType
      .values();

  private static String buildCommitteeSortMenuString() {
    StringBuilder buf = new StringBuilder(
        String.format(MSG_PROMPT_ENUM,
            String.format("how to sort %ss", MSG_COMMITTEE.toLowerCase())));
    for (int i = 0; i < COMMITTEE_SORT_OPTIONS.length; i++) {
      buf.append("\n").append(i + 1).append(") ")
          .append(COMMITTEE_SORT_OPTIONS[i].getDisplayText());
    }
    return buf.toString();
  }

  private static final String COMMITTEE_SORT_MENU = buildCommitteeSortMenuString();
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
    int choiceIdx = -1;
    boolean isValid = false;
    do {
      System.out
          .print(DEGREE_MENU + '\n' + String.format(MSG_PROMPT, MSG_CHOICE));
      try {
        choiceIdx = Integer.parseInt(s.nextLine()) - 1;
        isValid = choiceIdx >= 0 && choiceIdx < DEGREE_OPTIONS.length;
        if (!isValid)
          System.err.println(MSG_FAIL_INVALID_CHOICE);
      } catch (NumberFormatException _) {
        System.err.println(MSG_FAIL_INVALID_CHOICE);
      }
    } while (!isValid);

    return DEGREE_OPTIONS[choiceIdx];
  }

  private static CommitteeSortType promptForCommitteeSortType() {
    int choiceIdx = -1;
    boolean isValid = false;
    do {
      System.out
          .print(COMMITTEE_SORT_MENU + '\n'
              + String.format(MSG_PROMPT, MSG_CHOICE));
      try {
        choiceIdx = Integer.parseInt(s.nextLine()) - 1;
        isValid = choiceIdx >= 0 && choiceIdx < COMMITTEE_SORT_OPTIONS.length;
        if (!isValid)
          System.err.println(MSG_FAIL_INVALID_CHOICE);
      } catch (NumberFormatException _) {
        System.err.println(MSG_FAIL_INVALID_CHOICE);
      }
    } while (!isValid);

    return COMMITTEE_SORT_OPTIONS[choiceIdx];
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
        System.err.printf(MSG_FAIL_NOT_EXISTS + "%n",
            String.format(MSG_LECTURER_WITH_ID, lecturerId));
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
        System.err.printf(MSG_FAIL_NOT_EXISTS + "%n",
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
        System.err.printf(MSG_FAIL_NOT_EXISTS + "%n",
            MSG_DEPARTMENT + " " + departmentName);
    } while (department == null);

    return department;
  }

  private static ArrayList<String> promptForArticles() {
    ArrayList<String> result = new ArrayList<String>();
    int count = 0;

    String lastItem;
    do {
      System.out.printf(MSG_PROMPT, MSG_ARTICLE_NAME + " (" + (count + 1) + ")"
          + MSG_PROMPT_EMPTY_TO_FINISH);
      lastItem = s.nextLine();

      if (!(lastItem.isBlank())) {
        result.add(lastItem);
      }
    } while (!(lastItem.isBlank()));

    return result;
  }
  // endregion

  // region New item creation
  private static void addLecturer() {
    boolean addSuccess = false;

    do {
      System.out.printf(MSG_PROMPT, MSG_LECTURER_ID);
      String id = s.nextLine();
      System.out.printf(MSG_PROMPT, MSG_LECTURER + " name");
      String name = s.nextLine();
      Lecturer.Degree degree = promptForDegree();
      System.out.printf(MSG_PROMPT, "degree title");
      String degreeTitle = s.nextLine();
      System.out.printf(MSG_PROMPT, MSG_SALARY.toLowerCase());
      try {
        double salary = Double.parseDouble(s.nextLine());

        Lecturer newLecturer = new Lecturer(
            id,
            name,
            degree,
            degreeTitle,
            salary);
        if (degree == Lecturer.Degree.PHD || degree == Lecturer.Degree.PROF) {
          ArrayList<String> articles = promptForArticles();

          if (degree == Lecturer.Degree.PROF) {
            System.out.printf(MSG_PROMPT, "Prof. awarding body");
            String awardingBodyName = s.nextLine();

            newLecturer = new ProfLecturer(newLecturer, articles,
                awardingBodyName);
          } else
            newLecturer = new PhdLecturer(newLecturer, articles);
        }

        college.addLecturer(newLecturer);
        addSuccess = true;
      } catch (NumberFormatException _) {
        System.err.printf(MSG_FAIL_INPUT_NOT_POSITIVE_NUM + "%n", MSG_SALARY);
      } catch (IllegalArgumentException e) {
        System.err.println(e.getMessage()); // and retry
      }
    } while (!addSuccess);

    System.out.printf(MSG_SUCCESS_CREATED, MSG_LECTURER);
  }

  private static void addDepartment() {
    boolean addSuccess = false;

    do {
      System.out.printf(MSG_PROMPT, MSG_DEPARTMENT_NAME);
      String name = s.nextLine();
      System.out.printf(MSG_PROMPT, "number of students in department");
      try {
        int studentCount = Integer.parseInt(s.nextLine());

        Department newDepartment = new Department(name, studentCount);
        college.addDepartment(newDepartment);
        addSuccess = true;
      } catch (NumberFormatException _) {
        System.err.printf(MSG_FAIL_INPUT_NOT_POSITIVE_INT + "%n",
            MSG_STUDENT_COUNT);
      } catch (IllegalArgumentException e) {
        System.err.println(e.getMessage()); // and retry
      }
    } while (!addSuccess);

    System.out.printf(MSG_SUCCESS_CREATED, MSG_DEPARTMENT);
  }

  private static void addCommittee() {
    if (!college.validCommitteeHeadExists())
      throw new OptionUnavailableException(MSG_FAIL_NO_VALID_COMMITTEE_HEAD);

    boolean addSuccess = false;
    do {
      System.out.printf(MSG_PROMPT, MSG_COMMITTEE_NAME);
      String name = s.nextLine();
      Lecturer head = promptForLecturer("head lecturer ID");

      try {
        Committee newCommittee = new Committee(name, head);
        college.addCommittee(newCommittee);
        addSuccess = true;
      } catch (InvalidCommitteeHeadException e) {
        System.err.println(e.getMessage()); // and retry
      }
    } while (!addSuccess);

    System.out.printf(MSG_SUCCESS_CREATED, MSG_COMMITTEE);
  }
  // endregion

  // region Committees
  private static void addCommitteeMember() {
    if (college.getCommittees().isEmpty())
      throw new OptionUnavailableException(
          String.format(MSG_FAIL_NONE_EXIST, MSG_COMMITTEE.toLowerCase()));

    Lecturer lecturer = promptForLecturer();
    Committee committee = promptForCommittee();

    committee.addMember(lecturer);
    System.out.printf(MSG_SUCCESS_ADDED_TO, lecturer.getName(),
        committee.getName());
  }

  private static void removeCommitteeMember() {
    if (college.getCommittees().isEmpty())
      throw new OptionUnavailableException(
          String.format(MSG_FAIL_NONE_EXIST, MSG_COMMITTEE.toLowerCase()));

    Lecturer lecturer = promptForLecturer();
    Committee committee = promptForCommittee();

    committee.removeMember(lecturer);
    System.out.printf(MSG_SUCCESS_REMOVED_FROM, lecturer.getName(),
        committee.getName());
  }

  private static void setCommitteeHead() {
    if (college.getCommittees().isEmpty())
      throw new OptionUnavailableException(
          String.format(MSG_FAIL_NONE_EXIST, MSG_COMMITTEE.toLowerCase()));

    Lecturer lecturer = promptForLecturer();
    Committee committee = promptForCommittee();

    committee.setHead(lecturer);
    System.out.printf(MSG_SUCCESS_COMMITTEE_HEAD_SET, lecturer.getName(),
        committee.getName());
  }

  private static void duplicateCommittee() throws CloneNotSupportedException {
    if (college.getCommittees().isEmpty())
      throw new OptionUnavailableException(
          String.format(MSG_FAIL_NONE_EXIST, MSG_COMMITTEE.toLowerCase()));

    Committee committee = promptForCommittee();
    Committee dupCommittee = college.duplicateCommittee(committee);

    System.out.printf(MSG_SUCCESS_DUPLICATED, MSG_COMMITTEE,
        dupCommittee.getName());
  }
  // endregion

  // region Departments
  private static void addDeptLecturer() {
    boolean lecturersEmpty = college.getLecturers().isEmpty();
    boolean departmentsEmpty = college.getDepartments().isEmpty();
    if (lecturersEmpty || departmentsEmpty) {
      String msgReason = lecturersEmpty ? MSG_LECTURER : MSG_DEPARTMENT;
      throw new OptionUnavailableException(
          String.format(MSG_FAIL_NONE_EXIST, msgReason.toLowerCase()));
    }

    Lecturer lecturer = promptForLecturer();
    Department department = promptForDepartment();

    lecturer.setDepartment(department);
    System.out.printf(MSG_SUCCESS_ADDED_TO, lecturer.getName(),
        department.getName());
  }
  // endregion

  // region Printing info
  private static void showSalaryAvg() {
    double salaryAvg = college.getLecturerSalaryAvg();
    System.out.println("College average salary: " + salaryAvg + "₪");
  }

  private static void showDeptSalaryAvg() {
    boolean departmentsEmpty = college.getDepartments().isEmpty();
    if (departmentsEmpty)
      throw new OptionUnavailableException(
          String.format(MSG_FAIL_NONE_EXIST, MSG_DEPARTMENT.toLowerCase()));

    Department department = promptForDepartment();
    double deptSalaryAvg = college.getLecturerSalaryAvg(department);
    System.out
        .println("Department average salary for " + department.getName() + ": "
            + deptSalaryAvg + "₪");
  }

  private static <T> void showItems(List<T> items) {
    String typeMsg;
    if (items == college.getLecturers())
      typeMsg = MSG_LECTURER;
    else if (items == college.getCommittees())
      typeMsg = MSG_COMMITTEE;
    else
      throw new IllegalArgumentException(
          "Items argument must be one of: college.getLecturers(), college.getCommittees()");

    System.out.printf("ALL %sS%n", typeMsg.toUpperCase());
    if (items.isEmpty())
      System.err.printf(MSG_FAIL_NONE_EXIST + "%n", typeMsg.toLowerCase());
    else {
      for (int i = 0; i < items.size(); i++) {
        System.out.println(items.get(i));
      }
    }
  }

  private static void compareLecturers() {
    System.out.println("LECTURERS BY ARTICLES PUBLISHED");

    List<ValidCommitteeHead> filtered = college.getValidCommitteeHeads();
    if (filtered.isEmpty())
      System.err.printf(MSG_FAIL_NONE_EXIST + "%n",
          String.format(MSG_VALID, MSG_LECTURER.toLowerCase()));
    else {
      Collections.sort(filtered, new LecturerArticleCountComparator());
      // Print in descending order
      for (int i = filtered.size() - 1; i >= 0; i--) {
        ValidCommitteeHead curr = filtered.get(i);
        System.out.printf("%s - %s %s%n",
            curr.getName(), curr.getArticleCount(),
            MSG_ARTICLE_COUNT_SHORT);
      }
    }
  }

  private static void compareCommittees() {
    List<Committee> committees = college.getCommittees();
    if (committees.isEmpty())
      System.err.printf(MSG_FAIL_NONE_EXIST + "%n",
          MSG_COMMITTEE.toLowerCase());

    // Clone list, so original is not modified
    ArrayList<Committee> sorted = new ArrayList<>(committees);

    CommitteeSortType sortType = promptForCommitteeSortType();
    System.out.println();
    Collections.sort(sorted, sortType.getComparator());
    // Print in descending order
    for (int i = sorted.size() - 1; i >= 0; i--) {
      Committee curr = sorted.get(i);
      System.out.printf(
          "%s - %s %s%n",
          curr.getName(),
          sortType == CommitteeSortType.MEMBER_COUNT
              ? curr.getMembers().size()
              : curr.getTotalArticleCount(),
          sortType.getRowSuffix());
    }
  }
  // endregion
}
