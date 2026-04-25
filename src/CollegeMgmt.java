import java.util.Scanner;

/**
 * Omer Peled - 209110519
 */
public class CollegeMgmt {
  final static String MENU = """
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

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);

    String[] lecturers = new String[0];
    int lecturerCount = 0;
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
      case 5:
      case 6:
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
