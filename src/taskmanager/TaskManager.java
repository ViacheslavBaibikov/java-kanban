package taskmanager;

import model.*;

import java.util.*;

public class TaskManager {

    private int nextId = 1;

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();

    private int generateId() {
        return nextId++;
    }

    // получаем список всех задач
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }


    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void createTask(Task task) {
        int id = generateId();
        task.setId(id);
        tasks.put(id, task);
    }

    public void createEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epics.put(id, epic);
    }

    public void createSubtask(Subtask subtask) {
        int id = generateId();
        subtask.setId(id);
        subtasks.put(id, subtask);

        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtask(subtask);
            updateEpicStatus(epic);
        }
    }

    // обновление задач, подзадач, эпиков
    public void updateTask(Task task) {
        if (task == null || !tasks.containsKey(task.getId())) {
            throw new NoSuchElementException("Задача с ID " + task.getId() + " не найдена");
        }
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
    }

    //обновление подзадач - исправлено: теперь удаляется ссылка на старый объект
    //подзадачи и добавляется новая
    public void updateSubtask(Subtask subtask) {
        if (subtask == null || !subtasks.containsKey(subtask.getId())) {
            throw new NoSuchElementException("Подзадача с ID " + (subtask != null ? subtask.getId() : "null") + " не найдена");
        }

        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            List<Subtask> epicSubtasks = epic.getSubtasks();
            for (int i = 0; i < epicSubtasks.size(); i++) {
                if (epicSubtasks.get(i).getId() == subtask.getId()) {
                    epicSubtasks.remove(i);
                    break;
                }
            }

            epic.getSubtasks().add(subtask);
            updateEpicStatus(epic);
        }
    }

    // удаление
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Subtask subtask : epic.getSubtasks()) {
                subtasks.remove(subtask.getId());
            }
        }
    }

    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(subtask);
                updateEpicStatus(epic);
            }
        }
    }

    // удаляем все
    public void deleteAll() {
        tasks.clear();
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtasks().clear();
            //updateEpicStatus(epic);
        }
        epics.clear();
    }


    // получаем копию списка подзадач эпика  - исправлено - теперь возвращает копию а не сам список
    public List<Subtask> getSubtasksOfEpic(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            return new ArrayList<>(epic.getSubtasks());
        }
        return new ArrayList<>();
    }


    // изменение приоритета
    public void updateTaskPriority(int taskId, TaskPriority priority) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.setPriority(priority);
        } else {
            System.out.println("Нет задач или подзадач для обновления приоритета.");
        }
    }

    //Обновление статуса эпика
    private void updateEpicStatus(Epic epic) {
        List<Subtask> subtaskList = epic.getSubtasks();
        if (subtaskList.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Subtask subtask : subtaskList) {
            if (subtask.getStatus() != TaskStatus.NEW) {
                allNew = false;
            }
            if (subtask.getStatus() != TaskStatus.DONE) {
                allDone = false;
            }
        }

        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
