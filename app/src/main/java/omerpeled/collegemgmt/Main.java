/**
 * Omer Peled - 209110519
 */

package omerpeled.collegemgmt;

import static omerpeled.collegemgmt.utils.Messages.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import omerpeled.collegemgmt.exceptions.*;
import omerpeled.collegemgmt.utils.MenuOption;

public class Main {
  private static final String DEFAULT_FILENAME = "college.dat";

  static Scanner s;
  static College college;

  // File load/save related args
  private static String filename;
  private static boolean noLoad;
  private static boolean noSave;

  public static void main(String[] args) {
    parseArgs(args);
    s = new Scanner(System.in);
    college = loadCollege();

    MainMenuOption choice;
    do {
      choice = promptForMenuOption(MainMenuOption.class);
      if (choice != MainMenuOption.EXIT)
        System.out.println(); // Separate from menu

      try {
        switch (choice) {
          case EXIT:
            saveCollege();
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

      if (choice != MainMenuOption.EXIT)
        System.out.println(); // Separate from menu
    } while (choice != MainMenuOption.EXIT);

    s.close();
  }

  // region Menus
  private enum MainMenuOption implements MenuOption {
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

    MainMenuOption(String displayText) {
      this.displayText = displayText;
    }

    @Override
    public String getDisplayText() {
      return displayText;
    }

    @Override
    public String getPromptTitle() {
      return String.format("COLLEGE STAFF MANAGEMENT - %s%n",
          college.getName());
    }
  }

  private enum CommitteeSortType implements MenuOption {
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

    @Override
    public String getDisplayText() {
      return displayText;
    }

    public Comparator<Committee> getComparator() {
      return comparator;
    }

    public String getRowSuffix() {
      return rowSuffix;
    }

    @Override
    public String getPromptTitle() {
      return String.format(MSG_PROMPT_ENUM,
          String.format("how to sort %ss", MSG_COMMITTEE.toLowerCase()));
    }
  }

  static final CommitteeSortType[] COMMITTEE_SORT_OPTIONS = CommitteeSortType
      .values();

  private static <T extends Enum<T> & MenuOption> String buildMenuString(
      Class<T> enumClass, int optionOffset) {
    T[] options = enumClass.getEnumConstants();
    if (options == null || options.length < 1)
      throw new IllegalArgumentException(
          "enumClass is not an enum or has no constants");

    StringBuilder str = new StringBuilder(options[0].getPromptTitle());
    for (int i = 0; i < options.length; i++) {
      str.append("\n").append(i + optionOffset).append(") ")
          .append(options[i].getDisplayText());
    }
    return str.toString();
  }
  // endregion

  // region Prompt for types
  private static <T extends Enum<T> & MenuOption> T promptForMenuOption(
      Class<T> enumClass) {
    return promptForMenuOption(enumClass, 0);
  }

  private static <T extends Enum<T> & MenuOption> T promptForMenuOption(
      Class<T> enumClass, int optionOffset) {
    T[] options = enumClass.getEnumConstants();
    if (options == null || options.length < 1)
      throw new IllegalArgumentException(
          "enumClass is not an enum or has no constants");

    int choiceIdx = -1;
    boolean isValid = false;
    do {
      System.out
          .print(buildMenuString(enumClass, optionOffset) + '\n'
              + String.format(MSG_PROMPT, MSG_CHOICE));
      try {
        // Avoid \n in buffer after Enter
        choiceIdx = Integer.parseInt(s.nextLine()) - optionOffset;
        isValid = choiceIdx >= 0 && choiceIdx < options.length;
        if (!isValid)
          System.err.println(MSG_FAIL_INVALID_CHOICE);
      } catch (NumberFormatException _) {
        System.err.println(MSG_FAIL_INVALID_CHOICE);
      }
    } while (!isValid);

    return options[choiceIdx];
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

      if (!(lastItem.isBlank()))
        result.add(lastItem);
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
      Lecturer.Degree degree = promptForMenuOption(Lecturer.Degree.class, 1);
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
      Committee.MemberDegree memberDegree = promptForMenuOption(
          Committee.MemberDegree.class, 1);

      try {
        Committee newCommittee = new Committee(name, head, memberDegree);
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

    CommitteeSortType sortType = promptForMenuOption(CommitteeSortType.class,
        1);
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

  // region Data load & save
  private static College loadNewCollege() {
    System.out.print("Welcome!\nEnter college name: ");
    String collegeName = s.nextLine();
    return new College(collegeName);
  }

  private static College loadCollege() {
    if (noLoad) // Start fresh if --no-load arg was passed
      return loadNewCollege();

    String loadFilename = filename != null ? filename : DEFAULT_FILENAME;
    try (ObjectInputStream inFile = new ObjectInputStream(
        new FileInputStream(loadFilename))) {
      Object loadedData = inFile.readObject();
      if (loadedData instanceof College loadedCollege)
        return loadedCollege;
      else
        System.err.printf("Error: File %s is in an invalid format.",
            loadFilename);
    } catch (FileNotFoundException _) {
      if (filename != null)
        System.err
            .printf("Error: File %s not found.%n%n", loadFilename);
    } catch (IOException | ClassNotFoundException e) {
      System.err.printf("Could not load file %s: %s%n%n", loadFilename,
          e.getMessage());
    }

    // If loading failed, start fresh instead
    return loadNewCollege();
  }

  private static void saveCollege() throws IOException {
    if (noSave) // Skip saving if --no-save arg was passed
      return;

    String saveFilename = filename != null ? filename : DEFAULT_FILENAME;

    try (ObjectOutputStream outFile = new ObjectOutputStream(
        new FileOutputStream(saveFilename))) {
      outFile.writeObject(college);
    } catch (FileNotFoundException e) {
      System.err
          .println("Could not save to file: " + e);
    }
  }
  // endregion

  // region Parse args
  private static void parseArgs(String[] args) {
    // Default values
    filename = null;
    noLoad = false;
    noSave = false;

    for (int i = 0; i < args.length; i++) {
      String arg = args[i];
      if (arg.equals("--no-load"))
        noLoad = true;
      else if (arg.equals("--no-save"))
        noSave = true;
      else if (filename == null)
        filename = arg;
    }

    // Passing a filename with both --no-load and --no-save is pointless,
    // what would you do with the filename?
    if (filename != null && noLoad && noSave) { // NOSONAR: false positive
      System.err.println(
          "Filename may not be provided alongside both --no-load and --no-save.");
      System.exit(1);
    }
  }
  // endregion
}
