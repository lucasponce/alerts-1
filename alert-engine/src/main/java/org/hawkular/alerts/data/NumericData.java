package org.hawkular.alerts.data;

public class NumericData extends Data {

    private Double value;

	public NumericData(String id, Double value) {
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
		return "Data [value=" + value + ", " + getId() + "]";
    }

}
