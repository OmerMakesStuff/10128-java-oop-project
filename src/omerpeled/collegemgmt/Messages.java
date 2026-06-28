package omerpeled.collegemgmt;

public class Messages {
  private Messages() {
    throw new UnsupportedOperationException(
        "Utility class cannot be instantiated");
  }

  public static final String MSG_PROMPT = "Enter %s: ";

  public static final String MSG_SUCCESS_CREATED = "%s added.%n";
  public static final String MSG_SUCCESS_DUPLICATED = "%s duplicated as %s.%n";
  public static final String MSG_SUCCESS_ADDED_TO = "%s has been added to %s.%n";
  public static final String MSG_SUCCESS_REMOVED_FROM = "%s has been removed from %s.%n";
  public static final String MSG_SUCCESS_COMMITTEE_HEAD_SET = "%s is now the head of %s.%n";

  public static final String MSG_FAIL_EXISTS = "%s already exists!";
  public static final String MSG_FAIL_NOT_EXISTS = "%s doesn't exist!";
  public static final String MSG_FAIL_NONE_EXIST = "No %ss exist.%n";
  public static final String MSG_FAIL_ALREADY_ADDED = "%s is already in %s!%n";
  public static final String MSG_FAIL_NOT_ADDED = "%s is not in %s!%n";
  public static final String MSG_FAIL_NO_VALID_COMMITTEE_HEAD = "valid committee head doesn't exist.\n";
  public static final String MSG_FAIL_INVALID_COMMITTEE_HEAD = "%s cannot be a committee head! (degree must be %s or %s)";
  public static final String MSG_FAIL_REMOVE_COMMITTEE_HEAD = "%s is the head of %s! To remove them, a new head must be set first.%n";
  public static final String MSG_FAIL_INVALID_CHOICE = "Invalid choice!\n";
  public static final String MSG_FAIL_UNAVAILABLE_OPT = "Option unavailable - %s";
  public static final String MSG_FAIL_EXCEPTION = "Error: %s%n";

  public static final String MSG_CHOICE = "choice";
  public static final String MSG_LECTURER = "Lecturer";
  public static final String MSG_LECTURER_ID = "lecturer ID";
  public static final String MSG_LECTURER_WITH_ID = MSG_LECTURER
      + " with ID %s";
  public static final String MSG_COMMITTEE = "Committee";
  public static final String MSG_COMMITTEE_NAME = "committee name";
  public static final String MSG_DEPARTMENT = "Department";
  public static final String MSG_DEPARTMENT_NAME = "department name";
}
