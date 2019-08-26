package com.workdistribution.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Teja Kasavajhula
 *
 */
public enum AgentStatus {

	BUSY("busy"),
	AVAILABLE("available");
		
	@Getter
	@Setter
	private String value;

    private AgentStatus(String value) {
		this.value = value;
	}

}
