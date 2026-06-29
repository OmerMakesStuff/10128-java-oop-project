package omerpeled.collegemgmt.exceptions;

import static omerpeled.collegemgmt.Messages.MSG_FAIL_EXISTS;

/**
 * Thrown when attempting to create a new item with an identifier (name/ID) that
 * already exists.
 */
public class ItemExistsException extends CollegeException {
  public ItemExistsException(String type) {
    super(String.format(MSG_FAIL_EXISTS, type));
  }
}
