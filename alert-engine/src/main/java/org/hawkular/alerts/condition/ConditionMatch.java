package org.hawkular.alerts.condition;

public class ConditionMatch extends Condition {

    String log;
    long time;

    public ConditionMatch(Condition condition, String log) {
        this(condition.getTriggerId(), condition.getConditionSetSize(), condition.getConditionSetIndex(), log);
    }

    public ConditionMatch(String triggerId, int conditionSetSize, int conditionSetIndex, String log) {

        super(triggerId, conditionSetSize, conditionSetIndex);
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

    @Override
    public String toString() {
        return "ConditionMatch [log=" + log + ", time=" + time + "]";
    }

}
