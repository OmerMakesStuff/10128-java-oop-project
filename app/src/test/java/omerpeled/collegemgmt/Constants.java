package omerpeled.collegemgmt;

import static omerpeled.collegemgmt.Utils.joinLines;

class Constants {
  static final String[] DEFAULT_ARGS = { "--no-load", "--no-save" };

  static final String COLLEGE_NAME = "College of Test";

  // region Lecturers
  static final String LECTURER_BSC = joinLines(
      "1", "123", "First", "1", "something", "1000");

  static final String LECTURER_MSC = joinLines(
      "1", "456", "Second", "2", "something but better", "1000");

  static final String LECTURER_PHD = joinLines(
      "1", "789", "Doc", "3", "many things", "5000",
      "Doc article 1", "Doc article 2", "Doc article 3", "Doc article 4",
      "" // end of articles
  );

  static final String LECTURER_PHD_2 = joinLines(
      "1", "ABC", "Other Doc", "3", "many other things", "5000",
      "Doc article 5", "Doc article 6",
      "" // end of articles
  );

  static final String LECTURER_PROF = joinLines(
      "1", "DEF", "Prof", "4", "everything", "100000",
      "Prof article 1", "Prof article 2", "Prof article 3", "Prof article 4",
      "Prof article 5", "Prof article 6", "Prof article 7", "Prof article 8",
      "Prof article 9", "Prof article 10",
      "", // end of articles
      "FAKE PROF FOR FREE!!!1!1");

  static final String ALL_LECTURERS = joinLines(
      LECTURER_BSC, LECTURER_MSC, LECTURER_PHD, LECTURER_PHD_2, LECTURER_PROF);
  // endregion

  // region Departments
  static final String DEPT_D1 = joinLines("7", "d1", "111");
  static final String DEPT_D2 = joinLines("7", "d2", "222");

  static final String ALL_DEPARTMENTS = joinLines(DEPT_D1, DEPT_D2);

  static final String SETUP_DATA = joinLines(ALL_LECTURERS, ALL_DEPARTMENTS);
  // endregion

  // region Input builders
  static String addToDept(String lecturerId, String deptName) {
    return joinLines("8", lecturerId, deptName);
  }

  /** memberDegree: 1=BSc/MSc, 2=PhD, 3=Prof. */
  static String addCommittee(String name, String headId, int memberDegree) {
    return joinLines("2", name, headId, String.valueOf(memberDegree));
  }

  static String addToCommittee(String lecturerId, String committeeName) {
    return joinLines("3", lecturerId, committeeName);
  }

  static String removeFromCommittee(String lecturerId, String committeeName) {
    return joinLines("5", lecturerId, committeeName);
  }

  static String setCommitteeHead(String lecturerId, String committeeName) {
    return joinLines("4", lecturerId, committeeName);
  }

  static String duplicateCommittee(String committeeName) {
    return joinLines("6", committeeName);
  }

  static final String LIST_LECTURERS = "11";
  static final String LIST_COMMITTEES = "13";
  // endregion
}
