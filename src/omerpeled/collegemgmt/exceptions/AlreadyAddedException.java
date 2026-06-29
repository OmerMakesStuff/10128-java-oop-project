package omerpeled.collegemgmt.exceptions;

import static omerpeled.collegemgmt.Messages.MSG_FAIL_ALREADY_ADDED;

/**
 * Thrown when attempting to add an item to an object, but that item has already
 * been added.
 */
public class AlreadyAddedException extends CollegeException {
  public AlreadyAddedException(String addedItem, String addedTo) {
    super(String.format(MSG_FAIL_ALREADY_ADDED, addedItem, addedTo));
  }
}
