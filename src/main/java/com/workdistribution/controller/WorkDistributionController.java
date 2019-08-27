package com.workdistribution.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.workdistribution.model.TaskCreationRequest;
import com.workdistribution.service.WorkDistributionService;
import com.workdistribution.utils.ErrorConstants;


/**
 * @author Teja Kasavajhula
 *
 */
@Controller
public class WorkDistributionController {

	@Autowired
    	private WorkDistributionService service;

	@PostMapping(value = "/v1/task")
    	public ResponseEntity createTask(@RequestBody(required=true) TaskCreationRequest request) {
		List<String> acceptablePriorityValues = Arrays.asList(TaskPriorityValues.values())
				.stream()
				.map(p -> p.getValue())
				.collect(Collectors.toList());

		List<String> acceptableSkillValues = Arrays.asList(SkillValues.values())
				.stream()
				.map(s -> s.getValue())
				.collect(Collectors.toList());

		if(StringUtils.isBlank(request.getRequesterName()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorConstants.MISSING_REQUESTER);

		if(StringUtils.isBlank(request.getPriority())
				|| !acceptablePriorityValues.contains(request.getPriority()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorConstants.INVALID_PRIORITY_VALUE);

		if(request.getSkills() == null || request.getSkills().isEmpty()
				|| !acceptableSkillValues.containsAll(request.getSkills()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorConstants.INVALID_SKILL_VALUE);
		
	    	return service.createTask(request);
    }

	@PutMapping(value = "/v1/task/{taskId}")
    	public ResponseEntity updateTaskAsComplete(@PathVariable("taskId") String taskId) {

		if(StringUtils.isBlank(taskId))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorConstants.MISSING_TASKID);
		
        	return service.updateTask(taskId);
    }

	@GetMapping(value="/v1/agents")
	public ResponseEntity getActiveAgents() {
        	return service.getActiveAgents();		
	}
}
