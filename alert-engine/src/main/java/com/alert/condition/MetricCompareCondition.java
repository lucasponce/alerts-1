package com.alert.condition;

/**
 * For conditions like, "X > 80% of Y" or "FreeSpace < 20% of TotalSpace" 
 */
public class MetricCompareCondition extends Condition {

    public enum Operator {
        LT, GT, LTE, GTE
    };

    private String metric1Id;
    private Operator operator;
    private String metric2Id;
    private Double metric2Multiplier;

    public MetricCompareCondition(String triggerId, String metric1Id, Operator operator, String metric2Id,
        int conditionSetSize, int conditionSetIndex, Double metric2Multiplier) {

        super(triggerId, conditionSetSize, conditionSetIndex);

        this.metric1Id = metric1Id;
        this.operator = operator;
        this.metric2Id = metric2Id;
        this.metric2Multiplier = metric2Multiplier;
    }

    public String getMetric1Id() {
        return metric1Id;
    }

    public void setMetric1Id(String metric1Id) {
        this.metric1Id = metric1Id;
    }

    public String getMetric2Id() {
        return metric2Id;
    }

    public void setMetric2Id(String metric2Id) {
        this.metric2Id = metric2Id;
    }

    public Double getMetric2Multiplier() {
        return metric2Multiplier;
    }

    public void setMetric2Multiplier(Double metric2Multiplier) {
        this.metric2Multiplier = metric2Multiplier;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getLog(double metric1Value, double metric2Value) {
        return getTriggerId() + " : " + metric1Value + " " + getOperator().name() + " " + getMetric2Multiplier() + "("
            + metric2Value + ")";
    }

    static public boolean match(Operator operator, double metric2Multiplier, double metric1Value, double metric2Value) {
        double threshold = (metric2Multiplier * metric2Value);
        switch (operator) {
        case LT:
            return metric1Value < threshold;
        case GT:
            return metric1Value > threshold;
        case LTE:
            return metric1Value <= threshold;
        case GTE:
            return metric1Value >= threshold;
        default:
            System.out.println("UNKNOWN OPERATOR: " + operator.name());
            return false;
        }
    }

}
