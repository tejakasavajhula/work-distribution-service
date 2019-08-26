package com.workdistribution.repository;

import java.util.HashMap;
import java.util.Map;

import com.workdistribution.model.Task;

import lombok.Getter;

/**
 * @author Teja Kasavajhula
 *
 */
public class TaskRepository {

	@Getter
	private static Map<String, Task> activeTasks = new HashMap<>();
	
	private static Map<String, Task> completedTasks = new HashMap<>();

	public static void getActiveTask(String taskId) {
		activeTasks.get(taskId);
	}

	public static void addActiveTask(Task task) {
		activeTasks.put(task.getTaskId(), task);
	}

	public static void removeActiveTask(Task task) {
		activeTasks.remove(task.getTaskId(), task);
	}

	public static void addCompletedTask(Task task) {
		completedTasks.put(task.getTaskId(), task);
	}

}
