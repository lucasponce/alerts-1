package com.alert.condition;

public abstract class Condition {

    private String triggerCategory; // Anything such that category + id is unique among all conditions
    private String triggerId; // e.g. Metric|Metric-12

    private int conditionSetId; // e.g. 
    private int conditionSetSize; // e.g. 2 [conditions]
    private int conditionSetIndex; // e.g. 1 [of 2 conditions]

    public Condition(String triggerCategory, String triggerId, int conditionSetId, int conditionSetSize,
        int conditionSetIndex) {
        super();
        this.triggerCategory = triggerCategory;
        this.triggerId = triggerId;
        this.conditionSetId = conditionSetId;
        this.conditionSetSize = conditionSetSize;
        this.conditionSetIndex = conditionSetIndex;
    }

    public String getTriggerCategory() {
        return triggerCategory;
    }

    public void setTriggerCategory(String triggerCategory) {
        this.triggerCategory = triggerCategory;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public int getConditionSetId() {
        return conditionSetId;
    }

    public void setConditionSetId(int conditionSetId) {
        this.conditionSetId = conditionSetId;
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
