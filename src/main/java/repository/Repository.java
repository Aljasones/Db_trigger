package repository;

import model.Status;
import model.Task;
import model.TaskLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    public Repository() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection connection = DriverManager.getConnection("db.sqlite");
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY, phrase TEXT NOT NULL, status TEXT NOT NULL)");
            statement.execute("CREATE TABLE IF NOT EXISTS tasks_log(id_t INTEGER NOT NULL, date_t TEXT NOT NULL)");
            statement.execute("CREATE TRIGGER after_insert AFTER UPDATE ON tasks BEGIN INSERT INTO tasks_log(id_t, date_t) VALUES (NEW.id, datetime('now')); END;");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTask(Task task) {
        try (Connection connection = DriverManager.getConnection("db.sqlite");
             Statement statement = connection.createStatement()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO tasks (phrase, status) VALUES (?,?)");

            preparedStatement.setString(1, task.getPhrase());
            preparedStatement.setString(2, task.getStatus().toString());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(Task task) {
        try (Connection connection = DriverManager.getConnection("db.sqlite");
             Statement statement = connection.createStatement()) {

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tasks set status=? where id=?");
            preparedStatement.setString(1, task.getStatus().toString());
            preparedStatement.setInt(2, task.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAllTasks() {
        try (Connection connection = DriverManager.getConnection("db.sqlite")) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT *  FROM tasks");

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Task> tasks = new ArrayList<>();
            while (resultSet.next()) {
                Task task = new Task();
                task.setStatus(Status.valueOf(resultSet.getString("status")));
                task.setPhrase(resultSet.getString("phrase"));
                task.setId(resultSet.getInt("id"));
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<TaskLog> getTasksLog() {
        try (Connection connection = DriverManager.getConnection("db.sqlite")) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT *  FROM tasks_log");

            ResultSet resultSet = preparedStatement.executeQuery();
            List<TaskLog> taskLogs = new ArrayList<>();
            while (resultSet.next()) {
                TaskLog taskLog = new TaskLog();
                taskLog.setDate_t(resultSet.getString("date_t"));
                taskLog.setId_t(resultSet.getInt("id_t"));
                taskLogs.add(taskLog);
            }
            return taskLogs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


