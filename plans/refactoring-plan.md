# Monday Task Management Application - Comprehensive Refactoring Plan

## Executive Summary

This document outlines a systematic refactoring plan to address code quality issues identified in the Monday task management application. The plan is organized by priority (High/Medium/Low) and includes specific implementation steps, dependencies, and risk mitigation strategies.

**Analysis Date:** 2026-02-18
**Project:** Monday Task Management Application
**Refactoring Goal:** Improve code quality, maintainability, and adherence to SOLID principles

---

## Overview of Issues

| Issue | Severity | Affected Files | Lines of Code |
|-------|----------|----------------|---------------|
| Parser violates SRP | High | Parser.java | 384 |
| Storage violates SRP | High | Storage.java | 282 |
| Ui violates SRP | High | Ui.java | 337 |
| Code duplication in command parsing | High | Parser.java | Multiple methods |
| Code duplication in task display logic | High | Ui.java | Multiple methods |
| Duplicated task number validation | Medium | TaskList.java, Command classes | Multiple methods |
| Duplicated isOnDate method | Medium | Deadline.java, Event.java | 2 methods |
| Identical isExit() implementations | Medium | Command classes | Multiple classes |
| MainWindow mixed concerns | Medium | MainWindow.java | 68-line start method |
| Monday mixed concerns | Medium | Monday.java | 139 lines |
| Inconsistent error handling | Medium | Multiple files | Throughout |
| Missing validation (event date order) | Medium | Event.java | Constructor |
| Magic numbers/strings | Low | Multiple files | Throughout |
| Unused code (parseCheerCommand) | Low | Parser.java | 1 method |

---

## HIGH PRIORITY REFACTORING TASKS

### Task 1.1: Refactor Parser.java to Follow Single Responsibility Principle

**Priority:** High
**Risk:** Medium
**Estimated Impact:** Significant improvement in maintainability and testability

**Files to Modify:**
- [`src/main/java/monday/parser/Parser.java`](src/main/java/monday/parser/Parser.java) (384 lines)

**New Files to Create:**
- `src/main/java/monday/parser/CommandParser.java` - Main command parsing logic
- `src/main/java/monday/parser/TaskArgumentParser.java` - Parse task-specific arguments
- `src/main/java/monday/parser/TaskNumberParser.java` - Parse and validate task numbers
- `src/main/java/monday/parser/DateParser.java` - Parse date/time arguments

**Specific Changes:**

1. **Extract CommandParser.java**
   - Move `parseCommand()` method
   - Move `extractCommandWord()` method
   - Move `getUnknownCommandErrorMessage()` method
   - Move `isCommandOnlyInput()` method
   - Move `extractDescription()` method

2. **Extract TaskArgumentParser.java**
   - Move `parseToDoCommand()` method
   - Move `parseDeadlineCommand()` method
   - Move `parseEventCommand()` method
   - Move `parseMarkCommand()` method
   - Move `parseUnmarkCommand()` method
   - Move `parseDeleteCommand()` method
   - Move `parseFindCommand()` method
   - Move `parseViewCommand()` method
   - Move `parseCheerCommand()` method

3. **Extract TaskNumberParser.java**
   - Move `parseTaskNumber()` method
   - Add validation logic for task number ranges
   - Add consistent error message generation

4. **Extract DateParser.java**
   - Move `parseViewDate()` method
   - Consolidate date parsing logic
   - Add support for multiple date formats

5. **Update Parser.java**
   - Become a facade that delegates to specialized parsers
   - Maintain backward compatibility with existing API
   - Remove duplicate code

**Benefits:**
- Each class has a single, well-defined responsibility
- Easier to test individual parsing components
- Reduced code duplication
- Better separation of concerns

**Dependencies:**
- None (can be done independently)

**Risks and Mitigation:**
- **Risk:** Breaking existing tests that depend on Parser
  - **Mitigation:** Maintain Parser as a facade with same public API
  - **Mitigation:** Run full test suite after each extraction
- **Risk:** Increased complexity due to more classes
  - **Mitigation:** Clear package structure and documentation
  - **Mitigation:** Use package-private classes where appropriate

**Test Impact:**
- Update [`ParserTest.java`](src/test/java/monday/parser/ParserTest.java) to use new parser classes
- Add unit tests for individual parser components
- Ensure all existing parser tests pass

---

### Task 1.2: Refactor Storage.java to Follow Single Responsibility Principle

**Priority:** High
**Risk:** Medium
**Estimated Impact:** Significant improvement in maintainability and testability

**Files to Modify:**
- [`src/main/java/monday/storage/Storage.java`](src/main/java/monday/storage/Storage.java) (282 lines)

