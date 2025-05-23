package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Epic extends Task {
    private final List<Subtask> subtasks;

    public Epic(String name, String description, TaskPriority priority) {
        super(name, description, TaskStatus.NEW, priority); // статус задается по умолчанию
        this.type = TaskType.EPIC;
        this.subtasks = new ArrayList<>();
    }


    //  добавление подзадачи
    public void addSubtask(Subtask subtask) {
        Objects.requireNonNull(subtask, "Подзадача не может быть пустой");
        subtasks.add(subtask);
    }

    // удаление подзадачи по id
    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    // Получить список подзадач
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks);
    }

}



