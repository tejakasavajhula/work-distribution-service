package com.workdistribution.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomUtils;

import com.workdistribution.enums.AgentStatus;
import com.workdistribution.enums.SkillValues;
import com.workdistribution.enums.TaskPriorityValues;
import com.workdistribution.enums.TaskStatus;
import com.workdistribution.model.Agent;
import com.workdistribution.model.AgentResponse;
import com.workdistribution.model.Task;
import com.workdistribution.model.TaskCreationRequest;

/**
 * @author Teja Kasavajhula
 *
 */
public class AgentRepository {

	private static Map<String, Agent> availableAgents = Collections.synchronizedMap(getAgents());
	private static Map<String, Agent> assignedAgents = Collections.synchronizedMap(new HashMap<>());

	public static Task assignNextAvailableAgent(TaskCreationRequest request) {
		Task response = null;

		List<Agent> availableAgentsWithSkillset = availableAgents.values().stream()
				.filter(a -> a.getSkills().containsAll(request.getSkills())).collect(Collectors.toList());

		if (!availableAgentsWithSkillset.isEmpty()) {
			response = buildTaskResponse(request, availableAgentsWithSkillset.get(0));
			updateAgentAvailability(availableAgentsWithSkillset.get(0));
		} 
		
		else if (TaskPriorityValues.HIGH.getValue().equalsIgnoreCase(request.getPriority())) {
			Map<String, Agent> assignedAgentsWithSkillset = assignedAgents.entrySet().stream()
					.filter(a -> a.getValue().getSkills().containsAll(request.getSkills()))
					.collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

			List<Task> lowPriorityTasks = TaskRepository.getActiveTasks().values().stream()
					.filter(t -> TaskPriorityValues.LOW.getValue().equalsIgnoreCase(t.getPriority()))
					.filter(t -> assignedAgentsWithSkillset.containsKey(t.getAssignedAgentId()))
					.collect(Collectors.toList());

			if (!lowPriorityTasks.isEmpty()) {
				lowPriorityTasks.sort((x, y) -> assignedAgents.get(y.getAssignedAgentId()).getLatestActivityTimestamp()
						.compareTo(assignedAgents.get(x.getAssignedAgentId()).getLatestActivityTimestamp()));

				Task task = lowPriorityTasks.get(0);
				String agentId = task.getAssignedAgentId();

				task.setStatus(TaskStatus.NEW.getValue());
				task.setAssignedAgentId(new String());
				TaskRepository.addActiveTask(task);
				response = buildTaskResponse(request, assignedAgents.get(agentId));
				updateAgentTimestamp(assignedAgents.get(agentId));
			}
		}

		return response;
	}

	private static Task buildTaskResponse(TaskCreationRequest request, Agent agent) {
		Task response = null;

		if (agent != null) {
			Long taskId = RandomUtils.nextLong();
			response = new Task(taskId.toString(), agent.getAgentId(), TaskStatus.ASSIGNED.getValue(),
					request.getTaskDescription(), request.getRequesterName(), request.getPriority(),
					request.getSkills());
		}

		return response;
	}

	public static Agent getAssignedAgent(String agentId) {
		Agent agent = assignedAgents.get(agentId);
		return agent;
	}

	public static List<AgentResponse> getAllAssignedAgents() {
		List<AgentResponse> agents = TaskRepository.getActiveTasks()
				.values()
				.stream()
				.filter(t -> TaskStatus.ASSIGNED.getValue().equalsIgnoreCase(t.getStatus()))
				.map(t -> buildAgentResponse(t))
				.collect(Collectors.toList());

		return agents;
	}

	private static AgentResponse buildAgentResponse(Task t) {
		Agent agent = assignedAgents.get(t.getAssignedAgentId());
		AgentResponse response = null;
		if (agent != null)
			response = new AgentResponse(agent, t.getTaskId(), t.getStatus(), t.getTaskDescription(),
					t.getRequesterName(), t.getPriority(), t.getSkills());
		return response;

	}

	public static void updateAgentAvailability(Agent agent) {
		if (AgentStatus.AVAILABLE.getValue().equalsIgnoreCase(agent.getStatus())) {
			agent.setStatus(AgentStatus.BUSY.getValue());
			agent.setLatestActivityTimestamp(new Date());
			assignedAgents.put(agent.getAgentId(), agent);
			availableAgents.remove(agent.getAgentId());
		}

		else if (AgentStatus.BUSY.getValue().equalsIgnoreCase(agent.getStatus())) {
			agent.setStatus(AgentStatus.AVAILABLE.getValue());
			agent.setLatestActivityTimestamp(new Date());
			assignedAgents.remove(agent.getAgentId());
			availableAgents.put(agent.getAgentId(), agent);
		}
	}

	public static void updateAgentTimestamp(Agent agent) {
		if (AgentStatus.BUSY.getValue().equalsIgnoreCase(agent.getStatus())) {
			agent.setLatestActivityTimestamp(new Date());
			assignedAgents.put(agent.getAgentId(), agent);
		}
	}

	private static Map<String, Agent> getAgents() {
		Map<String, Agent> response = new HashMap<>();
		List<String> skillset1 = new ArrayList<>();
		skillset1.add(SkillValues.SKILL1.getValue());
		skillset1.add(SkillValues.SKILL2.getValue());
		skillset1.add(SkillValues.SKILL3.getValue());

		List<String> skillset2 = new ArrayList<>();
		skillset2.add(SkillValues.SKILL1.getValue());
		skillset2.add(SkillValues.SKILL3.getValue());

		List<String> skillset3 = new ArrayList<>();
		skillset3.add(SkillValues.SKILL1.getValue());
		skillset3.add(SkillValues.SKILL2.getValue());

		List<String> skillset4 = new ArrayList<>();
		skillset4.add(SkillValues.SKILL3.getValue());
		skillset4.add(SkillValues.SKILL2.getValue());

		for (Integer i = 0; i < 20; i++) {
			List<String> skills;
			switch (i % 4) {
			case 0:
				skills = skillset1;
				break;
			case 1:
				skills = skillset2;
				break;
			case 2:
				skills = skillset3;
				break;
			case 3:
				skills = skillset4;
				break;

			default:
				skills = null;
				break;
			}
			Agent agent = new Agent(i.toString(), skills, AgentStatus.AVAILABLE.getValue(), new Date());
			response.put(i + "", agent);
		}
		return response;
	}

}