**New Files to Create:**
- `src/main/java/monday/storage/FileStorage.java` - File I/O operations
- `src/main/java/monday/storage/TaskSerializer.java` - Task encoding/decoding
- `src/main/java/monday/storage/TaskDeserializer.java` - Task parsing from storage format
- `src/main/java/monday/storage/CorruptionHandler.java` - Handle corrupted lines

**Specific Changes:**

1. **Extract FileStorage.java**
   - Move `loadTasks()` method (file reading logic only)
   - Move `saveTasks()` method (file writing logic only)
   - Move directory creation logic
   - Move file existence checks

2. **Extract TaskSerializer.java**
   - Move `encodeTask()` method
   - Add methods to encode each task type
   - Add validation for encoded data

3. **Extract TaskDeserializer.java**
   - Move `parseTask()` method
   - Move `extractFieldValue()` method
   - Add methods to parse each task type
   - Add validation for parsed data

4. **Extract CorruptionHandler.java**
   - Move `backupCorruptedLine()` method
   - Add methods to track corruption statistics
   - Add methods to generate corruption reports

5. **Update Storage.java**
   - Become a facade that coordinates file operations and serialization
   - Maintain backward compatibility with existing API
   - Delegate to specialized components

**Benefits:**
- Clear separation between file I/O and data serialization
- Easier to test serialization logic independently
- Better error handling and recovery
- Reduced complexity in individual classes

**Dependencies:**
- None (can be done independently)

**Risks and Mitigation:**
- **Risk:** Breaking existing storage format
  - **Mitigation:** Maintain exact same storage format
  - **Mitigation:** Comprehensive testing of load/save cycles
- **Risk:** Error handling changes
  - **Mitigation:** Preserve exception types and messages
  - **Mitigation:** Add integration tests for error scenarios

**Test Impact:**
- Update [`StorageTest.java`](src/test/java/monday/storage/StorageTest.java) to use new components
- Add unit tests for individual components
- Ensure all existing storage tests pass
- Test corruption handling thoroughly

---

### Task 1.3: Refactor Ui.java to Follow Single Responsibility Principle

**Priority:** High
**Risk:** Low
**Estimated Impact:** Moderate improvement in maintainability

**Files to Modify:**
- [`src/main/java/monday/ui/Ui.java`](src/main/java/monday/ui/Ui.java) (337 lines)

**New Files to Create:**
- `src/main/java/monday/ui/MessageFormatter.java` - Format messages for display
- `src/main/java/monday/ui/TaskListFormatter.java` - Format task lists
- `src/main/java/monday/ui/GreetingGenerator.java` - Generate greeting messages
- `src/main/java/monday/ui/ResponseBuilder.java` - Build response strings

**Specific Changes:**

1. **Extract MessageFormatter.java**
   - Move `showResponse()` method
   - Move `showError()` method
   - Move `showEmptyInputError()` method
   - Move `showCommandOnlyError()` method
   - Move `showInvalidTaskNumberError()` method
   - Move `showCorruptionMessage()` method
   - Move `showCheerMessage()` method

2. **Extract TaskListFormatter.java**
   - Move `showTaskList()` method
   - Move `showFilteredTasks()` method
   - Move `showMatchingTasks()` method
   - Extract common StringBuilder pattern into helper method
   - Add method to format individual task entries

3. **Extract GreetingGenerator.java**
   - Move `getGrumpyGreeting()` method
   - Move `buildGreeting()` method
   - Move `getGreetingForGui()` method
   - Move `showGreeting()` method

4. **Extract ResponseBuilder.java**
   - Move `showTaskAdded()` method
   - Move `showTaskDeleted()` method
   - Move `showTaskMarked()` method
   - Move `showFarewell()` method
   - Move `showHelp()` method

5. **Update Ui.java**
   - Become a facade that delegates to formatters
   - Maintain backward compatibility with existing API
   - Remove duplicate StringBuilder patterns

6. **Eliminate Code Duplication**
   - Extract common StringBuilder pattern from `showTaskList()`, `showFilteredTasks()`, and `showMatchingTasks()` into `formatTaskList()` helper method
   - Consolidate LINE wrapping logic from `showResponse()` and `showCheerMessage()`

**Benefits:**
- Clear separation of formatting concerns
- Reduced code duplication
- Easier to test formatting logic
- More maintainable message generation

**Dependencies:**
- None (can be done independently)

**Risks and Mitigation:**
- **Risk:** Breaking UI output format
  - **Mitigation:** Maintain exact same output format
  - **Mitigation:** Comprehensive UI testing
- **Risk:** Changes to Monday's personality
  - **Mitigation:** Preserve all grumpy messages exactly
  - **Mitigation:** Manual review of all UI output

**Test Impact:**
- Update UI-related tests to use new formatters
- Add unit tests for individual formatters
- Ensure all existing UI tests pass
- Manual verification of all UI output

---

### Task 1.4: Eliminate Code Duplication in Command Parsing

