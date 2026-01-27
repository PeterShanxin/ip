#!/usr/bin/env bash

# Colors and formatting
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color
BOLD='\033[1m'

# Print header
echo -e "${BOLD}===============================================${NC}"
echo -e "${BLUE}              MONDAY Test Runner              ${NC}"
echo -e "${BOLD}===============================================${NC}\n"

# Print Java version for debugging
echo -e "${BOLD}[1/5] Checking Java version...${NC}"
java -version

# check if using Java 21
java_ver=$(java -version 2>&1 | grep -i version | head -n 1)
echo -e "${BLUE}Detected Java version: ${NC}$java_ver"

version=$(echo $java_ver | sed -n 's/.*version "\([0-9]*\).*/\1/p')
echo -e "${BLUE}Parsed major version: ${NC}$version"

if [ "$version" != "21" ]; then
    echo -e "\n${RED}╔════ ERROR ══════════════════════════════════╗${NC}"
    echo -e "${RED}║ Please use Java 21 (current version: $version)${NC}"
    echo -e "${RED}╚═════════════════════════════════════════════╝${NC}"
    exit 1
fi

echo -e "${GREEN}✓ Java version check passed${NC}\n"

# create bin directory if it doesn't exist
echo -e "${BOLD}[2/5] Setting up test environment...${NC}"
if [ ! -d "../bin" ]
then
    mkdir ../bin
    echo -e "${BLUE}Created bin directory${NC}"
fi

# delete output from previous run
if [ -e "./ACTUAL.TXT" ]
then
    rm ACTUAL.TXT
    echo -e "${BLUE}Cleaned up previous test outputs${NC}"
fi

# compile the code into the bin folder, terminates if error occurred
echo -e "\n${BOLD}[3/5] Compiling source files...${NC}"
if ! javac -cp ../src/main/java -Xlint:none -d ../bin ../src/main/java/monday/*.java
then
    echo -e "\n${RED}╔════ ERROR ══════════════════════════════════╗${NC}"
    echo -e "${RED}║            BUILD FAILURE                     ║${NC}"
    echo -e "${RED}╚═════════════════════════════════════════════╝${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Compilation successful${NC}\n"

# run the program, feed commands from test files
echo -e "${BOLD}[4/5] Running test files...${NC}\n"

# Define test files in order
TEST_FILES="01-happy-path.txt 02-empty-errors.txt 03-todo-errors.txt 04-deadline-errors.txt 05-event-errors.txt 06-mark-unmark-errors.txt 07-case-insensitivity.txt 08-edge-cases.txt"
PASSED=0
FAILED=0

for testfile in $TEST_FILES; do
    echo -e "${BLUE}Testing: $testfile${NC}"
    java -classpath ../bin monday.Monday < $testfile > ACTUAL.TXT

    expected="${testfile%.txt}-expected.txt"

    # convert to UNIX format for comparison
    cp $expected EXPECTED-UNIX.TXT 2>/dev/null
    dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT 2>/dev/null

    # compare the output to the expected output
    diff ACTUAL.TXT EXPECTED-UNIX.TXT > /dev/null 2>&1
    if [ $? -eq 0 ]; then
        echo -e "  ${GREEN}✓ [PASSED]${NC} $testfile"
        ((PASSED++))
    else
        echo -e "  ${RED}✗ [FAILED]${NC} $testfile"
        ((FAILED++))
    fi
    echo
done

echo -e "${BOLD}[5/5] Test Summary${NC}"
echo
echo -e "${BOLD}===============================================${NC}"
echo -e "${BLUE}              Test Results                    ${NC}"
echo -e "${BOLD}===============================================${NC}"
echo -e "  ${GREEN}Passed:$NC $PASSED"
echo -e "  ${RED}Failed:$NC $FAILED"
echo -e "  Total:  8 test files"
echo -e "${BOLD}===============================================${NC}\n"

if [ $FAILED -gt 0 ]; then
    echo -e "${RED}╔════ ERROR ══════════════════════════════════╗${NC}"
    echo -e "${RED}║         Some tests FAILED                  ║${NC}"
    echo -e "${RED}╚═════════════════════════════════════════════╝${NC}"
    exit 1
else
    echo -e "${GREEN}╔════ SUCCESS ════════════════════════════════╗${NC}"
    echo -e "${GREEN}║         All tests passed!                ║${NC}"
    echo -e "${GREEN}╚═════════════════════════════════════════════╝${NC}"
    exit 0
fi
