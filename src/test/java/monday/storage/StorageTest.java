package monday.storage;

import monday.exception.MondayStorageException;
import monday.task.Deadline;
import monday.task.Event;
import monday.task.LoadResult;
import monday.task.ToDo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for Storage.
 * Tests file I/O operations with temp directory to avoid affecting actual data.
 */
public class StorageTest {

    private static final String TEST_DIR = "test_data";
    private static final String TEST_FILE = "test_monday.txt";

    private Storage storage;
    private Path testDataDir;
    private Path testFilePath;

    @BeforeEach
    public void setUp() throws IOException {
        // Create unique temp directory for each test
        String uniqueDir = TEST_DIR + "_" + System.currentTimeMillis();
        testDataDir = Path.of(uniqueDir);
        testFilePath = testDataDir.resolve(TEST_FILE);
        storage = new Storage(uniqueDir, TEST_FILE);
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up test directory
        if (Files.exists(testDataDir)) {
            Files.walk(testDataDir)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            // Ignore cleanup errors
                        }
                    });
        }
    }

    @Test
    public void testConstructor_validInput() {
        Storage testStorage = new Storage("data", "monday.txt");
        assertNotNull(testStorage);
    }

    @Test
    public void testLoadTasks_fileNotExists() throws MondayStorageException {
        LoadResult result = storage.loadTasks();
        assertNotNull(result);
        assertEquals(0, result.getTasks().size());
        assertEquals(0, result.getCorruptedLineCount());
        assertFalse(result.hasCorruption());
        assertTrue(Files.exists(testFilePath));
    }

    @Test
    public void testLoadTasks_emptyFile() throws IOException, MondayStorageException {
        Files.createDirectories(testDataDir);
        Files.createFile(testFilePath);
        LoadResult result = storage.loadTasks();
        assertNotNull(result);
        assertEquals(0, result.getTasks().size());
        assertEquals(0, result.getCorruptedLineCount());
    }

    @Test
    public void testLoadTasks_validTasks() throws IOException, MondayStorageException {
        Files.createDirectories(testDataDir);
        String content = "T | 0 | Read book\n"
                + "D | 0 | Return book | by: 2024-12-02 18:00\n"
                + "E | 0 | Meeting | from: 2024-12-25 14:00 | to: 2024-12-25 18:00\n"
                + "T | 1 | Done task";
        Files.writeString(testFilePath, content);

        LoadResult result = storage.loadTasks();
        List<monday.task.Task> tasks = result.getTasks();

        assertEquals(4, tasks.size());
        assertEquals(0, result.getCorruptedLineCount());
        assertFalse(result.hasCorruption());
    }

    @Test
    public void testLoadTasks_validTasks_doneStatus() throws IOException, MondayStorageException {
        Files.createDirectories(testDataDir);
        String content = "T | 1 | Done task\n"
                + "D | 1 | Done deadline | by: 2024-12-02 18:00\n"
                + "E | 1 | Done event | from: 2024-12-25 14:00 | to: 2024-12-25 18:00";
        Files.writeString(testFilePath, content);

        LoadResult result = storage.loadTasks();
        List<monday.task.Task> tasks = result.getTasks();

        assertEquals(3, tasks.size());
        assertTrue(tasks.get(0).isDone());
        assertTrue(tasks.get(1).isDone());
        assertTrue(tasks.get(2).isDone());
    }

    @Test
    public void testLoadTasks_corruptedLine_tooFewParts() throws IOException, MondayStorageException {
        Files.createDirectories(testDataDir);
        String content = "T | 0 | Valid task\n"
                + "T | 0\n"  // Missing description
                + "T | 0 | Another valid task";
        Files.writeString(testFilePath, content);

        LoadResult result = storage.loadTasks();
        assertEquals(2, result.getTasks().size());
        assertEquals(1, result.getCorruptedLineCount());
        assertTrue(result.hasCorruption());
    }

    @Test
    public void testLoadTasks_corruptedLine_emptyDescription() throws IOException, MondayStorageException {
        Files.createDirectories(testDataDir);
        String content = "T | 0 | Valid task\n"
                + "T | 0 |   \n"  // Empty description after trim
                + "T | 0 | Another valid task";
        Files.writeString(testFilePath, content);

        LoadResult result = storage.loadTasks();
        assertEquals(2, result.getTasks().size());
        assertEquals(1, result.getCorruptedLineCount());
    }

    @Test
    public void testLoadTasks_corruptedLine_invalidDate() throws IOException, MondayStorageException {
        Files.createDirectories(testDataDir);
        String content = "D | 0 | Task | by: invalid-date\n"
                + "T | 0 | Valid task";
        Files.writeString(testFilePath, content);

        LoadResult result = storage.loadTasks();
        assertEquals(1, result.getTasks().size());
        assertEquals(1, result.getCorruptedLineCount());
    }

    @Test
    public void testLoadTasks_corruptedLine_unknownType() throws IOException, MondayStorageException {
        Files.createDirectories(testDataDir);
        String content = "T | 0 | Valid task\n"
                + "X | 0 | Unknown type\n"
                + "T | 0 | Another valid task";
        Files.writeString(testFilePath, content);

        LoadResult result = storage.loadTasks();
        assertEquals(2, result.getTasks().size());
        assertEquals(1, result.getCorruptedLineCount());
    }

    @Test
    public void testLoadTasks_corruptedLine_deadlineMissingBy() throws IOException, MondayStorageException {
        Files.createDirectories(testDataDir);
        String content = "D | 0 | Task\n"
                + "T | 0 | Valid task";
        Files.writeString(testFilePath, content);

        LoadResult result = storage.loadTasks();
        assertEquals(1, result.getTasks().size());
        assertEquals(1, result.getCorruptedLineCount());
    }

    @Test
    public void testLoadTasks_corruptedLine_eventMissingFrom() throws IOException, MondayStorageException {
        Files.createDirectories(testDataDir);
        String content = "E | 0 | Event | to: 2024-12-25 18:00\n"
                + "T | 0 | Valid task";
        Files.writeString(testFilePath, content);

        LoadResult result = storage.loadTasks();
        assertEquals(1, result.getTasks().size());
        assertEquals(1, result.getCorruptedLineCount());
    }

    @Test
    public void testLoadTasks_corruptedLine_eventMissingTo() throws IOException, MondayStorageException {
        Files.createDirectories(testDataDir);
        String content = "E | 0 | Event | from: 2024-12-25 14:00\n"
                + "T | 0 | Valid task";
        Files.writeString(testFilePath, content);

        LoadResult result = storage.loadTasks();
        assertEquals(1, result.getTasks().size());
        assertEquals(1, result.getCorruptedLineCount());
    }

    @Test
    public void testLoadTasks_emptyLinesSkipped() throws IOException, MondayStorageException {
        Files.createDirectories(testDataDir);
        String content = "T | 0 | Task 1\n"
                + "\n"
                + "T | 0 | Task 2\n"
                + "   \n"
                + "T | 0 | Task 3";
        Files.writeString(testFilePath, content);

        LoadResult result = storage.loadTasks();
        assertEquals(3, result.getTasks().size());
        assertEquals(0, result.getCorruptedLineCount());
    }

    @Test
    public void testLoadTasks_extraWhitespace() throws IOException, MondayStorageException {
        Files.createDirectories(testDataDir);
        String content = "T  |  0  |  Task with spaces\n"
                + "D | 0 | Deadline | by: 2024-12-02 18:00";
        Files.writeString(testFilePath, content);

        LoadResult result = storage.loadTasks();
        assertEquals(2, result.getTasks().size());
        assertEquals("Task with spaces", result.getTasks().get(0).getDescription());
    }

    @Test
    public void testSaveTasks_validList() throws MondayStorageException, IOException {
        List<monday.task.Task> tasks = List.of(
                new ToDo("Read book"),
                new Deadline("Return book", LocalDateTime.of(2024, 12, 2, 18, 0))
        );

        storage.saveTasks(tasks);

        assertTrue(Files.exists(testFilePath));
        String content = Files.readString(testFilePath);
        assertTrue(content.contains("Read book"));
        assertTrue(content.contains("Return book"));
        assertTrue(content.contains("by: 2024-12-02 18:00"));
    }

    @Test
    public void testSaveTasks_emptyList() throws MondayStorageException, IOException {
        List<monday.task.Task> tasks = List.of();

        storage.saveTasks(tasks);

        assertTrue(Files.exists(testFilePath));
        String content = Files.readString(testFilePath);
        assertTrue(content.isEmpty());
    }

    @Test
    public void testSaveTasks_allTaskTypes() throws MondayStorageException, IOException {
        List<monday.task.Task> tasks = List.of(
                new ToDo("Todo task"),
                new Deadline("Deadline task", LocalDateTime.of(2024, 12, 2, 18, 0)),
                new Event("Event task", LocalDateTime.of(2024, 12, 25, 14, 0),
                        LocalDateTime.of(2024, 12, 25, 18, 0))
        );

        storage.saveTasks(tasks);

        assertTrue(Files.exists(testFilePath));
        String content = Files.readString(testFilePath);
        assertTrue(content.contains("T | 0 | Todo task"));
        assertTrue(content.contains("D | 0 | Deadline task"));
        assertTrue(content.contains("E | 0 | Event task"));
    }

    @Test
    public void testSaveTasks_overwritesExisting() throws MondayStorageException, IOException {
        Files.createDirectories(testDataDir);
        // Write initial content
        Files.writeString(testFilePath, "T | 0 | Old task");

        List<monday.task.Task> tasks = List.of(
                new ToDo("New task")
        );

        storage.saveTasks(tasks);

        String content = Files.readString(testFilePath);
        assertFalse(content.contains("Old task"));
        assertTrue(content.contains("New task"));
    }

    @Test
    public void testSaveAndLoad_roundtrip() throws MondayStorageException {
        List<monday.task.Task> originalTasks = List.of(
                new ToDo("Todo task"),
                new Deadline("Deadline task", LocalDateTime.of(2024, 12, 2, 18, 0)),
                new Event("Event task", LocalDateTime.of(2024, 12, 25, 14, 0),
                        LocalDateTime.of(2024, 12, 25, 18, 0))
        );

        storage.saveTasks(originalTasks);
        LoadResult result = storage.loadTasks();

        List<monday.task.Task> loadedTasks = result.getTasks();
        assertEquals(3, loadedTasks.size());
        assertEquals(0, result.getCorruptedLineCount());

        assertEquals("Todo task", loadedTasks.get(0).getDescription());
        assertEquals("Deadline task", loadedTasks.get(1).getDescription());
        assertEquals("Event task", loadedTasks.get(2).getDescription());
    }

    @Test
    public void testSaveTasks_doneStatus() throws MondayStorageException, IOException {
        List<monday.task.Task> tasks = List.of(
                new ToDo("Undone task"),
                new ToDo("Done task")
        );
        tasks.get(1).markAsDone();

        storage.saveTasks(tasks);

        String content = Files.readString(testFilePath);
        assertTrue(content.contains("T | 0 | Undone task"));
        assertTrue(content.contains("T | 1 | Done task"));
    }
}