**Priority:** High
**Risk:** Low
**Estimated Impact:** Moderate improvement in maintainability

**Files to Modify:**
- [`src/main/java/monday/parser/TaskArgumentParser.java`](src/main/java/monday/parser/TaskArgumentParser.java) (new file from Task 1.1)

**Specific Changes:**

1. **Extract Common Validation Logic**
   - Create `validateNotEmpty(String value, String fieldName)` method
   - Create `validateContainsPrefix(String content, TaskPrefix prefix)` method
   - Create `validateDateTimeFormat(String dateTimeString)` method

2. **Extract Common Parsing Patterns**
   - Create `parsePrefixField(String content, TaskPrefix prefix)` method
   - Create `parseDateTimeField(String field)` method
   - Create `validateEventDateOrder(LocalDateTime from, LocalDateTime to)` method

3. **Refactor Individual Parse Methods**
   - Update `parseToDoCommand()` to use common validation
   - Update `parseDeadlineCommand()` to use common parsing
   - Update `parseEventCommand()` to use common parsing and date validation
   - Update `parseMarkCommand()` to use common task number parsing
   - Update `parseUnmarkCommand()` to use common task number parsing
   - Update `parseDeleteCommand()` to use common task number parsing

4. **Add Missing Validation**
   - Add validation to ensure event 'to' date is after 'from' date
   - Add validation to ensure deadline date is in the future (optional)
   - Add validation to ensure description length is reasonable

**Benefits:**
- Reduced code duplication
- Consistent error handling
- Easier to add new validation rules
- More maintainable parsing logic

**Dependencies:**
- Depends on Task 1.1 (Parser refactoring)

**Risks and Mitigation:**
- **Risk:** Changes to error messages
  - **Mitigation:** Preserve exact error messages
  - **Mitigation:** Test all error scenarios
- **Risk:** Breaking existing parsing behavior
  - **Mitigation:** Comprehensive parser testing
  - **Mitigation:** Run full test suite

**Test Impact:**
- Update parser tests to use new validation methods
- Add tests for new validation logic
- Ensure all existing parser tests pass

---

### Task 1.5: Eliminate Code Duplication in Task Display Logic

**Priority:** High
**Risk:** Low
**Estimated Impact:** Moderate improvement in maintainability

**Files to Modify:**
- [`src/main/java/monday/ui/TaskListFormatter.java`](src/main/java/monday/ui/TaskListFormatter.java) (new file from Task 1.3)

**Specific Changes:**

1. **Extract Common StringBuilder Pattern**
   - Create `formatTaskList(List<Task> tasks)` method that builds the common pattern
   - Create `formatTaskEntry(int index, Task task)` method for individual task formatting
   - Create `formatTaskListHeader(String headerText)` method for header formatting

2. **Refactor Display Methods**
   - Update `showTaskList()` to use `formatTaskList()` and `formatTaskEntry()`
   - Update `showFilteredTasks()` to use `formatTaskList()` and `formatTaskEntry()`
   - Update `showMatchingTasks()` to use `formatTaskList()` and `formatTaskEntry()`

3. **Consolidate LINE Wrapping Logic**
   - Extract `wrapWithLine(String message)` method
   - Update `showResponse()` to use `wrapWithLine()`
   - Update `showCheerMessage()` to use `wrapWithLine()`

**Benefits:**
- Reduced code duplication
- Consistent task list formatting
- Easier to modify display format
- More maintainable UI code

**Dependencies:**
- Depends on Task 1.3 (Ui refactoring)

**Risks and Mitigation:**
- **Risk:** Changes to UI output format
  - **Mitigation:** Maintain exact same output format
  - **Mitigation:** Manual verification of all UI output
- **Risk:** Breaking existing UI tests
  - **Mitigation:** Comprehensive UI testing
  - **Mitigation:** Run full test suite

**Test Impact:**
- Update UI tests to use new formatting methods
- Ensure all existing UI tests pass
- Manual verification of all UI output

---

## MEDIUM PRIORITY REFACTORING TASKS

### Task 2.1: Eliminate Duplicated Task Number Validation

**Priority:** Medium
**Risk:** Low
**Estimated Impact:** Moderate improvement in maintainability

**Files to Modify:**
- [`src/main/java/monday/task/TaskList.java`](src/main/java/monday/task/TaskList.java) (180 lines)
- [`src/main/java/monday/command/MarkCommand.java`](src/main/java/monday/command/MarkCommand.java) (64 lines)
- [`src/main/java/monday/command/DeleteCommand.java`](src/main/java/monday/command/DeleteCommand.java) (55 lines)
- [`src/main/java/monday/command/ViewCommand.java`](src/main/java/monday/command/ViewCommand.java) (42 lines)

**Specific Changes:**

