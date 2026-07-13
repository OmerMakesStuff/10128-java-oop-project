package omerpeled.collegemgmt.exceptions;

import static omerpeled.collegemgmt.utils.Messages.MSG_FAIL_INVALID_COMMITTEE_HEAD;
import static omerpeled.collegemgmt.utils.Messages.MSG_FAIL_INVALID_COMMITTEE_MEMBER;

import omerpeled.collegemgmt.Committee;
import omerpeled.collegemgmt.Lecturer;

/**
 * Thrown when attempting to add a lecturer to a committee, but their degree
 * doesn't match the committee's member degree.
 */
public class InvalidCommitteeMemberException extends CollegeException {
  public InvalidCommitteeMemberException(Committee committee,
      Lecturer lecturer) {
    super(String.format(
        MSG_FAIL_INVALID_COMMITTEE_MEMBER,
        lecturer.getName(),
        committee.getMemberDegree().getDisplayText()));
  }
}
