package test;

import manager.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagersTest {

    @Test
    public void testManagersReturnsInitializedTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "Managers.getDefault() должен проходить инициализацию через таскменеджер");
    }

    @Test
    public void testManagersReturnsInitializedHistoryManager() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        assertNotNull(historyManager, "Managers.getDefaultHistory() должен проходить инициализацию через Хистори-менеджер");
    }
}
