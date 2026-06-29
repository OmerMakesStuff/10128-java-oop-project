package omerpeled.collegemgmt.exceptions;

import omerpeled.collegemgmt.Messages;

/**
 * Thrown when attempting to remove an item from an object, but that item wasn't
 * already added to it.
 */
public class NotAddedException extends RuntimeException {
  public NotAddedException(String addedItem, String addedTo) {
    super(String.format(Messages.MSG_FAIL_NOT_ADDED, addedItem, addedTo));
  }
}
