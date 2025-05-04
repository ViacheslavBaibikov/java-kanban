package test;

import model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {

    @Test
    public void testTaskEqualityById() {
        Task task1 = new Task("Task 1", "Description", TaskStatus.NEW, TaskPriority.MEDIUM);
        Task task2 = new Task("Task 1", "Description", TaskStatus.NEW, TaskPriority.MEDIUM);
        task1.setId(1);
        task2.setId(1);

        assertEquals(task1, task2, "Задачи с одинаковым ID должны быть одинаковы");
    }

    @Test
    public void testEpicEqualityById() {
        Epic epic1 = new Epic("Epic 1", "Epic description", TaskPriority.HIGH);
        Epic epic2 = new Epic("Epic 1", "Epic description", TaskPriority.HIGH);
        epic1.setId(1);
        epic2.setId(1);

        assertEquals(epic1, epic2, "Эпики с одинаковым ID должны быть одинаковы");
    }

    @Test
    public void testSubtaskEqualityById() {
        Subtask subtask1 = new Subtask("Subtask 1", "Subtask description", TaskStatus.NEW, TaskPriority.MEDIUM, 1);
        Subtask subtask2 = new Subtask("Subtask 1", "Subtask description", TaskStatus.NEW, TaskPriority.MEDIUM, 1);
        subtask1.setId(1);
        subtask2.setId(1);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым ID должны быть одинаковы");
    }
}
