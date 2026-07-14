package omerpeled.collegemgmt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static omerpeled.collegemgmt.Constants.*;
import static omerpeled.collegemgmt.Utils.*;

class MainTest {
  @AfterEach
  void restoreStreams() {
    Utils.restoreStreams();
  }

  // region Lecturers
  @Nested
  class LecturerTests {
    @Test
    void addBscLecturer() {
      var result = run(joinLines(COLLEGE_NAME, LECTURER_BSC, "0"));
      assertFalse(result.hasError(), "adding a BSc lecturer should succeed");
    }

    @Test
    void addMscLecturer() {
      var result = run(joinLines(COLLEGE_NAME, LECTURER_MSC, "0"));
      assertFalse(result.hasError(), "adding a MSc lecturer should succeed");
    }

    @Test
    void addPhdLecturer() {
      var result = run(joinLines(COLLEGE_NAME, LECTURER_PHD, "0"));
      assertFalse(result.hasError(), "adding a PhD lecturer should succeed");
    }

    @Test
    void addProfLecturer() {
      var result = run(joinLines(COLLEGE_NAME, LECTURER_PROF, "0"));
      assertFalse(result.hasError(), "adding a Prof. lecturer should succeed");
    }

    @Test
    void addDuplicateLecturerIdFails() {
      var result = run(
          joinLines(COLLEGE_NAME, LECTURER_BSC, LECTURER_BSC, "0"));
      assertTrue(result.err().contains("Lecturer with ID 123 already exists!"),
          "adding a lecturer with a duplicate ID should fail");
    }

    @Test
    void listLecturers() {
      var result = run(joinLines(COLLEGE_NAME, LECTURER_BSC, LECTURER_MSC,
          LIST_LECTURERS, "0"));
      assertAll("both lecturers should appear in listing",
          () -> assertTrue(result.out().contains("First (123)")),
          () -> assertTrue(result.out().contains("Second (456)")));
    }

    @Test
    void listLecturersShowsDepartment() {
      var result = run(joinLines(
          COLLEGE_NAME, LECTURER_BSC, DEPT_D1, addToDept("123", "d1"),
          LIST_LECTURERS, "0"));
      assertTrue(result.out().contains("Department: d1"),
          "lecturer listing should reflect assigned department");
    }

    @Test
    void listLecturersAfterDepartmentChange() {
      var result = run(joinLines(
          COLLEGE_NAME, LECTURER_BSC, DEPT_D1, DEPT_D2,
          addToDept("123", "d1"), addToDept("123", "d2"),
          LIST_LECTURERS, "0"));
      assertAll("listing should show updated department after move",
          () -> assertTrue(result.out().contains("Department: d2")),
          () -> assertFalse(result.out().contains("Department: d1")));
    }

    @Test
    void listLecturersShowsCommittee() {
      var result = run(joinLines(
          COLLEGE_NAME, LECTURER_PHD, LECTURER_PHD_2,
          addCommittee("c1", "789", 2),
          addToCommittee("ABC", "c1"),
          LIST_LECTURERS, "0"));
      // Both head and member should list c1, string should appear twice
      int timesListed = result.out().split("Committees: c1", -1).length - 1;
      assertEquals(2, timesListed,
          "both head and member should list c1 in their committees");
    }
  }
  // endregion

  // region Departments
  @Nested
  class DepartmentTests {
    @Test
    void addDepartment() {
      var result = run(joinLines(COLLEGE_NAME, DEPT_D1, "0"));
      assertFalse(result.hasError(), "adding a department should succeed");
    }

    @Test
    void addDuplicateDepartmentFails() {
      var result = run(joinLines(COLLEGE_NAME, DEPT_D1, DEPT_D1, "0"));
      assertTrue(result.err().contains("Department d1 already exists!"),
          "adding a department with a duplicate name should fail");
    }

    @Test
    void addLecturerToDepartment() {
      var result = run(joinLines(COLLEGE_NAME, LECTURER_BSC, DEPT_D1,
          addToDept("123", "d1"), "0"));
      assertFalse(result.hasError(),
          "adding a lecturer to a department should succeed");
    }

    @Test
    void addLecturerToDepartmentReplacesPrevious() {
      var result = run(joinLines(
          COLLEGE_NAME, LECTURER_BSC, DEPT_D1, DEPT_D2,
          addToDept("123", "d1"), addToDept("123", "d2"), "0"));
      assertFalse(result.hasError(),
          "moving a lecturer to a new department should succeed");
    }
  }
  // endregion

  // region Committees
  @Nested
  class CommitteeTests {
    private String setup() {
      return joinLines(COLLEGE_NAME, SETUP_DATA);
    }

    @Test
    void addCommitteeWithValidHead() {
      var result = run(joinLines(setup(), addCommittee("c1", "789", 2), "0"));
      assertFalse(result.hasError(),
          "a PhD lecturer should be accepted as a committee head");
    }

    @Test
    void addCommitteeWithInvalidHeadFails() {
      // The add-committee loop retries on failure, so we at456t with the BSc
      // lecturer (123) first, then supply Doc (789) to succeed on the retry
      var result = run(joinLines(
          setup(),
          joinLines("2", "c1", "123"),
          joinLines("2", "c1", "789", "2"),
          "0"));
      assertTrue(result.err().contains("First cannot be a committee head!"),
          "a BSc lecturer should not be accepted as a committee head");
    }

