/**
 * 
 */
package com.workdistribution.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author vkasavajhula
 *
 */
public enum TaskPriorityValues {

	LOW("low"),
	HIGH("high");

	@Getter
	@Setter
	private String value;

    private TaskPriorityValues(String value) {
		this.value = value;
	}
}
