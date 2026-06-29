package omerpeled.collegemgmt.exceptions;

import omerpeled.collegemgmt.Lecturer;
import omerpeled.collegemgmt.Messages;

/**
 * Thrown when attempting to set a lecturer as a committee head, but they're not
 * a valid committee head (PhD/Prof).
 */
public class InvalidCommitteeHeadException extends CollegeException {
  public InvalidCommitteeHeadException(Lecturer lecturer) {
    super(String.format(
        Messages.MSG_FAIL_INVALID_COMMITTEE_HEAD,
        lecturer.getName(),
        Lecturer.Degree.PHD.getDisplayName(),
        Lecturer.Degree.PROF.getDisplayName()));
  }
}
