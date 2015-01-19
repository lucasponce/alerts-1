package org.hawkular.alerts.condition;

public class StringCondition extends Condition {

	public enum Operator {
		EQUAL, NOT_EQUAL, STARTS_WITH, ENDS_WITH, CONTAINS, MATCH
	};

	private String dataId;
	private Operator operator;
	private String pattern;
	private boolean ignoreCase;

	public StringCondition(String triggerId, String dataId,
			int conditionSetSize, int conditionSetIndex, Operator operator,
			String pattern, boolean ignoreCase) {

		super(triggerId, conditionSetSize, conditionSetIndex);

		this.dataId = dataId;
		this.operator = operator;
		this.pattern = pattern;
		this.ignoreCase = ignoreCase;
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

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public String getLog(String value) {
		return getTriggerId() + " : " + value + " " + getOperator().name()
				+ " " + getPattern() + " " + "ignoreCase=" + isIgnoreCase();
	}

	static public boolean stringMatch(Operator operator, String pattern,
			String value, boolean ignoreCase) {

		if (ignoreCase && operator != Operator.MATCH) {
			pattern = pattern.toLowerCase();
			value = value.toLowerCase();
		}
		switch (operator) {
		case EQUAL:
			return value.equals(pattern);
		case NOT_EQUAL:
			return !value.equals(pattern);
		case ENDS_WITH:
			return value.endsWith(pattern);
		case STARTS_WITH:
			return value.startsWith(pattern);
		case CONTAINS:
			return value.contains(pattern);
		case MATCH:
			return value.matches(pattern);
		default:
			System.out.println("UNKNOWN OPERATOR: " + operator.name());
			return false;
		}
	}

}
