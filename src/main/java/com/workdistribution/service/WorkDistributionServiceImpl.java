package com.workdistribution.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.workdistribution.enums.TaskStatus;
import com.workdistribution.model.Agent;
import com.workdistribution.model.AgentResponse;
import com.workdistribution.model.Task;
import com.workdistribution.model.TaskCreationRequest;
import com.workdistribution.repository.AgentRepository;
import com.workdistribution.repository.TaskRepository;
import com.workdistribution.utils.ErrorConstants;

/**
 * @author Teja Kasavajhula
 *
 */
@Service
public class WorkDistributionServiceImpl implements WorkDistributionService {

	@Override
	public ResponseEntity<?> createTask(TaskCreationRequest request) {
		Task task = AgentRepository.assignNextAvailableAgent(request);

		if(task == null)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorConstants.NO_AVAILABLE_AGENT);
			
		else
			TaskRepository.addActiveTask(task);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(task);

	}

	@Override
	public ResponseEntity<?> updateTask(String taskId) {
		Task task = TaskRepository.getActiveTasks().get(taskId);

		if (task == null)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorConstants.TASKID_NOT_FOUND);

		else {
			TaskRepository.removeActiveTask(task);
			task.setStatus(TaskStatus.COMPLETED.getValue());
			TaskRepository.addCompletedTask(task);
			Agent assignedAgent = AgentRepository.getAssignedAgent(task.getAssignedAgentId());
			AgentRepository.updateAgentAvailability(assignedAgent);
		}
		return ResponseEntity.status(HttpStatus.OK).body(task);

	}

	@Override
	public ResponseEntity<?> getActiveAgents() {

		List<AgentResponse> agents = AgentRepository.getAllAssignedAgents();
		
		if(agents==null || agents.isEmpty())
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorConstants.NO_ACTIVE_TASKS);

		return ResponseEntity.status(HttpStatus.OK).body(agents);
	}

}
