package org.hawkular.alerts.condition;

public class CompareConditionEval extends ConditionEval {

    private CompareCondition condition;
    private Double value1;
    private Double value2;

    public CompareConditionEval(CompareCondition condition, Double value1, Double value2) {
        super(condition.match(value1, value2));

        this.condition = condition;
        this.value1 = value1;
        this.value2 = value2;
    }

    public CompareCondition getCondition() {
        return condition;
    }

    public Double getValue1() {
        return value1;
    }

    public Double getValue2() {
        return value2;
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
        return condition.getLog(value1, value2);
    }

}
