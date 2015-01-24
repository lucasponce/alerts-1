package org.hawkular.alerts.condition;

public class StringConditionEval extends ConditionEval {

    private StringCondition condition;
    private String value;

    public StringConditionEval(StringCondition condition, String value) {
        super(condition.match(value));

        this.condition = condition;
        this.value = value;
    }

    public StringCondition getCondition() {
        return condition;
    }

    public String getValue() {
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
