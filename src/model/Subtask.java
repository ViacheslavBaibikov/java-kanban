package model;

public class Subtask extends Task {
    private final int epicId;  // ID эпика, к которому принадлежит подзадача

    public Subtask(String name, String description, TaskStatus status, TaskPriority priority, int epicId) {
        super(name, description, status, priority);
        this.type = TaskType.SUBTASK;
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

}
