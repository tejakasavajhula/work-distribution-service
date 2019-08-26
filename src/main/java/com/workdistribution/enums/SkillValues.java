package com.workdistribution.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Teja Kasavajhula
 *
 */
public enum SkillValues {

	SKILL1("skill1"),
	SKILL2("skill2"),
	SKILL3("skill3");
		
	@Getter
	@Setter
	private String value;

    private SkillValues(String value) {
		this.value = value;
	}

}
