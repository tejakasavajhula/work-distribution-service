package com.workdistribution.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.workdistribution.enums.SkillValues;
import com.workdistribution.enums.TaskPriorityValues;
import com.workdistribution.enums.TaskStatus;
import com.workdistribution.exception.ApiBusinessException;
import com.workdistribution.model.Task;
import com.workdistribution.model.TaskCreationRequest;
import com.workdistribution.service.WorkDistributionService;

@RunWith(SpringRunner.class)
public class WorkDistributionControllerTest {

	@Mock
	private WorkDistributionService service;

	@InjectMocks
	WorkDistributionController controller;

	@Test(expected = ApiBusinessException.class)
	public void createTaskTestWithoutRequesterName() {
		TaskCreationRequest request = new TaskCreationRequest();
		request.setTaskDescription("taskDescription");
		request.setPriority(TaskPriorityValues.LOW.getValue());
		request.setSkills(Collections.singletonList(SkillValues.SKILL1.getValue()));

		controller.createTask(request);
	}

	@Test(expected = ApiBusinessException.class)
	public void createTaskTestWithInvalidPriority() {
		TaskCreationRequest request = new TaskCreationRequest();
		request.setRequesterName("requesterName");
		request.setPriority("very high");
		request.setTaskDescription("taskDescription");
		request.setSkills(Collections.singletonList(SkillValues.SKILL1.getValue()));

		controller.createTask(request);
		
	}

	@Test(expected = ApiBusinessException.class)
	public void createTaskTestWithoutPriority() {
		TaskCreationRequest request = new TaskCreationRequest();
		request.setRequesterName("requesterName");
		request.setTaskDescription("taskDescription");
		request.setSkills(Collections.singletonList(SkillValues.SKILL1.getValue()));

		controller.createTask(request);
	}

	@Test(expected = ApiBusinessException.class)
	public void createTaskTestWithInvalidSkills() {
		TaskCreationRequest request = new TaskCreationRequest();
		request.setRequesterName("requesterName");
		request.setTaskDescription("taskDescription");
		request.setPriority(TaskPriorityValues.LOW.getValue());
		request.setSkills(Collections.singletonList("xyz"));

		controller.createTask(request);
	}

	@Test(expected = ApiBusinessException.class)
	public void createTaskTestWithoutSkills() {
		TaskCreationRequest request = new TaskCreationRequest();
		request.setRequesterName("requesterName");
		request.setTaskDescription("taskDescription");
		request.setPriority(TaskPriorityValues.LOW.getValue());

		controller.createTask(request);
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
