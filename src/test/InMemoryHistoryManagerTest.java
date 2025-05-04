package test;

import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import model.Task;
import model.TaskPriority;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private HistoryManager historyManager;

    @BeforeEach
    void setup() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void shouldAddTaskToHistory() {
        Task task = new Task("История задачи", "Описание", TaskStatus.NEW, TaskPriority.MEDIUM);
        task.setId(1);

        historyManager.add(task);
        List<Task> history = historyManager.getHistory();

        assertEquals(1, history.size(), "История должна содержать одну задачу после добавления");
        assertEquals(task, history.getFirst(), "Задача в истории должна совпадать с добавленной");
    }

    @Test
    void shouldNotDuplicateTasksInHistory() {
        Task task = new Task("Повтор", "Описание", TaskStatus.NEW, TaskPriority.LOW);
        task.setId(1);

        historyManager.add(task);
        historyManager.add(task);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();

        assertEquals(1, history.size(), "Одинаковая задача не должна дублироваться в истории");
    }

    @Test
    void shouldKeepTasksInAccessOrder() {
        Task task1 = new Task("Первая", "1", TaskStatus.NEW, TaskPriority.LOW);
        task1.setId(1);
        Task task2 = new Task("Вторая", "2", TaskStatus.NEW, TaskPriority.MEDIUM);
        task2.setId(2);
        Task task3 = new Task("Третья", "3", TaskStatus.NEW, TaskPriority.HIGH);
        task3.setId(3);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        List<Task> history = historyManager.getHistory();

        assertEquals(List.of(task1, task2, task3), history, "Задачи должны храниться в порядке добавления");
    }

    @Test
    void shouldMoveTaskToEndIfAccessedAgain() {
        Task task1 = new Task("Первая", "1", TaskStatus.NEW, TaskPriority.LOW);
        task1.setId(1);
        Task task2 = new Task("Вторая", "2", TaskStatus.NEW, TaskPriority.MEDIUM);
        task2.setId(2);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1); // повторный просмотр

        List<Task> history = historyManager.getHistory();

        assertEquals(List.of(task2, task1), history, "Задача должна переместиться в конец истории при повторном добавлении");
    }

    @Test
    void shouldClearHistoryOnRemove() {
        Task task = new Task("Очистка", "Удаление", TaskStatus.NEW, TaskPriority.LOW);
        task.setId(1);

        historyManager.add(task);
        historyManager.remove(task);


        List<Task> history = historyManager.getHistory();
        assertTrue(history.isEmpty(), "История должна быть пустой после удаления задачи");
    }
}

