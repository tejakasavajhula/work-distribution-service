package com.workdistribution.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Teja Kasavajhula
 *
 */
@ToString
@AllArgsConstructor
public class Agent implements Serializable{

	private static final long serialVersionUID = 1L;

	@Getter 
	@Setter	
	private String agentId;

	@Getter 
	@Setter	
	private List<String> skills;

	@Getter 
	@Setter	
	private String status;

	@Getter 
	@Setter	
	private Date latestActivityTimestamp;

}
