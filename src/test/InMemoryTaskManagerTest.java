package test;

import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private InMemoryTaskManager manager;

    @BeforeEach
    void setup() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        manager = new InMemoryTaskManager(historyManager);
    }

    @Test
    void shouldAddAndRetrieveTaskById() {
        Task task = new Task("Test Task", "Test Description", TaskStatus.NEW, TaskPriority.MEDIUM);
        manager.createTask(task);
        int id = task.getId();

        Task retrieved = manager.getTaskById(id);
        assertNotNull(retrieved, "Задача должна быть найдена по ID");
        assertEquals(task, retrieved, "Полученная задача должна совпадать с исходной");
    }

    @Test
    void shouldAddAndRetrieveEpicAndSubtaskById() {
        Epic epic = new Epic("Test Epic", "Epic Description", TaskPriority.HIGH);
        manager.createEpic(epic);
        int epicId = epic.getId();

        Subtask subtask = new Subtask("Test Subtask", "Subtask Description", TaskStatus.NEW, TaskPriority.LOW, epicId);
        manager.createSubtask(subtask);
        int subtaskId = subtask.getId();

        Epic retrievedEpic = manager.getEpicById(epicId);
        Subtask retrievedSubtask = manager.getSubtaskById(subtaskId);

        assertNotNull(retrievedEpic, "Эпик должен быть найден по ID");
        assertNotNull(retrievedSubtask, "Подзадача должна быть найдена по ID");
        assertEquals(epic, retrievedEpic, "Полученный эпик должен совпадать с исходным");
        assertEquals(subtask, retrievedSubtask, "Полученная подзадача должна совпадать с исходной");
    }

    @Test
    void shouldNotConflictTasksWithGeneratedIds() {
        Task task1 = new Task("Task 1", "Desc 1", TaskStatus.NEW, TaskPriority.LOW);
        Task task2 = new Task("Task 2", "Desc 2", TaskStatus.IN_PROGRESS, TaskPriority.MEDIUM);

        manager.createTask(task1);
        manager.createTask(task2);

        assertNotEquals(task1.getId(), task2.getId(), "Задачи должны иметь уникальные ID");
    }

    @Test
    void shouldPreserveTaskFieldsAfterAdding() {
        Task task = new Task("Persistent Task", "Stable Data", TaskStatus.DONE, TaskPriority.HIGH);
        manager.createTask(task);
        int id = task.getId();

        Task retrieved = manager.getTaskById(id);

        assertEquals(task.getDescription(), retrieved.getDescription(), "Описание должно сохраняться без изменений");
        assertEquals(task.getStatus(), retrieved.getStatus(), "Статус должен сохраняться без изменений");
        assertEquals(task.getPriority(), retrieved.getPriority(), "Приоритет должен сохраняться без изменений");
    }
}
