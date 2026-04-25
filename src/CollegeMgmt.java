import java.util.Scanner;

/**
 * Omer Peled - 209110519
 */
public class CollegeMgmt {
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

      alreadyExists = false;
      for (int i = 0; i < items.length; i++) {
        if (items[i].equals(newItem))
          alreadyExists = true;
      }

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

    Scanner s = new Scanner(System.in);
    int choice = 0;

    do {
      System.out.print(MENU + "\nEnter choice: ");
      choice = Integer.parseInt(s.nextLine()); // Avoid \n in buffer after Enter
      System.out.println();

      switch (choice) {
      case 1:
        String lecturerName;
        boolean alreadyExists;

        do {
          System.out.print("Enter lecturer name: ");
          lecturerName = s.nextLine();

          alreadyExists = false;
          for (int i = 0; i < lecturers.length; i++) {
            if (lecturers[i].equals(lecturerName))
              alreadyExists = true;
          }

          if (alreadyExists)
            System.err.println("Lecturer " + lecturerName + " already exists!");
        } while (alreadyExists);

        lecturerCount++;
        // Skip resizing array?
        if (lecturerCount <= lecturers.length)
          break;

        String[] newLecturers = new String[lecturers.length * 2];
        for (int i = 0; i < lecturers.length; i++) {
          newLecturers[i] = lecturers[i];
        }
        newLecturers[lecturerCount - 1] = lecturerName;
        lecturers = newLecturers;
        System.out.println("Lecturer added.");
        break;

      case 5:
      case 6:
        System.err.println("Not implemented yet.");
        break;

      case 7:
        if (lecturers.length < 1) {
          System.err.println("No lecturers added.");
          break;
        }

        System.out.println("ALL LECTURERS");
        for (int i = 0; i < lecturers.length; i++) {
          System.out.println(lecturers[i]);
        }
        break;

      // TODO: Reorder
      case 2:
      case 3:
      case 4:
      case 8:
        System.out.println("TODO: Option " + choice);
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
