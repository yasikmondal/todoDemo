package com.demo.TodoDemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TodoService {

	private Connection connection;

	public TodoService() {
		connection = DBConnection.getConnection();
	}

	public void addTask(Task task) {
		try {
			System.out.println("Entering to add Tasks method");
			PreparedStatement preparedStatement = connection.prepareStatement(
					"insert into todolist(name,description,priority,status,archived) values (?, ?, ?,?,?)");
			System.out.println("Task:" + task.getTaskName());
			//preparedStatement.setInt(1, 10);
			preparedStatement.setString(1, task.getTaskName());
			preparedStatement.setString(2, task.getTaskDescription());
			preparedStatement.setString(3, task.getTaskPriority());
			preparedStatement.setString(4, task.getTaskStatus());
			preparedStatement.setBoolean(5, false);
			preparedStatement.executeUpdate();
			System.out.println("Exiting to add Tasks method");

		} catch (SQLException e) {
			System.out.println("SQLException Exception in addTask method");
			e.printStackTrace();
		}
	}


	public void changeTaskStatus(Integer taskId, String status) throws ParseException {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update todolist set status= '"+ status+ "' where id=" + taskId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Task> getAllTasks() {
		System.out.println("Entering to getAllTasks method");
		List<Task> tasks = new ArrayList<Task>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from todolist where archived=false");
			while (rs.next()) {
				Task task = new Task();
				task.setTaskId(rs.getInt("id"));
				task.setTaskName(rs.getString("name"));
				task.setTaskDescription(rs.getString("description"));
				task.setTaskPriority(rs.getString("priority"));
				task.setTaskStatus(rs.getString("status"));
				tasks.add(task);
				System.out.println("Exiting to getAllTasks method");
			}
		} catch (SQLException e) {
			System.out.println("SQLException Exception in getAllTasks method");
			e.printStackTrace();
		}

		return tasks;
	}


	public void deteteTask(Integer taskIds) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM todolist WHERE id=" + taskIds);
			
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}