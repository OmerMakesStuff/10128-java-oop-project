package omerpeled.collegemgmt.exceptions;

import static omerpeled.collegemgmt.utils.Messages.MSG_FAIL_REMOVE_COMMITTEE_HEAD;

import omerpeled.collegemgmt.Committee;
import omerpeled.collegemgmt.Lecturer;

/**
 * Thrown when attempting to remove a lecturer from a committee they're the head
 * of.
 */
public class RemoveCommitteeHeadException extends CollegeException {
  public RemoveCommitteeHeadException(Lecturer lecturer, Committee committee) {
    super(String.format(MSG_FAIL_REMOVE_COMMITTEE_HEAD, lecturer.getName(),
        committee.getName()));
  }
}
