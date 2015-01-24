package org.hawkular.alerts.condition;

public abstract class ConditionEval {

	boolean match;
	long time;

    public ConditionEval(boolean match) {
        this.match = match;
		this.time = System.currentTimeMillis();
	}

	public boolean isMatch() {
		return match;
	}

	public void setMatch(boolean match) {
		this.match = match;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

    @Override
    public String toString() {
        return "ConditionEval [time=" + time + ", match=" + match + " : " + getLog() + "]";
    }

    public abstract String getTriggerId();

    public abstract int getConditionSetSize();

    public abstract int getConditionSetIndex();

    public abstract String getLog();
}
