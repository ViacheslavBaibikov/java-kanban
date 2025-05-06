package model;

import java.util.*;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected TaskStatus status;
    protected TaskPriority priority;
    protected TaskType type;

    public Task(String name, String description, TaskStatus status, TaskPriority priority) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.type = TaskType.TASK;
    }


    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id == 0) {
            this.id = id;
        } else {
            throw new IllegalStateException("ID уже присвоен");
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public String toString() {
        return type + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public String getDescription() {
        return description;
    }

    public TaskPriority getPriority() {
        return priority;
    }

}

