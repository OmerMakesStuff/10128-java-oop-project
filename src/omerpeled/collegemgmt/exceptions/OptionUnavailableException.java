package omerpeled.collegemgmt.exceptions;

import omerpeled.collegemgmt.Messages;

public class OptionUnavailableException extends RuntimeException {
  public OptionUnavailableException(String detail) {
    super(String.format(Messages.MSG_FAIL_UNAVAILABLE_OPT, detail));
  }
}
