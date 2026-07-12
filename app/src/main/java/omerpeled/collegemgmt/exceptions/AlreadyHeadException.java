package omerpeled.collegemgmt.exceptions;

import static omerpeled.collegemgmt.utils.Messages.MSG_FAIL_ALREADY_HEAD;

import omerpeled.collegemgmt.Committee;
import omerpeled.collegemgmt.ValidCommitteeHead;

/**
 * Thrown when attempting to set a lecturer as committee head, but they are
 * already the head of that committee.
 */
public class AlreadyHeadException extends CollegeException {
  public AlreadyHeadException(ValidCommitteeHead lecturer,
      Committee committee) {
    super(String.format(MSG_FAIL_ALREADY_HEAD, lecturer.getName(),
        committee.getName()));
  }
}
