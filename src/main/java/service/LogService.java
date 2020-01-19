package service;

import model.TaskLog;
import repository.Repository;

import java.util.List;

public class LogService {
    private Repository repository;

    public LogService(Repository repository) {
        this.repository = repository;
    }

    public List<TaskLog> getLogs() {
        return repository.getTasksLog();
    }
}