1. **Centralize Task Number Validation in TaskList**
   - Add `validateTaskNumber(int taskNumber)` method that throws CommandException
   - Remove `getInvalidTaskNumberMessage()` method (move to CommandException)
   - Update all methods to use `validateTaskNumber()`

2. **Update Command Classes**
   - Update `MarkCommand.execute()` to use `taskList.validateTaskNumber()`
   - Update `DeleteCommand.execute()` to use `taskList.validateTaskNumber()`
   - Update `ViewCommand.execute()` to use `taskList.validateTaskNumber()`
   - Remove duplicate validation logic from command classes

3. **Remove Redundant Methods**
   - Remove `isValidTaskNumber()` from TaskList (use validateTaskNumber instead)
   - Remove duplicate error message generation

**Benefits:**
- Single source of truth for task number validation
- Consistent error messages
- Reduced code duplication
- Easier to modify validation logic

**Dependencies:**
- None (can be done independently)

**Risks and Mitigation:**
- **Risk:** Changes to error messages
  - **Mitigation:** Preserve exact error messages
  - **Mitigation:** Test all error scenarios
- **Risk:** Breaking existing behavior
  - **Mitigation:** Comprehensive testing
  - **Mitigation:** Run full test suite

**Test Impact:**
- Update [`TaskListTest.java`](src/test/java/monday/task/TaskListTest.java)
- Update [`MarkCommandTest.java`](src/test/java/monday/command/MarkCommandTest.java)
- Update [`DeleteCommand.java`](src/main/java/monday/command/DeleteCommand.java) tests
- Ensure all existing tests pass

---

### Task 2.2: Eliminate Duplicated isOnDate Method

**Priority:** Medium
**Risk:** Low
**Estimated Impact:** Minor improvement in maintainability

**Files to Modify:**
- [`src/main/java/monday/task/Task.java`](src/main/java/monday/task/Task.java) (base class)
- [`src/main/java/monday/task/Deadline.java`](src/main/java/monday/task/Deadline.java) (83 lines)
- [`src/main/java/monday/task/Event.java`](src/main/java/monday/task/Event.java) (114 lines)

**Specific Changes:**

1. **Add isOnDate to Task Base Class**
   - Add abstract `isOnDate(LocalDateTime date)` method to Task
   - Add default implementation that returns false (for ToDo tasks)

2. **Update Deadline**
   - Remove `isOnDate()` method (use default from Task)
   - OR: Keep implementation if it needs special logic

3. **Update Event**
   - Remove `isOnDate()` method (use default from Task)
   - OR: Keep implementation if it needs special logic

4. **Alternative Approach (Preferred)**
   - Create `DateFilterable` interface with `isOnDate()` method
   - Implement in Deadline and Event
   - Use interface in TaskList.filterTasksByDate()

**Benefits:**
- Reduced code duplication
- Clearer intent (which tasks support date filtering)
- Better separation of concerns
- Easier to add new task types

**Dependencies:**
- None (can be done independently)

**Risks and Mitigation:**
- **Risk:** Breaking existing behavior
  - **Mitigation:** Ensure exact same behavior
  - **Mitigation:** Comprehensive testing
- **Risk:** Changes to task hierarchy
  - **Mitigation:** Maintain backward compatibility
  - **Mitigation:** Run full test suite

**Test Impact:**
- Update [`DeadlineTest.java`](src/test/java/monday/task/DeadlineTest.java)
- Update [`EventTest.java`](src/test/java/monday/task/EventTest.java)
- Update [`TaskListTest.java`](src/test/java/monday/task/TaskListTest.java)
- Ensure all existing tests pass

---

### Task 2.3: Eliminate Identical isExit() Implementations

**Priority:** Medium
**Risk:** Low
**Estimated Impact:** Minor improvement in maintainability

**Files to Modify:**
- [`src/main/java/monday/command/Command.java`](src/main/java/monday/command/Command.java) (base class)
- [`src/main/java/monday/command/AddCommand.java`](src/main/java/monday/command/AddCommand.java) (65 lines)
- [`src/main/java/monday/command/DeleteCommand.java`](src/main/java/monday/command/DeleteCommand.java) (55 lines)
- [`src/main/java/monday/command/MarkCommand.java`](src/main/java/monday/command/MarkCommand.java) (64 lines)
- [`src/main/java/monday/command/ExitCommand.java`](src/main/java/monday/command/ExitCommand.java) (37 lines)
- [`src/main/java/monday/command/ListCommand.java`](src/main/java/monday/command/ListCommand.java) (41 lines)
- [`src/main/java/monday/command/FindCommand.java`](src/main/java/monday/command/FindCommand.java) (46 lines)
- [`src/main/java/monday/command/ViewCommand.java`](src/main/java/monday/command/ViewCommand.java) (42 lines)
- [`src/main/java/monday/command/HelpCommand.java`](src/main/java/monday/command/HelpCommand.java) (39 lines)
- [`src/main/java/monday/command/CheerCommand.java`](src/main/java/monday/command/CheerCommand.java) (90 lines)

