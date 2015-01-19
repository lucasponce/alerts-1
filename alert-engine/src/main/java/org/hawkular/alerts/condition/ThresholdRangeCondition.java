package org.hawkular.alerts.condition;

public class ThresholdRangeCondition extends Condition {

    public enum Operator {
        INCLUSIVE("[", "]"), EXCLUSIVE("(", ")");

        private String low, high;

        Operator(String low, String high) {
            this.low = low;
            this.high = high;
        }

        public String getLow() {
            return low;
        }

        public String getHigh() {
            return high;
        }
    };

    private String dataId;
    private Operator operatorLow;
    private Operator operatorHigh;
    private Double thresholdLow;
    private Double thresholdHigh;
    private boolean inRange;

    public ThresholdRangeCondition(String triggerId, String dataId, int conditionSetSize, int conditionSetIndex,
        Operator operatorLow, Operator operatorHigh, Double thresholdLow, Double thresholdHigh, boolean inRange) {

        super(triggerId, conditionSetSize, conditionSetIndex);

        this.dataId = dataId;
        this.operatorLow = operatorLow;
		this.operatorHigh = operatorHigh;
        this.thresholdLow = thresholdLow;
        this.thresholdHigh = thresholdHigh;
        this.inRange = inRange;
    }

	public String getDataId() {
        return dataId;
    }

	public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Operator getOperatorLow() {
        return operatorLow;
    }

    public void setOperatorLow(Operator operatorLow) {
        this.operatorLow = operatorLow;
    }

    public Operator getOperatorHigh() {
        return operatorHigh;
    }

    public void setOperatorHigh(Operator operatorHigh) {
        this.operatorHigh = operatorHigh;
    }

    public Double getThresholdLow() {
        return thresholdLow;
    }

    public void setThresholdLow(Double thresholdLow) {
        this.thresholdLow = thresholdLow;
    }

    public Double getThresholdHigh() {
        return thresholdHigh;
    }

    public void setThresholdHigh(Double thresholdHigh) {
        this.thresholdHigh = thresholdHigh;
    }

    public boolean isInRange() {
        return inRange;
    }

    public void setInRange(boolean inRange) {
        this.inRange = inRange;
    }

    public String getLog(double value) {
        String range = operatorLow.getLow() + getThresholdLow() + " , " + getThresholdHigh() + operatorHigh.getHigh();
        return getTriggerId() + " : " + value + " " + range;
    }

	static public boolean thresholdRangeMatch(Operator operatorLow,
			Operator operatorHigh, double thresholdLow, double thresholdHigh,
        boolean inRange, double value) {

		boolean aboveLow = false;
		boolean belowHigh = false;

        switch (operatorLow) {
        case INCLUSIVE:
			aboveLow = value >= thresholdLow;
            break;
        case EXCLUSIVE:
			aboveLow = value > thresholdLow;
            break;
        default:
            System.out.println("UNKNOWN OPERATOR LOW: " + operatorLow.name());
            return false;
        }

		if (!aboveLow && inRange) {
			return false;
		}

        switch (operatorHigh) {
        case INCLUSIVE:
			belowHigh = value <= thresholdHigh;
            break;
        case EXCLUSIVE:
			belowHigh = value < thresholdHigh;
            break;
        default:
            System.out.println("UNKNOWN OPERATOR HIGH: " + operatorHigh.name());
            return false;
        }

		return (belowHigh == inRange);
    }

}
