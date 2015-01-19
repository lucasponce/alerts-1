package org.hawkular.alerts.condition;

import org.hawkular.alerts.data.Availability.AvailabilityType;

public class AvailabilityCondition extends Condition {

	public enum Operator {
		DOWN, NOT_UP, UP
	};

	private String dataId;
	private Operator operator;

	public AvailabilityCondition(String triggerId, String dataId,
			int conditionSetSize, int conditionSetIndex, Operator operator) {

		super(triggerId, conditionSetSize, conditionSetIndex);

		this.dataId = dataId;
		this.operator = operator;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String getLog(AvailabilityType value) {
		return getTriggerId() + " : " + value + " " + getOperator().name();
	}

	static public boolean availabilityMatch(Operator operator,
			AvailabilityType value) {
		switch (operator) {
		case DOWN:
			return value == AvailabilityType.DOWN;
		case UP:
			return value == AvailabilityType.UP;
		case NOT_UP:
			return value != AvailabilityType.UP;
		default:
			System.out.println("UNKNOWN OPERATOR: " + operator.name());
			return false;
		}
	}

}
