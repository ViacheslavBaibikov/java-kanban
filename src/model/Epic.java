package model;

import taskmanager.TaskManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Epic extends Task {
    private final List<Subtask> subtasks;

    public Epic(String name, String description, TaskStatus status, TaskPriority priority) {
        super(name, description, status, priority);
        this.type = TaskType.EPIC;
        this.subtasks = new ArrayList<>();
    }

    // Метод для добавления подзадачи
    public void addSubtask(Subtask subtask) {
        Objects.requireNonNull(subtask, "Подзадача не может быть пустой");
        subtasks.add(subtask);
    }

    // Метод для удаления подзадачи по id
    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    // Получить список подзадач
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks);
    }

}