**Specific Changes:**

1. **Change Command.isExit() to Default Method**
   - Change `isExit()` from abstract to default method returning false
   - Remove `isExit()` implementations from all non-exit commands

2. **Update ExitCommand**
   - Keep `isExit()` implementation returning true
   - OR: Add `ExitCommand` marker interface

3. **Alternative Approach**
   - Create `ExitCommand` marker interface
   - Use `instanceof ExitCommand` check instead of `isExit()`

**Benefits:**
- Reduced code duplication
- Less boilerplate in command classes
- Clearer intent (only ExitCommand exits)
- Easier to add new commands

**Dependencies:**
- None (can be done independently)

**Risks and Mitigation:**
- **Risk:** Breaking existing behavior
  - **Mitigation:** Ensure exact same behavior
  - **Mitigation:** Comprehensive testing
- **Risk:** Changes to command pattern
  - **Mitigation:** Maintain backward compatibility
  - **Mitigation:** Run full test suite

**Test Impact:**
- Update [`CommandResultTest.java`](src/test/java/monday/command/CommandResultTest.java)
- Update all command tests
- Ensure all existing tests pass

---

### Task 2.4: Refactor MainWindow.java to Separate Concerns

**Priority:** Medium
**Risk:** Medium
**Estimated Impact:** Moderate improvement in maintainability

**Files to Modify:**
- [`src/main/java/monday/ui/MainWindow.java`](src/main/java/monday/ui/MainWindow.java) (141 lines)

**New Files to Create:**
- `src/main/java/monday/ui/GuiBuilder.java` - Build GUI components
- `src/main/java/monday/ui/GuiEventHandler.java` - Handle GUI events
- `src/main/java/monday/ui/GuiStyler.java` - Apply styles to GUI components

**Specific Changes:**

1. **Extract GuiBuilder.java**
   - Move `start()` method lines 42-77 (container setup)
   - Move `start()` method lines 78-99 (scene configuration)
   - Create `buildContainer()` method
   - Create `buildScrollPane()` method
   - Create `buildInputField()` method
   - Create `buildSendButton()` method
   - Create `buildMainLayout()` method
   - Create `buildScene()` method

2. **Extract GuiEventHandler.java**
   - Move `start()` method lines 103-105 (event handlers)
   - Move `handleUserInput()` method
   - Move `showMessage()` method

3. **Extract GuiStyler.java**
   - Extract all styling logic from `start()` method
   - Create `applyDarkTheme()` method
   - Create `applyScrollPaneStyle()` method
   - Create `applyInputFieldStyle()` method
   - Create `applyButtonStyle()` method
   - Create `applyScrollbarStyle()` method

4. **Update MainWindow.java**
   - Use GuiBuilder to build components
   - Use GuiEventHandler to handle events
   - Use GuiStyler to apply styles
   - Simplify `start()` method to orchestrate these components

**Benefits:**
- Clear separation of GUI construction, event handling, and styling
- Easier to test individual components
- More maintainable GUI code
- Reduced complexity in MainWindow

**Dependencies:**
- None (can be done independently)

**Risks and Mitigation:**
- **Risk:** Breaking GUI functionality
  - **Mitigation:** Comprehensive GUI testing
  - **Mitigation:** Manual verification of all GUI features
- **Risk:** Changes to GUI appearance
  - **Mitigation:** Maintain exact same appearance
  - **Mitigation:** Visual regression testing

**Test Impact:**
- Add unit tests for individual GUI components
- Manual verification of all GUI features
- Ensure all existing tests pass

---

### Task 2.5: Refactor Monday.java to Separate Concerns

**Priority:** Medium
**Risk:** Medium
**Estimated Impact:** Moderate improvement in maintainability

**Files to Modify:**
- [`src/main/java/monday/Monday.java`](src/main/java/monday/Monday.java) (139 lines)

**New Files to Create:**
- `src/main/java/monday/ApplicationInitializer.java` - Initialize application components
- `src/main/java/monday/CommandProcessor.java` - Process commands
- `src/main/java/monday/GuiOrchestrator.java` - Orchestrate GUI setup

**Specific Changes:**

1. **Extract ApplicationInitializer.java**
   - Move `Monday()` constructor logic
   - Move `loadTasks()` method
   - Create `initializeComponents()` method
   - Create `loadTaskData()` method
   - Create `handleLoadErrors()` method

2. **Extract CommandProcessor.java**
   - Move `getResponse()` method
   - Create `processCommand(String input)` method
   - Create `handleCommandResult(CommandResult result)` method
   - Create `handleExecutionError(Exception e)` method

3. **Extract GuiOrchestrator.java**
   - Move `start()` method logic
   - Create `setupGui(Stage stage)` method
   - Create `displayGreeting()` method
   - Create `displayCorruptionWarning()` method

