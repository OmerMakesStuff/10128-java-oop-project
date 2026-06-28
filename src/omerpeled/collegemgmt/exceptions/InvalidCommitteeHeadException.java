package omerpeled.collegemgmt.exceptions;

import omerpeled.collegemgmt.Lecturer;
import omerpeled.collegemgmt.Messages;

public class InvalidCommitteeHeadException extends IllegalArgumentException {
  public InvalidCommitteeHeadException(Lecturer lecturer) {
    super(String.format(
        Messages.MSG_FAIL_INVALID_COMMITTEE_HEAD,
        lecturer.getName(),
        Lecturer.Degree.PHD.getDisplayName(),
        Lecturer.Degree.PROF.getDisplayName()));
  }
}
