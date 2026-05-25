/**
 * Omer Peled - 209110519
 */

package omerpeled.collegemgmt;

import java.util.Scanner;

public class Main {
  static Scanner s;
  static College college;

  // region Menu options
  public enum MenuOption {
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
  // endregion

  // region I/O utils (printing messages, user input)
  private static void promptForLecturer() {
    Lecturer existingLecturer;
    String name;
    do {
      System.out.print("Enter lecturer name: ");
      name = s.nextLine();

      existingLecturer = college.getLecturerByName(name);
      if (existingLecturer != null)
        System.err.println("Lecturer " + name + " already exists!");
    } while (existingLecturer != null);

    String id;
    do {
      System.out.print("Enter lecturer ID: ");
      id = s.nextLine();

      existingLecturer = college.getLecturerById(id);
      if (existingLecturer != null)
        System.err.println("Lecturer with ID " + id + " already exists!");
    } while (existingLecturer != null);

    // TODO: Prompt for degree (menu)
    // TODO: Enter department, check if exists, leave empty for null
    // TODO: Enter other details

    Lecturer newLecturer = new Lecturer(
        name,
        id,
        Lecturer.Degree.BSC,
        "Dummy",
        1000,
        null);
    college.addLecturer(newLecturer);
  }

  /**
   * Generic prompt for string; Will be removed later.
   */
  private static void promptForItem(College.ItemType type) {

    String newItem;
    College.AddItemStatus addStatus;

    do {
      System.out.print("Enter " + type.displayName.toLowerCase() + " name: ");
      newItem = s.nextLine();
      switch (type) {
        case College.ItemType.COMMITTEE:
          addStatus = college.addCommittee(newItem);
          break;
        case College.ItemType.DEPARTMENT:
          addStatus = college.addDepartment(newItem);
          break;
        default:
          System.out.println(
              "Invalid item type, or it cannot be used with promptForItem.");
          return;
      }

      switch (addStatus) {
        case College.AddItemStatus.FAIL_EXISTS:
          System.err
              .println(type.displayName + " " + newItem + " already exists!");
          break;
        case College.AddItemStatus.SUCCESS:
          System.out.println(type.displayName + " added.");
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
    String[] committees = college.getCommittees();
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

  // region Main
  private static String buildMenu() {
    StringBuffer menuBuf = new StringBuffer(
        "COLLEGE STAFF MANAGEMENT - " + college.getName() + "\n\n");
    for (int i = 0; i < MENU_OPTIONS.length; i++) {
      menuBuf.append(MENU_OPTIONS[i].ordinal()).append(") ")
          .append(MENU_OPTIONS[i].displayText).append("\n");
    }
    return menuBuf.toString();
  }

  public static void main(String[] args) {
    s = new Scanner(System.in);

    System.out.print("Welcome!\nEnter college name: ");
    String collegeName = s.nextLine();
    college = new College(collegeName);
    System.out.println();

    final String MENU = buildMenu();

    int choiceIdx;

    do {
      System.out.print(MENU + "\nEnter choice: ");
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
          promptForItem(College.ItemType.COMMITTEE);
          break;

        case MenuOption.ADD_DEPARTMENT:
          promptForItem(College.ItemType.DEPARTMENT);
          break;

        case MenuOption.ADD_LECTURER_TO_COMM:
          System.out.print("Enter lecturer name: ");
          String lecturerName = s.nextLine();
          Lecturer existingLecturer = college.getLecturerByName(lecturerName);
          if (existingLecturer == null) {
            System.err.println("Lecturer doesn't exist!");
            break;
          }

          System.out.print("Enter committee name: ");
          String committeeName = s.nextLine();
          String existingCommittee = college.getCommitteeByName(committeeName);
          if (existingCommittee == null) {
            System.err.println("Committee doesn't exist!");
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
  // endregion
}
