# iP.AI - AI-Assisted Development Record

This project uses the iP.AI approach to minimize hand-coding by leveraging generative AI tools.

## AI Tools Used

### Initial Setup (Week 1)

- **Tool**: Claude Code (Anthropic) with GLM API (GLM-4.7) integration
- **Purpose**: AI-assisted Java development for CS2103T Individual Project
- **Configuration**: Initialized with project documentation (CLAUDE.md), skills, custom workflows

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
