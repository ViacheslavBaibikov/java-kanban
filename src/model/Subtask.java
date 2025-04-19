package model;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String name, String description, TaskStatus status, TaskPriority priority, int epicId) {
        super(name, description, status, priority);
        this.type = TaskType.SUBTASK;
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

}
