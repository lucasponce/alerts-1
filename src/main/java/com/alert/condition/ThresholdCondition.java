package com.alert.condition;

public class ThresholdCondition extends Condition {

    public enum Operator {
        LT, GT, LTE, GTE
    };

    private Operator operator;
    private Double threshold;

    public ThresholdCondition(String triggerCategory, String triggerId, int conditionSetSize, int conditionSetId,
        int conditionSetIndex, Operator operator, Double threshold) {

        super(triggerCategory, triggerId, conditionSetId, conditionSetSize, conditionSetIndex);

        this.operator = operator;
        this.threshold = threshold;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public String getLog(double value) {
        return getTriggerCategory() + "/" + getTriggerId() + " : " + value + " " + getOperator().name() + " "
            + getThreshold();
    }

    static public boolean match(Operator operator, double threshold, double value) {
        switch (operator) {
        case LT:
            return value < threshold;
        case GT:
            return value > threshold;
        case LTE:
            return value <= threshold;
        case GTE:
            return value >= threshold;
        default:
            System.out.println("UNKNOWN OPERATOR: " + operator.name());
            return false;
        }
    }

}
