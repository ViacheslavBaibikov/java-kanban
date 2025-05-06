package test;

import manager.*;
import model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagerTest {

    @Test
    public void testHistoryManagerStoresPreviousTaskVersions() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

        Task task = new Task("Task 1", "Description", TaskStatus.NEW, TaskPriority.MEDIUM);

        historyManager.add(task);

        assertTrue(historyManager.getHistory().contains(task), "История должна содержать добавленную задачу");

        task.setStatus(TaskStatus.IN_PROGRESS);

        historyManager.add(task);


        assertEquals(1, historyManager.getHistory().size(), "История должна содержать последнюю версию задачи");
        assertEquals(TaskStatus.IN_PROGRESS, historyManager.getHistory().getFirst().getStatus(), "Статус задачи в истории должен быть обновлен");
    }
}

