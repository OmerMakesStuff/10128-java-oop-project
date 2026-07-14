package omerpeled.collegemgmt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

class Utils {
  static String joinLines(String... lines) {
    return String.join("\n", lines);
  }

  record RunResult(String out, String err) {
    boolean hasError() {
      return !err.isEmpty();
    }
  }

  private static final InputStream ORIGINAL_IN = System.in;
  private static final PrintStream ORIGINAL_OUT = System.out;
  private static final PrintStream ORIGINAL_ERR = System.err;

  /** Runs {@link Main#main} with the given input fed as stdin. Captures stdout and stderr. */
  static RunResult run(String input) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    System.setErr(new PrintStream(err));

    Main.main(Constants.DEFAULT_ARGS);

    restoreStreams();
    return new RunResult(out.toString(), err.toString());
  }

  static void restoreStreams() {
    System.setIn(ORIGINAL_IN);
    System.setOut(ORIGINAL_OUT);
    System.setErr(ORIGINAL_ERR);
  }
}