4. **Update Monday.java**
   - Use ApplicationInitializer to initialize components
   - Use CommandProcessor to process commands
   - Use GuiOrchestrator to setup GUI
   - Simplify `start()` method to orchestrate these components

**Benefits:**
- Clear separation of initialization, command processing, and GUI setup
- Easier to test individual components
- More maintainable application code
- Reduced complexity in Monday

**Dependencies:**
- None (can be done independently)

**Risks and Mitigation:**
- **Risk:** Breaking application functionality
  - **Mitigation:** Comprehensive integration testing
  - **Mitigation:** Manual verification of all features
- **Risk:** Changes to application behavior
  - **Mitigation:** Maintain exact same behavior
  - **Mitigation:** Run full test suite

**Test Impact:**
- Add unit tests for individual components
- Add integration tests for component interactions
- Ensure all existing tests pass

---

### Task 2.6: Standardize Error Handling Patterns

**Priority:** Medium
**Risk:** Medium
**Estimated Impact:** Significant improvement in maintainability

**Files to Modify:**
- [`src/main/java/monday/exception/ParseException.java`](src/main/java/monday/exception/ParseException.java)
- [`src/main/java/monday/command/CommandException.java`](src/main/java/monday/command/CommandException.java)
- [`src/main/java/monday/exception/MondayStorageException.java`](src/main/java/monday/exception/MondayStorageException.java)
- [`src/main/java/monday/Monday.java`](src/main/java/monday/Monday.java)
- Multiple command classes

**New Files to Create:**
- `src/main/java/monday/exception/ErrorHandler.java` - Centralized error handling

**Specific Changes:**

1. **Create ErrorHandler.java**
   - Create `handleParseException(ParseException e)` method
   - Create `handleCommandException(CommandException e)` method
   - Create `handleStorageException(MondayStorageException e)` method
   - Create `handleUnexpectedException(Exception e)` method
   - Create `formatErrorMessage(Exception e)` method

2. **Standardize Exception Messages**
   - Ensure all exceptions have consistent message format
   - Add error codes to exceptions for better tracking
   - Add context information to exceptions

3. **Update Monday.java**
   - Use ErrorHandler to handle exceptions in `getResponse()`
   - Remove duplicate error handling logic

4. **Update Command Classes**
   - Use consistent exception throwing patterns
   - Add context to exceptions where appropriate

5. **Update Storage.java**
   - Use consistent exception throwing patterns
   - Add context to exceptions where appropriate

**Benefits:**
- Consistent error handling across the application
- Easier to debug errors
- Better error messages for users
- Reduced code duplication

**Dependencies:**
- None (can be done independently)

**Risks and Mitigation:**
- **Risk:** Changes to error messages
  - **Mitigation:** Preserve exact error messages
  - **Mitigation:** Test all error scenarios
- **Risk:** Breaking existing error handling
  - **Mitigation:** Comprehensive error testing
  - **Mitigation:** Run full test suite

**Test Impact:**
- Add tests for error handling
- Update exception tests
- Ensure all existing tests pass

---

### Task 2.7: Add Missing Validation

**Priority:** Medium
**Risk:** Low
**Estimated Impact:** Moderate improvement in robustness

**Files to Modify:**
- [`src/main/java/monday/task/Event.java`](src/main/java/monday/task/Event.java)
- [`src/main/java/monday/parser/TaskArgumentParser.java`](src/main/java/monday/parser/TaskArgumentParser.java) (from Task 1.1)

**Specific Changes:**

1. **Add Event Date Order Validation**
   - Add validation in Event constructor to ensure 'to' date is after 'from' date
   - Throw IllegalArgumentException if validation fails
   - Add error message explaining the issue

2. **Add Deadline Date Validation (Optional)**
   - Add validation in Deadline constructor to ensure date is in the future
   - Throw IllegalArgumentException if validation fails
   - Make this optional (configurable)

3. **Add Description Length Validation**
   - Add validation in Task constructor to ensure description is not empty
   - Add validation to ensure description is not too long (e.g., 500 characters)
   - Throw IllegalArgumentException if validation fails

4. **Add Task Number Range Validation**
   - Add validation in TaskList methods to ensure task number is within valid range
   - Throw IndexOutOfBoundsException if validation fails
   - Provide clear error message

**Benefits:**
- More robust application
- Better error messages for invalid input
- Prevents invalid state
- Easier to debug issues

**Dependencies:**
- Depends on Task 1.1 (Parser refactoring) for parser validation

**Risks and Mitigation:**
- **Risk:** Breaking existing behavior
  - **Mitigation:** Make validation optional where appropriate
  - **Mitigation:** Provide clear migration path
- **Risk:** Changes to error messages
  - **Mitigation:** Preserve exact error messages where possible
  - **Mitigation:** Test all validation scenarios

