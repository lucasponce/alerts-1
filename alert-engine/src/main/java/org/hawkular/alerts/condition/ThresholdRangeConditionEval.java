package org.hawkular.alerts.condition;

public class ThresholdRangeConditionEval extends ConditionEval {

    private ThresholdRangeCondition condition;
    private Double value;

    public ThresholdRangeConditionEval(ThresholdRangeCondition condition, Double value) {
        super(condition.match(value));

        this.condition = condition;
        this.value = value;
    }

    public ThresholdRangeCondition getCondition() {
        return condition;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String getTriggerId() {
        return condition.getTriggerId();
    }

    @Override
    public int getConditionSetSize() {
        return condition.getConditionSetSize();
    }

    @Override
    public int getConditionSetIndex() {
        return condition.getConditionSetIndex();
    }

    @Override
    public String getLog() {
        return condition.getLog(value);
    }

}
