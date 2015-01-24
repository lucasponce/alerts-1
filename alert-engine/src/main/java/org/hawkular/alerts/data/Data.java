package org.hawkular.alerts.data;

public class Data<T extends Object> {

    private String id; // e.g. Metric-12
    private T value;

    public Data(String id, T value) {
        super();
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Data [" + id + " = " + value + "]";
    }

}
