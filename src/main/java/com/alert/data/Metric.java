package com.alert.data;

public class Metric extends Data {

	private Double value;

	public Metric(String triggerCategory, String triggerId, Double value) {
		super(triggerCategory, triggerId);

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
		return "Metric [value=" + value + ", " + getTriggerCategory() + "/"
				+ getTriggerId()
				+ "]";
	}

}
