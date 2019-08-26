package com.workdistribution.service;

import org.springframework.http.ResponseEntity;

import com.workdistribution.model.TaskCreationRequest;

/**
 * @author Teja Kasavajhula
 *
 */
public interface WorkDistributionService {

	public ResponseEntity<?> createTask(TaskCreationRequest request);
	
	public ResponseEntity<?> updateTask(String taskId);

	public ResponseEntity<?> getActiveAgents();

}
