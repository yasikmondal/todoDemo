package com.demo.TodoDemo;

import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoDemoController {

	TodoService todoService = new TodoService();

	@RequestMapping(value = "/tasks", method = RequestMethod.GET, headers = "Accept=application/json")

	public List<Task> getAllTasks() {
		System.out.println("Entering to Getting all Tasks Url");
		List<Task> tasks = todoService.getAllTasks();
		System.out.println(tasks);
		System.out.println("Exiting to Getting Tasks Url");

		return tasks;

	}

	@RequestMapping(value = "/tasks/insert/{taskName}/{taskDesc}/{taskPriority}/{taskStatus}", method = RequestMethod.POST, headers = "Accept=application/json")
	public List<Task> addTask(@PathVariable String taskName, @PathVariable String taskDesc,
			@PathVariable String taskPriority, @PathVariable String taskStatus) throws ParseException {
		System.out.println("Entering to addTask url");
		Task task = new Task();
		task.setTaskName(taskName);
		task.setTaskDescription(taskDesc);
		task.setTaskPriority(taskPriority);
		task.setTaskStatus(taskStatus);
		todoService.addTask(task);
		System.out.println("Exiting to addTask url");
		return todoService.getAllTasks();

	}

	@RequestMapping(value = "/tasks/delete/{taskIds}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	
	public void deleteTask(@PathVariable int taskIds) {
		// todoRepository.deleteById(taskIds);
		todoService.deteteTask(taskIds);

	}

	@RequestMapping(value = "/tasks/updateTasks/{taskIds}/{taskStatus}", method = RequestMethod.PUT, headers = "Accept=application/json")


	public void updateTask(@PathVariable int taskIds, @PathVariable String taskStatus) {
		System.out.println("Entering to update Tasks Url");

		if ("ACTIVE".equalsIgnoreCase(taskStatus)) {
			taskStatus = "COMPLETED";
		} else {
			taskStatus = "ACTIVE";
		}

		try {
			todoService.changeTaskStatus(taskIds, taskStatus);
		} catch (ParseException e) {

			e.printStackTrace();
		} finally {
			System.out.println("Exiting from update Tasks Url");

		}

	}

}
