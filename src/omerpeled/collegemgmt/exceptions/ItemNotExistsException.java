package omerpeled.collegemgmt.exceptions;

import static omerpeled.collegemgmt.utils.Messages.MSG_FAIL_NOT_EXISTS;

/**
 * Thrown when attempting to find an existing item, but an item with that
 * identifier (name/ID) does not exist.
 */
public class ItemNotExistsException extends CollegeException {
  public ItemNotExistsException(String type) {
    super(String.format(MSG_FAIL_NOT_EXISTS, type));
  }
}
