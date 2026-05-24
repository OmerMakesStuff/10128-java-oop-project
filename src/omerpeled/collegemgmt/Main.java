/**
 * Omer Peled - 209110519
 */

package omerpeled.collegemgmt;

import java.util.Scanner;

public class Main {
  private static Scanner s;

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

  public static final MenuOption[] MENU_OPTIONS = MenuOption.values();
  // endregion

  // region General string array utils
  public static boolean isArrayEmpty(String[] items) {
    if (items.length < 1)
      return true;
    // Arrays with only null are considered empty
    for (int i = 0; i < items.length; i++) {
      if (items[i] != null)
        return false;
    }
    return true;
  }

  public static boolean itemExists(String[] items, String item) {
    for (int i = 0; i < items.length; i++) {
      if (items[i] != null && items[i].equals(item))
        return true;
    }
    return false;
  }

  public static String[] addItem(String[] items, int logicalSize,
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
  // endregion

  // region I/O utils (printing messages, user input)
  public static String promptForItem(String[] items, Scanner s,
      String promptMsg, String alreadyExistsMsg) {
    String newItem;
    boolean alreadyExists;

    do {
      System.out.print(promptMsg);
      newItem = s.nextLine();

      alreadyExists = itemExists(items, newItem);
      if (alreadyExists)
        System.err.println(alreadyExistsMsg);
    } while (alreadyExists);

    return newItem;
  }

  public static void printItems(String[] items) {
    if (isArrayEmpty(items))
      System.err.println("None exist.");
    else {
      for (int i = 0; i < items.length; i++) {
        if (items[i] != null)
          System.out.println(items[i]);
      }
    }
  }
  // endregion

  // region Main
  public static void main(String[] args) {
    StringBuffer menuBuf = new StringBuffer("COLLEGE STAFF MANAGEMENT\n\n");
    for (int i = 0; i < MENU_OPTIONS.length; i++) {
      menuBuf.append(MENU_OPTIONS[i].ordinal() + ") "
          + MENU_OPTIONS[i].displayText + "\n");
    }
    final String MENU = menuBuf.toString();

    String[] lecturers = new String[1];
    int lecturerCount = 0;
    String[] committees = new String[1];
    int committeeCount = 0;
    String[] departments = new String[1];
    int departmentCount = 0;

    s = new Scanner(System.in);
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
          String newLecturer = promptForItem(lecturers, s,
              "Enter lecturer name: ", "Lecturer already exists!");
          lecturers = addItem(lecturers, lecturerCount, newLecturer);
          lecturerCount++;
          System.out.println("Lecturer added.");
          break;

        case MenuOption.ADD_COMMITTEE:
          String newCommittee = promptForItem(committees, s,
              "Enter committee name: ", "Committee already exists!");
          committees = addItem(committees, committeeCount, newCommittee);
          committeeCount++;
          System.out.println("committee added.");
          break;

        case MenuOption.ADD_DEPARTMENT:
          String newDepartment = promptForItem(departments, s,
              "Enter department name: ", "Department already exists!");
          departments = addItem(departments, departmentCount, newDepartment);
          departmentCount++;
          System.out.println("department added.");
          break;

        case MenuOption.ADD_LECTURER_TO_COMM:
          System.out.print("Enter lecturer name: ");
          String lecturerName = s.nextLine();
          boolean lecturerExists = itemExists(lecturers, lecturerName);
          if (!lecturerExists) {
            System.err.println("Lecturer doesn't exist!");
            break;
          }

          System.out.print("Enter committee name: ");
          String committeeName = s.nextLine();
          boolean committeeExists = itemExists(committees, committeeName);
          if (!committeeExists)
            System.err.println("Committee doesn't exist!");

          // TODO: Rest of implementation

          break;

        case SHOW_LECTURER_SALARY_AVG:
        case SHOW_LECTURER_SALARY_DEPT_AVG:
          System.err.println("Not implemented yet.");
          break;

        case SHOW_LECTURERS:
          System.out.println("ALL LECTURERS");
          printItems(lecturers);
          break;

        case SHOW_COMMITTEES:
          System.out.println("ALL COMMITTEES");
          printItems(committees);
          break;
      }

      System.out.println();
    } while (choiceIdx != 0);

    s.close();
  }
  // endregion
}
