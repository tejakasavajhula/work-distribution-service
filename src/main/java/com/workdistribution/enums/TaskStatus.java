package com.workdistribution.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Teja Kasavajhula
 *
 */
public enum TaskStatus {

	NEW("new"),
	ASSIGNED("assigned"),
	COMPLETED("completed");
		
	@Getter
	@Setter
	private String value;

    private TaskStatus(String value) {
		this.value = value;
	}

}
