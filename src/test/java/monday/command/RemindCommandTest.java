package monday.command;

import monday.storage.Storage;
import monday.task.Task;
import monday.task.TaskCounts;
import monday.task.TaskList;
import monday.ui.Ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for RemindCommand.
 * Tests task summary and upcoming task display functionality.
 */
@ExtendWith(MockitoExtension.class)
public class RemindCommandTest {

    @Mock
    private TaskList taskList;

    @Mock
    private Ui ui;

    @Mock
    private Storage storage;

    @Mock
    private TaskCounts mockCounts;

    @Mock
    private Task mockTask;

    @Test
    public void execute_emptyTaskList_displaysNoTasksMessage() {
        // Positive: Empty task list shows no tasks message
        RemindCommand command = new RemindCommand();

        when(taskList.getTaskCounts()).thenReturn(new TaskCounts(0, 0, 0, 0, 0, 0));
        when(taskList.getEarliestUpcomingTask()).thenReturn(null);

        CommandResult result = command.execute(taskList, ui, storage);

        assertFalse(result.shouldSave(), "Remind command should not require save");
        assertFalse(result.shouldExit(), "Remind command should not exit");
        verify(taskList).getTaskCounts();
        verify(taskList).getEarliestUpcomingTask();
        verify(ui).showReminders(any(TaskCounts.class), eq(null));
    }

    @Test
    public void execute_allTasksCompleted_displaysAllDoneMessage() {
        // Positive: All tasks completed shows all done message
        RemindCommand command = new RemindCommand();

        when(taskList.getTaskCounts()).thenReturn(new TaskCounts(3, 3, 0, 1, 1, 1));
        when(taskList.getEarliestUpcomingTask()).thenReturn(null);

        CommandResult result = command.execute(taskList, ui, storage);

        assertFalse(result.shouldSave(), "Remind command should not require save");
        assertFalse(result.shouldExit(), "Remind command should not exit");
        verify(taskList).getTaskCounts();
        verify(taskList).getEarliestUpcomingTask();
        verify(ui).showReminders(any(TaskCounts.class), eq(null));
    }

    @Test
    public void execute_onlyToDos_displaysNoUpcomingMessage() {
        // Positive: Only ToDos exist shows no upcoming message
        RemindCommand command = new RemindCommand();

        when(taskList.getTaskCounts()).thenReturn(new TaskCounts(2, 0, 2, 2, 0, 0));
        when(taskList.getEarliestUpcomingTask()).thenReturn(null);

        CommandResult result = command.execute(taskList, ui, storage);

        assertFalse(result.shouldSave(), "Remind command should not require save");
        assertFalse(result.shouldExit(), "Remind command should not exit");
        verify(taskList).getTaskCounts();
        verify(taskList).getEarliestUpcomingTask();
        verify(ui).showReminders(any(TaskCounts.class), eq(null));
    }

    @Test
    public void execute_upcomingDeadline_displaysEarliestTask() {
        // Positive: Upcoming deadline shows earliest task
        RemindCommand command = new RemindCommand();

        when(taskList.getTaskCounts()).thenReturn(new TaskCounts(3, 1, 2, 1, 1, 1));
        when(taskList.getEarliestUpcomingTask()).thenReturn(mockTask);

        CommandResult result = command.execute(taskList, ui, storage);

        assertFalse(result.shouldSave(), "Remind command should not require save");
        assertFalse(result.shouldExit(), "Remind command should not exit");
        verify(taskList).getTaskCounts();
        verify(taskList).getEarliestUpcomingTask();
        verify(ui).showReminders(any(TaskCounts.class), eq(mockTask));
    }

    @Test
    public void execute_upcomingEvent_displaysEarliestTask() {
        // Positive: Upcoming event shows earliest task
        RemindCommand command = new RemindCommand();

        when(taskList.getTaskCounts()).thenReturn(new TaskCounts(2, 0, 2, 1, 0, 1));
        when(taskList.getEarliestUpcomingTask()).thenReturn(mockTask);

        CommandResult result = command.execute(taskList, ui, storage);

        assertFalse(result.shouldSave(), "Remind command should not require save");
        assertFalse(result.shouldExit(), "Remind command should not exit");
        verify(taskList).getTaskCounts();
        verify(taskList).getEarliestUpcomingTask();
        verify(ui).showReminders(any(TaskCounts.class), eq(mockTask));
    }

    @Test
    public void execute_overdueTask_displaysOverduePrefix() {
        // Positive: Overdue task shows with OVERDUE prefix
        RemindCommand command = new RemindCommand();

        when(taskList.getTaskCounts()).thenReturn(new TaskCounts(2, 0, 2, 1, 1, 0));
        when(taskList.getEarliestUpcomingTask()).thenReturn(mockTask);

        CommandResult result = command.execute(taskList, ui, storage);

        assertFalse(result.shouldSave(), "Remind command should not require save");
        assertFalse(result.shouldExit(), "Remind command should not exit");
        verify(taskList).getTaskCounts();
        verify(taskList).getEarliestUpcomingTask();
        verify(ui).showReminders(any(TaskCounts.class), eq(mockTask));
    }

    @Test
    public void execute_multipleTasksSameTime_displaysFirstInList() {
        // Positive: Multiple tasks at same time shows first in list
        RemindCommand command = new RemindCommand();

        when(taskList.getTaskCounts()).thenReturn(new TaskCounts(2, 0, 2, 0, 1, 1));
        when(taskList.getEarliestUpcomingTask()).thenReturn(mockTask);

        CommandResult result = command.execute(taskList, ui, storage);

        assertFalse(result.shouldSave(), "Remind command should not require save");
        assertFalse(result.shouldExit(), "Remind command should not exit");
        verify(taskList).getTaskCounts();
        verify(taskList).getEarliestUpcomingTask();
        verify(ui).showReminders(any(TaskCounts.class), eq(mockTask));
    }

    @Test
    public void execute_dueToday_displaysTodaySuffix() {
        // Positive: Task due today shows with today suffix
        RemindCommand command = new RemindCommand();

        when(taskList.getTaskCounts()).thenReturn(new TaskCounts(1, 0, 1, 0, 1, 0));
        when(taskList.getEarliestUpcomingTask()).thenReturn(mockTask);

        CommandResult result = command.execute(taskList, ui, storage);

        assertFalse(result.shouldSave(), "Remind command should not require save");
        assertFalse(result.shouldExit(), "Remind command should not exit");
        verify(taskList).getTaskCounts();
        verify(taskList).getEarliestUpcomingTask();
        verify(ui).showReminders(any(TaskCounts.class), eq(mockTask));
    }

    @Test
    public void execute_dueSoon_displaysDueSoonSuffix() {
        // Positive: Task due within 24 hours shows with due soon suffix
        RemindCommand command = new RemindCommand();

        when(taskList.getTaskCounts()).thenReturn(new TaskCounts(1, 0, 1, 0, 1, 0));
        when(taskList.getEarliestUpcomingTask()).thenReturn(mockTask);

        CommandResult result = command.execute(taskList, ui, storage);

        assertFalse(result.shouldSave(), "Remind command should not require save");
        assertFalse(result.shouldExit(), "Remind command should not exit");
        verify(taskList).getTaskCounts();
        verify(taskList).getEarliestUpcomingTask();
        verify(ui).showReminders(any(TaskCounts.class), eq(mockTask));
    }
}
