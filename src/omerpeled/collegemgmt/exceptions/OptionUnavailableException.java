package omerpeled.collegemgmt.exceptions;

import omerpeled.collegemgmt.Messages;

/**
 * Thrown when attempting to use functionality in the app that cannot be used
 * yet. For example, adding a lecturer to a department when no departments
 * exist.
 */
public class OptionUnavailableException extends RuntimeException {
  public OptionUnavailableException(String detail) {
    super(String.format(Messages.MSG_FAIL_UNAVAILABLE_OPT, detail));
  }
}
