package omerpeled.collegemgmt.exceptions;

import static omerpeled.collegemgmt.Messages.MSG_FAIL_UNAVAILABLE_OPT;

/**
 * Thrown when attempting to use functionality in the app that cannot be used
 * yet. For example, adding a lecturer to a department when no departments
 * exist.
 */
public class OptionUnavailableException extends CollegeException {
  public OptionUnavailableException(String detail) {
    super(String.format(MSG_FAIL_UNAVAILABLE_OPT, detail));
  }
}
