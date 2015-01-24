package org.hawkular.alerts.condition;

import org.hawkular.alerts.data.Availability.AvailabilityType;

public class AvailabilityConditionEval extends ConditionEval {

    private AvailabilityCondition condition;
    private AvailabilityType value;

    public AvailabilityConditionEval(AvailabilityCondition condition, AvailabilityType value) {
        super(condition.match(value));

        this.condition = condition;
        this.value = value;
    }

    public AvailabilityCondition getCondition() {
        return condition;
    }

    public AvailabilityType getValue() {
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
