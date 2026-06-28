# Java OOP College Management Project

College management project for Java OOP course at Afeka college.

## Folder Structure

- `src`: Contains all Java source files.
- `bin`: Compiled output will be generated here, no need to touch it.
- `.vscode`: IDE specific settings for VS Code.
- `.idea`: IDE specific settings for IntelliJ IDEA.

## VS Code tasks

If running this project in VS Code, you have some tasks you can from the command palette (`Ctrl+Shift+P`) → Run task. These include:

- **Run program** \
  Just run Main, without any extra preset input.
- **Run program with preset data input** \
  Start with preset data (lecturers & departments) defined in `tests/inputs_data.txt`.
- **Run program with preset data & tests inputs** \
  Same as previous option, but also adds some testing (add to department, committee, etc) from `tests/inputs_tests.txt`.

> [!IMPORTANT]
> The last two tasks have been written for Linux. They might work on macOS (untested) but they **definitely do NOT work on Windows (CMD/Powershell)!**
