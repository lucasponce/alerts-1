package org.hawkular.alerts.condition;

public abstract class Condition {

    private String triggerId; // the owning trigger

    private int conditionSetSize; // e.g. 2 [conditions]
    private int conditionSetIndex; // e.g. 1 [of 2 conditions]

    public Condition(String triggerId, int conditionSetSize, int conditionSetIndex) {

        this.triggerId = triggerId;
        this.conditionSetSize = conditionSetSize;
        this.conditionSetIndex = conditionSetIndex;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public int getConditionSetSize() {
        return conditionSetSize;
    }

    public void setConditionSetSize(int conditionSetSize) {
        this.conditionSetSize = conditionSetSize;
    }

    public int getConditionSetIndex() {
        return conditionSetIndex;
    }

    public void setConditionSetIndex(int conditionSetIndex) {
        this.conditionSetIndex = conditionSetIndex;
    }

}
