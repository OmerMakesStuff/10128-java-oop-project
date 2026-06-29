package omerpeled.collegemgmt.exceptions;

import omerpeled.collegemgmt.Messages;

/**
 * Thrown when attempting to add an item to an object, but that item has already
 * been added.
 */
public class AlreadyAddedException extends RuntimeException {
  public AlreadyAddedException(String addedItem, String addedTo) {
    super(String.format(Messages.MSG_FAIL_ALREADY_ADDED, addedItem, addedTo));
  }
}
