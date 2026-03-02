# MONDAY User Guide

MONDAY is a grumpy but efficient task manager for power users who prefer keyboard commands over mouse clicks. Named after everyone's least favorite day of the week, MONDAY has a sarcastic personality but gets the job done.

## Running from source (Windows)

Use:

```powershell
.\gradlew.bat run
```

Do not run `.\gradlew.bat - run` (the extra `-` causes a Gradle task lookup error).

### Windows ARM64 + JavaFX runtime

If you're on Windows ARM64, switch this shell to x64 JDK before running GUI:

```powershell
$env:JAVA_HOME_X64 = [Environment]::GetEnvironmentVariable('JAVA_HOME_X64','Machine')
if (-not $env:JAVA_HOME_X64) {
    $env:JAVA_HOME_X64 = [Environment]::GetEnvironmentVariable('JAVA_HOME_X64','User')
}
$env:JAVA_HOME = $env:JAVA_HOME_X64
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
java -XshowSettings:properties -version 2>&1 | Select-String "java.home|os.arch"
.\gradlew.bat run
```

Sanity check: `os.arch` should be `amd64`.

## Adding tasks

Add a task by typing any text that isn't a command. MONDAY will reluctantly remember it for you.

Example: `read book`

Expected output:

```
added: read book
```

Notes:
- MONDAY can store up to 100 tasks
- Empty input (just spaces) will be rejected with a grumpy error message
- Task descriptions can contain spaces and any characters

## Listing tasks

View all your tasks by typing `list`.

Example: `list`

Expected output:

```
____________________________________________________________
1. [ ] read book
2. [X] return book
____________________________________________________________
```

Notes:
- Tasks are displayed with numbered list format
- Tasks marked with [X] are completed, while [ ] indicates incomplete tasks
- If you haven't added any tasks yet, MONDAY will be skeptical about it
- The `list` command is case-insensitive (works with LIST, List, lIsT, etc.)

## Marking tasks as done

Mark a task as done by typing `mark X`, where X is the task number shown in the list.

Example: `mark 1`

Expected output:

```
____________________________________________________________
Fine. I've marked this task as done:
  [X] read book
____________________________________________________________
```

Notes:
- Task numbers are based on the order shown in the list command
- Marked tasks are displayed with [X] to indicate completion
- If you provide an invalid task number, MONDAY will grumpily tell you the valid range
- The `mark` command is case-insensitive (works with MARK, Mark, mArK, etc.)

## Marking tasks as not done

Mark a task as not done by typing `unmark X`, where X is the task number shown in the list.

Example: `unmark 1`

Expected output:

```
____________________________________________________________
Ugh, I've marked this task as not done:
  [ ] read book
____________________________________________________________
```

Notes:
- Unmarked tasks are displayed with [ ] to indicate they are not yet completed
- Use this if you accidentally marked the wrong task as done
- The `unmark` command is case-insensitive (works with UNMARK, Unmark, uNmArK, etc.)

## Adding a todo task

Add a simple todo task without any date/time attached to it by typing `todo` followed by the task description.

Example: `todo borrow book`

Expected output:

```
____________________________________________________________
Fine. I've added this todo:
  [T][ ] borrow book
Now you have 1 task in the list.
____________________________________________________________
```

Notes:
- Todo tasks are displayed with a `[T]` icon to indicate they are simple tasks
- The task description can contain spaces and any characters
- Empty descriptions will be rejected with a grumpy error message

## Adding a deadline

Add a deadline task with a due date/time by typing `deadline` followed by the description and `/by` marker with the due date/time.

Example: `deadline return book /by Sunday`

Expected output:

```
____________________________________________________________
Fine. I've added this deadline:
  [D][ ] return book (by: Sunday)
Now you have 1 task in the list.
____________________________________________________________
```

Notes:
- Deadline tasks are displayed with a `[D]` icon and include the due date/time
- The `/by` marker is required to separate the description from the due date
- Due dates can be in any format (e.g., "Sunday", "2024-12-25", "Friday 5pm")
- Missing the `/by` marker or due date will result in a helpful error message

