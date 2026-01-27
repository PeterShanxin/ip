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

### Week 2

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
