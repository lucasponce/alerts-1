package org.hawkular.alerts.data;

public class Metric extends Data {

    private Double value;

    public Metric(String id, Double value) {
        super(id);

        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Metric [value=" + value + ", " + getId() + "]";
    }

}
