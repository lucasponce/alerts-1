package com.alert.condition;

public class ConditionMatch extends Condition {

    String log;
    long time;

    public ConditionMatch(Condition condition, String log) {
        this(condition.getTriggerCategory(), condition.getTriggerId(), condition.getConditionSetId(), condition
            .getConditionSetSize(), condition.getConditionSetIndex(), log);
    }

    public ConditionMatch(String triggerCategory, String triggerId, int conditionSetId, int conditionSetSize,
        int conditionSetIndex, String log) {

        super(triggerCategory, triggerId, conditionSetId, conditionSetSize, conditionSetIndex);
        this.time = System.currentTimeMillis();
        this.log = log;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
