# MONDAY User Guide

MONDAY is a grumpy but efficient task manager for power users who prefer keyboard commands over mouse clicks. Named after everyone's least favorite day of the week, MONDAY has a sarcastic personality but gets the job done.

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

## Adding deadlines

// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```

## Feature ABC

// Feature details


## Feature XYZ

// Feature details