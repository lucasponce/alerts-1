package com.alert.trigger;

import java.util.HashSet;
import java.util.Set;

import com.alert.condition.Condition;

public class TriggerTemplate {

    public enum Match {
        ALL, ANY
    };

    private String name;
    private String description;
    private Match match = Match.ALL;
    private Set<Condition> conditions;

    // private Set<AlertNotificationTemplate> notifiers;
    // private AlertDampeningTemplate dampening = null;
    // private AlertPriority priority = null;
    // private Recovery recovery = null;

    public TriggerTemplate(String name) {
        if (null == name || name.isEmpty()) {
            throw new IllegalArgumentException("Trigger name must be non-empty.");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (null == name || name.isEmpty()) {
            throw new IllegalArgumentException("Trigger name must be non-empty.");
        }

        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Set<Condition> getConditions() {
        if (null == conditions) {
            conditions = new HashSet<Condition>();
        }
        return conditions;
    }

    public void setConditions(Set<Condition> conditions) {
        this.conditions = conditions;
    }

    public void addCondition(Condition condition) {
        if (null == condition) {
            return;
        }
        getConditions().add(condition);
    }

    public void addConditions(Set<Condition> conditions) {
        if (null == conditions) {
            return;
        }
        getConditions().addAll(conditions);
    }

    public void removeCondition(Condition condition) {
        if (null == condition) {
            return;
        }
        getConditions().remove(condition);
    }

}
