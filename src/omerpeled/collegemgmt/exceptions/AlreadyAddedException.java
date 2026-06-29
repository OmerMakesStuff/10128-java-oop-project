package omerpeled.collegemgmt.exceptions;

import omerpeled.collegemgmt.Messages;

public class AlreadyAddedException extends RuntimeException {
  public AlreadyAddedException(String addedItem, String addedTo) {
    super(String.format(Messages.MSG_FAIL_ALREADY_ADDED, addedItem, addedTo));
  }
}
