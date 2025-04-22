import taskmanager.TaskManager;
import model.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);

        // -------- тесты ------------

        // Создание задач
        Task task1 = new Task("Задача 1", "Описание", TaskStatus.IN_PROGRESS, TaskPriority.HIGH);
        taskManager.createTask(task1);
        Task task2 = new Task("Задача 2", "Описание", TaskStatus.NEW, TaskPriority.MEDIUM);
        taskManager.createTask(task2);

        // Создание эпиков с подзадачами
        Epic epic1 = new Epic("Эпик 1", "Описание", TaskPriority.MEDIUM);
        taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание", TaskStatus.NEW, TaskPriority.HIGH, epic1.getId());
        taskManager.createSubtask(subtask1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание", TaskStatus.IN_PROGRESS, TaskPriority.LOW, epic1.getId());
        taskManager.createSubtask(subtask2);

        Epic epic2 = new Epic("Эпик 2", "Описание", TaskPriority.LOW);
        taskManager.createEpic(epic2);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание", TaskStatus.DONE, TaskPriority.MEDIUM, epic2.getId());
        taskManager.createSubtask(subtask3);

        // Печать всего
        System.out.println("Все задачи:");
        printAllTasks(taskManager);

        System.out.println("Все эпики:");
        taskManager.getAllEpics().forEach(System.out::println);

        System.out.println("Все подзадачи:");
        taskManager.getAllSubtasks().forEach(System.out::println);

        // Изменение статусов
        task1.setStatus(TaskStatus.DONE);
        taskManager.updateTask(task1);
        subtask1.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(subtask1);

        // Печать задач и эпиков после изменения статусов
        System.out.println("\nЗадачи после изменения статусов:");
        printAllTasks(taskManager);

        System.out.println("\nЭпики после изменения статусов:");
        taskManager.getAllEpics().forEach(System.out::println);

        // Удаление задачи и эпика
        System.out.println("\nУдаление задачи с ID " + task1.getId());
        taskManager.deleteTask(task1.getId());

        System.out.println("Удаление эпика с ID " + epic2.getId());
        taskManager.deleteEpic(epic2.getId());

        // Печать задач и эпиков после удаления
        System.out.println("\nВсе задачи после удаления одной из здач:");
        printAllTasks(taskManager);

        System.out.println("\nВсе эпики после удаления одного из эпиков:");
        taskManager.getAllEpics().forEach(System.out::println);

        while (true) {
            printMenu();
            int choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    createTask(taskManager, scanner);
                    break;
                case 2:
                    createEpic(taskManager, scanner);
                    break;
                case 3:
                    createSubtask(taskManager, scanner);
                    break;
                case 4:
                    updateTask(taskManager, scanner);
                    break;
                case 5:
                    updateEpic(taskManager, scanner);
                    break;
                case 6:
                    updateSubtask(taskManager, scanner);
                    break;
                case 7:
                    deleteTask(taskManager, scanner);
                    break;
                case 8:
                    deleteEpic(taskManager, scanner);
                    break;
                case 9:
                    deleteSubtask(taskManager, scanner);
                    break;
                case 10:
                    deleteAllTasks(taskManager);
                    break;
                case 11:
                    deleteSubtasksByEpic(taskManager, scanner);
                    break;
                case 12:
                    deleteAllEpics(taskManager);
                    break;
                case 13:
                    printAllTasks(taskManager);
                    break;
                case 14:
                    printSubtasksOfEpic(taskManager, scanner);
                    break;
                case 15:
                    updateTaskOrSubtaskPriority(taskManager, scanner);
                    break;
                case 16:
                    deleteAll(taskManager);
                    break;
                case 17:
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    // Показываем меню
    private static void printMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Создать задачу");
        System.out.println("2. Создать эпик");
        System.out.println("3. Создать подзадачу");
        System.out.println("4. Обновить задачу");
        System.out.println("5. Обновить эпик");
        System.out.println("6. Обновить подзадачу");
        System.out.println("7. Удалить задачу");
        System.out.println("8. Удалить эпик");
        System.out.println("9. Удалить подзадачу");
        System.out.println("10. Удалить все задачи");
        System.out.println("11. Удалить все подзадачи конкретного эпика");
        System.out.println("12. Удалить все эпики");
        System.out.println("13. Вывести все задачи");
        System.out.println("14. Вывести подзадачи эпика");
        System.out.println("15. Обновить приоритет задачи или подзадачи");
        System.out.println("16. Удалить все задачи, эпики и подзадачи");
        System.out.println("17. Выход");
        System.out.print("Выберите опцию: ");
    }

    // проверка на ввод числа
    private static int getIntInput(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод! Пожалуйста, введите целое число.");
            }
        }
    }

    // проверка на ввод строки
    private static String getStringInput(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) {
                return input;
            } else {
                System.out.println("Ввод не может быть пустым. Пожалуйста, введите значение.");
            }
        }
    }

    // Ввод приоритета с проверкой
    private static TaskPriority getTaskPriorityInput(Scanner scanner) {
        while (true) {
            System.out.print("Введите приоритет (LOW, MEDIUM, HIGH): ");
            String priority = scanner.nextLine();
            try {
                return TaskPriority.valueOf(priority.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Неверный приоритет! Введите LOW, MEDIUM или HIGH.");
            }
        }
    }

    //Обновление приоритета
    private static void updateTaskOrSubtaskPriority(TaskManager taskManager, Scanner scanner) {
        System.out.print("Введите ID задачи или подзадачи для обновления приоритета: ");
        int id = getIntInput(scanner);
        scanner.nextLine();

        TaskPriority priority = getTaskPriorityInput(scanner);

        if (taskManager.getTaskById(id) != null) {
            taskManager.updateTaskPriority(id, priority);
            System.out.println("Приоритет задачи обновлён.");
        } else if (taskManager.getSubtaskById(id) != null) {
            taskManager.updateTaskPriority(id, priority);
            System.out.println("Приоритет подзадачи обновлён.");
        } else {
            System.out.println("Задача или подзадача с таким ID не найдена.");
        }
    }

    // Вывод подзадач эпика
    private static void printSubtasksOfEpic(TaskManager taskManager, Scanner scanner) {
        System.out.print("Введите ID эпика для вывода подзадач: ");
        int epicId = getIntInput(scanner);
        List<Subtask> subtasks = taskManager.getSubtasksOfEpic(epicId);
        if (subtasks.isEmpty()) {
            System.out.println("В эпике нет подзадач или нет такого эпика");
        } else {
            System.out.println("Подзадачи эпика: " + subtasks);
        }

    }

    // Вывод всех задач
    private static void printAllTasks(TaskManager taskManager) {
        List<Task> tasks = taskManager.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("Задач нет");
        } else {
            System.out.println("Все задачи: " + tasks);
        }

    }

    // Создание задачи
    private static void createTask(TaskManager taskManager, Scanner scanner) {
        System.out.print("Введите название задачи: ");
        String name = getStringInput(scanner);

        System.out.print("Введите описание задачи: ");
        String description = getStringInput(scanner);

        TaskPriority priority = getTaskPriorityInput(scanner);

        Task task = new Task(name, description, TaskStatus.IN_PROGRESS, priority);
        taskManager.createTask(task);
        System.out.println("Задача создана с ID: " + task.getId());
    }

    // Создание эпика
    private static void createEpic(TaskManager taskManager, Scanner scanner) {
        System.out.print("Введите название эпика: ");
        String name = getStringInput(scanner);

        System.out.print("Введите описание эпика: ");
        String description = getStringInput(scanner);

        Epic epic = new Epic(name, description, TaskPriority.MEDIUM);
        taskManager.createEpic(epic);
        System.out.println("Эпик создан с ID: " + epic.getId());
    }

    // Создание подзадачи
    private static void createSubtask(TaskManager taskManager, Scanner scanner) {
        System.out.print("Введите ID эпика, к которому будет привязана подзадача: ");
        int epicId = getIntInput(scanner);
        Epic epic = taskManager.getEpicById(epicId);
        if (epic == null || epicId == 0) {
            System.out.println("Эпик с таким ID не найден.");
            return;
        }
        System.out.print("Введите название подзадачи: ");
        String name = getStringInput(scanner);

        System.out.print("Введите описание подзадачи: ");
        String description = getStringInput(scanner);

        TaskPriority priority = getTaskPriorityInput(scanner);

        Subtask subtask = new Subtask(name, description, TaskStatus.IN_PROGRESS, priority, epicId);
        taskManager.createSubtask(subtask);
        System.out.println("Подзадача создана с ID: " + subtask.getId());
    }

    // Обновить задачу
    private static void updateTask(TaskManager taskManager, Scanner scanner) {
        if (taskManager.getAllTasks().isEmpty()) {
            System.out.println("Нет задач для обновления.");
            return;
        }

        System.out.print("Введите ID задачи для обновления: ");
        int id = getIntInput(scanner);
        Task task = taskManager.getTaskById(id);
        if (task == null || id == 0) {
            System.out.println("Задача с таким ID не найдена.");
            return;
        }

        System.out.print("Введите новое название задачи: ");
        String name = getStringInput(scanner);

        System.out.print("Введите новое описание задачи: ");
        String description = getStringInput(scanner);

        TaskPriority priority = getTaskPriorityInput(scanner);

        task.setName(name);
        task.setDescription(description);
        task.setPriority(priority);
        taskManager.updateTask(task);

        System.out.println("Задача обновлена.");
    }


    // Обновить эпик
    private static void updateEpic(TaskManager taskManager, Scanner scanner) {
        System.out.print("Введите ID эпика для обновления: ");
        int id = getIntInput(scanner);
        Epic epic = taskManager.getEpicById(id);
        if (epic == null || id == 0) {
            System.out.println("Эпик с таким ID не найден.");
            return;
        }

        System.out.print("Введите новое название эпика: ");
        String name = getStringInput(scanner);

        System.out.print("Введите новое описание эпика: ");
        String description = getStringInput(scanner);

        epic.setName(name);
        epic.setDescription(description);
        taskManager.updateEpic(epic);

        System.out.println("Эпик обновлён.");
    }

    // Обновить подзадачу
    private static void updateSubtask(TaskManager taskManager, Scanner scanner) {

        System.out.print("Введите ID подзадачи для обновления: ");
        int id = getIntInput(scanner);
        Subtask subtask = taskManager.getSubtaskById(id);
        if (subtask == null) {
            System.out.println("Подзадача с таким ID не найдена.");
            return;
        }

        System.out.print("Введите новое название подзадачи: ");
        String name = getStringInput(scanner);

        System.out.print("Введите новое описание подзадачи: ");
        String description = getStringInput(scanner);

        TaskPriority priority = getTaskPriorityInput(scanner);

        subtask.setName(name);
        subtask.setDescription(description);
        subtask.setPriority(priority);
        taskManager.updateSubtask(subtask);

        System.out.println("Подзадача обновлена.");
    }


    // Удалить задачу
    private static void deleteTask(TaskManager taskManager, Scanner scanner) {
        System.out.print("Введите ID задачи для удаления: ");
        int id = getIntInput(scanner);
        Task task = taskManager.getTaskById(id);
        if (task == null || id == 0) {
            System.out.println("Задача с таким ID не найдена.");
            return;
        }
        taskManager.deleteTask(id);
        System.out.println("Задача с ID " + id + " удалена.");
    }

    // Удалить эпик
    private static void deleteEpic(TaskManager taskManager, Scanner scanner) {
        System.out.print("Введите ID эпика для удаления: ");
        int id = getIntInput(scanner);
        Epic epic = taskManager.getEpicById(id);
        if (epic == null) {
            System.out.println("Эпик с таким ID не найден.");
            return;
        }
        taskManager.deleteEpic(id);
        System.out.println("Эпик с ID " + id + " удалён.");
    }


    // Удалить подзадачу
    private static void deleteSubtask(TaskManager taskManager, Scanner scanner) {
        System.out.print("Введите ID подзадачи для удаления: ");
        int id = getIntInput(scanner);
        Subtask subtask = taskManager.getSubtaskById(id);
        if (subtask == null) {
            System.out.println("Подзадача с таким ID не найдена.");
            return;
        }
        taskManager.deleteSubtask(id);
        System.out.println("Подзадача с ID " + id + " удалена.");

    }

    //удалить все задачи эпики и подзадачи
    private static void deleteAll(TaskManager taskManager) {
        taskManager.deleteAll();
        System.out.println("Все задачи, эпики и подзадачи удалены");
    }
    //удаляем все задачи
    private static void deleteAllTasks(TaskManager taskManager) {
        taskManager.deleteAllTasks();
        System.out.println("Все задачи удалены");
    }

    //удаляем все подзадачи конкретного эпика
    private static void deleteSubtasksByEpic(TaskManager taskManager, Scanner scanner) {
        System.out.print("Введите ID эпика для удаления: ");
        int id = getIntInput(scanner);
        Epic epic = taskManager.getEpicById(id);
        if (epic == null) {
            System.out.println("Эпик с таким ID не найден.");
            return;
        }
        taskManager.deleteSubtasksByEpicId(id);
        System.out.println("Подзадачи эпика с ID " + id + " удалены.");
    }

    //удаляем все эпики
    private static void deleteAllEpics(TaskManager taskManager) {
        taskManager.deleteAllEpics();
        System.out.println("Все эпики удалены");
    }
}