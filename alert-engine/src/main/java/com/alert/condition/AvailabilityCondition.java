package com.alert.condition;

import com.alert.data.Availability.AvailabilityType;

public class AvailabilityCondition extends Condition {

    public enum Operator {
        RED, YELLOW, GREEN, NOT_GREEN
    };

    private String metricId;
    private Operator operator;

    public AvailabilityCondition(String triggerId, String metricId, int conditionSetSize, int conditionSetIndex,
        Operator operator) {

        super(triggerId, conditionSetSize, conditionSetIndex);

        this.metricId = metricId;
        this.operator = operator;
    }

    public String getMetricId() {
        return metricId;
    }

    public void setMetricId(String metricId) {
        this.metricId = metricId;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getLog(double value) {
        return getTriggerId() + " : " + value + " " + getOperator().name();
    }

    static public boolean match(Operator operator, AvailabilityType value) {
        switch (operator) {
        case RED:
            return value == AvailabilityType.RED;
        case YELLOW:
            return value == AvailabilityType.YELLOW;
        case GREEN:
            return value == AvailabilityType.GREEN;
        case NOT_GREEN:
            return value != AvailabilityType.GREEN;
        default:
            System.out.println("UNKNOWN OPERATOR: " + operator.name());
            return false;
        }
    }

}
