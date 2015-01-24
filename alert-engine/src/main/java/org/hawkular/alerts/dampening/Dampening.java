package org.hawkular.alerts.dampening;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hawkular.alerts.condition.ConditionEval;

public class Dampening {

    public enum Type {
        STRICT, RELAXED_COUNT, RELAXED_TIME
    };

    private String triggerId;
    private Type type;
    private int evalTrueSetting;
    private int evalTotalSetting;
    private long evalTimeSetting;

    private int numTrueEvals;
    private int numEvals;
    private long trueEvalsStartTime;
    private boolean satisfied;
    private List<Set<ConditionEval>> satisfyingEvals = new ArrayList<Set<ConditionEval>>();

    public Dampening(String triggerId, Type type, int evalTrueSetting,
            int evalTotalSetting, long evalTimeSetting) {
        super();
        this.triggerId = triggerId;
        this.type = type;
        this.evalTrueSetting = evalTrueSetting;
        this.evalTotalSetting = evalTotalSetting;
        this.evalTimeSetting = evalTimeSetting;

        reset();
    }

    public String getTriggerId() {
        return triggerId;
    }

    public int getNumTrueEvals() {
        return numTrueEvals;
    }

    public void setNumTrueEvals(int numTrueEvals) {
        this.numTrueEvals = numTrueEvals;
    }

    public long getTrueEvalsStartTime() {
        return trueEvalsStartTime;
    }

    public void setTrueEvalsStartTime(long trueEvalsStartTime) {
        this.trueEvalsStartTime = trueEvalsStartTime;
    }

    public int getNumEvals() {
        return numEvals;
    }

    public void setNumEvals(int numEvals) {
        this.numEvals = numEvals;
    }

    public Type getType() {
        return type;
    }

    public int getEvalTrueSetting() {
        return evalTrueSetting;
    }

    public int getEvalTotalSetting() {
        return evalTotalSetting;
    }

    public long getEvalTimeSetting() {
        return evalTimeSetting;
    }

    public boolean isSatisfied() {
        return satisfied;
    }

    public List<Set<ConditionEval>> getSatisfyingEvals() {
        return satisfyingEvals;
    }

    public void addSatisfyingEvals(Set<ConditionEval> satisfyingEvals) {
        this.satisfyingEvals.add(satisfyingEvals);
    }

    public void addSatisfyingEvals(ConditionEval... satisfyingEvals) {
        this.satisfyingEvals.add(new HashSet<ConditionEval>(Arrays.asList(satisfyingEvals)));
    }

    public void perform(ConditionEval... conditionEvals) {
        boolean trueEval = true;
        for (ConditionEval ce : conditionEvals) {
            if (!ce.isMatch()) {
                trueEval = false;
                break;
            }
        }

        // If we had previously started our time and now have exceeded our time limit then we must start over
        long now = System.currentTimeMillis();
        if (type == Type.RELAXED_TIME && trueEvalsStartTime != 0L) {
            if ((now - trueEvalsStartTime) > evalTimeSetting) {
                reset();
            }
        }

        numEvals += 1;
        if (trueEval) {
            numTrueEvals += 1;
            addSatisfyingEvals(conditionEvals);

            switch (type) {
                case STRICT:
                case RELAXED_COUNT:
                    if (numTrueEvals == evalTrueSetting) {
                        satisfied = true;
                    }
                    break;

                case RELAXED_TIME:
                    if (trueEvalsStartTime == 0L) {
                        trueEvalsStartTime = now;
                    }
                    if ((numTrueEvals == evalTrueSetting) && ((now - trueEvalsStartTime) < evalTimeSetting)) {
                        satisfied = true;
                    }
                    break;
            }
        } else {
            switch (type) {
                case STRICT:
                    reset();
                    break;
                case RELAXED_COUNT:
                    int numNeeded = evalTrueSetting - numTrueEvals;
                    int chancesLeft = evalTotalSetting - numEvals;
                    if (numNeeded > chancesLeft) {
                        reset();
                    }
                    break;
                case RELAXED_TIME:
                    break;
            }
        }
    }

    public void reset() {
        this.numTrueEvals = 0;
        this.numEvals = 0;
        this.trueEvalsStartTime = 0L;
        this.satisfied = false;
        this.satisfyingEvals.clear();
    }

    public String getLog() {
        StringBuilder sb = new StringBuilder("[" + triggerId + ", numTrueEvals="
                + numTrueEvals + ", numEvals=" + numEvals + ", trueEvalsStartTime=" + trueEvalsStartTime
                + ", satisfied=" + satisfied);
        if (satisfied) {
            for (Set<ConditionEval> ces : satisfyingEvals) {
                sb.append("\n\t[");
                String space = "";
                for (ConditionEval ce : ces) {
                    sb.append(space);
                    sb.append("[");
                    sb.append(ce.getLog());
                    sb.append("]");
                    space = " ";
                }
                sb.append("]");

            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Dampening [triggerId=" + triggerId + ", type=" + type + ", evalTrueSetting=" + evalTrueSetting
                + ", evalTotalSetting=" + evalTotalSetting + ", evalTimeSetting=" + evalTimeSetting + ", numTrueEvals="
                + numTrueEvals + ", numEvals=" + numEvals + ", trueEvalsStartTime=" + trueEvalsStartTime
                + ", satisfied=" + satisfied + ", satisfyingEvals=" + satisfyingEvals + "]";
    }

}