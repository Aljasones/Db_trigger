package service;

import model.Status;
import model.Task;
import repository.Repository;

import java.util.List;

public class TasksService {
    private Repository repository;

    public TasksService() {
        try {
            repository = new Repository();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void createTask(String phrase){
        Task task = new Task();
        task.setPhrase(phrase);
        task.setStatus(Status.Waiting);
        repository.createTask(task);
    }

    public void updateTask (Task task) {
        repository.updateTask(task);
    }

    public List<Task> getAllTasks() {
      return repository.getAllTasks();
    }

}
