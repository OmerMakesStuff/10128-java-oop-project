package omerpeled.collegemgmt.exceptions;

import omerpeled.collegemgmt.Messages;

public class ItemNotExistsException extends RuntimeException {
  public ItemNotExistsException(String type) {
    super(String.format(Messages.MSG_FAIL_NOT_EXISTS, type));
  }
}
