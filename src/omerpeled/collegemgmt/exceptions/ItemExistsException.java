package omerpeled.collegemgmt.exceptions;

import omerpeled.collegemgmt.Messages;

/**
 * Thrown when attempting to create a new item with an identifier (name/ID) that
 * already exists.
 */
public class ItemExistsException extends CollegeException {
  public ItemExistsException(String type) {
    super(String.format(Messages.MSG_FAIL_EXISTS, type));
  }
}
