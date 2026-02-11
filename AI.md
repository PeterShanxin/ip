# iP.AI - AI-Assisted Development Record

This project uses the iP.AI approach to minimize hand-coding by leveraging generative AI tools.

## AI Tools Used

### Initial Setup (Week 1)

- **Tool**: Claude Code (Anthropic) with GLM API (GLM-4.7) integration
- **Purpose**: AI-assisted Java development for CS2103T Individual Project
- **Configuration**: Initialized with project documentation (CLAUDE.md), skills, custom workflows

## General Workflow Observations

### Agent Review Best Practices

**Key Insight**: AI agents work better with separate sessions for implementation vs. review.

**Why this matters**:

- The agent that implements code has "blind spots" - they know the intent and may overlook edge cases
- A separate reviewer (fresh agent/session) has no preconceptions and catches more issues
- Example: Level-3 implementer wrote functional code but missed:
  - Failing integration tests (EXPECTED.TXT not updated)
  - Missing unit tests
  - Input validation weakness

**Recommended workflow**:

1. **Implementation session** - Focus on feature completion
2. **Separate review session** - Fresh agent reviews the work without implementation context
3. **Fix session** - Address issues found by reviewer

This separation of concerns improves code quality significantly.

## Development Log

*[Weekly updates to include: for which increment, observations on what worked/didn't work, time saved]*

### Week 3

#### Level-0: Rename, Greet, Exit

- **What was attempted**: Implement initial skeletal version that greets user and exits, rename Duke to Monday
- **What worked**:
  - Successfully renamed Duke.java to Monday.java
  - Implemented greet/exit functionality
  - Updated test scripts and documentation
- **What didn't work**:
  - AI left `seedu.duke.Monday` in build.gradle instead of `Monday` (wrong package path)
  - Comment said "Grumpy greeting" but output was polite - inconsistency
  - No horizontal lines initially (spec said optional, but fits personality better)
- **Fixes applied**:
  - Corrected mainClass to `"Monday"` in build.gradle
  - Updated greeting/exit messages to be grumpy (matches MONDAY personality)
  - Added horizontal line separators
- **Observations**: AI made a verification gap - claimed build.gradle was updated but didn't validate the change was correct. Always test Gradle commands after build config changes.

#### Level-1: Echo

- **What was attempted**: Add command echoing and change exit command from 'exit' to 'bye'
- **What worked**:
  - Successfully changed exit command to `bye` (case-insensitive)
  - Command echoing properly wraps input in dividers
  - Maintained grumpy MONDAY personality in exit message
  - Text UI tests updated and passing
  - Clean commit message following se-edu conventions
- **What didn't work**: Nothing - implementation was spec-compliant on first attempt
- **Observations**: AI handled this straightforward increment well. Properly extracted divider to constant, used `equalsIgnoreCase()` for robust input handling, and closed Scanner resource. The customized personality ("Finally, you're leaving...") aligns with spec encouragement to personalize the chatbot.

#### Level-2: Add, List

- **What was attempted**: Add ability to store user text and display as numbered list
- **What worked**:
  - Used `ArrayList<String>` for flexible task storage (better than fixed array)
  - Added `MAX_TASKS = 100` constant with proper enforcement
  - `list` command shows numbered output (1. task, 2. task, etc.)
  - Edge cases handled: empty input, empty list, max tasks reached
  - Case-insensitive command matching (`equalsIgnoreCase`)
  - Grumpy personality maintained in all messages
  - Text UI tests updated and passing
  - Clean commit following se-edu conventions
  - Code review: 100% SE-EDU coding standards compliance
- **What didn't work**: Nothing - implementation was spec-compliant on first attempt
- **Observations**: AI made good architectural decisions (ArrayList over fixed array while enforcing limit). Agentic workflows (`/stage-prepare-commit`, `/review-code-quality`) significantly improved consistency - time saved for manual standards checking needed.

#### Level-3: Mark as Done (with Unmark)

- **What was attempted**: Add task completion tracking with mark/unmark commands
- **What worked**:
  - Created `Task` class with proper encapsulation (description, isDone)
  - Both mark/unmark commands implemented
  - Proper error handling for invalid input
  - User guide updated with examples
- **What didn't work**:
  - Initial implementation had bugs: case-sensitive commands (inconsistent), code duplication, magic strings
  - Text UI test failing (EXPECTED.TXT not updated)
  - No unit tests written for Task class
- **Observations**: Implementer agent (449994c) produced functional code but with bugs. Separate reviewer agent (4ff1465) caught and fixed: case-sensitivity bug, code duplication, magic strings, redundant calls. **Key insight: Agent self-review has blind spots; use separate review sessions.**

#### Level-4: ToDo, Deadline, Event

- **What was attempted**: Add three task types with date/time tracking (todo, deadline, event)
- **What worked**:
  - Excellent inheritance design: Task base class with ToDo/Deadline/Event subclasses
  - Proper polymorphism with getTypeIcon() and getFullDescription() overrides
  - Date/time parsing with /by, /from, /to markers working correctly
  - Comprehensive error messages aligned with grumpy personality
  - User guide fully documented with examples
  - Code review caught and fixed: generic Exception catching → specific ArrayIndexOutOfBoundsException
- **What didn't work**: Initial commit used generic `catch (Exception)` instead of specific exception types
- **Fixes applied**: Replaced generic Exception with ArrayIndexOutOfBoundsException in handleDeadline/handleEvent
- **Observations**: AI demonstrated strong OOD principles (inheritance, polymorphism, immutability). Code review workflow essential - caught exception handling issue post-commit. Time saved: ~2 hours (vs manual implementation + debugging).

#### A-TextUiTesting: Automated Text UI Testing

- **What was attempted**: Implement comprehensive automated testing using I/O redirection technique
- **What worked**:
  - One-shot success - implementation worked correctly on first attempt
  - Multiple test files strategy (8 focused tests) for better maintainability
  - Enhanced test runners with pass/fail tracking and summary reports
  - Comprehensive coverage: 13 unique error messages + all happy paths
  - Hybrid approach: multiple files for development, single combined file for grading
  - Code review: EXCELLENT rating, no critical or major issues found
- **What didn't work**: Nothing - implementation was spec-compliant on first attempt
- **Observations**: AI performed very well with thorough exploration before implementation. User consultation on approach (multiple vs single files) and scope (stretch goal) was key decision point. Systematic approach: infrastructure → test files → expected outputs → validation. Time saved: 3-4 hours (vs manual testing).

#### Level-5: Handle Errors

- **What was attempted**: Add precise unknown command error detection for command-only inputs (e.g., "todo", "mark", "blah")
- **What worked**:
  - Implemented `extractCommandWord()` helper for clean first-word extraction
  - Implemented `getUnknownCommandErrorMessage()` for grumpy error messages
  - Refactored main loop to use precise command detection (matches command words exactly)
  - All 8 text UI tests passing with updated expected outputs
  - Code review: EXCELLENT rating, no issues after cleanup
  - Plan mode effectively explored codebase and designed approach before implementation
  - Code review caught unused method (`isKnownCommand()`) for cleaner code
- **What didn't work**: Initial implementation included unused `isKnownCommand()` method that review agent flagged as minor lint issue
- **Fixes applied**: Removed unused method during code review cleanup phase
- **Observations**: Preconfigured workflow highly effective, especially context review agent. Plan mode excellent for codebase exploration. Task agents efficiently handled code review with isolated context. Code review caught unused method that would have been missed. Time saved: 2-3 hours (vs manual implementation + testing).

#### Level-6: Delete Command

- **What was attempted**: Add delete command to remove tasks from the task list by task number
- **What worked**:
  - Pattern-following approach: reused mark/unmark implementation pattern successfully
  - Created `handleTaskRemoval()` helper method (DRY principle)
  - Proper validation: non-numeric and out-of-range error handling
  - Grumpy error messages aligned with MONDAY personality
  - Updated help text and user guide
  - Text UI test file created (09-delete-errors) with comprehensive coverage
  - All 9 tests passing (8 existing + 1 new)
  - Code review: Grade A, no issues found
- **What didn't work**: Nothing - implementation was spec-compliant on first attempt
- **Observations**: One-shot success due to pattern-following approach. The delete command followed the same structure as mark/unmark (validate input → perform action → confirm). This demonstrates the value of recognizing and reusing established patterns. AI avoided code duplication by extracting shared logic. Time saved: ~0.5 hours (vs manual implementation + testing).

#### A-Enums: Use Java Enums

- **What was attempted**: Replace hardcoded string literals with type-safe Java enums throughout the codebase
- **What worked**:
  - Created 3 enums: TaskType (task icons), TaskPrefix (parameter prefixes), CommandType (command words with aliases)
  - Demonstrated proper enum features: state, methods, factory pattern, switch compatibility
  - Refactored 5 source files (Task, ToDo, Deadline, Event, Monday) to use enums
  - Replaced else-if chain with switch statement on CommandType enum
  - All 8 text UI tests passing
  - Code review: EXCELLENT rating, 0 issues (3 info-level observations only)
  - One-shot success - implementation worked correctly on first attempt
- **What didn't work**: Nothing - implementation was spec-compliant on first attempt
- **Observations**: AI demonstrated strong understanding of Java enum capabilities. Factory methods in CommandType (fromString(), isCommand(), isValid()) eliminate magic strings throughout codebase. Type safety reduces bugs and improves maintainability. One-shot success saved significant debugging time. Time saved: ~3 hours (vs manual refactoring + testing).

### Week 4

#### Level-7: File Persistence

- **What was attempted**: Add automatic save/load functionality for tasks to enable data persistence between sessions
- **What worked**:
  - Created Storage class with load/save methods using java.nio.file for OS-independent paths
  - Pipe-delimited file format (human-readable): "T | 0 | read book", "D | 1 | task | by: deadline"
  - Auto-load on startup, auto-save after every task modification (add/delete/mark/unmark)
  - Graceful handling: missing files created automatically, corrupted lines skipped with warnings
  - MondayStorageException custom exception for error handling
  - GitHub Copilot review: only 1 minor privacy suggestion (removed line content from error message)
  - Code review: EXCELLENT rating, 0 issues found
  - All text UI tests passing with data/ cleanup in test runner
- **What didn't work**: Nothing - implementation was spec-compliant on first attempt
- **Fixes applied**: Removed corrupted line content from stderr output (privacy improvement per Copilot review)
- **Observations**: Surprisingly effective - GLM-4.7 (open-source) handled this longer, complex task very well. Code review only gave 1 tiny suggestion. Branch workflow (--no-ff merge, separate branch, tagging) executed correctly. Time saved: ~3 hours (vs manual file I/O implementation + testing).

#### Level-7 Stretch Goal: Enhanced Corrupted Data Handling

- **What was attempted**: Handle corrupted data in monday.txt with field validation, corruption summary, and automatic backup
- **What worked**:
  - Created LoadResult wrapper class to return both tasks and corruption metadata
  - Field validation: rejects empty descriptions and empty date/time fields
  - Corruption summary: displays count of corrupted lines to user on load
  - Corrupted line backup: saves to monday.txt.corrupted for recovery
  - Save on exit: cleans file even if user only views list (user-requested enhancement)
  - All 8 text UI tests passing
- **What didn't work**: First attempt needed code review fixes (hollow state, interface types), and a behavior enhancement (save on exit) was added only after user pointed out the issue
- **Fixes applied**: Fixed hollow state in Monday.java, changed to List<Task> interface, extracted constants/helper methods
- **Observations**: AI took longer on this task and had room for improvement. Code review found several issues (2 P1, 4 P2) that needed fixing. Save-on-exit behavior wasn't considered until user questioned it - highlights the importance of providing clear guidance to AI. Still, GLM-4.7 performed competently overall.

#### Level-8: Dates and Times

- **What was attempted**: Replace string-based date storage with java.time.LocalDateTime and add view command to filter tasks by date
- **What worked**:
  - Created DateTimeParser utility class with multi-format parsing (yyyy-MM-dd HHmm OR d/M/yyyy HHmm)
  - Changed Deadline/Event to use LocalDateTime instead of String storage
  - Added view <date> command filtering both deadlines and events by date
  - Supports multiple input formats with graceful fallback
  - Storage format: yyyy-MM-dd HH:mm (sortable), Display format: MMM dd yyyy HHmm
  - Copilot review: 6 suggestions (1 accepted, 5 rejected - valid design/personality choices)
  - Two code review cycles: 2 major + 6 minor → 0 major + 2 minor → 0 issues
  - All changes merged to master, tagged as Level-8, pushed
- **What didn't work**:
  - Error message format bug (old 'Sunday' example in Monday.java:623)
  - Copilot suggested multi-day event support (rejected - not in spec)
- **Fixes applied**:
  - Fixed error message to use new datetime format (Copilot review)
  - Extracted DateTimeParser utility (eliminated duplicate formatters)
  - Replaced generic Exception with DateTimeParseException in Storage
  - Removed redundant wrapper methods after second review
- **Observations**: AI sometimes moves too fast - need careful prompt engineering and monitoring. Performance was acceptable overall. Multiple review cycles improved quality (2+6 issues → 0). Time saved: ~2 hours (vs manual LocalDateTime refactoring + multi-format parsing).

#### A-MoreOOP: Use More OOP

- **What was attempted**: Major OOP refactoring to extract UI, storage, and parsing logic into separate classes following Command pattern
- **What worked**:
  - Monday.java reduced from 669 lines to 109 lines (84% reduction)
  - Created 13 new classes: Ui, Parser, TaskList, Command, CommandResult, CommandException, ParseException, and 8 Command subclasses
  - Full Command pattern implementation with polymorphic execute() methods
  - Storage refactored to instance-based (was static)
  - All functionality preserved, grumpy personality intact
  - Code review found only minor issues (2 tidy suggestions) - all fixed
  - SE-EDU coding standards followed throughout
- **What didn't work**: Nothing - implementation was spec-compliant on first attempt with only tidy-up fixes needed after review
- **Fixes applied**: Minor tidy-ups after code review (extracted duplicate error method, constant ordering)
- **Observations**: AI pushed beyond optional requirements with production-quality plan. Prompt engineering matters significantly - engaged another agent to craft the prompt. Frequent commits as instructed improved workflow vs previous increments (no explicit instruction = no commits before). Time saved: ~4 hours (vs manual OOP refactoring + testing).

#### A-Packages: Multiple package organization/stretch goal

- **What was attempted**: Reorganize codebase from single package to logical multi-package structure per Java conventions
- **What worked**:
  - Agent correctly identified repo already met Requirement 1 (single package acceptable)
  - Detailed plan for Requirement 2 (stretch goal) with 7 logical subpackages
  - One-shot implementation: organized 24 files into task, command, parser, storage, ui, exception, util
  - All package declarations and imports updated correctly
  - Cleaned up empty seedu/duke/ directory
  - Updated runtest.bat to use Gradle-built JAR
  - Code review passed with no critical issues
  - Successfully merged to master, tagged, and pushed
- **What didn't work**: Nothing - implementation was spec-compliant on first attempt
- **Observations**: Agent demonstrated strong understanding of package organization principles and Java project structure. Correctly assessed that Requirement 1 was already satisfied before proceeding to stretch goal. Clean one-shot execution with minimal errors. Time saved: ~2 hours (vs manual package refactoring + import updates).

#### A-Gradle: Gradle Enhancements

- **What was attempted**: Enhance Gradle build configuration with Checkstyle, Javadoc, and custom test tasks beyond core requirements
- **What worked**:
  - Agent correctly identified core Gradle requirements already implemented (build, test, run, shadowJar)
  - Added Checkstyle plugin v10.12.5 with proper configuration
  - Added Javadoc task configuration with proper settings
  - Created custom runTextUiTest task for automated text UI testing
  - All enhancements integrated cleanly into existing build.gradle
- **What didn't work**:
  - Code review found major issues during implementation (2 major issues detected)
  - AI attempted to rush through steps after first fix without adequate verification
- **Fixes applied**: All issues identified by code review were addressed and fixed
- **Observations**: Agent demonstrated good situational awareness (recognized existing Gradle support). However, review found major issues at times - proving a separate review layer is important. Notably, AI tried to rush subsequent steps after first fix, highlighting that human monitoring and review checkpoints remain critical in agentic workflows. 8 commits total. Time saved: ~3 hours.

#### A-JUnit: JUnit Tests

- **What was attempted**: Add comprehensive JUnit tests covering all major classes in the codebase
- **What worked**:
  - Created 10 test classes with 162 tests total covering command, parser, storage, task, and util packages
  - All tests passing on first attempt after auto-fix for one test failure
  - Test coverage includes: command execution, parsing logic, storage operations, task methods, date parsing
  - Edge cases covered: empty inputs, invalid formats, null values, boundary conditions
  - Copilot review found only 1 minor suggestion (assertNotSame for defensive copying)
  - One-shot success with predefined test specifications in prompt
- **What didn't work**: One test failure initially (auto-fixed by AI during implementation)
- **Fixes applied**: Applied Copilot suggestion for assertNotSame to verify defensive copying in TaskList.getTasks()
- **Observations**: AI completed with high accuracy in oneshot, inclusive of auto fix against test failure, review found very limited minor lints - predefined tests in prompt helps. Time saved: ~4 hours (vs manual test writing + debugging).

#### A-JavaDoc: JavaDoc Comments

- **What was attempted**: Add JavaDoc comments to all non-private classes and methods
- **What worked**:
  - AI review agent (a654ee6) thoroughly analyzed all 28 Java source files across 7 packages
  - Review confirmed 100% JavaDoc coverage already exists from previous development work
  - All public classes, methods, and constructors have complete JavaDoc documentation
  - All JavaDoc follows SE-EDU format: verb-first descriptions, blank lines before tags, proper punctuation
  - Javadoc generation passes without warnings
  - All 162 tests pass
- **What didn't work**: Nothing - the increment requirement was already satisfied
- **Fixes applied**: None needed - no code changes required
- **Observations**: Professional AI work from previous increments (A-MoreOOP, A-Packages, A-Gradle, A-JUnit) had already added comprehensive JavaDoc. The AI reviewer demonstrated excellent analysis capabilities by systematically verifying coverage across all files rather than making unnecessary edits. This is a positive outcome - previous work was high quality and no remediation was needed.

### Multi-Branch Session: A-CodingStandard, Level-9, A-Cheer

- **What was attempted**: Implement three increments (A-CodingStandard, Level-9, A-Cheer) using parallel git worktrees for concurrent development
- **What worked**:
  - A-CodingStandard: Fixed import ordering in 16 files to comply with SE-EDU standards (static, java, org, com, monday imports)
  - Level-9: Added find command with case-insensitive keyword search across all tasks, plus 9 new ParserTest cases
  - A-Cheer: Added cheer command that displays colored grumpy quotes from data/cheer.txt
  - Used git worktrees for parallel development (4 branches: A-CodingStandard, Level-9, A-Cheer, plus master)
  - Successfully resolved merge conflicts when integrating parallel branches
  - Codex and GitHub Copilot reviews provided valuable feedback (caught import issues, missing Javadoc, file path problems)
- **What didn't work**:
  - Parallel branch development proved very troublesome due to merge conflicts
  - Each merge required careful conflict resolution (import ordering, file modifications overlapping)
  - A-Cheer initially had file path issues (data/cheer.txt location)
  - Multiple review cycles needed to address all findings from external tools
- **Fixes applied**:
  - Reordered imports in 16 files per SE-EDU conventions
  - Fixed Javadoc missing for new find/cheer commands
  - Corrected file path for cheer.txt resource loading
- **Observations**: AI helps and guides, AI understands the tasks, external reviews like Codex and GitHub Copilot helps; though learnt resolving conflicts but taken the lesson that don't do parallel branches if not necessary - very troublesome. The experience showed that while AI can handle parallel workflows and conflict resolution, the overhead of merging outweighs the benefits for small increments. Time spent on conflict resolution negated any theoretical parallelism gains. External AI review tools (Codex, Copilot) were valuable for catching issues that human review might miss.

#### A-CheckStyle: Checkstyle Integration

- **What was attempted**: Integrate Checkstyle into Gradle build with SE-EDU coding standards configuration
- **What worked**:
  - Checkstyle plugin configured with custom rules (config/checkstyle/checkstyle.xml)
  - Project import groups properly defined (monday.* and org.junit grouping)
  - Suppressions for JUnit test naming conventions (MethodName check)
  - Build task integrated with checkstyle validation
  - Fixed pre-existing Level-9 parser bugs (find and cheer command routing)
  - Fixed import order violations across codebase (12 files)
- **What didn't work**:
  - Model selection issues after claude code update (possibly GLM 4.5 instead of 4.7)
  - Session less efficient than before, possibly due to long context
  - Required significant debugging/undo/redo interactions
- **Fixes applied**: Model toggling improved performance after correction to GLM 4.7
- **Observations**: AI session inefficiency this time - model confusion and debugging overhead reduced time savings. Manual coding would take similar time considering bypass/debugging effort. Highlights importance of correct model selection and context management. Time saved: ~0 hours (minimal gains due to debugging overhead).

#### Level-10: JavaFX GUI

- **What was attempted**: Add JavaFX graphical user interface to replace text UI with a modern windowed application
- **What worked**:
  - Created Launcher.java main class for JavaFX application startup
  - Added JavaFX dependencies to build.gradle
  - Implemented MainWindow.fxml with dialog layout and UI styling
  - Created DialogBox.java for chat message display
  - Integrated with existing Monday logic via Ui class modifications
- **What didn't work**:
  - GLM 4.7 could not handle initial request due to ARM64 JDK incompatibility with Gradle/JavaFX
  - GLM kept retrying without proposing alternative solutions
- **Fixes applied**:
  - Switched to Codex (frontier model) which suggested x64 JDK workaround
  - Set JAVA_HOME_X64 environment variable for GUI emulation
- **Observations**: Sometimes a better model provides a better response. GLM just kept retrying without proposing alternative plan, while Codex suggested x64 JDK download and setup for emulation. Codex runs very fast in general. Multi-model approach valuable when stuck - knowing when to escalate matters. Time saved: ~2 hours (with Codex assistance vs manual JavaFX setup).

#### A-Varargs: Use Java Varargs Feature

- **What was attempted**: Refactor CommandType enum to use varargs for aliases parameter
- **What worked**:
  - Changed constructor parameter from `String[] aliases` to `String... aliases` (varargs)
  - Updated enum constant `BYE("bye", new String[]{"exit"})` to `BYE("bye", "exit")`
  - Cleaner, more idiomatic Java code
- **What didn't work**: Nothing - implementation was spec-compliant on first attempt
- **Fixes applied**: None needed
- **Observations**: This increment is a very simple task; GLM4.7 handled it very fast. Varargs syntax improvement makes code more readable and follows modern Java conventions. Time saved: ~0.25 hours (15 minutes vs manual refactoring).
