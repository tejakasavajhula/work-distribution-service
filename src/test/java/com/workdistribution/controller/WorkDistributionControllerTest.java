package com.workdistribution.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.Collections;

import com.workdistribution.enums.SkillValues;
import com.workdistribution.enums.TaskPriorityValues;
import com.workdistribution.enums.TaskStatus;
import com.workdistribution.model.Task;
import com.workdistribution.model.TaskCreationRequest;
import com.workdistribution.service.WorkDistributionService;
import com.workdistribution.utils.ErrorConstants;

@RunWith(SpringRunner.class)
public class WorkDistributionControllerTest {

	@Mock
	private WorkDistributionService service;

	@InjectMocks
	WorkDistributionController controller;

	@Test
	public void createTaskTestWithoutRequesterName() {
		TaskCreationRequest request = new TaskCreationRequest();
		request.setTaskDescription("taskDescription");
		request.setPriority(TaskPriorityValues.LOW.getValue());
		request.setSkills(Collections.singletonList(SkillValues.SKILL1.getValue()));

		ResponseEntity response = controller.createTask(request);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(ErrorConstants.MISSING_REQUESTER, response.getBody());
	}

	@Test
	public void createTaskTestWithInvalidPriority() {
		TaskCreationRequest request = new TaskCreationRequest();
		request.setRequesterName("requesterName");
		request.setPriority("very high");
		request.setTaskDescription("taskDescription");
		request.setSkills(Collections.singletonList(SkillValues.SKILL1.getValue()));

		ResponseEntity response = controller.createTask(request);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(ErrorConstants.INVALID_PRIORITY_VALUE, response.getBody());
	}

	@Test
	public void createTaskTestWithoutPriority() {
		TaskCreationRequest request = new TaskCreationRequest();
		request.setRequesterName("requesterName");
		request.setTaskDescription("taskDescription");
		request.setSkills(Collections.singletonList(SkillValues.SKILL1.getValue()));

		ResponseEntity response = controller.createTask(request);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(ErrorConstants.INVALID_PRIORITY_VALUE, response.getBody());
	}

	@Test
	public void createTaskTestWithInvalidSkills() {
		TaskCreationRequest request = new TaskCreationRequest();
		request.setRequesterName("requesterName");
		request.setTaskDescription("taskDescription");
		request.setPriority(TaskPriorityValues.LOW.getValue());
		request.setSkills(Collections.singletonList("xyz"));

		ResponseEntity response = controller.createTask(request);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(ErrorConstants.INVALID_SKILL_VALUE, response.getBody());
	}

	@Test
	public void createTaskTestWithoutSkills1() {
		TaskCreationRequest request = new TaskCreationRequest();
		request.setRequesterName("requesterName");
		request.setTaskDescription("taskDescription");
		request.setPriority(TaskPriorityValues.LOW.getValue());

		ResponseEntity response = controller.createTask(request);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(ErrorConstants.INVALID_SKILL_VALUE, response.getBody());
	}

	@Test
	public void createTaskTest() {
		TaskCreationRequest request = new TaskCreationRequest();
		request.setRequesterName("requesterName");
		request.setTaskDescription("taskDescription");
		request.setPriority(TaskPriorityValues.LOW.getValue());
		request.setSkills(Collections.singletonList(SkillValues.SKILL1.getValue()));

		Task task = new Task("123", "111", TaskStatus.ASSIGNED.getValue(), request.getTaskDescription(), request.getRequesterName() ,
				request.getPriority(), request.getSkills());
		
		ResponseEntity value = ResponseEntity.status(HttpStatus.CREATED).body(task);
		Mockito.when(service.createTask(any(TaskCreationRequest.class))).thenReturn(value);
		
		Task response = (Task) controller.createTask(request).getBody();
		
		assertEquals(request.getRequesterName(), response.getRequesterName());
		assertEquals(request.getPriority(), response.getPriority());
		assertEquals(request.getTaskDescription(), response.getTaskDescription());
		assertEquals(request.getSkills(), response.getSkills());
		assertEquals(request.getSkills(), response.getSkills());
		assertEquals("123",response.getTaskId());
		assertEquals("111",response.getAssignedAgentId());
	}
}
