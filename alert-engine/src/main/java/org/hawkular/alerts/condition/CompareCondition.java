package org.hawkular.alerts.condition;

/**
 * For conditions like, "X > 80% of Y" or "FreeSpace < 20% of TotalSpace"
 */
public class CompareCondition extends Condition {

	public enum Operator {
		LT, GT, LTE, GTE
	};

	private String data1Id;
	private Operator operator;
	private String data2Id;
	private Double data2Multiplier;

	public CompareCondition(String triggerId, int conditionSetSize,
			int conditionSetIndex, String data1Id, Operator operator,
			Double data2Multiplier, String data2Id) {

		super(triggerId, conditionSetSize, conditionSetIndex);

		this.data1Id = data1Id;
		this.operator = operator;
		this.data2Id = data2Id;
		this.data2Multiplier = data2Multiplier;
	}

	public String getData1Id() {
		return data1Id;
	}

	public void setData1Id(String data1Id) {
		this.data1Id = data1Id;
	}

	public String getData2Id() {
		return data2Id;
	}

	public void setData2Id(String data2Id) {
		this.data2Id = data2Id;
	}

	public Double getData2Multiplier() {
		return data2Multiplier;
	}

	public void setData2Multiplier(Double data2Multiplier) {
		this.data2Multiplier = data2Multiplier;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String getLog(double value1, double value2) {
		Double val = getData2Multiplier() * value2;
		return getTriggerId() + " : " + value1 + " " + getOperator().name()
				+ " " + val + " (" + getData2Multiplier() + "*" + value2
				+ ")";
	}

    public boolean match(double data1Value, double data2Value) {
		double threshold = (data2Multiplier * data2Value);
		switch (operator) {
		case LT:
			return data1Value < threshold;
		case GT:
			return data1Value > threshold;
		case LTE:
			return data1Value <= threshold;
		case GTE:
			return data1Value >= threshold;
		default:
			System.out.println("UNKNOWN OPERATOR: " + operator.name());
			return false;
		}
	}

}
