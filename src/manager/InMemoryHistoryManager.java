package manager;

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> history = new LinkedList<>();
    private final static int HISTORY_LIMIT = 10;

    @Override
    public void add(Task task) {
        if(history.size() >= HISTORY_LIMIT) {
            history.removeFirst();
            history.addLast(task); //исправил, спасибо за замечание, прошу прощения за невнимательность
        } else {
            history.addLast(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public void remove(Task task) {
        history.remove(task);
    }
}

