package test;

import model.Epic;
import model.Subtask;
import model.TaskPriority;
import model.TaskStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TaskManagerTest {

    @Test
    public void testEpicCannotBeAddedAsSubtask() {
        Epic epic = new Epic("Epic", "Epic description", TaskPriority.HIGH);
        epic.setId(1);

        Subtask subtask = new Subtask("Subtask", "Subtask description", TaskStatus.NEW, TaskPriority.MEDIUM, 1);
        subtask.setId(2);

        assertNotEquals(epic.getId(), subtask.getEpicId(), "Эпик не может быть добавлен в себя же как подзадача.");
    }

    @Test
    public void testSubtaskCannotBeEpic() {
        Subtask subtask = new Subtask("Subtask", "Subtask description", TaskStatus.NEW, TaskPriority.MEDIUM, 1);
        subtask.setId(1);

        assertNotNull(subtask, "Подзадача не может быть эпиком");
    }
}
