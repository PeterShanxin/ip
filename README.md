# MONDAY Project

A CS2103T Individual Project - a productivity manager desktop application called MONDAY with a grumpy personality.

## Setting up in IntelliJ

Prerequisites: JDK 21.

1. Open IntelliJ (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into IntelliJ as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 21** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/Monday.java` file, right-click it, and choose `Run Monday.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something like the below as the output:
   ```
   ____________________________________________________________

   Ugh. It's Monday. YES, THE MONDAY. Unhelpful, unwilling, and exactly what you deserve.

   Today is [current date]

   [day-specific message]

   Type 'help' for how to use this app. (It's cute that you think it'll work.)
   What do you want?
   ____________________________________________________________
   ```

   **Note:** The greeting includes a dynamic date and day-specific message that changes based on the current day of the week. MONDAY has a grumpy personality with unique messages for each day.

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Running Tests

The project comes with a set of test scripts for different operating systems:

- **MacOS/Linux**: Run the shell script
  ```bash
  cd text-ui-test
  ./runtest.sh
  ```

- **Windows**: Run the batch script
  ```batch
  cd text-ui-test
  runtest.bat
  ```

These scripts will compile the source files, run the tests, and compare the output against the expected output.
