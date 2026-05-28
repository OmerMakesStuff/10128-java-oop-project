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
    System.out.println();

    final String MENU = buildMenuString();

    int choiceIdx;

    do {
      System.out.println(MENU);
      System.out.printf(MSG_PROMPT, "choice");
      // Avoid \n in buffer after Enter
      choiceIdx = Integer.parseInt(s.nextLine());
      System.out.println();

      // Handle choices not covered by MenuOption enum
      if (choiceIdx < 0 || choiceIdx >= MENU_OPTIONS.length) {
        System.err.println("Invalid choice!");
        continue;
      }

      MenuOption choice = MENU_OPTIONS[choiceIdx];
      switch (choice) {
        case EXIT:
          break; // Just exit - choiceIdx = 0

        case MenuOption.ADD_LECTURER:
          promptForLecturer();
          break;

        case MenuOption.ADD_COMMITTEE:
          promptForCommittee();
          break;

        case MenuOption.ADD_DEPARTMENT:
          promptForDepartment();
          break;

        case MenuOption.ADD_LECTURER_TO_COMM:
          System.out.print("Enter lecturer name: ");
          String lecturerName = s.nextLine();
          Lecturer existingLecturer = college.getLecturerByName(lecturerName);
          if (existingLecturer == null) {
            System.err.printf(MSG_FAIL_NOT_EXISTS, "Lecturer");
            break;
          }

          System.out.print("Enter committee name: ");
          String committeeName = s.nextLine();
          Committee existingCommittee = college
              .getCommitteeByName(committeeName);
          if (existingCommittee == null) {
            System.err.printf(MSG_FAIL_NOT_EXISTS, "Committee");
            break;
          }

          // TODO: Rest of implementation

          break;

        case SHOW_LECTURER_SALARY_AVG, SHOW_LECTURER_SALARY_DEPT_AVG:
          System.err.println("Not implemented yet.");
          break;

        case SHOW_LECTURERS:
          System.out.println("ALL LECTURERS");
          printLecturers();
          break;

        case SHOW_COMMITTEES:
          System.out.println("ALL COMMITTEES");
          printCommittees();
          break;
      }

      System.out.println();
    } while (choiceIdx != 0);

    s.close();
  }

  // region Messages
  private static final String MSG_PROMPT = "Enter %s: ";
  private static final String MSG_SUCCESS_ADD = "%s added.\n";
  private static final String MSG_FAIL_EXISTS = "%s already exists!\n";
  private static final String MSG_FAIL_NOT_EXISTS = "%s doesn't exist!\n";
  // endregion

  // region Menus
  public enum MenuOption {
    // TODO: Add missing menu options, make sure to order correctly
    EXIT("Exit"),
    ADD_LECTURER("Add lecturer"),
    ADD_COMMITTEE("Add committee"),
    ADD_DEPARTMENT("Add department"),
    ADD_LECTURER_TO_COMM("Add lecturer to committee"),
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

  private static String buildMenuString() {
    StringBuilder menuBuf = new StringBuilder(
        "COLLEGE STAFF MANAGEMENT - " + college.getName() + "\n\n");
    for (int i = 0; i < MENU_OPTIONS.length; i++) {
      menuBuf.append(MENU_OPTIONS[i].ordinal()).append(") ")
          .append(MENU_OPTIONS[i].displayText).append("\n");
    }
    return menuBuf.toString();
  }

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

  // region I/O (printing messages, user input)

  private static Lecturer.Degree promptForDegree() {
    int choiceIdx;
    boolean isValid = false;
    do {
      System.out.print(DEGREE_MENU + '\n' + MSG_PROMPT.replace("%s", "choice"));
      choiceIdx = Integer.parseInt(s.nextLine()) - 1;
      isValid = choiceIdx >= 0 && choiceIdx < DEGREE_OPTIONS.length;
      if (!isValid)
        System.err.println("Invalid choice!");
    } while (!isValid);

    return DEGREE_OPTIONS[choiceIdx];
  }

  private static void promptForLecturer() {
    College.AddItemStatus addStatus;

    do {
      System.out.printf(MSG_PROMPT, "lecturer ID");
      String id = s.nextLine();
      System.out.printf(MSG_PROMPT, "lecturer name");
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
          salary,
          null); // Unassigned to dept by default

      addStatus = college.addLecturer(newLecturer);
      switch (addStatus) {
        case College.AddItemStatus.FAIL_EXISTS:
          System.err.printf(MSG_FAIL_EXISTS, "Lecturer with ID " + id);
          System.err.println();
          break;
        case College.AddItemStatus.SUCCESS:
          System.out.printf(MSG_SUCCESS_ADD, "Lecturer");
          break;
      }
    } while (addStatus != College.AddItemStatus.SUCCESS);
  }

  private static void promptForDepartment() {
    College.AddItemStatus addStatus;

    do {
      System.out.printf(MSG_PROMPT, "department name");
      String name = s.nextLine();
      System.out.printf(MSG_PROMPT, "number of students in department");
      int studentCount = Integer.parseInt(s.nextLine());

      Department newDepartment = new Department(name, studentCount);
      addStatus = college.addDepartment(newDepartment);
      switch (addStatus) {
        case College.AddItemStatus.FAIL_EXISTS:
          System.err.printf(MSG_FAIL_EXISTS, "Department " + name);
          System.err.println();
          break;
        case College.AddItemStatus.SUCCESS:
          System.out.printf(MSG_SUCCESS_ADD, "Department");
          break;
      }
    } while (addStatus != College.AddItemStatus.SUCCESS);
  }

  private static void promptForCommittee() {
    College.AddItemStatus addStatus;

    do {
      System.out.printf(MSG_PROMPT, "committee name");
      String name = s.nextLine();
      // TODO: Set head of committee
      Committee newCommittee = new Committee(name, null);
      addStatus = college.addCommittee(newCommittee);
      switch (addStatus) {
        case College.AddItemStatus.FAIL_EXISTS:
          System.err.printf(MSG_FAIL_EXISTS, "Committee " + name);
          System.err.println();
          break;
        case College.AddItemStatus.SUCCESS:
          System.out.printf(MSG_SUCCESS_ADD, "Committee");
          break;
      }
    } while (addStatus != College.AddItemStatus.SUCCESS);
  }

  // FIXME: CODE DUPLICATION due to different array types :(
  // Will be unified when I can use generics
  private static void printLecturers() {
    Lecturer[] lecturers = college.getLecturers();
    if (lecturers[0] == null)
      System.err.println("No lecturers exist.");
    else {
      for (int i = 0; i < lecturers.length; i++) {
        if (lecturers[i] != null)
          System.out.println(lecturers[i]);
      }
    }
  }

  private static void printCommittees() {
    Committee[] committees = college.getCommittees();
    if (committees[0] == null)
      System.err.println("No committees exist.");
    else {
      for (int i = 0; i < committees.length; i++) {
        if (committees[i] != null)
          System.out.println(committees[i]);
      }
    }
  }
  // endregion
}
