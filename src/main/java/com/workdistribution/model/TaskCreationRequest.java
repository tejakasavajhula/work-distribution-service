package com.workdistribution.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Teja Kasavajhula
 *
 */
@ToString
public class TaskCreationRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	@Getter 
	@Setter	
	private String taskDescription;

	@Getter 
	@Setter	
	private String requesterName;

	@Getter 
	@Setter
	private String priority;
	
	@Getter 
	@Setter	
	private List<String> skills;

}