    @Test
    void addValidMemberToCommittee() {
      var result = run(joinLines(
          setup(), addCommittee("c1", "789", 2), addToCommittee("ABC", "c1"),
          "0"));
      assertFalse(result.hasError(),
          "a PhD lecturer should be accepted as a member of a PhD committee");
    }

    @Test
    void addInvalidMemberToCommitteeFails() {
      var result = run(joinLines(
          setup(), addCommittee("c1", "789", 2), addToCommittee("123", "c1"),
          "0"));
      assertTrue(
          result.err().contains("First cannot be added to this committee!"),
          "a BSc lecturer should not be accepted as a member of a PhD committee");
    }

    @Test
    void removeMemberFromCommittee() {
      var result = run(joinLines(
          setup(), addCommittee("c1", "789", 2),
          addToCommittee("ABC", "c1"), removeFromCommittee("ABC", "c1"), "0"));
      assertFalse(result.hasError(),
          "removing a member from a committee should succeed");
    }

    @Test
    void setCommitteeHead() {
      var result = run(joinLines(
          setup(), addCommittee("c1", "789", 2),
          Constants.setCommitteeHead("DEF", "c1"), "0"));
      assertFalse(result.hasError(),
          "setting a new committee head should succeed");
    }

    @Test
    void duplicateCommittee() {
      var result = run(joinLines(
          setup(), addCommittee("c2", "789", 1),
          Constants.duplicateCommittee("c2"), "0"));
      assertFalse(result.hasError(), "duplicating a committee should succeed");
    }

    @Test
    void duplicateCommitteeNameIncrement() {
      var result = run(joinLines(
          setup(), addCommittee("c2", "789", 1),
          Constants.duplicateCommittee("c2"), // → c2-new
          Constants.duplicateCommittee("c2"), // → c2-new2 (c2-new exists)
          "0"));
      assertFalse(result.hasError(),
          "duplicating a committee twice should produce '-new' then '-new2'");
    }

    @Test
    void removeNonMemberFromCommitteeFails() {
      // After Other Doc (ABC) becomes head of c2-new2, Doc (PhD) can't be a
      // BSc/MSc member so they're just removed; Removing them again should fail
      var result = run(joinLines(
          setup(),
          addCommittee("c2", "789", 1), // BSc/MSc, Doc as head
          addToCommittee("123", "c2"),
          addToCommittee("456", "c2"),
          Constants.duplicateCommittee("c2"), // → c2-new
          Constants.duplicateCommittee("c2"), // → c2-new2
          removeFromCommittee("123", "c2-new2"),
          Constants.setCommitteeHead("ABC", "c2-new2"),
          removeFromCommittee("789", "c2-new2"), // Doc not in committee — fails
          "0"));
      assertTrue(result.err().contains("Doc is not in c2-new2!"),
          "removing a lecturer not in the committee should fail");
    }

    @Test
    void listCommittees() {
      var result = run(joinLines(setup(), addCommittee("c1", "789", 2),
          LIST_COMMITTEES, "0"));
      assertAll("committee listing should show name, head and member degree",
          () -> assertTrue(result.out().contains("c1")),
          () -> assertTrue(result.out().contains("Head: Doc")),
          () -> assertTrue(result.out().contains("Member degree: PhD")));
    }

    @Test
    void listCommitteesShowsMembers() {
      var result = run(joinLines(
          setup(), addCommittee("c1", "789", 2), addToCommittee("ABC", "c1"),
          LIST_COMMITTEES, "0"));
      assertTrue(result.out().contains("Members: Other Doc"),
          "committee listing should show added members");
    }

    @Test
    void listCommitteesAfterHeadChange() {
      // Doc (PhD) stays in c1 as a member after Prof takes over as head
      var result = run(joinLines(
          setup(), addCommittee("c1", "789", 2),
          Constants.setCommitteeHead("DEF", "c1"),
          LIST_COMMITTEES, "0"));
      assertAll("committee listing should reflect new head",
          () -> assertTrue(result.out().contains("Head: Prof")),
          () -> assertFalse(result.out().contains("Head: Doc")));
    }

    @Test
    void listCommitteesAfterMemberRemoved() {
      var result = run(joinLines(
          setup(), addCommittee("c1", "789", 2),
          addToCommittee("ABC", "c1"), removeFromCommittee("ABC", "c1"),
          LIST_COMMITTEES, "0"));
      assertAll("committee listing should show no members after removal",
          () -> assertFalse(result.out().contains("Members: Other Doc")),
          () -> assertTrue(result.out().contains("No members.")));
    }

    @Test
    void listCommitteesDuplicateShowsBothWithMembers() {
      var result = run(joinLines(
          setup(), addCommittee("c2", "789", 1),
          addToCommittee("123", "c2"), addToCommittee("456", "c2"),
          Constants.duplicateCommittee("c2"),
          LIST_COMMITTEES, "0"));
      assertAll("both c2 and c2-new should be listed with their members",
          () -> assertTrue(result.out().contains("c2")),
          () -> assertTrue(result.out().contains("c2-new")),
          () -> {
            int memberLines = result.out().split("Members:", -1).length - 1;
            assertEquals(2, memberLines,
                "both committees should have a members line");
          });
    }
  }
  // endregion
}
