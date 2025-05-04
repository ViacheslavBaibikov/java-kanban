package manager;

import model.*;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private int nextId = 1;

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();

    private final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager defaultHistory) {
        this.historyManager = defaultHistory;
    }


    private int generateId() {
        return nextId++;
    }

    @Override
    public List<Task> getAllTasks() {
        historyManager.add(new Task("Просмотр всех задач", "Просмотр списка всех задач", TaskStatus.IN_PROGRESS, TaskPriority.MEDIUM));
        return new ArrayList<>(tasks.values());
    }



    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }


    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void createTask(Task task) {
        int id = generateId();
        task.setId(id);
        tasks.put(id, task);
    }

    @Override
    public void createEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epics.put(id, epic);
    }

    @Override
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

    @Override
    public void updateTask(Task task) {
        if (task == null || !tasks.containsKey(task.getId())) {
            throw new NoSuchElementException("Задача с ID " + task.getId() + " не найдена");
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask == null || !subtasks.containsKey(subtask.getId())) {
            throw new NoSuchElementException("Подзадача с ID " + (subtask != null ? subtask.getId() : "null") + " не найдена");
        }

        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            List<Subtask> epicSubtasks = epic.getSubtasks();
            for (int i = 0; i < epicSubtasks.size(); i++) {
                if (Objects.equals(epicSubtasks.get(i).getId(),subtask.getId())) {
                    epicSubtasks.remove(i);
                    break;
                }
            }

            epic.getSubtasks().add(subtask);
            updateEpicStatus(epic);
        }
    }

    @Override
    public void updateTaskPriority(int taskId, TaskPriority priority) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.setPriority(priority);
        } else {
            System.out.println("Нет задач или подзадач для обновления приоритета.");
        }
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Subtask subtask : epic.getSubtasks()) {
                subtasks.remove(subtask.getId());
            }
        }
    }

    @Override
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

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteSubtasksByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            System.out.println("Эпик с ID " + epicId + " не найден.");
            return;
        }

        for (Subtask subtask : epic.getSubtasks()) {
            subtasks.remove(subtask.getId());
        }

        for (Subtask subtask : epic.getSubtasks()) {
            epic.removeSubtask(subtask);
        }

        epic.getSubtasks().clear();
        updateEpicStatus(epic);
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAll() {
        tasks.clear();
        subtasks.clear();
        epics.clear();
    }

    @Override
    public List<Subtask> getSubtasksOfEpic(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            return new ArrayList<>(epic.getSubtasks());
        }
        return new ArrayList<>();
    }



        @Override
        public List<Task> getHistory() {
            return historyManager.getHistory();
    }


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
