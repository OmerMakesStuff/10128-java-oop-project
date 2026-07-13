package omerpeled.collegemgmt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static omerpeled.collegemgmt.Constants.*;
import static omerpeled.collegemgmt.Utils.joinLines;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

class MainTest {
  private final InputStream originalIn = System.in;
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  // region Lecturers
  @Nested
  class LecturerTests {
    @Test
    void addBscLecturer() {
      String input = joinLines(
          COLLEGE_NAME,
          LECTURER_BSC,
          "0" // Exit
      );
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      System.setOut(new PrintStream(out));

      Main.main(DEFAULT_ARGS);
      assertTrue(out.toString().contains("Lecturer added."),
          "adding a BSc lecturer should succeed");
    }

    @Test
    void addMscLecturer() {
      String input = joinLines(
          COLLEGE_NAME,
          LECTURER_MSC,
          "0" // Exit
      );
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      System.setOut(new PrintStream(out));

      Main.main(DEFAULT_ARGS);
      assertTrue(out.toString().contains("Lecturer added."),
          "adding a MSc lecturer should succeed");
    }

    @Test
    void addPhdLecturer() {
      String input = joinLines(
          COLLEGE_NAME,
          LECTURER_PHD,
          "0" // Exit
      );
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      System.setOut(new PrintStream(out));

      Main.main(DEFAULT_ARGS);
      assertTrue(out.toString().contains("Lecturer added."),
          "adding a PhD lecturer should succeed");
    }

    @Test
    void addProfLecturer() {
      String input = joinLines(
          COLLEGE_NAME,
          LECTURER_PROF,
          "0" // Exit
      );
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      System.setOut(new PrintStream(out));

      Main.main(DEFAULT_ARGS);
      assertTrue(out.toString().contains("Lecturer added."),
          "adding a Prof. lecturer should succeed");
    }

    @Test
    void addDuplicateLecturerIdsFails() {
      String input = joinLines(
          COLLEGE_NAME,
          LECTURER_BSC,
          LECTURER_BSC,
          "0" // Exit
      );
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      // Don't care about out here
      System.setOut(new PrintStream(OutputStream.nullOutputStream()));
      ByteArrayOutputStream err = new ByteArrayOutputStream();
      System.setErr(new PrintStream(err));

      Main.main(DEFAULT_ARGS);
      assertTrue(
          err.toString().contains("Lecturer with ID 123 already exists!"),
          "adding a duplicate lecturer should fail");
    }

    @Test
    void listLecturers() {
      String input = joinLines(
          COLLEGE_NAME,
          LECTURER_BSC,
          LECTURER_MSC,
          "11", // Main menu: Show lecturers
          "0" // Exit
      );
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      System.setOut(new PrintStream(out));

      Main.main(DEFAULT_ARGS);
      assertAll("listing added lecturers should succeed",
          () -> assertTrue(out.toString().contains("First (123)")),
          () -> assertTrue(out.toString().contains("Second (456)")));

    }

    @AfterEach
    void restoreStreams() {
      System.setIn(originalIn);
      System.setOut(originalOut);
      System.setErr(originalErr);
    }
  }
}
