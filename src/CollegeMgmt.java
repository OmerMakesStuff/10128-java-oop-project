import java.util.Scanner;

/**
 * Omer Peled - 209110519
 */
public class CollegeMgmt {
  public static boolean itemExists(String[] items, String item) {
    for (int i = 0; i < items.length; i++) {
      if (items[i] != null && items[i].equals(item))
        return true;
    }
    return false;
  }

  public static String promptForItem(String[] items, Scanner s,
      String promptMsg, String alreadyExistsMsg) {
    String newItem;
    boolean alreadyExists;

    do {
      System.out.print(promptMsg);
      newItem = s.nextLine();

      // If array is empty, skip checking if already exists
      // Since array length always needs to be doubled when resized,
      // it starts at 1
      boolean isEmpty = (items.length == 1) && (items[0] == null);
      if (isEmpty)
        return newItem;

      alreadyExists = itemExists(items, newItem);
      if (alreadyExists)
        System.err.println(alreadyExistsMsg);
    } while (alreadyExists);

    return newItem;
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

  public static void printItems(String[] items) {
    // Since array length always needs to be doubled when resized,
    // it starts at 1
    boolean isEmpty = (items.length == 1) && (items[0] == null);
    if (isEmpty)
      System.err.println("None exist.");
    else {
      for (int i = 0; i < items.length; i++) {
        if (items[i] != null)
          System.out.println(items[i]);
      }
    }
  }

  public static void main(String[] args) {
    final String MENU = """
        COLLEGE STAFF MANAGEMENT

        1 - Add lecturer
        2 - Add committee
        3 - Add department
        4 - Add lecturer to department
        5 - Show lecturer salary average
        6 - Show lecturer salary average in department
        7 - Show all lecturer details
        8 - Show all committee details

        0 - Exit
        """;

    String[] lecturers = new String[1];
    int lecturerCount = 0;
    String[] committees = new String[1];
    int committeeCount = 0;
    String[] departments = new String[1];
    int departmentCount = 0;

    Scanner s = new Scanner(System.in);
    int choice = 0;

    do {
      System.out.print(MENU + "\nEnter choice: ");
      choice = Integer.parseInt(s.nextLine()); // Avoid \n in buffer after Enter
      System.out.println();

      switch (choice) {
      case 1:
        String newLecturer = promptForItem(lecturers, s,
            "Enter lecturer name: ", "Lecturer already exists!");
        lecturers = addItem(lecturers, lecturerCount, newLecturer);
        lecturerCount++;
        System.out.println("Lecturer added.");
        break;

      case 2:
        String newCommittee = promptForItem(committees, s,
            "Enter committee name: ", "Committee already exists!");
        committees = addItem(committees, committeeCount, newCommittee);
        committeeCount++;
        System.out.println("committee added.");
        break;

      case 3:
        String newDepartment = promptForItem(departments, s,
            "Enter department name: ", "Department already exists!");
        departments = addItem(departments, departmentCount, newDepartment);
        departmentCount++;
        System.out.println("department added.");
        break;

      case 4:
        System.out.println("TODO: Option " + choice);
        break;

      case 5:
      case 6:
        System.err.println("Not implemented yet.");
        break;

      case 7:
        System.out.println("ALL LECTURERS");
        printItems(lecturers);
        break;

      case 8:
        System.out.println("ALL COMMITTEES");
        printItems(committees);
        break;

      case 0:
        break; // Just exit

      default:
        System.out.println("Invalid choice!");
        break;
      }

      System.out.println();
    } while (choice != 0);

    s.close();
  }
}