## Adding an event

Add an event task with start and end date/time by typing `event` followed by the description, `/from` marker with start time, and `/to` marker with end time.

Example: `event project meeting /from Mon 2pm /to 4pm`

Expected output:

```
____________________________________________________________
Fine. I've added this event:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 1 task in the list.
____________________________________________________________
```

Notes:

- Event tasks are displayed with an `[E]` icon and include both start and end times
- Both `/from` and `/to` markers are required in the correct order
- Times can be in any format (e.g., "Mon 2pm", "2024-12-25 14:00", "Friday evening")
- Missing either marker or providing them in the wrong order will trigger an error message

## Deleting a task

Remove a task from your list by typing `delete` followed by the task number.

Example: `delete 1`

Expected output:

```
____________________________________________________________
Fine. I've deleted this task:
  [ ] read book
Now you have 0 tasks in the list.
____________________________________________________________
```

Notes:
- Task numbers are based on the order shown in the `list` command
- Deleting a task shifts all subsequent task numbers down by one
- If you provide an invalid task number, MONDAY will grumpily tell you the valid range
- The `delete` command is case-insensitive

## Viewing tasks for a specific date

View tasks scheduled for a particular date by typing `view` followed by the date.

Example: `view tomorrow`

Expected output:

```
____________________________________________________________
Here are the tasks for tomorrow:
1. [ ] return book (by: tomorrow 6pm)
____________________________________________________________
```

Notes:
- The date can be in any format (e.g., "tomorrow", "Monday", "2024-12-25")
- Only deadline and event tasks are shown; todo tasks have no date
- If no tasks are scheduled for that date, MONDAY will let you know

## Finding tasks by keyword

Search for tasks containing a specific keyword by typing `find` followed by your search term.

Example: `find book`

Expected output:

```
____________________________________________________________
Here are the matching tasks:
1. [ ] read book
2. [X] return book
____________________________________________________________
```

Notes:
- The search is case-insensitive
- Searches task descriptions for partial matches
- If no matching tasks are found, MONDAY will inform you

## Viewing help

Display all available commands by typing `help`.

Example: `help`

Expected output:

```
____________________________________________________________
Available commands:
- todo: Add a simple task
- deadline: Add a task with deadline
- event: Add an event
- list: Show all tasks
- mark: Mark task as done
- unmark: Mark task as not done
- delete: Remove a task
- view: View tasks for a date
- find: Search tasks
- help: Show this message
- cheer: Get motivated
- remind: See upcoming tasks
- bye: Exit
____________________________________________________________
```

Notes:
- The `help` command provides a quick reference for all commands
- Typing an unknown command will also trigger the help message

## Getting cheered up

When you need some motivation (or sarcasm), type `cheer`.

Example: `cheer`

Expected output:

```
____________________________________________________________
Ugh, fine. Here's something to read while you're procrastinating:
"You'll regret this later. Probably."
____________________________________________________________
```

Notes:
- MONDAY will display a grumpy motivational quote
- Quotes are randomly selected from the available pool
- The `cheer` command is perfect for those moments when you need a reality check

## Viewing task reminders

Get a summary of your tasks and see your earliest upcoming task by typing `remind` (or `reminders`).

Example: `remind`

Expected output:

```
____________________________________________________________
You have 3 tasks total, 2 of which are incomplete.
Your next task: return book (by: tomorrow 6pm)
____________________________________________________________
```

Notes:
- Shows total task count and incomplete task count
- Displays the earliest upcoming deadline or event
- Useful for a quick overview of what needs to be done

## Exiting the application

Exit MONDAY gracefully by typing `bye` or `exit`.

Example: `bye`

Expected output:

```
____________________________________________________________
Goodbye. Try not to come back too soon. ... Just kidding, I'm always here.
____________________________________________________________
```

Notes:
- Both `bye` and `exit` work identically
- Your tasks are automatically saved when you exit
- MONDAY will miss you... maybe.
