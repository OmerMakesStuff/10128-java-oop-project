package omerpeled.collegemgmt;

import static omerpeled.collegemgmt.Utils.joinLines;

public class Constants {
  static final String[] DEFAULT_ARGS = { "--no-load", "--no-save" };

  static final String COLLEGE_NAME = "College of Test";

  static final String LECTURER_BSC = joinLines(
      "1", // Main menu: Add lecturer
      "123", // ID
      "First", // Name
      "1", // Degree: BSc
      "something", // Degree title
      "1000" // Salary
  );
  static final String LECTURER_MSC = joinLines(
      "1", // Main menu: Add lecturer
      "456", // ID
      "Second", // Name
      "2", // Degree: MSc
      "something else", // Degree title
      "1000" // Salary
  );
  static final String LECTURER_PHD = joinLines(
      "1", // Main menu: Add lecturer
      "789", // ID
      "Doc", // Name
      "3", // Degree: PhD
      "many things", // Degree title
      "5000", // Salary
      "Doc article 1", // Articles
      "Doc article 2",
      "Doc article 3",
      "Doc article 4",
      "" // End of articles
  );
  static final String LECTURER_PROF = joinLines(
      "1", // Main menu: Add lecturer
      "DEF", // ID
      "Prof", // Name
      "4", // Degree: Prof.
      "everything", // Degree title
      "100000", // Salary
      "Prof article 1", // Articles
      "Prof article 2",
      "Prof article 3",
      "Prof article 4",
      "Prof article 5",
      "Prof article 6",
      "Prof article 7",
      "Prof article 8",
      "Prof article 9",
      "Prof article 10",
      "", // End of articles
      "FAKE PROF FOR FREE!!!1!1" // Prof. awarding body
  );
}
