package com.alert.condition;

import java.util.Set;

public class Alert {
    private String triggerId;
    private Set<ConditionMatch> matches;

    public Alert(String triggerId, Set<ConditionMatch> matches) {
        super();
        this.triggerId = triggerId;
        this.matches = matches;
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

}