**Test Impact:**
- Add tests for new validation logic
- Update existing tests to handle validation
- Ensure all existing tests pass

---

## LOW PRIORITY REFACTORING TASKS

### Task 3.1: Extract Hardcoded Strings and Magic Numbers to Constants

**Priority:** Low
**Risk:** Low
**Estimated Impact:** Minor improvement in maintainability

**Files to Modify:**
- [`src/main/java/monday/task/TaskList.java`](src/main/java/monday/task/TaskList.java)
- [`src/main/java/monday/ui/Ui.java`](src/main/java/monday/ui/Ui.java)
- [`src/main/java/monday/parser/Parser.java`](src/main/java/monday/parser/Parser.java)
- [`src/main/java/monday/storage/Storage.java`](src/main/java/monday/storage/Storage.java)
- Multiple other files

**New Files to Create:**
- `src/main/java/monday/constants/ApplicationConstants.java` - Application-wide constants
- `src/main/java/monday/constants/MessageConstants.java` - Message constants
- `src/main/java/monday/constants/ValidationConstants.java` - Validation constants

**Specific Changes:**

1. **Extract Application Constants**
   - Move `MAX_TASKS = 100` to ApplicationConstants
   - Move file paths to ApplicationConstants
   - Move configuration values to ApplicationConstants

2. **Extract Message Constants**
   - Move all error messages to MessageConstants
   - Move all user-facing messages to MessageConstants
   - Organize messages by category (errors, info, warnings)

3. **Extract Validation Constants**
   - Move validation limits to ValidationConstants
   - Move date format patterns to ValidationConstants
   - Move regex patterns to ValidationConstants

4. **Update All Files**
   - Replace hardcoded strings with constants
   - Replace magic numbers with constants
   - Ensure consistent usage of constants

**Benefits:**
- Easier to maintain and modify messages
- Centralized configuration
- Reduced risk of typos
- Better internationalization support

**Dependencies:**
- None (can be done independently)

**Risks and Mitigation:**
- **Risk:** Changes to messages
  - **Mitigation:** Preserve exact messages
  - **Mitigation:** Test all message scenarios
- **Risk:** Breaking existing behavior
  - **Mitigation:** Comprehensive testing
  - **Mitigation:** Run full test suite

**Test Impact:**
- Update tests to use constants
- Ensure all existing tests pass
- Verify all messages are correct

---

### Task 3.2: Remove Unused Code

**Priority:** Low
**Risk:** Low
**Estimated Impact:** Minor improvement in code quality

**Files to Modify:**
- [`src/main/java/monday/parser/Parser.java`](src/main/java/monday/parser/Parser.java)

**Specific Changes:**

1. **Remove parseCheerCommand() Method**
   - Remove the `parseCheerCommand()` method from Parser
   - This method is never called (the main parseCommand directly creates new CheerCommand())
   - Update any tests that reference this method

2. **Search for Other Unused Code**
   - Search for unused imports
   - Search for unused methods
   - Search for unused fields
   - Remove all identified unused code

**Benefits:**
- Cleaner code
- Reduced maintenance burden
- Easier to understand codebase

**Dependencies:**
- None (can be done independently)

**Risks and Mitigation:**
- **Risk:** Removing code that is actually used
  - **Mitigation:** Thorough search for usage
  - **Mitigation:** Run full test suite
  - **Mitigation:** Manual code review

**Test Impact:**
- Run full test suite
- Ensure all tests pass
- Manual verification of functionality

---

## IMPLEMENTATION SEQUENCE

### Phase 1: High Priority (Foundation)
1. Task 1.1: Refactor Parser.java
2. Task 1.2: Refactor Storage.java
3. Task 1.3: Refactor Ui.java
4. Task 1.4: Eliminate Code Duplication in Command Parsing
5. Task 1.5: Eliminate Code Duplication in Task Display Logic

### Phase 2: Medium Priority (Consolidation)
6. Task 2.1: Eliminate Duplicated Task Number Validation
7. Task 2.2: Eliminate Duplicated isOnDate Method
8. Task 2.3: Eliminate Identical isExit() Implementations
9. Task 2.4: Refactor MainWindow.java
10. Task 2.5: Refactor Monday.java
11. Task 2.6: Standardize Error Handling Patterns
12. Task 2.7: Add Missing Validation

### Phase 3: Low Priority (Polish)
13. Task 3.1: Extract Hardcoded Strings and Magic Numbers
14. Task 3.2: Remove Unused Code

---

## TESTING STRATEGY

### Unit Testing
- Add unit tests for all new classes
- Ensure all existing tests pass after each refactoring task
- Test edge cases and error conditions

### Integration Testing
- Test interactions between refactored components
- Test full command execution flow
- Test storage load/save cycles

