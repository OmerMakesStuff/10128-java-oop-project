package omerpeled.collegemgmt.exceptions;

import omerpeled.collegemgmt.Messages;

public class ItemExistsException extends RuntimeException {
  public ItemExistsException(String type) {
    super(String.format(Messages.MSG_FAIL_EXISTS, type));
  }
}
