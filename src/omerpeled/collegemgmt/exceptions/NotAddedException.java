package omerpeled.collegemgmt.exceptions;

import static omerpeled.collegemgmt.utils.Messages.MSG_FAIL_NOT_ADDED;

/**
 * Thrown when attempting to remove an item from an object, but that item wasn't
 * already added to it.
 */
public class NotAddedException extends CollegeException {
  public NotAddedException(String addedItem, String addedTo) {
    super(String.format(MSG_FAIL_NOT_ADDED, addedItem, addedTo));
  }
}
