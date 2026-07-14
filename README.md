# Java OOP College Management Project

College management project for Java OOP course at Afeka college.

## Folder Structure

- `app/src/main/java`: Contains all Java source files.
- `app/src/test/java`: Contains all JUnit test files.
- `build`, `app/build`: Build output will be generated here, no need to touch most of it.
- `app/build/reports/tests/test/index.html`: Test reports from JUnit.
- `.vscode`: IDE specific settings for VS Code.
- `.idea`: IDE specific settings for IntelliJ IDEA.

## Run program

First, clone the repo and enter it:

```sh
git clone https://github.com/OmerMakesStuff/10128-java-oop-project.git
cd 10128-java-oop-project
```

Then run the app (use `./gradlew.bat` on Windows):

```sh
./gradlew app:run -q --console=plain
```

You can also use any of the VS Code scripts below.

### Arguments

The app accepts the following arguments:

- Filename: load a file other than `college.dat`
- `--no-load`: don't load from file, even if it exists, but still save to it
- `--no-save`: don't save to file, but still load from it

`--no-load` and `--no-save` can be used together to avoid touching any files. \
A filename may be provided alongside either `--no-load` or `--no-save`, but not both as that would be pointless.

To run the app with arguments:

```sh
./gradlew app:run -q --console=plain --args="<ARGS>"
```

Replace `<ARGS>` with the arguments you would like. For example:

```sh
./gradlew app:run -q --console=plain --args="other.dat --no-save"
```

## Run tests

There are some JUnit tests available to test the app. You can run them with

```sh
./gradlew app:test
```

To force running tests and clean up before running them:

```sh
./gradlew clean app:test --rerun
```

## VS Code tasks

If running this project in VS Code, you have some tasks you can run from the command palette (`Ctrl+Shift+P`) → Run task. These include:

- **Run program** \
  Just run Main, without any extra preset input. Load from and save to `college.dat`, the default file.
- **Run program - fresh** \
  Don't load from `college.dat`, but save to it on exit. Otherwise, same as Run program.
- **Run program - fresh & no save** \
  Don't load from `college.dat` or save to it. Otherwise, same as Run program.
- **Run program - playground** \
  Load some preset test data from `test/playground.dat`, without saving to it on exit.
- **Run tests** \
  Run all JUnit tests normally. Won't load from or save to a file.
- **Run tests - clean** \
  Force running all tests from a clean slate. (`clean` and `--rerun`)
