package manager;

import model.*;

import java.util.*;

public interface TaskManager {

    List<Task> getAllTasks();  // Получаем все задачи

    Task getTaskById(int id);  // Получаем задачу по ID

    Epic getEpicById(int id);  // Получаем эпик по ID

    Subtask getSubtaskById(int id);  // Получаем подзадачу по ID

    List<Epic> getAllEpics();  // Получаем все эпики

    List<Subtask> getAllSubtasks();  // Получаем все подзадачи

    void createTask(Task task);  // Создаём задачу

    void createEpic(Epic epic);  // Создаём эпик

    void createSubtask(Subtask subtask);  // Создаём подзадачу

    void updateTask(Task task);  // Обновляем задачу

    void updateEpic(Epic epic);  // Обновляем эпик

    void updateSubtask(Subtask subtask);  // Обновляем подзадачу

    void updateTaskPriority(int taskId, TaskPriority priority);  // Обновляем приоритет задачи

    void deleteTask(int id);  // Удаляем задачу

    void deleteEpic(int id);  // Удаляем эпик

    void deleteSubtask(int id);  // Удаляем подзадачу

    void deleteAllTasks();  // Удаляем все задачи

    void deleteSubtasksByEpicId(int epicId);  // Удаляем все подзадачи для эпика

    void deleteAllEpics();  // Удаляем все эпики

    void deleteAll();  // Удаляем все задачи, эпики и подзадачи

    List<Subtask> getSubtasksOfEpic(int epicId);  // Получаем подзадачи для эпика

    List<Task> getHistory(); //получаем историю просмотров
}