### Regression Testing
- Run full test suite after each refactoring task
- Verify all existing functionality works correctly
- Manual testing of GUI features

### Test Coverage Goals
- Maintain or improve existing test coverage
- Add tests for new components
- Ensure all critical paths are tested

---

## RISK MANAGEMENT

### High-Risk Tasks
- Task 1.1: Parser refactoring (Medium risk)
- Task 1.2: Storage refactoring (Medium risk)
- Task 2.4: MainWindow refactoring (Medium risk)
- Task 2.5: Monday refactoring (Medium risk)
- Task 2.6: Error handling standardization (Medium risk)

### Risk Mitigation Strategies
1. **Incremental Refactoring**: Complete one task at a time
2. **Comprehensive Testing**: Run full test suite after each task
3. **Backward Compatibility**: Maintain existing APIs
4. **Code Review**: Review all changes before merging
5. **Rollback Plan**: Keep git commits atomic for easy rollback

### Rollback Procedures
1. Each refactoring task should be in a separate commit
2. Use feature branches for each task
3. Keep detailed commit messages
4. Test thoroughly before merging

---

## SUCCESS CRITERIA

### Code Quality Metrics
- Reduced cyclomatic complexity
- Improved code duplication metrics
- Better adherence to SOLID principles
- Improved test coverage

### Maintainability Metrics
- Reduced lines of code per class
- Improved method cohesion
- Better separation of concerns
- Clearer code organization

### Functional Requirements
- All existing functionality preserved
- No regressions in behavior
- All tests passing
- GUI functionality intact

---

## CONCLUSION

This refactoring plan provides a systematic approach to improving the code quality of the Monday task management application. By following this plan, the codebase will become more maintainable, testable, and adhere to SOLID principles.

The plan is organized by priority to ensure the most impactful changes are completed first. Each task includes specific implementation steps, dependencies, and risk mitigation strategies to ensure successful execution.

**Next Steps:**
1. Review and approve this refactoring plan
2. Begin implementation with Phase 1 tasks
3. Monitor progress and adjust as needed
4. Complete all phases and verify success criteria

---

## APPENDICES

### Appendix A: File Structure After Refactoring

```
src/main/java/monday/
├── command/
│   ├── AddCommand.java
│   ├── AddDeadlineCommand.java
│   ├── AddEventCommand.java
│   ├── AddToDoCommand.java
│   ├── CheerCommand.java
│   ├── Command.java
│   ├── CommandException.java
│   ├── CommandResult.java
│   ├── CommandType.java
│   ├── DeleteCommand.java
│   ├── ExitCommand.java
│   ├── FindCommand.java
│   ├── HelpCommand.java
│   ├── ListCommand.java
│   ├── MarkCommand.java
│   └── ViewCommand.java
├── constants/
│   ├── ApplicationConstants.java
│   ├── MessageConstants.java
│   └── ValidationConstants.java
├── exception/
│   ├── ErrorHandler.java
│   ├── MondayStorageException.java
│   └── ParseException.java
├── parser/
│   ├── CommandParser.java
│   ├── DateParser.java
│   ├── Parser.java (facade)
│   ├── TaskArgumentParser.java
│   └── TaskNumberParser.java
├── storage/
│   ├── CorruptionHandler.java
│   ├── FileStorage.java
│   ├── Storage.java (facade)
│   ├── TaskDeserializer.java
│   └── TaskSerializer.java
├── task/
│   ├── Deadline.java
│   ├── Event.java
│   ├── LoadResult.java
│   ├── Task.java
│   ├── TaskList.java
│   ├── TaskPrefix.java
│   ├── TaskType.java
│   └── ToDo.java
└── ui/
    ├── DialogBox.java
    ├── GuiBuilder.java
    ├── GuiEventHandler.java
    ├── GuiOrchestrator.java
    ├── GuiStyler.java
    ├── GreetingGenerator.java
    ├── MainWindow.java
    ├── MessageFormatter.java
    ├── ResponseBuilder.java
    ├── TaskListFormatter.java
    └── Ui.java (facade)
```

### Appendix B: Refactoring Checklist

For each refactoring task:
- [ ] Create new class(es) as specified
- [ ] Move identified methods to new class(es)
- [ ] Update original class to use new class(es)
- [ ] Update all imports
- [ ] Run full test suite
- [ ] Fix any failing tests
- [ ] Update documentation
- [ ] Code review
- [ ] Merge changes

### Appendix C: Testing Checklist

For each refactoring task:
- [ ] Unit tests for new classes
- [ ] Integration tests for component interactions
- [ ] Regression tests for existing functionality
- [ ] Manual testing of GUI features
- [ ] Performance testing (if applicable)
- [ ] Error handling testing
- [ ] Edge case testing

---

**Document Version:** 1.0
**Last Updated:** 2026-02-18
**Author:** Kilo Code (Architect Mode)
