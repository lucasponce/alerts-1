package org.hawkular.alerts.condition;

import java.util.List;
import java.util.Set;

public class Alert {
    private String triggerId;
    private Set<ConditionMatch> matches;
    private List<Set<ConditionEval>> evals;

    public Alert(String triggerId, List<Set<ConditionEval>> evals) {
        super();
        this.triggerId = triggerId;
        this.evals = evals;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public Set<ConditionMatch> getMatches() {
        return matches;
    }

    public void setMatches(Set<ConditionMatch> matches) {
        this.matches = matches;
    }

    public void setEvals(List<Set<ConditionEval>> evals) {
        this.evals = evals;
    }

    @Override
    public String toString() {
        return "Alert [triggerId=" + triggerId + ", satisfyingEvals=" + evals + "]";
    }

}
