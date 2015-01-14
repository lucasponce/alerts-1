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

    private String metricId;
    private Operator operatorLow;
    private Operator operatorHigh;
    private Double thresholdLow;
    private Double thresholdHigh;
    private boolean inRange;

    public ThresholdRangeCondition(String triggerId, String metricId, int conditionSetSize, int conditionSetIndex,
        Operator operatorLow, Operator operatorHigh, Double thresholdLow, Double thresholdHigh, boolean inRange) {

        super(triggerId, conditionSetSize, conditionSetIndex);

        this.metricId = metricId;
        this.operatorLow = operatorLow;
        this.operatorLow = operatorHigh;
        this.thresholdLow = thresholdLow;
        this.thresholdHigh = thresholdHigh;
        this.inRange = inRange;
    }

    public String getMetricId() {
        return metricId;
    }

    public void setMetricId(String metricId) {
        this.metricId = metricId;
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

    static public boolean match(Operator operatorLow, Operator operatorHigh, double thresholdLow, double thresholdHigh,
        boolean inRange, double value) {

        boolean low = false;
        boolean high = false;

        switch (operatorLow) {
        case INCLUSIVE:
            low = value >= thresholdLow;
            break;
        case EXCLUSIVE:
            low = value > thresholdLow;
            break;
        default:
            System.out.println("UNKNOWN OPERATOR LOW: " + operatorLow.name());
            return false;
        }
        switch (operatorHigh) {
        case INCLUSIVE:
            high = value <= thresholdHigh;
            break;
        case EXCLUSIVE:
            high = value < thresholdHigh;
            break;
        default:
            System.out.println("UNKNOWN OPERATOR HIGH: " + operatorHigh.name());
            return false;
        }

        boolean isMatch = low & high;
        return inRange ? isMatch : !isMatch;
    }

}
